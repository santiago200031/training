package org.finance.scheduledTasks;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.finance.services.MLTrainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@ApplicationScoped
public class MLTrainTask {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Inject
    MLTrainService trainService;

    @Scheduled(every = "45s", concurrentExecution = Scheduled.ConcurrentExecution.SKIP)
    public void linearRegressionMLSparkTask() {
        long timeToPredict = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli();
        float predictedValue = trainService.performLinearRegressionPrediction(timeToPredict);
        LOGGER.info("PredictedValue: {}", predictedValue);
    }
}