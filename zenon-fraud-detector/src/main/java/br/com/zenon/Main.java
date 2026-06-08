package br.com.zenon;

import br.com.zenon.paysim.Transaction;
import br.com.zenon.paysim.TransactionIngestor;

import java.util.List;

public class Main {

    public static final String FILE = "data/ps_lFILE_ERRORog.csv";
    public static final String FILE_ERROR = "data/error.csv";

    void main() throws Exception {
        System.out.printf("Hello and welcome to Zenon Fraud Detector!%n");
        TransactionIngestor transactionIngestor = new TransactionIngestor();
        transactionIngestor.read(FILE_ERROR)
                .stream()
                .limit(1000)
                .forEach(IO::println);
    }
}
