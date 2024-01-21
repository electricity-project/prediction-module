package com.electricity.project.predictionmodule.domains.prediction.control;

import com.electricity.project.predictionmodule.httpclient.calculationdbaccess.CalculationDbAccessClient;
import com.electricity.project.predictionmodule.httpclient.realtimecalculations.RealTimeCalculationsClient;
import com.electricity.project.predictionmodule.powerstation.PowerStationDTO;
import com.electricity.project.predictionmodule.powerstation.PowerStationState;
import com.electricity.project.predictionmodule.prediction.PredictionDTO;
import com.electricity.project.predictionmodule.prediction.PredictionResultDTO;
import com.electricity.project.predictionmodule.realtimecalculations.OptimizationDTO;
import com.electricity.project.predictionmodule.realtimecalculations.OptimizeProductionDTO;
import com.electricity.project.predictionmodule.realtimecalculations.PowerProductionDTO;
import com.electricity.project.predictionmodule.realtimecalculations.PowerStationFilterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PredictionService {
    private final CalculationDbAccessClient calculationDbAccessClient;
    private final RealTimeCalculationsClient realTimeCalculationsClient;

    public List<PredictionResultDTO> makePredictionFor(PredictionDTO predictionDto) {
        List<PredictionResultDTO> predictionResult = new LinkedList<>();
        for (LocalDateTime date = predictionDto.getPredictionDate().atStartOfDay();
             date.isBefore(predictionDto.getPredictionDate().atStartOfDay());
             date = date.plusHours(1)
        ) {
            predictionResult.addLast(makePredictionFor(date, predictionDto.getPredictionStates()));
        }
        return predictionResult;
    }

    private PredictionResultDTO makePredictionFor(LocalDateTime date, Set<PowerStationState> predictionStates) {
        List<PowerStationDTO> powerStations = getAllPowerStations();
        List<PowerProductionDTO> powerProductions = calculatePowerProductions(powerStations, date);
        OptimizationDTO optimizationResult = optimize(powerProductions, predictionStates);
        powerProductions = filterByOnPowerStations(powerProductions, optimizationResult);
        return PredictionResultDTO.builder()
                .powerProduction(sumPowerProduction(powerProductions))
                .runningPowerStationsNumber(powerProductions.size())
                .build();
    }

    private static double sumPowerProduction(List<PowerProductionDTO> powerProductions) {
        return powerProductions.stream()
                .map(PowerProductionDTO::getProducedPower)
                .mapToDouble(Long::doubleValue)
                .sum();
    }

    private static List<PowerProductionDTO> filterByOnPowerStations(List<PowerProductionDTO> powerProductions, OptimizationDTO optimizationResult) {
        return powerProductions.stream()
                .filter(powerProduction -> !optimizationResult.getIpsToTurnOff().contains(powerProduction.getIpv6Address()))
                .toList();
    }

    private List<PowerProductionDTO> calculatePowerProductions(List<PowerStationDTO> powerStations, LocalDateTime date) {
        // TODO
        return Collections.singletonList(PowerProductionDTO.builder().build());
    }

    private List<PowerStationDTO> getAllPowerStations() {
        // TODO
        return Collections.emptyList();
    }

    private OptimizationDTO optimize(List<PowerProductionDTO> powerProductions, Set<PowerStationState> powerStationStates) {
        PowerStationFilterDTO powerStationFilter = PowerStationFilterDTO.builder()
                .statePatterns(powerStationStates)
                .build();
        return realTimeCalculationsClient.optimizePowerStationsState(OptimizeProductionDTO.builder()
                .powerProductions(powerProductions)
                .powerStationFilter(powerStationFilter)
                .build());
    }
}
