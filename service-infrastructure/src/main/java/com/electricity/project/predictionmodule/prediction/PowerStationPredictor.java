package com.electricity.project.predictionmodule.prediction;

import com.electricity.project.predictionmodule.powerstation.PowerStationDTO;
import com.electricity.project.predictionmodule.weather.ForecastHourWeatherDTO;

public interface PowerStationPredictor<T extends PowerStationDTO> {
    long makePrediction(T powerStation, ForecastHourWeatherDTO weatherForecast);
}
