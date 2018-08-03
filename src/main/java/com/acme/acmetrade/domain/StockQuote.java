package com.acme.acmetrade.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class StockQuote {
    private UUID id;
    private Double askPrice;
    private Double bidPrice;
    private Double lastTradedPrice;
    private Integer amountTraded;
    private TickerSymbol tickerSymbol;
    private LocalDateTime timestamp;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(Double askPrice) {
        this.askPrice = askPrice;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Double getLastTradedPrice() {
        return lastTradedPrice;
    }

    public void setLastTradedPrice(Double lastTradedPrice) {
        this.lastTradedPrice = lastTradedPrice;
    }

    public Integer getAmountTraded() {
        return amountTraded;
    }

    public void setAmountTraded(Integer amountTraded) {
        this.amountTraded = amountTraded;
    }

    public TickerSymbol getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(TickerSymbol tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }
}
