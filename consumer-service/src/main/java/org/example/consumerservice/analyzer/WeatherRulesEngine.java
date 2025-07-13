package org.example.consumerservice.analyzer;

import models.WeatherModel;

import java.util.List;

public class WeatherRulesEngine implements IceCreamMeltdownRule, ZeusAngerRule, MushroomRule, TeethChatteringRule {
    @Override
    public Boolean isIceCreamMelting(List<WeatherModel> data) {
        return data.stream()
                .mapToInt(WeatherModel::getTemperature)
                .average()
                .orElse(0) > 28;
    }
    @Override
    public Boolean isTeethChattering(List<WeatherModel> data) {
        return data.stream().anyMatch(w ->
                w.getTemperature() < -15 &&
                        w.getCondition().toLowerCase().contains("windy")
        );
    }
    @Override
    public Boolean isZeusAngry(List<WeatherModel> data) {
        return data.stream().anyMatch(w ->
                w.getCondition().toLowerCase().contains("thunderstruck")  &&
                                w.getTemperature() > 20
        );
    }
    @Override
    public Boolean isGoodForMushrooms(List<WeatherModel> data) {
        return data.stream().anyMatch(w ->
                w.getTemperature() >= 12 && w.getTemperature() <= 22 &&
                        (w.getCondition().toLowerCase().contains("rainy") ||
                                w.getCondition().toLowerCase().contains("damp"))
        );
    }
}
