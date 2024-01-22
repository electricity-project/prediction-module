package com.electricity.project.predictionmodule.prediction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.time.LocalDate;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutablePredictionDTO.class)
@JsonDeserialize(as = ImmutablePredictionDTO.class)
public interface PredictionDTO {
    static ImmutablePredictionDTO.Builder builder() {
        return ImmutablePredictionDTO.builder();
    }

    @JsonProperty(value = "predictionDate", required = true)
    LocalDate getPredictionDate();

    @JsonProperty(value = "powerStationsScope", required = true)
    PowerStationsScope getPowerStationsScope();
}
