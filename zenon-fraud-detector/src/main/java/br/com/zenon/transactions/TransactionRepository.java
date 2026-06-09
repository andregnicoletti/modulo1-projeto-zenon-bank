package br.com.zenon.transactions;

import java.util.Optional;

public interface TransactionRepository {

    Optional<Transaction> findTransactionByOriginCustomerName(String clientName);
    
}
