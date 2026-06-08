package br.com.zenon.fraud;

import br.com.zenon.transactions.Transaction;
import br.com.zenon.transactions.TransactionType;

import java.math.BigDecimal;

public record FraudAnalyzer(BigDecimal amount,
                            String nameOrigin,
                            TransactionType type) {

    public static FraudAnalyzer toFraudAnalyzer(Transaction transaction) {
        return new FraudAnalyzer(transaction.amount(), transaction.originCustomer().name(), transaction.type());
    }

}
