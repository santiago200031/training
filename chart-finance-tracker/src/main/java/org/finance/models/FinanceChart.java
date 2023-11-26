package org.finance.models;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Setter
@Builder
@Data
public class FinanceChart {
    @SerializedName("series")
    private Series series;
    @SerializedName("pricePreviousClose")
    private double pricePreviousClose;
    @SerializedName("priceChange")
    private double priceChange;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("timeLastUpdated")
    private String timeLastUpdated;

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}