package org.example.consumerservice.analyzer;

import models.WeatherModel;

import java.util.List;

public class WeatherAnalyzer {
    private final MushroomRule mushroomRule;
    private final ZeusAngerRule zeusAngerRule;
    private final TeethChatteringRule teethChatteringRule;
    private final IceCreamMeltdownRule iceCreamMeltdownRule;

    public WeatherAnalyzer(
            MushroomRule mushroomRule,
            ZeusAngerRule zeusAngerRule,
            TeethChatteringRule teethChatteringRule,
            IceCreamMeltdownRule iceCreamMeltdownRule) {

        this.mushroomRule = mushroomRule;
        this.zeusAngerRule = zeusAngerRule;
        this.teethChatteringRule = teethChatteringRule;
        this.iceCreamMeltdownRule = iceCreamMeltdownRule;
    }

    public String analyzeWeather(List<WeatherModel> data) {
        WeatherRulesEngine engine = new WeatherRulesEngine();
        StringBuilder result = new StringBuilder("Weather analysis:\n");

        if (mushroomRule.isGoodForMushrooms(data)) {
            result.append("The weather's nice enough to pick a couple of fly agaric.\n");
        }

        if (iceCreamMeltdownRule.isIceCreamMelting(data)) {
            result.append("IceCream is melting! RUN!\n");
        }

        if (zeusAngerRule.isZeusAngry(data)) {
            result.append("Zeus began chopping down the Yggdrasil!\n");
        }

        if (teethChatteringRule.isTeethChattering(data)) {
            result.append("Teeth chattering, too cold.\n");
        }

        if (result.toString().equals("Weather analysis:\n")) {
            result.append("Nothing interesting :(\n");
        }

        return result.toString();
    }
}
