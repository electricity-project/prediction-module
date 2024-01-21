package com.electricity.project.predictionmodule.domains.prediction.control;

import com.electricity.project.predictionmodule.httpclient.calculationdbaccess.CalculationDbAccessClient;
import com.electricity.project.predictionmodule.httpclient.realtimecalculations.RealTimeCalculationsClient;
import com.electricity.project.predictionmodule.prediction.PredictionDTO;
import com.electricity.project.predictionmodule.prediction.PredictionResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PredictionService {
    private final CalculationDbAccessClient calculationDbAccessClient;
    private final RealTimeCalculationsClient realTimeCalculationsClient;

    public List<PredictionResultDTO> makePredictionFor(PredictionDTO predictionDto) {
        // TODO implement calculations
        return Collections.nCopies(24, PredictionResultDTO.builder()
                .powerProduction(12345)
                .runningPowerStationsNumber(100)
                .build());
    }
}
