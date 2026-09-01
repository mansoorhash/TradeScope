package org.TradeScope.backtester.chart;

import org.TradeScope.backtester.models.Candle;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

public class CandlestickChart extends JPanel {

    private List<Candle> candles;

    private final int padding = 40;

    public CandlestickChart() {
        candles = new ArrayList<>();
        setBackground(Color.WHITE);
    }

    public void setCandles(List<Candle> candles) {
        this.candles = candles;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        drawAxes(g2);

        if (candles == null || candles.isEmpty()) {
            drawNoDataMessage(g2);
            return;
        }

        drawCandles(g2);
    }

    private void drawAxes(Graphics2D g2) {

        int width = getWidth();
        int height = getHeight();

        g2.setColor(Color.DARK_GRAY);

        g2.drawLine(
            padding,
            padding,
            padding,
            height - padding
        );

        g2.drawLine(
            padding,
            height - padding,
            width - padding,
            height - padding
        );
    }

    private void drawNoDataMessage(Graphics2D g2) {
        g2.setColor(Color.GRAY);

        g2.drawString(
            "No candle data",
            getWidth() / 2 - 40,
            getHeight() / 2
        );
    }

    private void drawCandles(Graphics2D g2) {

        double highestPrice = findHighestPrice();
        double lowestPrice = findLowestPrice();

        int chartWidth = getWidth() - (padding * 2);

        int chartHeight = getHeight() - (padding * 2);

        double candleSpacing = (double) chartWidth / candles.size();

        int candleWidth = Math.max(4, (int) (candleSpacing * 0.6));

        for (int i = 0; i < candles.size(); i++) {

            Candle candle = candles.get(i);

            int centerX =
                padding +
                    (int) (i * candleSpacing)
                    +
                    (int) (candleSpacing / 2);

            int highY = priceToY(
                candle.getHigh(),
                highestPrice,
                lowestPrice,
                chartHeight
            );

            int lowY = priceToY(
                candle.getLow(),
                highestPrice,
                lowestPrice,
                chartHeight
            );

            int openY = priceToY(
                candle.getOpen(),
                highestPrice,
                lowestPrice,
                chartHeight
            );

            int closeY = priceToY(
                candle.getClose(),
                highestPrice,
                lowestPrice,
                chartHeight
            );

            boolean bullish = candle.getClose() >= candle.getOpen();

            if (bullish) {
                g2.setColor(new Color(0, 150, 0));
            } else {
                g2.setColor(new Color(200, 0, 0));
            }

            // Wick
            g2.drawLine(
                centerX,
                highY,
                centerX,
                lowY
            );

            int bodyTop = Math.min(openY, closeY);

            int bodyBottom = Math.max(openY, closeY);

            int bodyHeight = Math.max(2, bodyBottom - bodyTop);

            int bodyX = centerX - candleWidth / 2;

            // Candle body
            g2.fillRect(
                    bodyX,
                    bodyTop,
                    candleWidth,
                    bodyHeight
            );
        }
    }

    private int priceToY(
        double price,
        double highestPrice,
        double lowestPrice,
        int chartHeight
    ) {

        double priceRange = highestPrice - lowestPrice;

        if (priceRange == 0) {
            return getHeight() / 2;
        }

        double percentage = (price - lowestPrice) / priceRange;

        return padding + chartHeight - (int) (percentage * chartHeight);
    }

    private double findHighestPrice() {

        double highest = Double.MIN_VALUE;

        for (Candle candle : candles) {
            highest = Math.max(
                highest,
                candle.getHigh()
            );
        }

        return highest;
    }

    private double findLowestPrice() {

        double lowest = Double.MAX_VALUE;

        for (Candle candle : candles) {
            lowest = Math.min(
                lowest,
                candle.getLow()
            );
        }

        return lowest;
    }
}