package org.example.consumerservice.analyzer;

import models.WeatherModel;

import java.util.List;

public interface TeethChatteringRule {
    Boolean isTeethChattering(List<WeatherModel> data);
}
