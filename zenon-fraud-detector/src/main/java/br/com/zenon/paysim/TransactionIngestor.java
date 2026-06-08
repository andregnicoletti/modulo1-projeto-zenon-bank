package br.com.zenon.paysim;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TransactionIngestor {

    public List<Transaction> ingestFile(final String fileName) throws Exception {

        Path path = Paths.get(fileName);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));

        List<Transaction> transactions = new ArrayList<>();

        int rows = 0;
        bufferedReader.readLine();
        while (rows < 1000) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }

            transactions.add(Transaction.parseRow(line));
            rows++;
        }

        System.out.println("Finished ingesting file. Total transactions ingested: " + transactions.size());

        return transactions;
    }

}
