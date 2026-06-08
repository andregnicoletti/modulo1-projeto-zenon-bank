package br.com.zenon;

import br.com.zenon.paysim.Transaction;
import br.com.zenon.paysim.TransactionType;

import java.math.BigDecimal;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        System.out.printf("Hello and welcome to Zenon Fraud Detector!%n");

        final var transaction1 = new Transaction(1,
                TransactionType.PAYMENT,
                BigDecimal.valueOf(9839.64),
                "C1231006815",
                BigDecimal.valueOf(170136.0),
                BigDecimal.valueOf(160296.36),
                "M1979787155",
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                false,
                false);

        final var transaction2 = new Transaction(743,
                TransactionType.CASH_OUT,
                BigDecimal.valueOf(850002.52),
                "C1280323807",
                BigDecimal.valueOf(850002.52),
                BigDecimal.ZERO,
                "C873221189",
                BigDecimal.valueOf(6510099.11),
                BigDecimal.valueOf(7360101.63),
                true,
                false);

        System.out.println("Transaction 1: " + transaction1);
        System.out.println("Transaction 2: " + transaction2);

    }
}
