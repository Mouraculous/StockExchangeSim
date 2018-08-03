package com.acme.acmetrade.repository;

import com.acme.acmetrade.users.Trader;

import java.util.List;
import java.util.UUID;


    public interface TraderDAO {
        void addTrader(Trader trader);
        Trader retrieveTrader(int id);
        String deleteTrader(int id);
        List<Trader> getAllTraders();
    }
