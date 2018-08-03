package com.acme.acmetrade.repository;

import com.acme.acmetrade.domain.TickerSymbol;

import java.util.List;

public interface TickerDAO {
    void addTicker(TickerSymbol tickerSymbol);
    TickerSymbol retriveTicker(String tickerSymbol);
    List<TickerSymbol> getAllTickers();
}
