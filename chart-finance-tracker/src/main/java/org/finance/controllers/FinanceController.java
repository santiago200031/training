package org.finance.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.finance.models.Finance;
import org.finance.utils.CSVFileProperties;
import org.finance.utils.FinanceCSVReader;
import org.finance.utils.FinanceParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FinanceController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Inject
    FinanceParser financeParser;

    @Inject
    FinanceHttpController financeHttpController;

    @Inject
    FinanceCSVReader financeCSVReader;

    private final String apiUrlDekaGlobalChampions = "https://assets.msn.com/service/Finance/Quotes?" +
            "apikey=0QfOX3Vn51YCzitbLaRkTTBadtWpgTN8NZLW0C1SEM&" +
            "activityId=activity_id&" +
            "ocid=finance-utils-peregrine&" +
            "cm=de-de&it=edgeid&" +
            "traceparent=00-aabb2bdbe41a4e97a5ce3def603d7810-82729fa839b9438b-00&" +
            "ids=agynsm&wrapodata=false";
    private final String apiUrlFinances = "https://assets.msn.com/service/Finance/Quotes?" +
            "apikey=0QfOX3Vn51YCzitbLaRkTTBadtWpgTN8NZLW0C1SEM&" +
            "activityId=activity_id" +
            "&ocid=finance-utils-peregrine&cm=de-de&it=edgeid&traceparent=00-25662d47bd1540358a9434d3b86408e8-0097605488284de5-00&" +
            "ids=agynsm,afeeoc,aewihw,afg3ur,bxdz52,c2111,c2112,avyn9c,afx2kr,bsf9mw,c2113,bvhjqh,afy57w,c2114,af7stc,c2117,a6qja2,af56c7,afml52,af8esm,auvwoc,a3oxnm,a29b3m,awdk1h,aopnp2,a1xzim,a33k6h,aecfh7,a9j7bh,adfh77,ah7etc,ahkucw,ale3jc,apnmnm,afqnnm,afr4c7,bwa7c7,afqxrw,afrlyc,afufz2,afr3u2,afqnf2,aftpzr,aftdlh,afr9ec,aftkm7,c4eo6h,afv89c,afryjc,afqiww,afqpar,afugmw,afqgvh,afqia2,afq57w,aftz5r,afs84c,aft3k2,c7g9ar,afqoec,afr46h,afs7xm,aft4gh,afqqfr,afr1pr,aft452,afs93m,aftlr7,aftvpr,afti3m,afsc4c,aftjec,afr9yc,aznagh,c2115,c2116,c2118,c211a,c2119,c211d,c211b,c211q,c211c,c211e,c211h,c211f,c211i,c211k,c211g,c211j,c211l,c212a,c2122,c211p,c211n,c211m,c211o,c2135,c212p,c2123&wrapodata=false";
    private final String apiURLBTC = "https://assets.msn.com/service/Finance/Quotes?" +
            "apikey=0QfOX3Vn51YCzitbLaRkTTBadtWpgTN8NZLW0C1SEM&" +
            "activityId=activity_id" +
            "&ocid=finance-utils-peregrine&cm=de-de&it=edgeid&traceparent=00-5ddc0fb5a32e48b5be21d5b4983ec47e-2aadd4de2ac240b6-00&ids=c2111&wrapodata=false";

    public Finance getDekaGlobalChampions(UUID activityId) {
        String jsonResponse;
        LOGGER.debug("ActivityId: " + activityId);

        try {
            jsonResponse = makeGetRequest(apiUrlDekaGlobalChampions, activityId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return financeParser.jsonToFinance(jsonResponse);
    }

    public Finance getBTC(UUID activityId) {
        String jsonResponse;
        LOGGER.debug("ActivityId: " + activityId);

        try {
            jsonResponse = makeGetRequest(apiURLBTC, activityId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return financeParser.jsonToFinance(jsonResponse);
    }

    public List<Finance> getFinances(UUID activityId) {
        String jsonResponse;
        LOGGER.debug("ActivityId: " + activityId);

        try {
            jsonResponse = makeGetRequest(apiUrlFinances, activityId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return financeParser.jsonToFinanceList(jsonResponse);
    }

    private String makeGetRequest(String apiUrl, UUID activityId) throws Exception {
        String apiUrlWithActId = apiUrl.replace("activity_id", activityId.toString());
        return financeHttpController.makeGetRequest(apiUrlWithActId, activityId);
    }

    public Finance getLastDekaFinance() {
        return financeCSVReader
                .readLastFinanceCSV(CSVFileProperties.DEKA_FILE_PATH.getValue());
    }

    public Finance getLastBTCFinance() {
        return financeCSVReader
                .readLastFinanceCSV(CSVFileProperties.BTC_FILE_PATH.getValue());
    }

    public boolean isDekaDataEmpty() {
        return financeCSVReader
                .readFinanceCSV(CSVFileProperties.DEKA_FILE_PATH.getValue())
                .isEmpty();
    }

    public boolean isBTCDataEmpty() {
        return financeCSVReader
                .readFinanceCSV(CSVFileProperties.BTC_FILE_PATH.getValue())
                .isEmpty();
    }
}