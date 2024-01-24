package org.deeplearning.interfaces;

public interface AIControl {
    void train();

    String makePrediction(String dateString);
}