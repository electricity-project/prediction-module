package com.electricity.project.predictionmodule.httpclient.realtimecalculations;

import com.electricity.project.predictionmodule.realtimecalculations.OptimizationDTO;
import com.electricity.project.predictionmodule.realtimecalculations.OptimizeProductionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Component
public class RealTimeCalculationsClientImpl implements RealTimeCalculationsClient {
    private final WebClient client;

    public RealTimeCalculationsClientImpl(
            @Value("${central.module.url}${real.time.calculations.mapping.url}/api")
            String baseUrl
    ) {
        HttpClient httpClient = HttpClient.create().responseTimeout(Duration.ofSeconds(1000));
        client = WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Override
    public OptimizationDTO optimizePowerStationsState(OptimizeProductionDTO optimizeProduction) {
        return  client.post()
                .uri("/optimize_production")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(optimizeProduction), OptimizeProductionDTO.class)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .bodyToMono(OptimizationDTO.class)
                .block();
    }
}
