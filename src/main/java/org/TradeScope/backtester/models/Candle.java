package org.TradeScope.backtester.models;

import java.time.LocalDateTime;

public class Candle {

    private LocalDateTime time;

    private double open;
    private double high;
    private double low;
    private double close;

    private long volume;

    public Candle(
        LocalDateTime time,
        double open,
        double high,
        double low,
        double close,
        long volume
    ) {
        this.time = time;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public long getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return "Candle{" +
                "time=" + time +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                '}';
    }
}