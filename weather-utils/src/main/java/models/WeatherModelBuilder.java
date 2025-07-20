package models;

import java.time.LocalDate;

public class WeatherModelBuilder {
    private String city;
    private LocalDate date;
    private int temperature;
    private String condition;

    WeatherModel weatherModel;

    public WeatherModelBuilder() {

    }
    public WeatherModelBuilder addCity(String city) {
        this.city = city;
        return this;
    }
    public WeatherModelBuilder addDate(LocalDate date) {
        this.date = date;
        return this;
    }
    public WeatherModelBuilder addTemperature(int temperature) {
        this.temperature = temperature;
        return this;
    }
    public WeatherModelBuilder addCondition(String condition) {
        this.condition = condition;
        return this;
    }
    public WeatherModel build() {
        return new WeatherModel(city, date, temperature, condition);
    }
}
