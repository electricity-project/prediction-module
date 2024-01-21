package com.electricity.project.predictionmodule.prediction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutablePredictionResultDTO.class)
@JsonDeserialize(as = ImmutablePredictionResultDTO.class)
public interface PredictionResultDTO {
    static ImmutablePredictionResultDTO.Builder builder() {
        return ImmutablePredictionResultDTO.builder();
    }

    @JsonProperty(value = "powerProduction", required = true)
    double getPowerProduction();

    @JsonProperty(value = "runningPowerStationsNumber", required = true)
    int getRunningPowerStationsNumber();
}
