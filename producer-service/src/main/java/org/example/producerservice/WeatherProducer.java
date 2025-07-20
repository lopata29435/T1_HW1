package org.example.producerservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import models.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class WeatherProducer {
    private static final List<String> CITIES = List.of("Moscow", "St.Petersburg", "Magadan", "Novosibirsk", "Anapa");
    private static final List<String> CONDITIONS = List.of("sunny", "moody", "rainy", "damp", "thunderstruck", "windy");
    private static final String TOPIC_NAME = "weather";
    private static final int MAX_TEMP = 40;
    private static final int MIN_TEMP = -40;
    private static int offset = 0;
    public static void main(String[] args) throws Exception {
        var random = new Random();
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try (var producer = createProducer()) {
            while (true) {
                var weather = generateWeather(random, offset++);
                sendWeather(producer, mapper, weather);
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            System.err.println("Producer error: " + e.getMessage());
            e.printStackTrace();
        }

    }
    private static KafkaProducer<String, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }
    private static WeatherModel generateWeather(Random random, int offset) {
        String city = CITIES.get(random.nextInt(CITIES.size()));
        String condition = CONDITIONS.get(random.nextInt(CONDITIONS.size()));
        int temperature = random.nextInt(MAX_TEMP - MIN_TEMP) + MIN_TEMP;
        return new WeatherModelBuilder()
                .addCity(city)
                .addDate(LocalDate.now().plusDays(offset))
                .addTemperature(temperature)
                .addCondition(condition)
                .build();
    }
    private static void sendWeather(KafkaProducer<String, String> producer, ObjectMapper mapper, WeatherModel weather) {
        try {
            String json = mapper.writeValueAsString(weather);
            System.out.println("Sending JSON: " + json);
            producer.send(new ProducerRecord<>(TOPIC_NAME, json));
        } catch (Exception e) {
            System.err.println("Serialization or send error: " + e.getMessage());
        }
    }
}
