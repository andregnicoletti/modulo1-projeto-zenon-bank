package br.com.zenon;

import br.com.zenon.paysim.Transaction;
import br.com.zenon.paysim.TransactionIngestor;

import java.util.List;

public class Main {

    public static final String FILE = "data/ps_log.csv";

    void main() throws Exception {
        System.out.printf("Hello and welcome to Zenon Fraud Detector!%n");
        TransactionIngestor transactionIngestor = new TransactionIngestor();
        transactionIngestor.read(FILE).stream().limit(10).forEach(IO::println);
    }
}
