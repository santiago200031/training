package org.finance.scheduledTasks;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.finance.models.Finance;
import org.finance.services.FinanceService;
import org.finance.services.UserService;
import org.finance.services.priceDifferences.PriceDifferenceBTCService;
import org.finance.services.priceDifferences.PriceDifferenceDekaService;
import org.finance.utils.FinanceCSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@ApplicationScoped
public class FinanceServicesTasks {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ConfigProperty(name = "resources.deka.csv-file")
    private String dekaCsvFile;

    @ConfigProperty(name = "resources.btc.csv-file")
    private String btcCsvFile;

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

    private boolean firstStartDeka = true, firstStartBTC = true;

    @Scheduled(every = "60s", concurrentExecution = Scheduled.ConcurrentExecution.SKIP)
    public void saveInDekaFileIfPriceHasChanged() {
        saveInFileIfPriceHasChanged(
                dekaCsvFile,
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
                btcCsvFile,
                this.firstStartBTC,
                financeService::getBTC,
                financeService::getPreviousFinanceBTC,
                financeService::updatePreviousFinanceBTC,
                priceDifferenceServiceBTC::getDifferencePrice
        );
        this.firstStartBTC = false;
    }

    private void saveInFileIfPriceHasChanged(
            String path,
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

    private void handleSaveInFile(Finance currentFinance, float differencePrice, String path) {
        currentFinance.setDifferencePrice(differencePrice);
        LOGGER.info("Difference for {} was: {} EUR", currentFinance.getDisplayName(), differencePrice);

        financeCSVWriter.appendFinanceCSV(path, currentFinance);
    }

    private void handleFirstExecutionWithNoDataInFile(Finance finance, String path) {
        LOGGER.info("Inserting first data of {}...", finance.getDisplayName());
        finance.setPriceChange(finance.getPrice());
        finance.setDifferencePrice(finance.getPrice());
        finance.setLocalDateChange(String.valueOf(Instant.now().toEpochMilli()));
        financeCSVWriter.appendFinanceCSV(path, finance);

        if (path.equals(dekaCsvFile)) {
            financeService.updatePreviousFinanceDeka(finance);
            return;
        }

        if (path.equals(btcCsvFile)) {
            financeService.updatePreviousFinanceBTC(finance);
            return;
        }
    }

    private boolean checkIfDiffIsToSaveToType(String path, float differencePrice) {
        if (path.equals(dekaCsvFile)) {
            return differencePrice < -0.2f || differencePrice > 0.2f;
        }

        if (path.equals(btcCsvFile)) {
            return differencePrice < -2f || differencePrice > 2f;
        }

        return false;
    }
}