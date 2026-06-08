package br.com.zenon.transactions;

import java.math.BigDecimal;
import java.util.Objects;

public record TransactionCustomer(String name,
                                  BigDecimal oldBalance,
                                  BigDecimal newBalance) {
    public TransactionCustomer {
        Objects.requireNonNull(name, "Name is required");
        Objects.requireNonNull(oldBalance, "OldBalance is required");
        Objects.requireNonNull(newBalance, "NewBalance is required");

        if (oldBalance.signum() < 0) {
            throw new IllegalArgumentException("O valor deve ser positivo e maior que zero: " + oldBalance);
        }

        if (newBalance.signum() < 0) {
            throw new IllegalArgumentException("O valor deve ser positivo e maior que zero: " + newBalance);
        }

        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser vazio");
        }
    }

}
