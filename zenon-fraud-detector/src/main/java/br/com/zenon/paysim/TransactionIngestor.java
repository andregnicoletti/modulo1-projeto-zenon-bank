package br.com.zenon.paysim;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionIngestor {

    private List<Transaction> transactions = new ArrayList<>();

    public List<Transaction> read(final String fileName) {

        Path path = Path.of(fileName);
        try {
            List<String> lines = Files.readAllLines(path);
            return lines.stream()
                    .skip(1)
                    .limit(1000)
                    .map(Transaction::parseRow)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
    }

    public List<Transaction> readOldSchool(final String fileName) {
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            Scanner scanner = new Scanner(fileInputStream);
            int rowsCount = 0;
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                rowsCount++;

                if (rowsCount == 1) {
                    continue;
                }

                if (rowsCount >= 1000) {
                    break;
                }
                transactions.add(Transaction.parseRow(row));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
        return transactions;
    }


}
