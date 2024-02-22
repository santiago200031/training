package org.finance.utils;

import lombok.Getter;

@Getter
public enum CSVFileProperties {
    DELIMITER(","),
    CSV_HEADER("price,differencePrice,priceChange,displayName,timeLastUpdated,localDateChange");

    private final String value;

    CSVFileProperties(String value) {
        this.value = value;
    }
}