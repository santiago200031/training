package org.finance.scheduledTasks;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.finance.models.Finance;
import org.finance.services.FinanceService;
import org.finance.services.UserService;
import org.finance.services.priceDifferences.PriceDifferenceBTCService;
import org.finance.services.priceDifferences.PriceDifferenceDekaService;
import org.finance.utils.CSVFileProperties;
import org.finance.utils.FinanceCSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.UUID;

import static org.finance.utils.CSVFileProperties.BTC_FILE_PATH;
import static org.finance.utils.CSVFileProperties.DEKA_FILE_PATH;

@ApplicationScoped
public class FinanceServicesTasks {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private boolean firstStartDeka = true, firstStartBTC = true;

    @Inject
    FinanceService financeService;

    @Inject
    PriceDifferenceDekaService priceDifferenceServiceDeka;

    @Inject
    PriceDifferenceBTCService priceDifferenceServiceBTC;

    @Inject
    UserService userService;

    @Inject
    FinanceCSVWriter financeCSVWriter;

    //apikey=0QfOX3Vn51YCzitbLaRkTTBadtWpgTN8NZLW0C1SEM

    @Scheduled(every = "25s", concurrentExecution = Scheduled.ConcurrentExecution.SKIP)
    public void saveInDekaFileIfPriceHasChanged() {
        UUID activityId = userService.getActivityId();
        Finance currentDeka = financeService.getDekaGlobalChampions(activityId);
        Finance previousFinance = financeService.getPreviousFinanceDeka();

        if (this.firstStartDeka) {
            LOGGER.info("Task saveInDekaFileIfPriceHasChanged() started...");
            if (previousFinance == null) {
                handleFirstExecutionWithNoDataInFile(currentDeka, DEKA_FILE_PATH);
            }
            this.firstStartDeka = false;
            return;
        }

        assert previousFinance != null;
        float differencePrice = priceDifferenceServiceDeka.getDifferencePrice(currentDeka, previousFinance);

        if (checkIfDiffIsToSave(differencePrice)) {
            LOGGER.info("Saving in {}", currentDeka);
            handleSaveInFile(currentDeka, differencePrice, DEKA_FILE_PATH);

        }
    }

    //@Scheduled(every = "10s", concurrentExecution = Scheduled.ConcurrentExecution.SKIP)
    public void saveInBTCFileIfPriceHasChanged() {
        UUID activityId = userService.getActivityId();
        Finance currentBTC = financeService.getBTC(activityId);
        Finance previousFinance = financeService.getPreviousFinanceBTC();

        if (this.firstStartBTC) {
            LOGGER.info("Task saveInBTCFileIfPriceHasChanged() started...");
            if (previousFinance == null) {
                handleFirstExecutionWithNoDataInFile(currentBTC, BTC_FILE_PATH);
            }
            this.firstStartBTC = false;
            return;
        }
        if (previousFinance == null) {
            previousFinance = currentBTC;
        }

        assert previousFinance != null;
        float differencePrice = priceDifferenceServiceBTC.getDifferencePrice(currentBTC, previousFinance);

        if (checkIfDiffIsToSave(differencePrice)) {
            LOGGER.info("Saving in {}", currentBTC);
            handleSaveInFile(currentBTC, differencePrice, BTC_FILE_PATH);

        }
    }

    private boolean checkIfDiffIsToSave(float differencePrice) {
        return differencePrice < -1f || differencePrice > 1f;
    }

    private void handleSaveInFile(Finance currentFinance, float differencePrice, CSVFileProperties path) {
        currentFinance.setDifferencePrice(differencePrice);

        switch (path) {
            case DEKA_FILE_PATH:
                financeService.setPreviousFinanceDeka(currentFinance);
                financeCSVWriter.appendFinanceCSV(DEKA_FILE_PATH.getValue(), currentFinance);
                break;
            case BTC_FILE_PATH:
                financeService.setPreviousFinanceBTC(currentFinance);
                financeCSVWriter.appendFinanceCSV(BTC_FILE_PATH.getValue(), currentFinance);
                break;
        }
    }

    private void handleFirstExecutionWithNoDataInFile(Finance finance, CSVFileProperties path) {
        LOGGER.info("Inserting first data of " + finance.getDisplayName() + " ...");
        finance.setPriceChange(finance.getPrice());
        finance.setDifferencePrice(finance.getPrice());
        finance.setLocalDateChange(String.valueOf(Instant.now().toEpochMilli()));
        financeCSVWriter.appendFinanceCSV(path.getValue(), finance);
        switch (path) {
            case DEKA_FILE_PATH:
                financeService.setPreviousFinanceDeka(finance);
                break;
            case BTC_FILE_PATH:
                financeService.setPreviousFinanceBTC(finance);
                break;
        }
    }
}