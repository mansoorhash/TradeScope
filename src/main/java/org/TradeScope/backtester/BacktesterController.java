package org.TradeScope.backtester;

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
    private String selectedStock;

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

            int chartCount =
                controlPanel.getChartCount();

            chartGridPanel.setChartCount(
                chartCount
            );
        });

        controlPanel.addRunListener(e -> {
            runBacktest();
        });
    }

    private void runBacktest() {

        int chartCount = controlPanel.getChartCount();

        String selectedStock = controlPanel.getSelectedStock();

        Map<LocalDate, List<StockBar>> randomDays =
            stocks.getRandomDays(
                selectedStock,
                chartCount
            );

        int chartIndex = 0;

        for (
            Map.Entry<LocalDate, List<StockBar>> entry
            : randomDays.entrySet()
        ) {

            List<StockBar> bars = entry.getValue();
            List<Candle> candles = convertBarsToCandles(bars);

            chartGridPanel.setChartData(chartIndex,candles);
            chartIndex++;
        }
    }

    private List<Candle> convertBarsToCandles(
            List<StockBar> bars
    ) {

        List<Candle> candles =
                new ArrayList<>();

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