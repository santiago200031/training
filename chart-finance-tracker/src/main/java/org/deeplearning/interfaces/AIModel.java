package org.deeplearning.interfaces;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

public interface AIModel {
    String getPrediction(long timestamp);

    void trainModel(DataSetIterator iterator, MultiLayerConfiguration conf);

    void saveModel(String filePath);
}