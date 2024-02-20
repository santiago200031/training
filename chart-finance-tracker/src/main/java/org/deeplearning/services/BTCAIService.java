package org.deeplearning.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.deeplearning.controls.BTCAIControl;
import org.deeplearning.plots.PlotFinance;
import org.finance.models.Finance;

@ApplicationScoped
public class BTCAIService {

    @Inject
    private BTCAIControl aiControl;

    public void trainModel() {
        aiControl.trainNeuralNetwork();
    }

    public void calculatePolynomialFunction() {
        aiControl.calculatePolynomialFunction();
    }

    public Finance predictWithNeuralNetwork(String dateString) {
        return aiControl.predictWithNeuralNetwork(dateString);
    }

    public Finance predictWithPolynomialRegressionModel(String dateString) {
        return aiControl.predictWithPolynomialRegressionModel(dateString);
    }

    public PlotFinance visualizeModel() {
        return aiControl.visualizeModel();
    }

    public PlotFinance visualizeRegressionFunction() {
        return aiControl.visualizeRegressionFunction();
    }
}