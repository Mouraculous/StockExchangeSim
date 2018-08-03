package com.acme.acmetrade.services;

import com.acme.acmetrade.domain.TickerSymbol;

import java.util.List;

public interface TickerService {
    default String storeTicker(TickerSymbol tickerSymbol) { return storeTicker(tickerSymbol, 0); }
    String storeTicker(TickerSymbol tickerSymbol, int delay);
    TickerSymbol retrieveTicker(String tickerSymbol);
    List<TickerSymbol> listTickers();
}
