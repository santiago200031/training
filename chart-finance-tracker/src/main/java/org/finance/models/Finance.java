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
public class Finance {

    @SerializedName("id")
    private String id;
    @SerializedName("price")
    private float price;
    @SerializedName("priceChange")
    private float priceChange;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("timeLastUpdated")
    private String timeLastUpdated;

    private Float differencePrice;
    private String localDateChange;

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}