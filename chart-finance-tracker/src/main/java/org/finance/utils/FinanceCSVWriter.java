package org.finance.utils;

import jakarta.enterprise.context.ApplicationScoped;
import org.finance.models.Finance;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

import static org.finance.utils.CSVFileProperties.CSV_HEADER;

@ApplicationScoped
public class FinanceCSVWriter {

    public void writeFinanceCSV(String fileName, Finance... dataList) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName))) {
            fileWriter.write(CSV_HEADER.getValue());
            fileWriter.newLine();

            for (Finance data : dataList) {
                fileWriter.write(
                        data.getId() + "," +
                                data.getPrice() + "," +
                                data.getPriceChange() + "," +
                                data.getDisplayName() + "," +
                                data.getTimeLastUpdated()
                );
                fileWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void appendFinanceCSV(String fileName, Finance... dataList) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName, true))) {
            for (Finance data : dataList) {
                fileWriter.write(
                        data.getPrice() + "," +
                                data.getDifferencePrice() + "," +
                                data.getPriceChange() + "," +
                                data.getDisplayName() + "," +
                                data.getTimeLastUpdated() + "," +
                                Instant.now().toEpochMilli()
                );
            }

            fileWriter.newLine();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}