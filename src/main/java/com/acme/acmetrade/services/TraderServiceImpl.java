package com.acme.acmetrade.services;

import com.acme.acmetrade.repository.TraderDAO;
import com.acme.acmetrade.users.Trader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

import java.util.List;
import java.util.UUID;

@Service
public class TraderServiceImpl implements TraderService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TraderDAO traderDAO;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    public Integer storeTrader(Trader trader, int delay) {
        logger.info("Storing Order: "+trader);
        traderDAO.addTrader(trader);
        return trader.getId();
    }

    @Override
    public Trader retrieveTrader(int uuid) {
        return traderDAO.retrieveTrader(uuid);
    }

    @Override
    public String deleteTrader(int id) {
        return traderDAO.deleteTrader(id);
    }



    @Override
    public List<Trader> listTraders() {
        return traderDAO.getAllTraders();
    }
}
