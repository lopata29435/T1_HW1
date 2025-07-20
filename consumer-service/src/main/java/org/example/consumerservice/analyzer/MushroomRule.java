package org.example.consumerservice.analyzer;

import models.WeatherModel;

import java.util.List;

public interface MushroomRule {
    Boolean isGoodForMushrooms(List<WeatherModel> data);
}
