package com.acme.acmetrade.endpoints;

import com.acme.acmetrade.domain.StockQuote;
import com.acme.acmetrade.services.StockQuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/summary")
public class MarketSummaryEndpoint {

    @Autowired
    private StockQuotesService quoteService;

    @GetMapping("/asc")
    public List<StockQuote> getMarketSummaryAsc(){
        return quoteService.getLatestStockQuotes();
    }
}
