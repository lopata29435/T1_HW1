package org.example.consumerservice.analyzer;

import models.WeatherModel;

import java.util.List;

public interface ZeusAngerRule {
    Boolean isZeusAngry(List<WeatherModel> data);
}
