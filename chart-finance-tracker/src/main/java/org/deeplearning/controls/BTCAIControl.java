package org.deeplearning.controls;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.lang3.NotImplementedException;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.records.reader.impl.transform.TransformProcessRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning.configs.NNConfig;
import org.deeplearning.interfaces.AIControl;
import org.deeplearning.models.BTCAIModel;
import org.deeplearning.plots.PlotFinance;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.finance.models.Finance;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ApplicationScoped
public class BTCAIControl implements AIControl {
    @Inject
    private BTCAIModel aiModel;

    @ConfigProperty(name = "resources.btc")
    private String btcCsvFile;

    @Override
    public void train() {
        DataSetIterator dataSetIterator = loadCsvData(btcCsvFile);
        aiModel.trainModel(dataSetIterator);
    }

    @Override
    public Finance makePrediction(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        long timestamp = parsedDate.getTime();

        double prediction = aiModel.getPrediction(timestamp);

        return Finance.builder()
                .displayName("BTC Predicted")
                .localDateChange(dateString)
                .price((float) prediction)
                .build();
    }

    @Override
    public DataSetIterator loadCsvData(String filePath) {
        RecordReader recordReader = createCsvRecordReader(btcCsvFile);

        RecordReader transformProcessRecordReader = new TransformProcessRecordReader(
                recordReader, NNConfig.GET_TRANSFORMATION_PROCESS()
        );

        return new RecordReaderDataSetIterator(
                transformProcessRecordReader, NNConfig.BATCH_SIZE, NNConfig.LABEL_INDEX, NNConfig.NUM_CLASSES
        );
    }

    @Override
    public PlotFinance visualizeData() {
        throw new NotImplementedException("Method no implemented");
    }

    public RecordReader createCsvRecordReader(String filePath) {
        CSVRecordReader csvRecordReader = new CSVRecordReader(1, ',');
        try {
            csvRecordReader.initialize(new FileSplit(new File(filePath)));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error initializing CSVRecordReader", e);
        }
        return csvRecordReader;
    }
}