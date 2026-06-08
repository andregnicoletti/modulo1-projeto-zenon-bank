package br.com.zenon.paysim;

import java.math.BigDecimal;
import java.util.Optional;

public record Transaction(int step,
                          TransactionType type,
                          BigDecimal amount,
                          TransactionCustomer originCustomer,
                          TransactionCustomer recipientCustomer,
                          boolean isFraud,
                          boolean isFlaggedFraud) {

    public static Optional<Transaction> parseRow(final String row) {
        try {
            String[] chunk = row.split(",");

            for (String c : chunk) {
                if (c.trim().isEmpty()) {
                    throw new IllegalArgumentException("Empty value found in row: " + row);
                }
            }

            int step = Integer.parseInt(chunk[0]);
            if (step < 1) {
                throw new IllegalArgumentException("Invalid step: " + step);
            }

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

            validateBigDecimalNumbers(amount, originOldBalance, originNewBalance, recipientOldBalance, recipientNewBalance);

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

    private static void validateBigDecimalNumbers(BigDecimal... values) {
        for (BigDecimal value : values) {
            if (value.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Invalid BigDecimal value: " + value);
            }
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
