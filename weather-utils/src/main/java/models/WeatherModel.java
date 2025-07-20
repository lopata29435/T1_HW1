package models;

import java.time.LocalDate;

public class WeatherModel {
    final private String city;
    final private LocalDate date;
    final private int temperature;
    final private String condition;

    public WeatherModel(){
        this.city = null;
        this.date = null;
        this.condition = null;
        this.temperature = 0;
    }
    public WeatherModel(String city, LocalDate date, int temperature, String condition) {
        this.city = city;
        this.date = date;
        this.temperature = temperature;
        this.condition = condition;
    }
    public String getCity() {
        return city;
    }
    public LocalDate getDate() {
        return date;
    }
    public int getTemperature() {
        return temperature;
    }
    public String getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return "{city: " + city + "; date: " + date + ";  temperature: " + temperature + "; condition: " + condition + '}';
    }
}
