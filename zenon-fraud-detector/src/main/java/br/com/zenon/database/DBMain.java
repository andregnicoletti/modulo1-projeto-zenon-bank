package br.com.zenon.database;

import br.com.zenon.transactions.Transaction;
import br.com.zenon.transactions.TransactionIngestor;
import br.com.zenon.transactions.TransactionSQLRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DBMain {

    public static final String FILE = "data/ps_log.csv";
    public static final int READER_LIMIT = 10_000;

    void main() throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        IO.println("Conexão estabelecida: " + connection);


        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Transaction> transactions = transactionIngestor.read(FILE, READER_LIMIT);

        long init = System.currentTimeMillis();
        TransactionSQLRepository transactionSQLRepository = new TransactionSQLRepository();
        transactions.forEach(transactionSQLRepository::save);
        IO.println("Tempo gasto para salvar " + transactions.size() + " transações: " + (System.currentTimeMillis() - init) + " ms");

    }

}

