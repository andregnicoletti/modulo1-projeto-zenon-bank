package br.com.zenon.paysim;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public record Transaction(int step,
                          TransactionType type,
                          BigDecimal amount,
                          TransactionCustomer originCustomer,
                          TransactionCustomer recipientCustomer,
                          boolean isFraud,
                          boolean isFlaggedFraud) {

    public Transaction {
        Objects.requireNonNull(type, "type is null");
        Objects.requireNonNull(amount, "amount is null");
        Objects.requireNonNull(originCustomer, "originCustomer is null");
        Objects.requireNonNull(recipientCustomer, "recipientCustomer is null");

        if (step < 1) {
            throw new IllegalArgumentException("Step must be greater than 0");
        }
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
    }

    public static Optional<Transaction> parseRow(final String row) {
        try {
            String[] chunk = row.split(",");

            int step = Integer.parseInt(chunk[0]);

            var type = TransactionType.valueOf(chunk[1]);
            var amount = new BigDecimal(chunk[2]);

            var originCustomerName = chunk[3];
            var originOldBalance = new BigDecimal(chunk[4]);
            var originNewBalance = new BigDecimal(chunk[5]);

            var recipientCustomerName = chunk[6];
            var recipientOldBalance = new BigDecimal(chunk[7]);
            var recipientNewBalance = new BigDecimal(chunk[8]);

            var isFraud = Boolean.parseBoolean(chunk[9]);
            var isFlaggedFraud = Boolean.parseBoolean(chunk[10]);

            var transactional = new Transaction(
                    step,
                    type,
                    amount,
                    new TransactionCustomer(originCustomerName, originOldBalance, originNewBalance),
                    new TransactionCustomer(recipientCustomerName, recipientOldBalance, recipientNewBalance),
                    isFraud,
                    isFlaggedFraud);

            return Optional.of(transactional);

        } catch (Exception e) {
            System.err.println("Error: " + row);
            return Optional.empty();
        }
    }

    public static Transaction parseRowOld(final String row) {
        String[] chunk = row.split(",");

        int step = Integer.parseInt(chunk[0]);
        TransactionType type = TransactionType.valueOf(chunk[1]);
        BigDecimal amount = new BigDecimal(chunk[2]);

        String originCustomerName = chunk[3];
        BigDecimal originOldBalance = new BigDecimal(chunk[4]);
        BigDecimal originNewBalance = new BigDecimal(chunk[5]);

        String recipientCustomerName = chunk[6];
        BigDecimal recipientOldBalance = new BigDecimal(chunk[7]);
        BigDecimal recipientNewBalance = new BigDecimal(chunk[8]);

        boolean isFraud = Boolean.parseBoolean(chunk[9]);
        boolean isFlaggedFraud = Boolean.parseBoolean(chunk[10]);

        return new Transaction(
                step,
                type,
                amount,
                new TransactionCustomer(originCustomerName, originOldBalance, originNewBalance),
                new TransactionCustomer(recipientCustomerName, recipientOldBalance, recipientNewBalance),
                isFraud,
                isFlaggedFraud);
    }

}
