package org.finance.utils;

import jakarta.enterprise.context.ApplicationScoped;
import org.finance.models.Finance;

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


    public List<Finance> readFinanceCSV(String fileName) {
        List<Finance> dataList = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
            fileReader.readLine();

            String line;
            while ((line = fileReader.readLine()) != null) {
                try (Scanner rowScanner = new Scanner(line)) {
                    rowScanner.useDelimiter(CSVFileProperties.DELIMITER.getValue());
                    Finance data = Finance.builder()
                            .price(Float.parseFloat(rowScanner.next()))
                            .differencePrice(Float.parseFloat(rowScanner.next()))
                            .priceChange(Float.parseFloat(rowScanner.next()))
                            .displayName(rowScanner.next())
                            .timeLastUpdated(rowScanner.next())
                            .localDateChange(rowScanner.next())
                            .build();
                    dataList.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}