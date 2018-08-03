package com.acme.acmetrade.domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Order implements Comparable<Order>{
    private UUID id;
    private String tickerSymbol;
    private Side side;
    private OrderType orderType;
    private Double price;
    private Integer amount;
    private OrderState orderState;
    private Integer traderId;



    private LocalDate orderDate;

    public Order() {
    }

    public boolean isMatchingOrder(Order order){
        if(this.side.equals(order.side)
                || !this.orderType.equals(order.orderType)
                || !this.tickerSymbol.equals(order.tickerSymbol)
                || !this.amount.equals(order.amount))
            return false;

        if(this.orderType == OrderType.LIMIT)
            if(this.side.equals(Side.BUY))
                return this.price >= order.price;
            else return this.price <= order.price;
        return this.price.equals(order.price);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    @Override
    public int compareTo(Order o) {
        return this.getPrice().compareTo(o.getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(tickerSymbol, order.tickerSymbol) &&
                side == order.side &&
                orderType == order.orderType &&
                Objects.equals(price, order.price) &&
                Objects.equals(amount, order.amount) &&
                orderState == order.orderState &&
                Objects.equals(orderDate, order.orderDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, tickerSymbol, side, orderType, price, amount, orderState, orderDate);
    }
}
