package com.electricity.project.predictionmodule.domains.prediction.control;

import com.electricity.project.predictionmodule.domains.prediction.control.exception.DateOutOfPredictionRangeException;
import com.electricity.project.predictionmodule.domains.prediction.control.exception.UnknownPowerStation;
import com.electricity.project.predictionmodule.httpclient.calculationdbaccess.CalculationDbAccessClient;
import com.electricity.project.predictionmodule.httpclient.realtimecalculations.RealTimeCalculationsClient;
import com.electricity.project.predictionmodule.powerstation.PowerStationDTO;
import com.electricity.project.predictionmodule.powerstation.PowerStationState;
import com.electricity.project.predictionmodule.powerstation.SolarPanelDTO;
import com.electricity.project.predictionmodule.powerstation.WindTurbineDTO;
import com.electricity.project.predictionmodule.prediction.PowerStationPredictor;
import com.electricity.project.predictionmodule.prediction.PredictionDTO;
import com.electricity.project.predictionmodule.prediction.PredictionResultDTO;
import com.electricity.project.predictionmodule.realtimecalculations.OptimizationDTO;
import com.electricity.project.predictionmodule.realtimecalculations.OptimizeProductionDTO;
import com.electricity.project.predictionmodule.realtimecalculations.PowerProductionDTO;
import com.electricity.project.predictionmodule.realtimecalculations.PowerStationFilterDTO;
import com.electricity.project.predictionmodule.weather.ForecastHourWeatherDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PredictionService {
    private final CalculationDbAccessClient calculationDbAccessClient;
    private final RealTimeCalculationsClient realTimeCalculationsClient;
    private final PowerStationPredictor<WindTurbineDTO> windTurbinePredictor;
    private final PowerStationPredictor<SolarPanelDTO> solarPanelPredictor;

    public List<PredictionResultDTO> makePredictionFor(PredictionDTO predictionDto) {
        LocalDate predictionDate = predictionDto.getPredictionDate();
        if (dateNotInPredictionRange(predictionDate)) {
            throw new DateOutOfPredictionRangeException(predictionDate);
        }
        List<ForecastHourWeatherDTO> hoursWeatherForecast = calculationDbAccessClient.getWeatherForecastFor(predictionDate);
        List<PredictionResultDTO> predictionResult = new LinkedList<>();
        for (ForecastHourWeatherDTO weatherForecast : hoursWeatherForecast) {
            predictionResult.addLast(makePredictionFor(predictionDto.getPowerStationsScope().getStates(), weatherForecast));
        }
        return predictionResult;
    }

    private boolean dateNotInPredictionRange(LocalDate predictionDate) {
        LocalDate now = LocalDate.now();
        return predictionDate.isBefore(now.plusDays(16)) && predictionDate.isAfter(now);
    }

    private PredictionResultDTO makePredictionFor(Set<PowerStationState> predictionStates, ForecastHourWeatherDTO weatherForecast) {
        List<PowerProductionDTO> powerProductions = calculatePowerStationsPowerProduction(getAllPowerStations(), weatherForecast);
        OptimizationDTO optimizationResult = optimize(powerProductions, predictionStates);
        powerProductions = filterByTurnOnPowerStations(powerProductions, optimizationResult);
        return PredictionResultDTO.builder()
                .powerProduction(sumPowerProduction(powerProductions))
                .runningPowerStationsNumber(powerProductions.size())
                .timestamp(weatherForecast.getTime())
                .build();
    }

    private List<PowerProductionDTO> calculatePowerStationsPowerProduction(List<PowerStationDTO> powerStations, ForecastHourWeatherDTO weatherForecast) {
        return powerStations.stream()
                .map(powerStation -> calculatePowerStationPowerProduction(powerStation, weatherForecast))
                .toList();
    }

    private PowerProductionDTO calculatePowerStationPowerProduction(PowerStationDTO powerStation, ForecastHourWeatherDTO weatherForecast) {
        long producedPower = switch (powerStation) {
            case WindTurbineDTO windTurbine -> windTurbinePredictor.makePrediction(windTurbine, weatherForecast);
            case SolarPanelDTO solarPanel -> solarPanelPredictor.makePrediction(solarPanel, weatherForecast);
            default -> throw new UnknownPowerStation(powerStation);
        };
        return PowerProductionDTO.builder()
                .id(powerStation.getId())
                .state(powerStation.getState())
                .ipv6Address(powerStation.getIpv6Address())
                .timestamp(weatherForecast.getTime())
                .producedPower(producedPower)
                .build();
    }

    private List<PowerStationDTO> getAllPowerStations() {
        return calculationDbAccessClient.getFilteredPowerStations(PowerStationFilterDTO.builder().build());
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

    private static List<PowerProductionDTO> filterByTurnOnPowerStations(List<PowerProductionDTO> powerProductions, OptimizationDTO optimizationResult) {
        return powerProductions.stream()
                .filter(powerProduction -> !optimizationResult.getIpsToTurnOff().contains(powerProduction.getIpv6Address()))
                .toList();
    }

    private static double sumPowerProduction(List<PowerProductionDTO> powerProductions) {
        return powerProductions.stream()
                .map(PowerProductionDTO::getProducedPower)
                .mapToDouble(Long::doubleValue)
                .sum();
    }
}
