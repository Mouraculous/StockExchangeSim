package com.acme.acmetrade.endpoints;

import com.acme.acmetrade.domain.TickerSymbol;
import com.acme.acmetrade.services.TickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticker")
public class TickerEndpoint {

    @Autowired
    private TickerService tickerService;

    @PostMapping
    public String recordTicker(@RequestBody TickerSymbol tickerSymbol){
        return tickerService.storeTicker(tickerSymbol);
    }

    @GetMapping("/{tickerSymbol}")
    public TickerSymbol retrieveTicker(@PathVariable String tickerSymbol){
        return tickerService.retrieveTicker(tickerSymbol);
    }

    @GetMapping
    public List<TickerSymbol> getTickers(){
        return tickerService.listTickers();
    }
}
