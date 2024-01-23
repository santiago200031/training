package org.deeplearning.controls;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.datavec.api.records.listener.impl.LogRecordListener;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.records.reader.impl.transform.TransformProcessRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning.configs.NNConfig;
import org.deeplearning.interfaces.AIModel;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.io.File;
import java.io.IOException;


@ApplicationScoped
public class DekaAIControl {

    @Inject
    private AIModel aiModel;

    @ConfigProperty(name = "resources.deka")
    private String dekaCsvPath;

    public void trainDeka() {
        RecordReader rr = createCsvRecordReader(dekaCsvPath);
        rr.setListeners(new LogRecordListener());

        RecordReader transformProcessRecordReader = new TransformProcessRecordReader(
                rr, NNConfig.GET_TRANSFORMATION_PROCESS()
        );
        DataSetIterator iterator = createDataSetIterator(transformProcessRecordReader);

        aiModel.trainModel(iterator, NNConfig.BUILD_NEURONAL_NETWORK_CONF());
    }

    public String predictDeka(String timestampString) {
        return aiModel.getPrediction(timestampString);
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


    public DataSetIterator createDataSetIterator(RecordReader recordReader) {
        return new RecordReaderDataSetIterator(
                recordReader, NNConfig.BATCH_SIZE, NNConfig.LABEL_INDEX, NNConfig.NUM_CLASSES
        );
    }
}