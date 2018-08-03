package com.acme.acmetrade.endpoints;

import com.acme.acmetrade.domain.StockQuote;
import com.acme.acmetrade.domain.TickerSymbol;
import com.acme.acmetrade.services.StockQuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockQuoteEndpoint {

    @Autowired
    private StockQuotesService service;

    @PostMapping
    public StockQuote addStockQuote(@RequestBody StockQuote stockQuote){
        return service.addStockQuote(stockQuote);
    }


    @GetMapping("/from/{from}/to/{to}")
    public List<StockQuote> getStockQuotesSubset(@PathVariable LocalDate from, @PathVariable LocalDate to){
        return service.getStockQuotesSubset(from,to);
    }

    @GetMapping("")
    public List<StockQuote> getAllStockQuotes(){
        return service.getAllStockQuotes();
    }

    @GetMapping("/recent/{ticker}")
    public StockQuote getMostRecentQuote(@PathVariable String ticker){
        TickerSymbol tickerSymbol = new TickerSymbol();
        tickerSymbol.setTickerSymbol(ticker);
        return service.getMostRecentQuote(tickerSymbol);
    }
}
