package org.deeplearning.models;


import jakarta.enterprise.context.ApplicationScoped;
import org.deeplearning.configs.NNConfig;
import org.deeplearning.interfaces.AiModel;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;

@ApplicationScoped
public class DekaAiModel implements AiModel {

    private MultiLayerNetwork networkModel;

    @Override
    public String getPrediction(String timestampString) {
        if (networkModel == null) {
            return "Model not initialized";
        }

        long timestamp = Long.parseLong(timestampString);
        INDArray inputArray = Nd4j.create(new float[]{timestamp}, new int[]{1, 1});
        INDArray output = networkModel.output(inputArray);
        return Double.toString(output.getDouble(0));
    }

    @Override
    public void trainModel(DataSetIterator iterator, MultiLayerConfiguration conf) {
        if (this.networkModel == null) {
            this.networkModel = new MultiLayerNetwork(conf);
        }

        networkModel.init();
        for (int i = 0; i < NNConfig.NUM_EPOCHS; i++) {
            networkModel.fit(iterator);
        }
    }
}
