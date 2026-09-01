package org.TradeScope.api;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import markets.alpaca.client.AlpacaClientFactory;
import markets.alpaca.client.AlpacaCredentials;
import markets.alpaca.client.openapi.data.api.StockApi;
import markets.alpaca.client.openapi.data.model.Sort;
import markets.alpaca.client.openapi.data.model.StockBar;


public class Stocks {
    public Map<LocalDate, List<StockBar>> getRandomDays(String stockname, int count) {

        Map<LocalDate, List<StockBar>> randomDays = new LinkedHashMap<>();

        LocalDate startInclusive = LocalDate.now().minusYears(2);
        LocalDate endExclusive = LocalDate.now().minusDays(1);
        ZoneId marketZone = ZoneId.of("America/New_York");

        try {
            AlpacaCredentials credentials = AlpacaCredentials.fromTradingApiEnvironmentVariables();
            StockApi stockApi = new StockApi(AlpacaClientFactory.dataClient(credentials));

            while (randomDays.size() < count) {
                LocalDate randomDate = getRandomLocalDate(startInclusive, endExclusive);

                if (randomDays.containsKey(randomDate)) {
                    continue;
                }

                if (randomDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                        randomDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    continue;
                }

                OffsetDateTime start = randomDate.atTime(9, 30).atZone(marketZone).toOffsetDateTime();
                OffsetDateTime end = randomDate.atTime(16, 0).atZone(marketZone).toOffsetDateTime();

                var response = stockApi.stockBarSingle(
                        stockname,
                        "1Min",
                        start,
                        end,
                        10000,
                        null,
                        null,
                        null,
                        null,
                        null,
                        Sort.ASC
                );
                System.out.println(response.getBars());
                List<StockBar> bars = response.getBars();


                if (bars == null || bars.isEmpty()) {
                    continue;
                }

                randomDays.put(
                    randomDate,
                    new ArrayList<>(bars)
                );
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return randomDays;
    }

    static LocalDate getRandomLocalDate(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();

        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomEpochDay);
    }
}
