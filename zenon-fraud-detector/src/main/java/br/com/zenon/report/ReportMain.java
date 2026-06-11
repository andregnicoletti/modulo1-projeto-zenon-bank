package br.com.zenon.report;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReportMain {

    public static final String FILE = "data/ps_log.csv";
    public static final String FILE_ERROR = "data/error.csv";

    void main(String[] args) {

        String language = args.length > 0 ? args[0] : "en";
        var locale = Locale.of(language);

        var integerFormatter = NumberFormat.getIntegerInstance(locale);
        var currencyFormat = DecimalFormat.getCurrencyInstance(locale);
        currencyFormat.setCurrency(Currency.getInstance("USD"));

        var resourceBundle = ResourceBundle.getBundle("report", locale);

        var transactionReport = new TransactionReport();
        var statistic = transactionReport.generateReport(FILE);

        String formatTotalTransactions = integerFormatter.format(statistic.totalTransactions());
        String formatTotalFraud = integerFormatter.format(statistic.totalFrauds());
        String formatTotalAmount = currencyFormat.format(statistic.totalAmount());

        String msgTotalTransactions = resourceBundle.getString("label.total.transactions");
        String msgTotalFrauds = resourceBundle.getString("label.total.frauds");
        String msgTotalAmount = resourceBundle.getString("label.total.amount");

        IO.println("""
                %s: %s
                %s: %s
                %s: %s
                """.formatted(msgTotalTransactions, formatTotalTransactions,
                msgTotalFrauds, formatTotalFraud,
                msgTotalAmount, formatTotalAmount));

    }

}
