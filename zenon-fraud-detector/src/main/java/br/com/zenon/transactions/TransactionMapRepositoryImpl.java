package br.com.zenon.transactions;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionMapRepositoryImpl implements TransactionRepository {

    private final Map<String, Transaction> transactions;

    public TransactionMapRepositoryImpl(final List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactions = transactions.stream()
                .collect(Collectors.toMap(transaction -> transaction.originCustomer().name(), transaction -> transaction));
    }

    @Override
    public Optional<Transaction> findTransactionByOriginCustomerName(String clientName) {
        return Optional.of(transactions.get(clientName));
    }


}
