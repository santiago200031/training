package org.deeplearning.controls;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.records.reader.impl.transform.TransformProcessRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning.configs.NNConfig;
import org.deeplearning.interfaces.AIControl;
import org.deeplearning.models.DekaAIModel;
import org.deeplearning.plots.PlotFinance;
import org.deeplearning.utils.AICommons;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.finance.models.Finance;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.io.File;
import java.io.IOException;

@ApplicationScoped
public class DekaAIControl implements AIControl {

    @Inject
    private DekaAIModel aiModel;

    @Inject
    private NNConfig nnConfig;

    @Inject
    private AICommons aiCommons;

    @ConfigProperty(name = "resources.deka.csv-file")
    private String csvFile;

    @ConfigProperty(name = "resources.deka.regression.degree")
    private int degree;

    @ConfigProperty(name = "resources.deka.regression.function-file")
    private String functionFile;

    private PolynomialFunction function;

    @Override
    public void trainNeuralNetwork() {
        DataSetIterator iterator = loadCsvData(csvFile);
        aiModel.trainNeuralNetwork(iterator);
    }

    @Override
    public PolynomialFunction calculatePolynomialFunction() {
        DataSetIterator iterator = loadCsvData(csvFile);
        WeightedObservedPoints obs = new WeightedObservedPoints();
        while (iterator.hasNext()) {
            DataSet dataSet = iterator.next();
            INDArray features = dataSet.getFeatures();
            INDArray labels = dataSet.getLabels();

            for (int i = 0; i < features.length(); i++) {
                obs.add(features.getDouble(i), labels.getDouble(i));
            }
        }

        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);
        function = new PolynomialFunction(fitter.fit(obs.toList()));

        savePolynomialFunction(function);
        return function;
    }

    @Override
    public void trainBestNeuralNetwork() {
        DataSetIterator iterator = loadCsvData(csvFile);
        aiModel.saveModelAndStop(iterator);
    }

    @Override
    public Finance predictWithNeuralNetwork(String dateString) {
        long timestamp = aiCommons.getTimeStampFromDate(dateString);
        double prediction = aiModel.predictWithNeuralNetwork(timestamp);

        return Finance.builder()
                .displayName("Predicted Deka")
                .timeLastUpdated(dateString)
                .price((float) prediction)
                .build();
    }

    @Override
    public Finance predictWithPolynomialRegressionModel(String dateString) {
        if (function == null) {
            function = loadPolynomialFunction();
        }

        if (function == null) {
            function = calculatePolynomialFunction();
        }

        long timestamp = aiCommons.getTimeStampFromDate(dateString);
        double prediction = function.value(timestamp);

        return Finance.builder()
                .displayName("Predicted Deka")
                .timeLastUpdated(dateString)
                .price((float) prediction)
                .build();
    }

    @Override
    public DataSetIterator loadCsvData(String filePath) {
        RecordReader recordReader = createCsvRecordReader(csvFile);

        RecordReader transformProcessRecordReader = new TransformProcessRecordReader(
                recordReader, NNConfig.GET_TRANSFORMATION_PROCESS()
        );

        return new RecordReaderDataSetIterator(
                transformProcessRecordReader, nnConfig.getBatchSize(), nnConfig.getLabelIndex(), nnConfig.getNumClasses()
        );
    }

    @Override
    public PlotFinance visualizeModel() {
        DataSetIterator iterator = loadCsvData(csvFile);
        return aiModel.visualizeData(iterator);
    }

    @Override
    public PlotFinance visualizeRegressionFunction() {
        DataSetIterator iterator = loadCsvData(csvFile);
        return aiModel.visualizeData(iterator, function);
    }

    @Override
    public void savePolynomialFunction(PolynomialFunction function) {
        try {
            aiCommons.savePolynomialFunction(function, functionFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PolynomialFunction loadPolynomialFunction() {
        try {
            return aiCommons.loadPolynomialFunction(functionFile);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private RecordReader createCsvRecordReader(String filePath) {
        CSVRecordReader csvRecordReader = new CSVRecordReader(1, ',');
        try {
            csvRecordReader.initialize(new FileSplit(new File(filePath)));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error initializing CSVRecordReader", e);
        }
        return csvRecordReader;
    }
}