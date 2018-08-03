package com.acme.acmetrade.services;

import com.acme.acmetrade.domain.StockQuote;
import com.acme.acmetrade.domain.TickerSymbol;

import java.time.LocalDate;
import java.util.List;

public interface StockQuotesService {
    StockQuote addStockQuote(StockQuote stockQuote);
    List<StockQuote> getStockQuotesSubset(LocalDate from, LocalDate to);
    List<StockQuote> getAllStockQuotes();
    List<StockQuote> getLatestStockQuotes();
    StockQuote getMostRecentQuote(TickerSymbol ticker);
    List<StockQuote> getQuotesBySymbol(TickerSymbol tickerSymbol);
    void createChart(TickerSymbol tickerSymbol);

}
