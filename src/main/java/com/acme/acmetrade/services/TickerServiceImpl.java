package com.acme.acmetrade.services;

import com.acme.acmetrade.domain.TickerSymbol;
import com.acme.acmetrade.repository.TickerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TickerServiceImpl implements TickerService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TickerDAO tickerDAO;

    @Override
    public String storeTicker(TickerSymbol tickerSymbol, int delay) {
        logger.info("Storing Ticker: "+ tickerSymbol);
        if (delay > 0) {
            logger.info("Applying artificial delay of " + delay + " seconds");
            try {
                Thread.sleep(delay * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        tickerDAO.addTicker(tickerSymbol);

        return tickerSymbol.getTickerSymbol();
    }

    @Override
    public TickerSymbol retrieveTicker(String tickerSymbol) {
        return tickerDAO.retriveTicker(tickerSymbol);
    }

    @Override
    public List<TickerSymbol> listTickers() {
        return tickerDAO.getAllTickers();
    }
}
