package com.acme.acmetrade.repository;

import com.acme.acmetrade.domain.Order;

import java.util.List;
import java.util.UUID;

public interface MarketOrderDAO {
    void addOrder(Order order);
    void clearOrder();
    Order retrieveOrder(UUID id);
    UUID updateOrder(Order order);
    List<Order> getAllOrders();
    List<Order> getOrdersBySide(String side);
    List<Order> getOrdersInProgress();
    List<Order> getOrdersByParameters(String parameter, String parameter2);
    List<Order> getSortedOrdersByPrice(String sorting);
    List<Order> getOrdersByTrader(Integer traderId);
    Integer getTotalOrdersByTrader(Integer traderId);
    List<Order> getOrdersByTraderAndStatus(Integer traderId, String status);
}
