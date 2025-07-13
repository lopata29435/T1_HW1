package org.example.consumerservice.analyzer;

import models.WeatherModel;

import java.util.List;

public interface IceCreamMeltdownRule {
    Boolean isIceCreamMelting(List<WeatherModel> data);
}
