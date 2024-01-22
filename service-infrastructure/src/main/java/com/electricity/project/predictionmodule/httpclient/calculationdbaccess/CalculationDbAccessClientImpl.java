package com.electricity.project.predictionmodule.httpclient.calculationdbaccess;

import com.electricity.project.predictionmodule.powerstation.PowerStationDTO;
import com.electricity.project.predictionmodule.realtimecalculations.PowerStationFilterDTO;
import com.electricity.project.predictionmodule.weather.ForecastHourWeatherDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Component
public class CalculationDbAccessClientImpl implements CalculationDbAccessClient {
    private final WebClient client;

    public CalculationDbAccessClientImpl(
            @Value("${calculations.db.access.url}")
            String baseUrl
    ) {
        HttpClient httpClient = HttpClient.create().responseTimeout(Duration.ofSeconds(10));
        client = WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Override
    public List<PowerStationDTO> getFilteredPowerStations(PowerStationFilterDTO powerStationFilter) {
        return  client.post()
                .uri("/power-station/all_filter_list")
                .contentType(MediaType.APPLICATION_JSON)
                .body(powerStationFilter, PowerStationFilterDTO.class)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .bodyToFlux(PowerStationDTO.class)
                .collectList()
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(10)))
                .block();
    }

    @Override
    public List<ForecastHourWeatherDTO> getWeatherForecastFor(LocalDate date) {
        return  client.get()
                .uri("/weather-api/forecast", uriBuilder -> uriBuilder
                        .queryParam("date", date.toString())
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .bodyToFlux(ForecastHourWeatherDTO.class)
                .collectList()
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(10)))
                .block();
    }
}
