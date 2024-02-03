package org.deeplearning.models;

import jakarta.enterprise.context.ApplicationScoped;
import org.deeplearning.configs.NNConfig;
import org.deeplearning.interfaces.AIModel;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

@ApplicationScoped
public class BTCAIModel implements AIModel {

    private MultiLayerNetwork networkModel;

    @Override
    public double getPrediction(long timestamp) {
        if (networkModel == null) {
            return 0.0;
        }

        double timestampDouble = (double) timestamp;

        INDArray inputArray = Nd4j.create(
                new double[]{timestampDouble}, new int[]{1, 1}
        );

        INDArray output = networkModel.output(inputArray);
        return output.getDouble(0);
    }

    @Override
    public void trainModel(DataSetIterator iterator) {
        if (this.networkModel == null) {
            this.networkModel = new MultiLayerNetwork(NNConfig.BUILD_NEURONAL_NETWORK_CONF());
        }

        networkModel.setListeners(List.of(new ScoreIterationListener(100)));
        networkModel.init();
        for (int i = 0; i < NNConfig.NUM_EPOCHS; i++) {
            networkModel.fit(iterator);
        }
        saveModel("btcAIModel.zip");
    }

    public void saveModel(String filePath) {

        File file = new File(filePath);
        try {
            ModelSerializer.writeModel(this.networkModel, file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}