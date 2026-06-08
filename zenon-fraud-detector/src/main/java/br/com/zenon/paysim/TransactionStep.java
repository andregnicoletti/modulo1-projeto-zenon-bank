package br.com.zenon.paysim;

public enum TransactionStep {

    STEP_1(1),
    STEP_2(2),
    STEP_3(3),
    STEP_4(4),
    STEP_5(5),
    STEP_6(6),
    STEP_7(7),
    STEP_8(8),
    STEP_9(9),
    STEP_10(10);

    private final int value;

    TransactionStep(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}
