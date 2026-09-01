package org.TradeScope.api;

import markets.alpaca.client.AlpacaClientFactory;
import markets.alpaca.client.AlpacaCredentials;
import markets.alpaca.client.TradingApiEnvironment;
import markets.alpaca.client.openapi.trading.api.AccountsApi;
import markets.alpaca.client.openapi.trading.model.Account;

public class account {
    public void fetchAccount() {
        try {
            AlpacaCredentials credentials = AlpacaCredentials.fromTradingApiEnvironmentVariables();
            AccountsApi accountsApi = new AccountsApi(
                    AlpacaClientFactory.tradingClient(credentials, TradingApiEnvironment.PAPER));
            Account account = accountsApi.getAccount();
            System.out.println("Cash Balance: $" + account.getCash());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
