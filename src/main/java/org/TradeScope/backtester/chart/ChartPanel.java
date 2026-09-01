package org.TradeScope.backtester.chart;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class ChartPanel extends JPanel {

    private CandlestickChart candlestickChart;

    public ChartPanel(int count) {
        setLayout(new BorderLayout());

        setBorder(
            BorderFactory.createTitledBorder("Chart " + count)
        );

        candlestickChart = new CandlestickChart();

        add(candlestickChart, BorderLayout.CENTER);
    }

    public CandlestickChart getCandlestickChart() {
        return candlestickChart;
    }
}

