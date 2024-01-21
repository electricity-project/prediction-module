package com.electricity.project.predictionmodule.weather;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.time.LocalDateTime;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutableForecastHourWeatherDTO.class)
@JsonDeserialize(as = ImmutableForecastHourWeatherDTO.class)
public interface ForecastHourWeatherDTO {
    static ImmutableForecastHourWeatherDTO.Builder builder() {
        return ImmutableForecastHourWeatherDTO.builder();
    }

    @JsonProperty(value = "time", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime getTime();

    @JsonProperty(value = "temp_c", required = true)
    double getCelsiusTemperature();

    @JsonProperty(value = "wind_kph", required = true)
    double getKphWindSpeed();

    @JsonProperty(value = "pressure_mb", required = true)
    double getMbPressure();

    @JsonProperty(value = "humidity", required = true)
    int getPercentageHumidity();

    @JsonProperty(value = "cloud", required = true)
    int getPercentageCloudy();
}
