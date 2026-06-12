package br.com.zenon.transactions;

import java.sql.SQLException;
import java.util.Optional;

public interface TransactionRepository {

    Optional<Transaction> findByOriginCustomerName(String clientName);
    
    void save(Transaction transaction);
    
}
