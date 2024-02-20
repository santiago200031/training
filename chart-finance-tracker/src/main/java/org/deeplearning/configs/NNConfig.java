package org.deeplearning.configs;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.datavec.api.transform.TransformProcess;
import org.datavec.api.transform.schema.Schema;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

@ApplicationScoped
@Getter
public class NNConfig {
    @ConfigProperty(name = "resources.ai.neural-network.label-index")
    private int labelIndex;
    @ConfigProperty(name = "resources.ai.neural-network.learning-rate")
    private double learningRate;
    @ConfigProperty(name = "resources.ai.neural-network.regularizationL2")
    private double regularizationL2;
    @ConfigProperty(name = "resources.ai.neural-network.num-classes")
    private int numClasses;
    @ConfigProperty(name = "resources.ai.neural-network.num-in")
    private int numIn;
    @ConfigProperty(name = "resources.ai.neural-network.num-out")
    private int numOut;
    @ConfigProperty(name = "resources.ai.neural-network.num-epochs")
    private int numEpochs;
    @ConfigProperty(name = "resources.ai.neural-network.batch-size")
    private int batchSize;
    @ConfigProperty(name = "resources.ai.neural-network.hidden-layers-neurons")
    private int hiddenLayersNeurons;

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

    public MultiLayerConfiguration buildNeuronalNetworkConfig() {
        return new NeuralNetConfiguration.Builder()
                .weightInit(WeightInit.XAVIER)
                .activation(Activation.LEAKYRELU)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new Adam(learningRate))
                .l2(regularizationL2)
                .list()

                .layer(0, new DenseLayer.Builder()
                        .nIn(numIn).nOut(hiddenLayersNeurons)
                        .build())
                .layer(1, new DenseLayer.Builder()
                        .nIn(hiddenLayersNeurons).nOut(hiddenLayersNeurons)
                        .build())
                .layer(2, new DenseLayer.Builder()
                        .nIn(hiddenLayersNeurons).nOut(hiddenLayersNeurons)
                        .build())
                .layer(3, new DenseLayer.Builder()
                        .nIn(hiddenLayersNeurons).nOut(hiddenLayersNeurons)
                        .build())
                .layer(4, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(hiddenLayersNeurons).nOut(numOut).build())
                .build();
    }
}