package com.acme.acmetrade.services;

import com.acme.acmetrade.domain.Order;
import com.acme.acmetrade.domain.OrderState;
import com.acme.acmetrade.repository.MarketOrderDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MarketOrderDAO orderDAO;

    @Autowired
    private IdGenerator idGenerator;


    @Override
    public UUID storeOrder(Order order, int delay) {
        logger.info("Storing Order: "+order);
        if (delay > 0) {
            logger.info("Applying artificial delay of " + delay + " seconds");
            try {
                Thread.sleep(delay * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        order.setId(idGenerator.generateId());
        order.setOrderDate(LocalDate.now());
        order.setOrderState(OrderState.IN_PROGRESS);
        orderDAO.addOrder(order);
        return order.getId();
    }

    @Override
    public Order retrieveOrder(UUID id) {
        return orderDAO.retrieveOrder(id);
    }

    @Override
    public void deleteOrder(UUID id) {
        throw new NotImplementedException();
    }

    @Override
    public UUID updateOrder(Order order) {
        order.setOrderDate(LocalDate.now());
        Order previous = orderDAO.retrieveOrder(order.getId());
        if(!(previous.getOrderState() == OrderState.IN_PROGRESS)){
            return new UUID(12,12);
        }else{

        if(order.getOrderState() == null) order.setOrderState(previous.getOrderState());
        if(order.getAmount() == null) order.setAmount(previous.getAmount());
        if(order.getOrderType() == null) order.setOrderType(previous.getOrderType());
        if(order.getPrice() == null) order.setPrice(previous.getPrice());
        orderDAO.updateOrder(order);
        return order.getId();
        }
    }

    @Override
    public List<Order> listOrders() {
        return orderDAO.getAllOrders();
    }

    @Override
    public List<Order> getOrdersInProgress() { return orderDAO.getOrdersInProgress(); }

    @Override
    public List<Order> listOrdersByParameter(String parameter, String parameter2) {
        return orderDAO.getOrdersByParameters(parameter, parameter2);
    }

    @Override
    public List<Order> sortOrdersByPrice(String sorting) {
        return orderDAO.getSortedOrdersByPrice(sorting);
    }

    @Override
    public List<Order> listOrdersByTraders(Integer traderId) {
        return orderDAO.getOrdersByTrader(traderId);
    }

    @Override
    public Integer getTotalOrdersByTrader(Integer traderId) {
        return orderDAO.getTotalOrdersByTrader(traderId);
    }

    @Override
    public List<Order> getOrdersByTraderAndStatus(Integer traderId, String status) {
        return orderDAO.getOrdersByTraderAndStatus(traderId, status);
    }

    @Override
    public List<Order> listOrdersBySide(String side){
        return orderDAO.getOrdersBySide(side);
    }
}
