package com.acme.acmetrade.services;

import com.acme.acmetrade.domain.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    default UUID storeOrder(Order Order) { return storeOrder(Order, 0); }
    UUID storeOrder(Order Order, int delay);
    Order retrieveOrder(UUID uuid);
    List<Order> listOrdersBySide(String side);
    void deleteOrder(UUID id);
    List<Order> getOrdersInProgress();
    UUID updateOrder(Order Order) throws Exception;
    List<Order> listOrders();
    List<Order> listOrdersByParameter(String parameter, String parameter2);
    List<Order> sortOrdersByPrice(String sorting);
    List<Order> listOrdersByTraders(Integer traderId);
    Integer getTotalOrdersByTrader(Integer traderId);
    List<Order> getOrdersByTraderAndStatus(Integer traderId, String status);
}
