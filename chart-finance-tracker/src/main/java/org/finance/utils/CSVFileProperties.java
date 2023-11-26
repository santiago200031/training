package org.finance.utils;

import lombok.Getter;

import java.text.DecimalFormat;

@Getter
public enum CSVFileProperties {
    DELIMITER(","),
    CSV_HEADER("price,differencePrice,priceChange,displayName,timeLastUpdated,localDateChange"),
    DEKA_FILE_PATH("src/main/resources/financeDeka.csv"),
    BTC_FILE_PATH("src/main/resources/financeBTC.csv");

    private final String value;

    CSVFileProperties(String value) {
        this.value = value;
    }
}