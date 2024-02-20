package org.deeplearning.interfaces;

import org.deeplearning.plots.PlotFinance;
import org.finance.models.Finance;

public interface AIService {

    void trainBestModel();

    Finance predictWithPolynomialRegressionModel(String dateString);

    Finance predictWithNeuralNetwork(String dateOfPriceToPredict);

    void trainModel();

    PlotFinance visualizeModel();

    PlotFinance visualizeRegressionFunction();
}