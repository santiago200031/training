package org.deeplearning.services;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.deeplearning.controls.DekaAIControl;
import org.deeplearning.interfaces.AIService;
import org.deeplearning.plots.PlotFinance;
import org.finance.models.Finance;

@ApplicationScoped
public class DekaAIService implements AIService {

    @Inject
    private DekaAIControl aiControl;

    @Override
    public void trainModel() {
        aiControl.trainNeuralNetwork();
    }

    @Override
    public void trainBestModel() {
        aiControl.trainBestNeuralNetwork();
    }

    @Override
    public Finance predictWithNeuralNetwork(String dateString) {
        return aiControl.predictWithNeuralNetwork(dateString);
    }

    @Override
    public Finance predictWithPolynomialRegressionModel(String dateString) {
        return aiControl.predictWithPolynomialRegressionModel(dateString);
    }

    @Override
    public PlotFinance visualizeModel() {
        return aiControl.visualizeModel();
    }

    @Override
    public PlotFinance visualizeRegressionFunction() {
        return aiControl.visualizeRegressionFunction();
    }

    public void calculatePolynomialFunction() {
        aiControl.calculatePolynomialFunction();
    }
}