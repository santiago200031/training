package org.deeplearning.interfaces;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

public interface AiModel {
    String getPrediction(String timestampString);

    void trainModel(DataSetIterator iterator, MultiLayerConfiguration conf);
}