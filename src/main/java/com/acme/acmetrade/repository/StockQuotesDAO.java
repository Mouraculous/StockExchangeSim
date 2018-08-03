package com.acme.acmetrade.repository;

import com.acme.acmetrade.domain.StockQuote;
import com.acme.acmetrade.domain.TickerSymbol;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface StockQuotesDAO {
    StockQuote addStockQuote(StockQuote order);
    UUID updateStockQoute(StockQuote order);
    List<StockQuote> getAllStockQuotes();
    List<StockQuote> getLatestStockQuotes();
    StockQuote getMostRecentQuote(TickerSymbol tickerSymbol);
    List<StockQuote>getStockQuotesSubset(LocalDate from, LocalDate to);
    List<StockQuote> getQuotesBySymbol(TickerSymbol tickerSymbol);
    void createChart(TickerSymbol tickerSymbol);
}
