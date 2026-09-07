package org.TradeScope.backtester.ui;

import org.TradeScope.backtester.chart.ChartPanel;
import org.TradeScope.backtester.models.Candle;

import javax.swing.JPanel;

import java.awt.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class ChartGridPanel extends JPanel {

    private final List<ChartPanel> charts;

    private final List<List<Candle>> chartData;
    private final List<Integer> playbackIndexes;

    public ChartGridPanel() {

        charts = new ArrayList<>();
        chartData = new ArrayList<>();
        playbackIndexes = new ArrayList<>();

        setLayout(
            new GridLayout(
                1,
                1,
                5,
                5
            )
        );

        setChartCount(1);
    }

    public void setChartCount(int count) {

        removeAll();
        charts.clear();

        if (count <= 1) {

            setLayout(
                new GridLayout(
                    1,
                    1,
                    5,
                    5
                )
            );

        } else if (count <= 2) {

            setLayout(
                new GridLayout(
                    1,
                    2,
                    5,
                    5
                )
            );

        } else if (count <= 4) {

            setLayout(
                new GridLayout(
                    2,
                    2,
                    5,
                    5
                )
            );

        } else {

            setLayout(
                new GridLayout(
                    2,
                    3,
                    5,
                    5
                )
            );
        }

        for (int i = 0; i < count; i++) {

            ChartPanel chartPanel =
                    new ChartPanel(i + 1);

            charts.add(chartPanel);
            chartData.add(new ArrayList<>());
            playbackIndexes.add(0);

            add(chartPanel);
        }

        revalidate();
        repaint();
    }

    public void setChartData(
            int chartIndex,
            List<Candle> candles
    ) {

        if (
            chartIndex < 0 ||
                chartIndex >= charts.size()
        ) {
            return;
        }

        charts
            .get(chartIndex)
            .getCandlestickChart()
            .setCandles(candles);
    }

    public ChartPanel getChart(int index) {

        if (index < 0 || index >= charts.size()) {
            return null;
        }

        return charts.get(index);
    }

    public int getChartCount() {
        return charts.size();
    }
}
