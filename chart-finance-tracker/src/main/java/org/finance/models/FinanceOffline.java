package org.finance.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Data
public class FinanceOffline {
    private String displayName;
    private List<Float> prices;
    private List<Float> differencePrices;
    private List<String> pricesChanges;
    private List<String> timesLastUpdated;
    private List<String> localDateChanges;
}