package br.com.zenon.fraud;

import br.com.zenon.transactions.Transaction;
import br.com.zenon.transactions.TransactionType;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class FraudAnalyzer {

    private final List<Transaction> transactions;

    public FraudAnalyzer(final List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactions = transactions;
    }

    public long countFrauds() {
        return transactions.stream()
                .filter(Transaction::isFraud)
                .count();
    }


    public List<BigDecimal> findHighestValueFraudsAmounts(final int limit) {
        return transactions.stream()
                .filter(Transaction::isFraud)
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .map(Transaction::amount)
                .limit(limit)
                .toList();
    }

    public Set<String> findTopSuspiciousClient(final int limit) {
        return transactions.stream()
                .filter(Transaction::isFraud)
                .sorted(Comparator.comparing(Transaction::amount))
                .map(transaction -> transaction.originCustomer().name())
                .distinct()
                .limit(limit)
                .collect(Collectors.toSet());
    }

    public BigDecimal calculateTotalFraudLoss() {
        return transactions.stream()
                .filter(Transaction::isFraud)
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<TransactionType, Long> countFraudsByType() {
        return transactions.stream()
                .filter(Transaction::isFraud)
                .collect(Collectors.groupingBy(Transaction::type, Collectors.counting()));
    }
}
