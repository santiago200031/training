package org.finance.utils;

import lombok.Getter;

@Getter
public enum CSVFileProperties {
    DELIMITER(","),
    CSV_HEADER("price,differencePrice,priceChange,displayName,timeLastUpdated,localDateChange"),
    //TODO Delete and Change for the property of application.yaml
    DEKA_FILE_PATH("src/main/resources/financeDeka.csv"),
    BTC_FILE_PATH("src/main/resources/financeBTC.csv");

    private final String value;

    CSVFileProperties(String value) {
        this.value = value;
    }
}