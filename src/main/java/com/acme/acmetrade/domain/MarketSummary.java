package com.acme.acmetrade.domain;

public class MarketSummary {
    private TickerSymbol tickerSymbol;
    private Integer amountTraded;
    private Double latestPrice;

    public TickerSymbol getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(TickerSymbol tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public Integer getAmountTraded() {
        return amountTraded;
    }

    public void setAmountTraded(Integer amountTraded) {
        this.amountTraded = amountTraded;
    }

    public Double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(Double latestPrice) {
        this.latestPrice = latestPrice;
    }
}
