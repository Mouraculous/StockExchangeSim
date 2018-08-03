package com.acme.acmetrade.services;


import com.acme.acmetrade.domain.Order;
import com.acme.acmetrade.users.Trader;

import java.util.List;
import java.util.UUID;

public interface TraderService {
    default Integer storeTrader(Trader trader) { return storeTrader(trader, 0); }
    Integer storeTrader(Trader trader, int delay);
    Trader retrieveTrader(int uuid);
    String deleteTrader(int id);
    List<Trader> listTraders();

}
