package com.electricity.project.predictionmodule.prediction;

import com.electricity.project.predictionmodule.powerstation.SolarPanelDTO;
import com.electricity.project.predictionmodule.weather.ForecastHourWeatherDTO;
import org.springframework.stereotype.Component;

@Component
public class SolarPanelPredictor implements PowerStationPredictor<SolarPanelDTO> {

    @Override
    public long makePrediction(SolarPanelDTO powerStation, ForecastHourWeatherDTO weatherForecast) {
        // TODO
        return (long) (Math.random() * 1000);
    }
}
