package org.deeplearning.utils;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.deeplearning.exceptions.AIModelException;
import org.deeplearning.exceptions.AIModelLoadException;
import org.deeplearning.exceptions.AIModelNotFoundException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class AICommons {

    public MultiLayerNetwork loadAndSetModelFromPath(String aiModelFile) {
        File file = new File(aiModelFile);
        try {
            if (!file.exists()) {
                throw new AIModelNotFoundException();
            }
            try {
                return ModelSerializer.restoreMultiLayerNetwork(file);

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

    public double[] getPredictedValues(DataSetIterator iterator, MultiLayerNetwork layerNetwork, int degree) {
        iterator.reset();

        List<Double> predictedList = new ArrayList<>();

        while (iterator.hasNext()) {
            DataSet dataSet = iterator.next();

            for (int j = 0; j < dataSet.numExamples(); j++) {
                INDArray feature = dataSet.getFeatures().getRow(j);

                INDArray reshapedFeature = createPolynomialFeatures(feature, degree);

                INDArray prediction = layerNetwork.output(reshapedFeature);

                predictedList.add(prediction.getDouble(0));
            }
        }
        iterator.reset();

        return predictedList.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public INDArray createPolynomialFeatures(INDArray features, int degree) {
        int numSamples = features.rows();
        int numFeatures = features.columns();

        INDArray polyFeatures = Nd4j.zeros(numSamples, numFeatures * degree);

        for (int i = 0; i < numSamples; i++) {
            for (int j = 0; j < numFeatures; j++) {
                for (int d = 0; d < degree; d++) {
                    double val = Math.pow(features.getDouble(i, j), d + 1);
                    polyFeatures.putScalar(i, (long) j * degree + d, val);
                }
            }
        }

        return polyFeatures;
    }

    public double[] getPredictedValues(DataSetIterator iterator, PolynomialFunction function) {
        iterator.reset();
        List<Double> predictedList = new ArrayList<>();

        while (iterator.hasNext()) {
            DataSet dataSet = iterator.next();

            for (int j = 0; j < dataSet.numExamples(); j++) {
                INDArray row = dataSet.getFeatures().getRow(j);
                double value = row.getDouble(0);
                double prediction = function.value(value);
                predictedList.add(prediction);
            }
        }
        iterator.reset();

        return predictedList.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public void savePolynomialFunction(PolynomialFunction function, String filePath) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(function);
        objectOutputStream.close();
    }

    public PolynomialFunction loadPolynomialFunction(String filePath) throws IOException, ClassNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        FileInputStream fileInputStream = new FileInputStream(filePath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        PolynomialFunction function = (PolynomialFunction) objectInputStream.readObject();
        objectInputStream.close();
        return function;
    }

    public long getTimeStampFromDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return parsedDate.getTime();
    }
}