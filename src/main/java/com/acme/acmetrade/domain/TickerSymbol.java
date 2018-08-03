package com.acme.acmetrade.domain;

public class TickerSymbol {

    private String tickerSymbol;
    private String companyName;
    private String marketSector;

    public String getMarketSector() {
        return marketSector;
    }

    public void setMarketSector(String marketSector) {
        this.marketSector = marketSector;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
