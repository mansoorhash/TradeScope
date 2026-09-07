package org.TradeScope.backtester;

import javax.naming.ldap.Control;
import javax.swing.JOptionPane;

import markets.alpaca.client.openapi.data.model.StockBar;

import org.TradeScope.api.Stocks;
import org.TradeScope.backtester.models.Candle;
import org.TradeScope.backtester.ui.ChartGridPanel;
import org.TradeScope.backtester.ui.ControlPanel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BacktesterController {

    private final ControlPanel controlPanel;
    private final ChartGridPanel chartGridPanel;

    private final Stocks stocks;
    private final List<List<Candle>> generatedChartData = new ArrayList<>();
    private final List<Integer> playbackPositions = new ArrayList<>();
    private static final int INITIAL_WINDOW_SIZE = 30;

    private javax.swing.Timer backtestTimer;

    public BacktesterController(
        ControlPanel controlPanel,
        ChartGridPanel chartGridPanel
    ) {
        this.controlPanel = controlPanel;
        this.chartGridPanel = chartGridPanel;

        this.stocks = new Stocks();
        setupListeners();
    }

    private void setupListeners() {

        controlPanel.addChartCountListener(e -> {
            int chartCount = controlPanel.getChartCount();

            chartGridPanel.setChartCount(
                chartCount
            );
            restoreChartData();
            updateFillButton();
        });

        controlPanel.addFillMissingListener(e -> {
            fillMissingData();
        });

        controlPanel.addGenerateDataListener(e -> {
            generateDates();
        });

        controlPanel.addRunListener(e -> {
            runBacktest();
        });

        controlPanel.addStopListener(e -> {
            stopBacktest();
        });
    }

    private void generateDates() {
        int chartCount = controlPanel.getChartCount();
        String selectedStock = controlPanel.getSelectedStock();

        Map<LocalDate, List<StockBar>> randomDays =
            stocks.getRandomDays(
                selectedStock,
                chartCount
            );

        generatedChartData.clear();
        playbackPositions.clear();

        int chartIndex = 0;

        for (
            Map.Entry<LocalDate, List<StockBar>> entry
            : randomDays.entrySet()
        ) {

            List<StockBar> bars = entry.getValue();
            List<Candle> candles = convertBarsToCandles(bars);

            generatedChartData.add(candles);

            int startingPosition =
                Math.min(
                    INITIAL_WINDOW_SIZE,
                    candles.size()
                );

            playbackPositions.add(startingPosition);
            showCurrentWindow(chartIndex);
            chartIndex++;
        }

        updateFillButton();
    }

    private void fillMissingData() {

        int requestedChartCount = controlPanel.getChartCount();
        int existingDataCount = generatedChartData.size();
        int missingCount = requestedChartCount - existingDataCount;

        if (missingCount <= 0) return;

        String selectedStock = controlPanel.getSelectedStock();
        Map<LocalDate, List<StockBar>> newDays =
            stocks.getRandomDays(
                selectedStock,
                missingCount
            );

        for (Map.Entry<LocalDate, List<StockBar>> entry: newDays.entrySet()) {

            List<Candle> candles =
                convertBarsToCandles(
                    entry.getValue()
                );

            generatedChartData.add(candles);

            int startingPosition =
                Math.min(
                    INITIAL_WINDOW_SIZE,
                    candles.size()
                );

            playbackPositions.add(startingPosition);
        }

        restoreChartData();
        updateFillButton();
    }

    private void updateFillButton() {

        boolean missingCharts = (!generatedChartData.isEmpty() && controlPanel.getChartCount() > generatedChartData.size());

        controlPanel.setFillMissingEnabled(missingCharts);
    }

    private void runBacktest() {
        if (generatedChartData.isEmpty()) {

            JOptionPane.showMessageDialog(
                controlPanel,
                "Please generate data before running the backtest.",
                "Data Not Generated",
                JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (backtestTimer != null && backtestTimer.isRunning()) return;

        backtestTimer =
            new javax.swing.Timer(
                500,
                e -> advanceBacktest()
            );
        controlPanel.setRunButtonEnabled(false);
        controlPanel.setStopButtonEnabled(true);

        backtestTimer.start();
    }

    private void advanceBacktest() {

        boolean anyChartStillRunning = false;

        for (int i = 0; i < generatedChartData.size(); i++) {
            List<Candle> candles = generatedChartData.get(i);

            int position = playbackPositions.get(i);

            if (position < candles.size()) {
                position++;
                playbackPositions.set(
                    i,
                    position
                );
                showCurrentWindow(i);
                anyChartStillRunning = true;
            }
        }

        if (!anyChartStillRunning) {
            backtestTimer.stop();
            System.out.println("Backtest complete");
        }
    }

    private void stopBacktest() {
        if (backtestTimer != null && backtestTimer.isRunning()) {
            backtestTimer.stop();
        }

        System.out.println("Backtest stopped");
        controlPanel.setStopButtonEnabled(false);
        controlPanel.setRunButtonEnabled(true);
    }

    private void restoreChartData() {

        int chartCount = controlPanel.getChartCount();
        int chartsToRestore =
            Math.min(
                chartCount,
                generatedChartData.size()
            );

        for (int i = 0; i < chartsToRestore; i++) {
            chartGridPanel.setChartData(
                i,
                generatedChartData.get(i)
            );
        }
    }

    private void showCurrentWindow(int chartIndex) {

        List<Candle> candles = generatedChartData.get(chartIndex);

        int position = playbackPositions.get(chartIndex);

        int start =
            Math.max(
                0,
                position - INITIAL_WINDOW_SIZE
            );

        List<Candle> visibleCandles = new ArrayList<>(
            candles.subList(
                start,
                position
            )
        );

        chartGridPanel.setChartData(
            chartIndex,
            visibleCandles
        );
    }

    private List<Candle> convertBarsToCandles(
        List<StockBar> bars
    ) {

        List<Candle> candles = new ArrayList<>();

        for (StockBar bar : bars) {

            Candle candle =
                new Candle(
                    bar.getT().toLocalDateTime(),
                    bar.getO().doubleValue(),
                    bar.getH().doubleValue(),
                    bar.getL().doubleValue(),
                    bar.getC().doubleValue(),
                    bar.getV().longValue()
                );

            candles.add(candle);
        }

        return candles;
    }
}