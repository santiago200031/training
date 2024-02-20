package org.deeplearning.interfaces;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.deeplearning.plots.PlotFinance;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

public interface AIModel {
    void trainNeuralNetwork(DataSetIterator iterator);

    double predictWithNeuralNetwork(long timestamp);

    void saveModelAndStop(DataSetIterator iterator);

    PlotFinance visualizeData(DataSetIterator iterator);

    PlotFinance visualizeData(DataSetIterator iterator, PolynomialFunction function);

    void saveModel(String filePath);
}