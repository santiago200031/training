package org.finance.models;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@Getter
@Setter
@Builder
@Data
public class Series {
    @SerializedName("prices")
    private List<Float> prices;
    @SerializedName("timeStamps")
    private List<String> timestamps;
    @SerializedName("startTime")
    private String startTime;
    @SerializedName("endTime")
    private String endTime;

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}