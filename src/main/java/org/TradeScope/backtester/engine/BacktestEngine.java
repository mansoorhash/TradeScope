package org.TradeScope.backtester.engine;

import org.TradeScope.backtester.models.Candle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BacktestEngine {

    private Random random;

    public BacktestEngine() {
        random = new Random();
    }

    public List<Candle> generateTestCandles(
        int amount,
        double startingPrice
    ) {

        List<Candle> candles = new ArrayList<>();

        double previousClose = startingPrice;

        LocalDateTime time = LocalDateTime.now().minusDays(amount);

        for (int i = 0; i < amount; i++) {

            double open = previousClose;
            double change = random.nextDouble(-3, 3);
            double close = open + change;
            double high = Math.max(open, close) + random.nextDouble(0.2, 2);
            double low = Math.min(open, close) - random.nextDouble(0.2, 2);

            long volume = random.nextLong(
                100_000,
                1_000_000
            );

            Candle candle = new Candle(
                time,
                open,
                high,
                low,
                close,
                volume
            );

            candles.add(candle);

            previousClose = close;

            time = time.plusDays(1);
        }

        return candles;
    }
}