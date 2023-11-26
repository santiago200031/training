package org.finance.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.finance.models.FinanceChart;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FinanceChartController {

    @Inject
    FinanceHttpController financeHttpController;

    private final String apiUrlDekaGlobalChampions = "https://assets.msn.com/service/Finance/Charts?" +
            "apikey=activity_id&" +
            "activityId=8A337645-6F3A-44E4-8718-D3AD8965A511&ocid=finance-utils-peregrine&cm=de-de&it=edgeid&traceparent=00-1181b84cc69b423db789e8c738a5cfbf-67944000070e45d2-00&ids=agynsm&type=1M&wrapodata=false&disableSymbol=false";
    private final String apiUrlFinances = "https://assets.msn.com/service/Finance/Quotes?" +
            "apikey=activity_id&" +
            "activityId=8A337645-6F3A-44E4-8718-D3AD8965A511&ocid=finance-utils-peregrine&cm=de-de&it=edgeid&traceparent=00-25662d47bd1540358a9434d3b86408e8-0097605488284de5-00" +
            "&ids=agynsm,afeeoc,aewihw,afg3ur,bxdz52,c2111,c2112,avyn9c,afx2kr,bsf9mw,c2113,bvhjqh,afy57w,c2114,af7stc,c2117,a6qja2,af56c7,afml52,af8esm,auvwoc,a3oxnm,a29b3m,awdk1h,aopnp2,a1xzim,a33k6h,aecfh7,a9j7bh,adfh77,ah7etc,ahkucw,ale3jc,apnmnm,afqnnm,afr4c7,bwa7c7,afqxrw,afrlyc,afufz2,afr3u2,afqnf2,aftpzr,aftdlh,afr9ec,aftkm7,c4eo6h,afv89c,afryjc,afqiww,afqpar,afugmw,afqgvh,afqia2,afq57w,aftz5r,afs84c,aft3k2,c7g9ar,afqoec,afr46h,afs7xm,aft4gh,afqqfr,afr1pr,aft452,afs93m,aftlr7,aftvpr,afti3m,afsc4c,aftjec,afr9yc,aznagh,c2115,c2116,c2118,c211a,c2119,c211d,c211b,c211q,c211c,c211e,c211h,c211f,c211i,c211k,c211g,c211j,c211l,c212a,c2122,c211p,c211n,c211m,c211o,c2135,c212p,c2123&wrapodata=false";

    public FinanceChart getDekaGlobalChampions(UUID activityId) {
        return financeHttpController.getFinanceChart(apiUrlDekaGlobalChampions, activityId);
    }

    public List<FinanceChart> getFinanceCharts(UUID activityId) {
        return financeHttpController.getFinanceCharts(apiUrlFinances, activityId);
    }
}