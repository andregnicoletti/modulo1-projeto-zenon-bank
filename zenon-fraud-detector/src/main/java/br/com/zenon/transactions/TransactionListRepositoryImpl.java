package br.com.zenon.transactions;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TransactionListRepositoryImpl implements TransactionRepository {

    private final List<Transaction> transactions;

    public TransactionListRepositoryImpl(final List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactions = transactions;
    }

    @Override
    public Optional<Transaction> findByOriginCustomerName(String clientName) {
        return transactions.stream()
                .filter(transaction -> transaction.originCustomer().name().equalsIgnoreCase(clientName))
                .findFirst();
    }

    @Override
    public void save(Transaction transaction) {
        transactions.add(transaction);
    }


}
