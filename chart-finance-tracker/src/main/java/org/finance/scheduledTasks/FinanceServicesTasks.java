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
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

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

    @Scheduled(every = "60s", concurrentExecution = Scheduled.ConcurrentExecution.SKIP)
    public void saveInDekaFileIfPriceHasChanged() {
        saveInFileIfPriceHasChanged(
                DEKA_FILE_PATH,
                this.firstStartDeka,
                financeService::getDekaGlobalChampions,
                financeService::getPreviousFinanceDeka,
                financeService::updatePreviousFinanceDeka,
                priceDifferenceServiceDeka::getDifferencePrice
        );
        this.firstStartDeka = false;
    }

    @Scheduled(every = "17s", concurrentExecution = Scheduled.ConcurrentExecution.SKIP)
    public void saveInBTCFileIfPriceHasChanged() {
        saveInFileIfPriceHasChanged(
                BTC_FILE_PATH,
                this.firstStartBTC,
                financeService::getBTC,
                financeService::getPreviousFinanceBTC,
                financeService::updatePreviousFinanceBTC,
                priceDifferenceServiceBTC::getDifferencePrice
        );
        this.firstStartBTC = false;
    }

    private void saveInFileIfPriceHasChanged(
            CSVFileProperties path,
            boolean isFirstStart,
            Function<UUID, Finance> getCurrentFinance,
            Supplier<Finance> getPreviousFinance,
            Function<Finance, Finance> updatePreviousFinance,
            BiFunction<Finance, Finance, Float> getDifferencePriceFunction
    ) {
        UUID activityId = userService.getActivityId();
        Finance currentFinance = getCurrentFinance.apply(activityId);
        Finance previousFinance = getPreviousFinance.get();

        if (isFirstStart) {
            String methodName = currentFinance.getDisplayName().trim();
            LOGGER.debug("Task save{}InFileIfPriceHasChanged() started...", methodName);
            if (previousFinance == null) {
                handleFirstExecutionWithNoDataInFile(currentFinance, path);
            }
            return;
        }

        float differencePrice = getDifferencePriceFunction.apply(currentFinance, previousFinance);

        if (differencePrice != 0f) {
            updatePreviousFinance.apply(currentFinance);
        }

        if (checkIfDiffIsToSaveToType(path, differencePrice)) {
            LOGGER.debug("Saving in {}", currentFinance.getDisplayName().trim());
            handleSaveInFile(currentFinance, differencePrice, path);
        }
    }

    private void handleSaveInFile(Finance currentFinance, float differencePrice, CSVFileProperties path) {
        currentFinance.setDifferencePrice(differencePrice);
        LOGGER.info("Difference for {} was: {} EUR", currentFinance.getDisplayName(), differencePrice);

        financeCSVWriter.appendFinanceCSV(path.getValue(), currentFinance);
    }

    private void handleFirstExecutionWithNoDataInFile(Finance finance, CSVFileProperties path) {
        LOGGER.info("Inserting first data of {}...", finance.getDisplayName());
        finance.setPriceChange(finance.getPrice());
        finance.setDifferencePrice(finance.getPrice());
        finance.setLocalDateChange(String.valueOf(Instant.now().toEpochMilli()));
        financeCSVWriter.appendFinanceCSV(path.getValue(), finance);
        switch (path) {
            case DEKA_FILE_PATH:
                financeService.updatePreviousFinanceDeka(finance);
                break;
            case BTC_FILE_PATH:
                financeService.updatePreviousFinanceBTC(finance);
                break;
        }
    }

    private boolean checkIfDiffIsToSaveToType(CSVFileProperties path, float differencePrice) {
        switch (path) {
            case DEKA_FILE_PATH:
                return differencePrice < -0.2f || differencePrice > 0.2f;
            case BTC_FILE_PATH:
                return differencePrice < -2f || differencePrice > 2f;

        }
        return false;
    }
}