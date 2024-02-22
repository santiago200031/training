package org.deeplearning.models;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.deeplearning.configs.NNConfig;
import org.deeplearning.exceptions.SaveChartException;
import org.deeplearning.interfaces.AIModel;
import org.deeplearning.plots.PlotFinance;
import org.deeplearning.utils.AICommons;
import org.deeplearning4j.earlystopping.EarlyStoppingConfiguration;
import org.deeplearning4j.earlystopping.EarlyStoppingModelSaver;
import org.deeplearning4j.earlystopping.EarlyStoppingResult;
import org.deeplearning4j.earlystopping.saver.LocalFileModelSaver;
import org.deeplearning4j.earlystopping.scorecalc.DataSetLossCalculator;
import org.deeplearning4j.earlystopping.termination.MaxEpochsTerminationCondition;
import org.deeplearning4j.earlystopping.trainer.EarlyStoppingTrainer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

@ApplicationScoped
public class DekaAIModel implements AIModel {

    @ConfigProperty(name = "resources.deka.neural-network.model")
    private String neuralNetworkFile;
    @ConfigProperty(name = "resources.deka.neural-network.best-model")
    private String bestNeuralNetworkModelFile;
    @ConfigProperty(name = "resources.deka.neural-network.chart-file")
    private String neuralNetworkChartNameFile;
    @ConfigProperty(name = "resources.deka.regression.chart-file")
    private String functionChartNameFile;
    @ConfigProperty(name = "resources.ai.neural-network.evaluate-every-n-epochs")
    private int evaluateEveryNEpochs;
    @ConfigProperty(name = "resources.ai.neural-network.log-every-n-epochs")
    private int logEveryNEpochs;
    @ConfigProperty(name = "resources.deka.regression.degree")
    private int degree;

    @Inject
    private AICommons aiCommons;

    @Inject
    private NNConfig nnConfig;

    private MultiLayerNetwork layerNetwork;

    @Override
    public void trainNeuralNetwork(DataSetIterator iterator) {
        iterator.reset();

        DataSet allData = iterator.next();
        allData.shuffle(42);

        INDArray features = allData.getFeatures();

        INDArray polyFeatures = aiCommons.createPolynomialFeatures(features, degree);

        allData.setFeatures(polyFeatures);

        DataNormalization normalizer = new NormalizerStandardize();
        normalizer.fit(allData);
        normalizer.transform(allData);


        SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(1);
        DataSet trainingData = testAndTrain.getTrain();

        this.layerNetwork = new MultiLayerNetwork(nnConfig.buildNeuronalNetworkConfig());

        layerNetwork.setListeners(List.of(new ScoreIterationListener(logEveryNEpochs)));
        layerNetwork.init();

        for (int j = 0; j < nnConfig.getNumEpochs(); j++) {
            layerNetwork.fit(trainingData);
        }

        saveModel(neuralNetworkFile);
    }

    @Override
    public double predictWithNeuralNetwork(long timestamp) {
        this.layerNetwork = aiCommons.loadAndSetModelFromPath(neuralNetworkFile);

        double timestampDouble = (double) timestamp;

        INDArray timestampArray = Nd4j.create(new double[]{timestampDouble}, new int[]{1, 1});

        INDArray inputArray = aiCommons.createPolynomialFeatures(timestampArray, degree);

        INDArray output = layerNetwork.output(inputArray);
        return output.getDouble(0);
    }

    @Override
    public void saveModel(String filePath) {
        File file = new File(filePath);
        try {
            ModelSerializer.writeModel(this.layerNetwork, file, true);
        } catch (IOException e) {
            throw new SaveChartException(e, filePath);
        }
    }

    @Override
    public void saveModelAndStop(DataSetIterator iterator) {
        EarlyStoppingModelSaver<MultiLayerNetwork> saver = new LocalFileModelSaver(bestNeuralNetworkModelFile);
        DataSetLossCalculator lossCalculator = new DataSetLossCalculator(iterator, true);

        EarlyStoppingConfiguration<MultiLayerNetwork> esConf = new EarlyStoppingConfiguration.Builder<MultiLayerNetwork>()
                .epochTerminationConditions(new MaxEpochsTerminationCondition(nnConfig.getNumEpochs()))
                .scoreCalculator(lossCalculator)
                .evaluateEveryNEpochs(evaluateEveryNEpochs)
                .modelSaver(saver)
                .build();

        EarlyStoppingTrainer trainer = new EarlyStoppingTrainer(esConf, nnConfig.buildNeuronalNetworkConfig(), iterator);

        EarlyStoppingResult<MultiLayerNetwork> result = trainer.fit();
        this.layerNetwork = result.getBestModel();
    }

    public PlotFinance visualizeData(DataSetIterator iterator) {
        this.layerNetwork = aiCommons.loadAndSetModelFromPath(neuralNetworkFile);

        double[] actual = aiCommons.getActualValues(iterator);

        // Degree is here needed because of the Polynomial Feature
        double[] predicted = aiCommons.getPredictedValues(iterator, layerNetwork, degree);

        PlotFinance plot = new PlotFinance("Training Data", actual, predicted);
        plot.pack();
        plot.saveChart(neuralNetworkChartNameFile);

        return plot;
    }

    @Override
    public PlotFinance visualizeData(DataSetIterator iterator, PolynomialFunction function) {
        this.layerNetwork = aiCommons.loadAndSetModelFromPath(neuralNetworkFile);

        double[] actual = aiCommons.getActualValues(iterator);
        double[] predicted = aiCommons.getPredictedValues(iterator, function);

        PlotFinance plot = new PlotFinance("Training Data", actual, predicted);
        plot.pack();
        plot.saveChart(functionChartNameFile);

        return plot;
    }
}