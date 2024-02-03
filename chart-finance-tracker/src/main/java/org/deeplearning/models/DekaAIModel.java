package org.deeplearning.models;


import jakarta.enterprise.context.ApplicationScoped;
import org.deeplearning.configs.NNConfig;
import org.deeplearning.exceptions.AIModelException;
import org.deeplearning.exceptions.AIModelLoadException;
import org.deeplearning.exceptions.AIModelNotFoundException;
import org.deeplearning.interfaces.AIModel;
import org.deeplearning.plots.PlotFinance;
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
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DekaAIModel implements AIModel {

    private static final String BEST_MODEL_FILE = "bestModel.bin";

    private static final String CHART_NAME_FILE = "dekaAIModelTrained.png";

    private final String AIModelFile = "dekaAIModel.bin";

    private static final int evaluateEveryNEpochs = 150;

    private static final int logEveryNEpochs = 200;

    private MultiLayerNetwork networkModel;

    @Override
    public String getPrediction(long timestamp) {
        loadAndSetModelFromPath(AIModelFile);

        double timestampDouble = (double) timestamp;

        INDArray inputArray = Nd4j.create(
                new double[]{timestampDouble}, new int[]{1, 1}
        );

        INDArray output = networkModel.output(inputArray);
        return Double.toString(output.getDouble(0));
    }


    @Override
    public void trainModel(DataSetIterator iterator) {
        File file = new File(AIModelFile);
        if (file.exists()) {
            try {
                this.networkModel = ModelSerializer.restoreMultiLayerNetwork(file);
            } catch (IOException e) {
                throw new AIModelLoadException(AIModelFile, e);
            }

        } else {
            this.networkModel = new MultiLayerNetwork(NNConfig.BUILD_NEURONAL_NETWORK_CONF());
            networkModel.setListeners(List.of(new ScoreIterationListener(logEveryNEpochs)));
            networkModel.init();
            for (int i = 0; i < NNConfig.NUM_EPOCHS; i++) {
                networkModel.fit(iterator);
            }
            iterator.reset();

            saveModel(AIModelFile);
        }
    }


    @Override
    public void saveModel(String filePath) {
        File file = new File(filePath);
        try {
            ModelSerializer.writeModel(this.networkModel, file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveModelAndStop(DataSetIterator iterator) {
        EarlyStoppingModelSaver<MultiLayerNetwork> saver = new LocalFileModelSaver(BEST_MODEL_FILE);
        DataSetLossCalculator lossCalculator = new DataSetLossCalculator(iterator, true);

        var esConf = new EarlyStoppingConfiguration.Builder()
                .epochTerminationConditions(new MaxEpochsTerminationCondition(NNConfig.NUM_EPOCHS))
                .scoreCalculator(lossCalculator)
                .evaluateEveryNEpochs(evaluateEveryNEpochs)
                .modelSaver(saver)
                .build();

        EarlyStoppingTrainer trainer = new EarlyStoppingTrainer(esConf, NNConfig.BUILD_NEURONAL_NETWORK_CONF(), iterator);

        EarlyStoppingResult<MultiLayerNetwork> result = trainer.fit();
        this.networkModel = result.getBestModel();
    }

    public PlotFinance visualizeData(DataSetIterator iterator) {
        loadAndSetModelFromPath(AIModelFile);

        double[] actual = getActualValues(iterator);
        double[] predicted = getPredictedValues(iterator);

        PlotFinance plot = new PlotFinance("Training Data", actual, predicted);
        plot.pack();

        plot.saveChart(CHART_NAME_FILE);

        return plot;
    }

    private void loadAndSetModelFromPath(String aiModelFile) {
        File file = new File(aiModelFile);
        try {
            if (!file.exists()) {
                throw new AIModelNotFoundException();
            }
            try {
                this.networkModel = ModelSerializer.restoreMultiLayerNetwork(file);
            } catch (IOException e) {
                throw new AIModelLoadException();
            }
        } catch (AIModelNotFoundException | AIModelLoadException e) {
            throw new AIModelException(aiModelFile);
        }
    }

    public double[] getActualValues(DataSetIterator iterator) {
        iterator.reset();

        List<Double> actualList = new ArrayList<>();

        while (iterator.hasNext()) {
            DataSet dataSet = iterator.next();

            for (int j = 0; j < dataSet.numExamples(); j++) {
                actualList.add(dataSet.getLabels().getRow(j).getDouble(0));
            }
        }
        iterator.reset();

        return actualList.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public double[] getPredictedValues(DataSetIterator iterator) {
        iterator.reset();

        List<Double> predictedList = new ArrayList<>();

        while (iterator.hasNext()) {
            DataSet dataSet = iterator.next();

            for (int j = 0; j < dataSet.numExamples(); j++) {
                INDArray feature = dataSet.getFeatures().getRow(j);

                INDArray reshapedFeature = feature.reshape(1, feature.length());

                INDArray prediction = networkModel.output(reshapedFeature);

                predictedList.add(prediction.getDouble(0));
            }
        }
        iterator.reset();

        return predictedList.stream().mapToDouble(Double::doubleValue).toArray();
    }
}