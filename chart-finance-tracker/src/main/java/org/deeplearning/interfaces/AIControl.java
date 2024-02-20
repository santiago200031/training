package org.deeplearning.interfaces;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.deeplearning.plots.PlotFinance;
import org.finance.models.Finance;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

public interface AIControl {

    Finance predictWithPolynomialRegressionModel(String dateString);

    DataSetIterator loadCsvData(String filePath);

    void trainNeuralNetwork();

    void trainBestNeuralNetwork();

    Finance predictWithNeuralNetwork(String dateString);

    PolynomialFunction calculatePolynomialFunction();

    PlotFinance visualizeModel();

    PlotFinance visualizeRegressionFunction();

    void savePolynomialFunction(PolynomialFunction function);

    PolynomialFunction loadPolynomialFunction();
}