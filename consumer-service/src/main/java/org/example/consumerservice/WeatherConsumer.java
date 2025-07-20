package org.example.consumerservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import models.WeatherModel;
import org.example.consumerservice.analyzer.*;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class WeatherConsumer {
    private static final String TOPIC = "weather";
    private static final int ANALYZE_INTERVAL = 5;
    private static final int CLEAR_INTERVAL = 10;

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        WeatherRulesEngine weatherRulesEngine = new WeatherRulesEngine();
        WeatherAnalyzer weatherAnalyzer = new WeatherAnalyzer(weatherRulesEngine, weatherRulesEngine, weatherRulesEngine, weatherRulesEngine); //тупое решение, но у меня просто была задача потестить максимально большое количество подходов

        try (var consumer = createConsumer()) {
            consumer.subscribe(Collections.singletonList(TOPIC));
            List<WeatherModel> weatherList = new ArrayList<>();
            int iterationCount = 0;


            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, String> record : records) {
                    WeatherModel weather = processRecord(record, mapper);
                    if (weather != null) {
                        weatherList.add(weather);
                    }
                    iterationCount++;

                    if (iterationCount % ANALYZE_INTERVAL == 0) {
                        String report = weatherAnalyzer.analyzeWeather(weatherList);
                        System.out.println(report);
                    }

                    if (iterationCount % CLEAR_INTERVAL == 0) {
                        weatherList.clear();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Consumer error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static KafkaConsumer<String, String> createConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "weather-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new KafkaConsumer<>(props);
    }
    private static WeatherModel processRecord(ConsumerRecord<String, String> record, ObjectMapper mapper) {
        try {
            WeatherModel weather = mapper.readValue(record.value(), WeatherModel.class);
            System.out.println("Received: " + weather);
            return weather;
        } catch (Exception e) {
            System.err.println("Deserialization error: " + e.getMessage());
            System.err.println("Message: " + record.value());
            return null;
        }
    }
}

