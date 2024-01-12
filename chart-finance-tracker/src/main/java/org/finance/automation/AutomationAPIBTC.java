package org.finance.automation;

import jakarta.enterprise.context.ApplicationScoped;
import org.finance.utils.ExternalApiEndpoints;
import org.finance.utils.URLParamsConstants;

@ApplicationScoped
public class AutomationAPIBTC implements TradeActions {
    //TODO Take to config file to make configurable and use everywhere
    private final String BTC_TO_BUY = "2";
    private final String BTC_TO_SELL = "0.00009";


    @Override
    public void doBuy() {
        String URL = ExternalApiEndpoints.URL_BTC_BUY_BTC.replace(URLParamsConstants.AMOUNT_PARAM.getValue(), BTC_TO_BUY);

    }

    @Override
    public void doSell() {
        String URL = ExternalApiEndpoints.URL_BTC_SELL_BTC.replace(URLParamsConstants.AMOUNT_PARAM.getValue(), BTC_TO_SELL);

    }
}
