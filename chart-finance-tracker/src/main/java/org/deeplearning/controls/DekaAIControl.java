package org.deeplearning.controls;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.records.reader.impl.transform.TransformProcessRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning.configs.NNConfig;
import org.deeplearning.interfaces.AIControl;
import org.deeplearning.models.DekaAIModel;
import org.deeplearning.plots.PlotFinance;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@ApplicationScoped
public class DekaAIControl implements AIControl {

    @Inject
    private DekaAIModel aiModel;

    @ConfigProperty(name = "resources.deka")
    private String dekaCsvPath;

    @Override
    public void train() {
        DataSetIterator iterator = loadCsvData(dekaCsvPath);
        aiModel.trainModel(iterator);
    }

    public void trainBestModel() {
        DataSetIterator iterator = loadCsvData(dekaCsvPath);
        aiModel.saveModelAndStop(iterator);
    }

    @Override
    public String makePrediction(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        long timestamp = parsedDate.getTime();
        return aiModel.getPrediction(timestamp);
    }

    @Override
    public DataSetIterator loadCsvData(String filePath) {
        RecordReader recordReader = createCsvRecordReader(dekaCsvPath);

        RecordReader transformProcessRecordReader = new TransformProcessRecordReader(
                recordReader, NNConfig.GET_TRANSFORMATION_PROCESS()
        );

        return new RecordReaderDataSetIterator(
                transformProcessRecordReader, NNConfig.BATCH_SIZE, NNConfig.LABEL_INDEX, NNConfig.NUM_CLASSES
        );
    }

    @Override
    public PlotFinance visualizeData() {
        DataSetIterator iterator = loadCsvData(dekaCsvPath);
        return aiModel.visualizeData(iterator);
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