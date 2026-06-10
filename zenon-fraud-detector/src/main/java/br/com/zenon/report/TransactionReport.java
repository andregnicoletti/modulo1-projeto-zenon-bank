package br.com.zenon.report;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class TransactionReport {

    private record Report(BigDecimal amount, boolean isFraud) {
    }

    public record Statistic(long totalTransactions, long totalFrauds, BigDecimal totalAmount) {

        private static final Statistic ZERO = new Statistic(0, 0, BigDecimal.ZERO);

        private Statistic addReportTransaction(Report report) {
            return new Statistic(
                    totalTransactions + 1,
                    totalFrauds + (report.isFraud ? 1 : 0),
                    totalAmount.add(report.amount));
        }

        private Statistic add(Statistic other) {
            return new Statistic(totalTransactions + other.totalTransactions,
                    totalFrauds + other.totalFrauds,
                    totalAmount.add(other.totalAmount));
        }
    }

    public Statistic generateReport(final String fileName) {
        Path path = Path.of(fileName);
        try (Stream<String> lines = Files.lines(path)) {
            return lines.skip(1)
                    .map(this::parseRow)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .reduce(Statistic.ZERO,
                            Statistic::addReportTransaction,
                            Statistic::add);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
    }

    public Statistic generateReportV1(final String fileName) {
        Path path = Path.of(fileName);
        try (Stream<String> lines = Files.lines(path)) {
            return lines.skip(1)
                    .map(this::parseRow)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .reduce(
                            new Statistic(0, 0, BigDecimal.ZERO),
                            (Statistic before, Report report) -> {
                                return new Statistic(
                                        before.totalTransactions + 1,
                                        before.totalFrauds + (report.isFraud ? 1 : 0),
                                        before.totalAmount.add(report.amount));
                            }, (s1, s2) -> s1);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
    }

    private Optional<Report> parseRow(final String row) {
        try {
            String[] chunk = row.split(",");
            var amount = new BigDecimal(chunk[2]);
            var isFraud = "1".equals(chunk[9]);
            var report = new Report(amount, isFraud);
            return Optional.of(report);
        } catch (Exception e) {
            System.err.println("Error: " + row);
            return Optional.empty();
        }
    }

}

