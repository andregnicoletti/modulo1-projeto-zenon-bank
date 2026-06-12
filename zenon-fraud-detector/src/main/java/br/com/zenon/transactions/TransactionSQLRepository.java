package br.com.zenon.transactions;

import br.com.zenon.database.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TransactionSQLRepository implements TransactionRepository {

    @Override
    public Optional<Transaction> findByOriginCustomerName(String clientName) {

        try {
            String sql = """
                    select  t.step, t.step, t.type, t.amount, t.name_origin, t.old_balance_origin, t.new_balance_origin,
                            t.name_recipient, t.old_balance_recipient, t.new_balance_recipient, t.is_fraud, t.is_flagged_fraud
                    from transactions t where name_origin = ? ;
                    """;

            var connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, clientName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var step = resultSet.getInt("step");
                var type = resultSet.getString("type");
                var amount = resultSet.getBigDecimal("amount");
                var nameOrigin = resultSet.getString("name_origin");
                var oldBalanceOrigin = resultSet.getBigDecimal("old_balance_origin");
                var newBalanceOrigin = resultSet.getBigDecimal("new_balance_origin");
                var nameRecipient = resultSet.getString("name_recipient");
                var oldBalanceRecipient = resultSet.getBigDecimal("old_balance_recipient");
                var newBalanceRecipient = resultSet.getBigDecimal("new_balance_recipient");
                boolean isFraud = resultSet.getBoolean("is_fraud");
                boolean isFlaggedFraud = resultSet.getBoolean("is_flagged_fraud");

                var transactionType = TransactionType.valueOf(type);
                var transactionOrigin = new TransactionCustomer(nameOrigin, oldBalanceOrigin, newBalanceOrigin);
                var transactionRecipient = new TransactionCustomer(nameRecipient, oldBalanceRecipient, newBalanceRecipient);

                var transaction = new Transaction(step, transactionType, amount, transactionOrigin, transactionRecipient, isFraud, isFlaggedFraud);
                return Optional.of(transaction);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error ao buscar transação por nome do cliente: " + clientName, e);
        }

    }

    @Override
    public void save(Transaction transaction) {
            try {
                String sql = """
                        insert into transactions (step, type, amount, name_origin, old_balance_origin, new_balance_origin,
                                                name_recipient, old_balance_recipient, new_balance_recipient, is_fraud, is_flagged_fraud)
                        values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                        """;
    
                var connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, transaction.step());
                preparedStatement.setString(2, transaction.type().name());
                preparedStatement.setBigDecimal(3, transaction.amount());
                preparedStatement.setString(4, transaction.originCustomer().name());
                preparedStatement.setBigDecimal(5, transaction.originCustomer().oldBalance());
                preparedStatement.setBigDecimal(6, transaction.originCustomer().newBalance());
                preparedStatement.setString(7, transaction.recipientCustomer().name());
                preparedStatement.setBigDecimal(8, transaction.recipientCustomer().oldBalance());
                preparedStatement.setBigDecimal(9, transaction.recipientCustomer().newBalance());
                preparedStatement.setBoolean(10, transaction.isFraud());
                preparedStatement.setBoolean(11, transaction.isFlaggedFraud());
    
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error ao salvar transação: " + transaction, e);
            }
    }
}
