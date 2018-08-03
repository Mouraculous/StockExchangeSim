package com.acme.acmetrade.services;

import com.acme.acmetrade.domain.StockQuote;
import com.acme.acmetrade.domain.TickerSymbol;
import com.acme.acmetrade.repository.StockQuotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.IdGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class StockQuotesServiceImpl implements StockQuotesService {
    @Autowired
    private StockQuotesRepository repository;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    public StockQuote addStockQuote(StockQuote stockQuote) {
        stockQuote.setId(idGenerator.generateId());
        stockQuote.setTimestamp(LocalDateTime.now());
        return repository.addStockQuote(stockQuote);
    }

    @Override
    public List<StockQuote> getStockQuotesSubset(LocalDate from, LocalDate to) {
        return repository.getStockQuotesSubset(from,to);
    }

    @Override
    public List<StockQuote> getAllStockQuotes() {
        return repository.getAllStockQuotes();
    }

    @Override
    public List<StockQuote> getLatestStockQuotes() {
        return repository.getLatestStockQuotes();
    }

    @Override
    public StockQuote getMostRecentQuote(TickerSymbol ticker) {
        return repository.getMostRecentQuote(ticker);
    }

    @Override
    public List<StockQuote> getQuotesBySymbol(TickerSymbol tickerSymbol) {
        return repository.getQuotesBySymbol(tickerSymbol);
    }

    public void createChart(TickerSymbol tickerSymbol){
        repository.createChart(tickerSymbol);
    }
}
