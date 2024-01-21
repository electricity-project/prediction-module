package com.electricity.project.predictionmodule.httpclient.calculationdbaccess;

import com.electricity.project.predictionmodule.powerstation.PowerStationDTO;
import com.electricity.project.predictionmodule.realtimecalculations.PowerStationFilterDTO;
import com.electricity.project.predictionmodule.weather.ForecastHourWeatherDTO;

import java.time.LocalDate;
import java.util.List;

public interface CalculationDbAccessClient {
    List<PowerStationDTO> getFilteredPowerStations(PowerStationFilterDTO powerStationFilter);
    List<ForecastHourWeatherDTO> getWeatherForecastFor(LocalDate date);
}
