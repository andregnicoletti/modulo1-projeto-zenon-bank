package br.com.zenon.report;

public class ReportMain {

    public static final String FILE = "data/ps_log.csv";
    public static final String FILE_ERROR = "data/error.csv";

    void main() {

        var transactionReport = new TransactionReport();
        var statistic = transactionReport.generateReport(FILE);
        IO.println("""
                Total de linhas: %d
                Total de fraudes: %d
                Valor Total Transacionado: %.2f
                """.formatted(statistic.totalTransactions(), statistic.totalFrauds(), statistic.totalAmount()));

    }

}
