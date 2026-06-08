package br.com.zenon;

import br.com.zenon.fraud.FraudAnalyzer;
import br.com.zenon.transactions.Transaction;
import br.com.zenon.transactions.TransactionIngestor;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static final String FILE = "data/ps_log.csv";
    public static final String FILE_ERROR = "data/error.csv";

    void main() throws Exception {
        System.out.printf("Hello and welcome to Zenon Fraud Detector!%n");
        TransactionIngestor transactionIngestor = new TransactionIngestor();
        var transactions = transactionIngestor.read(FILE);

        //Listando as fraudes apenas
        var fraudAnalyzers = transactions
                .stream()
                .filter(Transaction::isFraud)
                .map(FraudAnalyzer::toFraudAnalyzer)
                .toList();

        //a) Apenas transações onde isFraud == true, imprima o tamanho da lista.
        IO.println("Total de Fraudes: " + fraudAnalyzers.size());

        //b) Imprima as 3 fraudes de maior valor (amount).
        IO.println("Top 3 Fraudes de Maior Valor:");
        fraudAnalyzers.stream()
                .sorted((f1, f2) -> f2.amount().compareTo(f1.amount()))
                .limit(3)
                .forEach(System.out::println);


        //c) Obter apenas os nomes dos clientes de origem (nameOrig) dessas fraudes e depois gere uma lista sem repetições (Set ou distinct) com os 5 maiores clientes suspeitos.
        Set<String> collect = fraudAnalyzers.stream()
                .sorted((f1, f2) -> f2.amount().compareTo(f1.amount()))
                .distinct()
                .limit(5)
                .map(FraudAnalyzer::nameOrigin)
                .collect(Collectors.toSet());
        IO.println("Clientes Suspeitos:");
        collect.forEach(System.out::println);
        
        //d Calcule o prejuízo total causado pelas fraudes (soma dos amount).
        var prejuizoTotal = fraudAnalyzers.stream()
                .map(FraudAnalyzer::amount)
                .sorted(BigDecimal::compareTo)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        IO.println("Prejuízo Total: " + prejuizoTotal);
        
        //e) Conte quantas fraudes ocorreram por tipo de transação (CASH_OUT, TRANSFER, etc...).
        fraudAnalyzers.stream()
                .collect(Collectors.groupingBy(FraudAnalyzer::type, Collectors.counting()))
                .forEach((tipo, quantidade) -> System.out.println("- " + tipo + ": " + quantidade));
        
    }
}
