package org.deeplearning.interfaces;

import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

public interface AIModel {
    String getPrediction(long timestamp);

    void trainModel(DataSetIterator iterator);

    void saveModel(String filePath);
}