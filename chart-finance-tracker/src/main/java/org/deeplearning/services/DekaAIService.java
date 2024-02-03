package org.deeplearning.services;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.deeplearning.controls.DekaAIControl;
import org.deeplearning.plots.PlotFinance;
import org.finance.models.Finance;

@ApplicationScoped
public class DekaAIService {

    @Inject
    private DekaAIControl aiControl;

    public void trainModel() {
        aiControl.train();
    }

    public void trainBestModel() {
        aiControl.trainBestModel();
    }

    public Finance makePrediction(String dateString) {
        return aiControl.makePrediction(dateString);
    }

    public PlotFinance visualizeModel() {
        return aiControl.visualizeData();
    }
}