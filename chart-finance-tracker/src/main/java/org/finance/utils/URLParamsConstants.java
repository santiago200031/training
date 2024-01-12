package org.finance.utils;

import lombok.Getter;

@Getter
public enum URLParamsConstants {
    AMOUNT_PARAM("amountValue"), ACTIVITY_ID("activity_id");

    private final String value;

    URLParamsConstants(String value) {
        this.value = value;
    }
}
