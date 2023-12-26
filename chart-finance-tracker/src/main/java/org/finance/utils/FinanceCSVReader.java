package org.finance.utils;

import jakarta.enterprise.context.ApplicationScoped;
import org.finance.models.Finance;
import org.finance.models.FinanceOffline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@ApplicationScoped
public class FinanceCSVReader {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public Finance readLastFinanceCSV(String fileName) {
        Finance lastData = null;

        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
            fileReader.readLine();

            String line;
            while ((line = fileReader.readLine()) != null) {

                try (Scanner rowScanner = new Scanner(line)) {
                    rowScanner.useDelimiter(CSVFileProperties.DELIMITER.getValue());
                    lastData = Finance.builder()
                            .price(Float.parseFloat(rowScanner.next()))
                            .differencePrice(Float.parseFloat(rowScanner.next()))
                            .priceChange(Float.parseFloat(rowScanner.next()))
                            .displayName(rowScanner.next())
                            .timeLastUpdated(rowScanner.next())
                            .localDateChange(rowScanner.next())
                            .build();
                } catch (Exception e) {
                    return null;
                }
            }

        } catch (IOException e) {
            handleFileException(fileName);
        }

        return lastData;
    }

    private void handleFileException(String fileName) {
        try {
            Files.createFile(Path.of(fileName));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    public FinanceOffline readFinanceCSV(String fileName) {
        FinanceOffline financeOffline = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
            fileReader.readLine();

            String displayName;
            List<Float> prices = new ArrayList<>();
            List<Float> differencePrices = new ArrayList<>();
            List<String> pricesChanges = new ArrayList<>();
            List<String> timesLastUpdated = new ArrayList<>();
            List<String> localDateChanges = new ArrayList<>();
            String line;
            while ((line = fileReader.readLine()) != null) {
                try (Scanner rowScanner = new Scanner(line)) {
                    rowScanner.useDelimiter(CSVFileProperties.DELIMITER.getValue());

                    prices.add(Float.parseFloat(rowScanner.next()));
                    differencePrices.add(Float.parseFloat(rowScanner.next()));
                    pricesChanges.add(rowScanner.next());
                    displayName = rowScanner.next();
                    timesLastUpdated.add(rowScanner.next());
                    localDateChanges.add(rowScanner.next());

                    financeOffline = FinanceOffline.builder()
                            .displayName(displayName)
                            .prices(prices)
                            .differencePrices(differencePrices)
                            .timesLastUpdated(timesLastUpdated)
                            .pricesChanges(pricesChanges)
                            .localDateChanges(localDateChanges)
                            .build();
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return financeOffline;
    }
}