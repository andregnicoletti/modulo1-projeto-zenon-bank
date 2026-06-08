package br.com.zenon.paysim;

import java.math.BigDecimal;

public record Transaction(int step,
                          TransactionType type,
                          BigDecimal amount,
                          TransactionCustomer originCustomer,
                          TransactionCustomer recipientCustomer,
                          boolean isFraud,
                          boolean isFlaggedFraud) {

    public static Transaction parseRow(final String row) {
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
