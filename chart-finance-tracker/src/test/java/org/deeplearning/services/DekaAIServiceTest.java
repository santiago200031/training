package org.deeplearning.services;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.deeplearning.plots.PlotFinance;
import org.finance.models.Finance;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;

@QuarkusTest
class DekaAIServiceTest {

    @Inject
    DekaAIService aiService;

    @Test
    @Disabled
    void testDekaAI() {
        aiService.trainModel();
    }

    @Test
    void testPredictionDekaAI() {
        String date = "23.01.2024";
        Finance prediction = aiService.makePrediction(date);
        System.out.println("Prediction of " + date + " is " + prediction);
    }

    @Test
    void visualizeModel() {
        PlotFinance plot = aiService.visualizeModel();
        plot.setVisible(true);
        try {
            sleep(10 * 60 * 60);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testTrainBestModelDekaAI() {
        String date1 = "23.01.2024";
        aiService.trainBestModel();
        Finance prediction1 = aiService.makePrediction(date1);

        String date2 = "01.02.2024";
        aiService.trainBestModel();
        Finance prediction2 = aiService.makePrediction(date2);
        System.out.println("Prediction of " + date1 + " is " + prediction1);
        System.out.println("Prediction of " + date2 + " is " + prediction2);
    }
}