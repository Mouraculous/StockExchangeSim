package com.acme.acmetrade.endpoints;

import com.acme.acmetrade.domain.Order;
import com.acme.acmetrade.domain.OrderState;
import com.acme.acmetrade.services.OrderService;
import com.acme.acmetrade.validators.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderEndpoint {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public UUID recordOrder(@RequestBody Order order, BindingResult result) {
        OrderValidator orderValidator = new OrderValidator();
        orderValidator.validate(order, result);

        if (result.hasErrors()) {
            throw new IllegalArgumentException("Amount should be more than 0");
        } else {
            return orderService.storeOrder(order);
        }
    }

    @PutMapping("/{id}")
    public UUID updateOrder(@PathVariable UUID id, @RequestBody Order order) throws Exception {
        order.setId(id);
        return orderService.updateOrder(order);
    }

    @PutMapping("/cancel/{id}")
    public UUID cancelOrder(@PathVariable UUID id) throws Exception {
        Order order = new Order();
        order.setId(id);
        order.setOrderState(OrderState.CANCELLED);
        return orderService.updateOrder(order);
    }

    @GetMapping("/side/{side}")
    public List<Order> getOrdersBySide(@PathVariable String side) {
        return orderService.listOrdersBySide(side);
    }

    //EXAMPLE: localhost:8080/order/group/SIDE/SELL

    @GetMapping("/group/{parameter}/{parameter2}")
    public List<Order> getOrdersByParameter(@PathVariable String parameter, @PathVariable String parameter2) {
        return orderService.listOrdersByParameter(parameter, parameter2);
    }

    //EXAMPLE: localhost:8080/sort/price/asc
    //EXAMPLE: localhost:8080/sort/price/desc

    @GetMapping("/sort/price/{sorting}")
    public List<Order> getSortedOrdersByPrice(@PathVariable String sorting) {
        return orderService.sortOrdersByPrice(sorting);
    }

    @GetMapping("/{id}")
    public Order retrieveOrder(@PathVariable UUID id) {
        return orderService.retrieveOrder(id);
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderService.listOrders();
    }

    @GetMapping("/trader/{trader_id}")
    public List<Order> getOrdersByTrader(@PathVariable Integer trader_id) {
        return orderService.listOrdersByTraders(trader_id);
    }

    @GetMapping("/trader/count/{trader_id}")
    public String getNumberOrdersByTrader(@PathVariable Integer trader_id) {
        return "Trader with ID: " + trader_id + " has number of orders: " + orderService.getTotalOrdersByTrader(trader_id);
    }

    @GetMapping("/trader/{trader_id}/status/{status}")
    public List<Order> getOrdersByTraderAndStatus(@PathVariable Integer trader_id, @PathVariable String status) {
        return orderService.getOrdersByTraderAndStatus(trader_id, status);
    }
}
