package com.electricity.project.predictionmodule.httpclient.realtimecalculations;

import com.electricity.project.predictionmodule.realtimecalculations.OptimizationDTO;
import com.electricity.project.predictionmodule.realtimecalculations.OptimizeProductionDTO;

public interface RealTimeCalculationsClient {

    OptimizationDTO optimizePowerStationsState(OptimizeProductionDTO optimizeProduction);
}
