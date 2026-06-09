package br.com.zenon;

import br.com.zenon.fraud.FraudAnalyzer;
import br.com.zenon.transactions.TransactionIngestor;
import br.com.zenon.transactions.TransactionListRepositoryImpl;
import br.com.zenon.transactions.TransactionMapRepositoryImpl;
import br.com.zenon.transactions.TransactionType;

import java.util.Map;

public class Main {

    public static final String FILE = "data/ps_log.csv";
    public static final String FILE_ERROR = "data/error.csv";

    void main() throws Exception {
        System.out.printf("Hello and welcome to Zenon Fraud Detector!%n");
        TransactionIngestor transactionIngestor = new TransactionIngestor();
        var transactions = transactionIngestor.read(FILE);

        var fraudAnalyzer = new FraudAnalyzer(transactions);

        //a) Apenas transações onde isFraud == true, imprima o tamanho da lista.
        long fraudCount = fraudAnalyzer.countFrauds();
        IO.println("Total de Fraudes: " + fraudCount);

        //b) Imprima as 3 fraudes de maior valor (amount).
        var highestValueFrauds = fraudAnalyzer.findHighestValueFraudsAmounts(3);
        IO.println("Top 3 Fraudes de Maior Valor:");
        highestValueFrauds.forEach(IO::println);

        //c) Obter apenas os nomes dos clientes de origem (nameOrig) dessas fraudes e depois gere uma lista sem repetições (Set ou distinct) com os 5 maiores clientes suspeitos.
        var suspiciousClient = fraudAnalyzer.findTopSuspiciousClient(5);
        IO.println("Top 5 Clientes Suspeitos:");
        suspiciousClient.forEach(IO::println);

        //d Calcule o prejuízo total causado pelas fraudes (soma dos amount).
        var totalFraudLoss = fraudAnalyzer.calculateTotalFraudLoss();
        IO.println("Prejuízo Total: " + totalFraudLoss);

        //e) Conte quantas fraudes ocorreram por tipo de transação (CASH_OUT, TRANSFER, etc...).
        Map<TransactionType, Long> fraudCountByType = fraudAnalyzer.countFraudsByType();
        IO.println("Fraudes por tipo:");
        fraudCountByType.forEach((type, count) -> {
            IO.println("- %s: %d".formatted(type, count));
        });

        //--------------------------------------------------------------
        IO.println("##################################################");
//        var clientName = "C1868032458";
        var clientName = "C1280323807"; //tail
        var transactionListRepository = new TransactionListRepositoryImpl(transactions);
        long init = System.currentTimeMillis();
        transactionListRepository.findByOriginCustomerName(clientName)
                .ifPresentOrElse(transaction -> IO.println("Transação encontrada: " + transaction),
                        () -> IO.println("Transação não encontrada para o cliente especificado: " + clientName));
        IO.println("Tempo gasto: %d ms".formatted(System.currentTimeMillis() - init));

        var transactionMapRepository = new TransactionMapRepositoryImpl(transactions);
        long init2 = System.currentTimeMillis();
        transactionMapRepository.findByOriginCustomerName(clientName)
                .ifPresentOrElse(transaction -> IO.println("Transação encontrada: " + transaction),
                        () -> IO.println("Transação não encontrada para o cliente especificado: " + clientName));
        IO.println("Tempo gasto: %d ms".formatted(System.currentTimeMillis() - init2));
        
    }
}
