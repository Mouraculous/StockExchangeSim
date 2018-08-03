package com.acme.acmetrade.repository;

import com.acme.acmetrade.TradeApplication;
import com.acme.acmetrade.domain.Order;
import com.acme.acmetrade.domain.OrderState;
import com.acme.acmetrade.domain.OrderType;
import com.acme.acmetrade.domain.Side;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TradeApplication.class})
public class MarketOrderRepositoryTest {

    @Autowired
    private MarketOrderRepository orderRepository;

    @Before
    public void setupDataBase() {
        Order order = new Order();
        order.setId(UUID.fromString("795ff97a-9505-450e-9ca9-3cb06518b0e0"));
        order.setTickerSymbol("GOOGL");
        order.setSide(Side.SELL);
        order.setOrderType(OrderType.LIMIT);
        order.setPrice(100.12);
        order.setAmount(15);
        order.setOrderState(OrderState.IN_PROGRESS);
        order.setOrderDate(LocalDate.now());
        order.setTraderId(1);

        orderRepository.addOrder(order);

        Order order2 = new Order();
        order2.setId(UUID.fromString("795ff97a-9505-450e-9ca9-3cb06518b0e1"));
        order2.setTickerSymbol("IBM");
        order2.setSide(Side.SELL);
        order2.setOrderType(OrderType.LIMIT);
        order2.setPrice(90.0);
        order2.setAmount(25);
        order2.setOrderState(OrderState.IN_PROGRESS);
        order2.setOrderDate(LocalDate.now());
        order2.setTraderId(2);

        orderRepository.addOrder(order2);


    }

    @Test
    public void insertOrdersCompletesSuccessfully() {
        List<Order> orders = orderRepository.getAllOrders();
        assertThat("market order size should be 5", 5, equalTo(orders.size()));



        Order order = orders.get(0);
        assertThat("id should be not null", "795ff97a-9505-450e-9ca9-3cb06518b0e0", equalTo(String.valueOf(order.getId())));
        assertThat("Ticker symbol should be GOOGL", "GOOGL", equalTo(order.getTickerSymbol()));
        assertThat("side should be SELL", Side.SELL, equalTo(order.getSide()));
        assertThat("order type should be LIMIT", OrderType.LIMIT, equalTo(order.getOrderType()));
        assertThat("price should be 100.12", 100.12, equalTo(order.getPrice()));
        assertThat("amount should be 15", 15, equalTo(order.getAmount()));
        assertThat("order state should be IN_PROGRESS", OrderState.IN_PROGRESS, equalTo(order.getOrderState()));
        assertThat("order date should be NOW", LocalDate.now(), equalTo(order.getOrderDate()));
        assertThat("trader ID should be 1", 1, equalTo(order.getTraderId()));
    }




    @Test
    public void updateFirstOrderCompletesSuccessfully() {
        List<Order> orders = orderRepository.getAllOrders();
        assertThat("market order size should be 2", 2, equalTo(orders.size()));

        Order order = orders.get(0);
        order.setAmount(17);
        order.setPrice(100.0);
        order.setOrderType(OrderType.MARKET);

        assertThat("id should be not null", "795ff97a-9505-450e-9ca9-3cb06518b0e0", equalTo(String.valueOf(order.getId())));
        assertThat("Ticker symbol should be GOOGL", "GOOGL", equalTo(order.getTickerSymbol()));
        assertThat("side should be SELL", Side.SELL, equalTo(order.getSide()));
        assertThat("order type should be MARKET", OrderType.MARKET, equalTo(order.getOrderType()));
        assertThat("price should be 100.0", 100.0, equalTo(order.getPrice()));
        assertThat("amount should be 17", 17, equalTo(order.getAmount()));
        assertThat("order state should be IN_PROGRESS", OrderState.IN_PROGRESS, equalTo(order.getOrderState()));
        assertThat("order date should be NOW", LocalDate.now(), equalTo(order.getOrderDate()));
    }

    @Test
    public void cancelOrderTest() {
        List<Order> orders = orderRepository.getAllOrders();
        assertThat("market order size should be 2", 2, equalTo(orders.size()));

        Order order = orders.get(0);
        order.setOrderState(OrderState.CANCELLED);

        assertThat("id should be not null", "795ff97a-9505-450e-9ca9-3cb06518b0e0", equalTo(String.valueOf(order.getId())));
        assertThat("Ticker symbol should be GOOGL", "GOOGL", equalTo(order.getTickerSymbol()));
        assertThat("side should be SELL", Side.SELL, equalTo(order.getSide()));
        assertThat("order type should be LIMIT", OrderType.LIMIT, equalTo(order.getOrderType()));
        assertThat("price should be 100.12", 100.12, equalTo(order.getPrice()));
        assertThat("amount should be 15", 15, equalTo(order.getAmount()));
        assertThat("order state should be IN_PROGRESS", OrderState.CANCELLED, equalTo(order.getOrderState()));
        assertThat("order date should be NOW", LocalDate.now(), equalTo(order.getOrderDate()));
    }





   @After
    public void clearDataBase() {
        orderRepository.clearOrder();
       List<Order> orders = orderRepository.getAllOrders();
       assertThat("market order size should be 0", 0, equalTo(orders.size()));


    }
}