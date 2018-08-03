package com.acme.acmetrade.endpoints;

import com.acme.acmetrade.services.TraderService;
import com.acme.acmetrade.users.Trader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trader")
public class TraderEndpoint {
    @Autowired
    private TraderService traderService;

    @PostMapping
    public Integer recordTrader(@RequestBody Trader trader){
        return traderService.storeTrader(trader);
    }

    @DeleteMapping("/{id}")
    public String deleteTrader(@PathVariable int id){
        return traderService.deleteTrader(id);
    }

    @GetMapping("/{id}")
    public Trader retrieveTrader(@PathVariable int id){
        Trader trader = traderService.retrieveTrader(id);
        if (trader==null){
            return null;
        }else return trader;

    }

    @GetMapping
    public List<Trader> getTraders(){
        return traderService.listTraders();
    }
}
