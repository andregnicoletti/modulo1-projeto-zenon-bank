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
        String[] split = row.split(",");
        return new Transaction(
                Integer.parseInt(split[0]),
                TransactionType.valueOf(split[1].toUpperCase()),
                new BigDecimal(split[2]),
                new TransactionCustomer(split[3],new BigDecimal(split[4]),new BigDecimal(split[5])),
                new TransactionCustomer(split[6],new BigDecimal(split[7]),new BigDecimal(split[8])),
                Boolean.parseBoolean(split[9]),
                Boolean.parseBoolean(split[10]));
    }

}
