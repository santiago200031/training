package org.deeplearning.interfaces;

import org.deeplearning.plots.PlotFinance;
import org.finance.models.Finance;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

public interface AIControl {

    DataSetIterator loadCsvData(String filePath);

    Finance makePrediction(String dateString);

    PlotFinance visualizeData();

    void train();
}