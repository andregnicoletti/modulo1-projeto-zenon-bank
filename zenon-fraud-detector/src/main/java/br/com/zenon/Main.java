package br.com.zenon;

import br.com.zenon.paysim.Transaction;
import br.com.zenon.paysim.TransactionIngestor;

import java.util.List;

public class Main {
  
    void main() throws Exception {
        System.out.printf("Hello and welcome to Zenon Fraud Detector!%n");

        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Transaction> transactions = transactionIngestor.ingestFile("data/archive/PS_20174392719_1491204439457_log.csv");
        transactions.forEach(System.out::println);

        
    }
}
