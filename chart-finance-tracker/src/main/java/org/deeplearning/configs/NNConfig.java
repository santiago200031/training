package org.deeplearning.configs;

import org.datavec.api.transform.TransformProcess;
import org.datavec.api.transform.schema.Schema;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.RmsProp;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class NNConfig {
    public static final int LABEL_INDEX = 0;
    public static final int NUM_CLASSES = 1;
    public static final int NUM_EPOCHS = 9000;
    public static final int BATCH_SIZE = 64;
    private static final int HIDDEN_LAYERS_NEURONS = 75;

    public static Schema GET_SCHEMA() {
        return new Schema.Builder()
                .addColumnDouble("price")
                .addColumnDouble("differencePrice")
                .addColumnDouble("priceChange")
                .addColumnString("displayName")
                .addColumnString("timeLastUpdated")
                .addColumnLong("localDateChange")
                .build();
    }

    public static TransformProcess GET_TRANSFORMATION_PROCESS() {
        return new TransformProcess.Builder(GET_SCHEMA())
                .removeColumns("displayName", "differencePrice", "priceChange", "timeLastUpdated")
                .build();
    }

    public static MultiLayerConfiguration BUILD_NEURONAL_NETWORK_CONF() {

        return new NeuralNetConfiguration.Builder()
                .weightInit(WeightInit.XAVIER)
                .activation(Activation.TANH)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new RmsProp(0.001))
                .l2(0.001)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(1).nOut(HIDDEN_LAYERS_NEURONS)
                        .build())
                .layer(1, new DenseLayer.Builder()
                        .nIn(HIDDEN_LAYERS_NEURONS).nOut(HIDDEN_LAYERS_NEURONS)
                        .build())
                .layer(2, new DenseLayer.Builder()
                        .nIn(HIDDEN_LAYERS_NEURONS).nOut(HIDDEN_LAYERS_NEURONS)
                        .build())
                .layer(3, new DenseLayer.Builder()
                        .nIn(HIDDEN_LAYERS_NEURONS).nOut(HIDDEN_LAYERS_NEURONS)
                        .build())
                .layer(4, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(HIDDEN_LAYERS_NEURONS).nOut(1).build())
                .build();
    }
}