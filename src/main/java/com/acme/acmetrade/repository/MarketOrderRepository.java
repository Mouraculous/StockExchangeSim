package com.acme.acmetrade.repository;

import com.acme.acmetrade.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
public class MarketOrderRepository implements MarketOrderDAO {

    private final static String sqlInsert =  "insert into " +
            "MARKET_ORDERS(ID, ORDER_STATE, TICKER_SYMBOL, SIDE, ORDER_TYPE, PRICE, AMOUNT, TIME_OF_ORDER, TRADER_ID) " +
            "values (?,?,?,?,?,?,?,?,?)";
    private final static String sqlSelectBySide =  "select * from MARKET_ORDERS where SIDE=\'";
    private final static String sqlSelectByState =  "select * from MARKET_ORDERS where ORDER_STATE=\'";
    private final static String sqlSelectByType =  "select * from MARKET_ORDERS where ORDER_TYPE=\'";
    private final static String sqlSelectAll =  "select * from MARKET_ORDERS";
    private final static String sqlSelectByTrader =  "select * from MARKET_ORDERS where TRADER_ID=";
    private final static String sqlSelectAmountByTrader =  "select count(*) from MARKET_ORDERS where TRADER_ID=";
    private final static String sqlSelectById =  "select * from MARKET_ORDERS where ID = '";
    private final static String sqlSortingByPrice =  "select * from MARKET_ORDERS order by PRICE ";
    private final static String sqlSelectInProgress =  "select * from MARKET_ORDERS where ORDER_STATE = 'IN_PROGRESS'";
    private final static String sqlUpdate =  "update MARKET_ORDERS set ORDER_STATE = ?, AMOUNT = ?, PRICE = ?, ORDER_TYPE = ? where ID = ?";
    private final static String sqlClear =  "delete from MARKET_ORDERS";

    private final JdbcTemplate jdbcTemplate;

    public MarketOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void addOrder(Order order) {
        jdbcTemplate
                .update(
                        sqlInsert,
                        order.getId(),
                        order.getOrderState().name(),
                        order.getTickerSymbol(),
                        order.getSide().name(),
                        order.getOrderType().name(),
                        order.getPrice(),
                        order.getAmount(),
                        order.getOrderDate(),
                        order.getTraderId());

    }

    @Override
    public void clearOrder() {
        jdbcTemplate.update(sqlClear);
    }

    @Override
    @Transactional
    public UUID updateOrder(Order order) {
        jdbcTemplate.update(
                sqlUpdate,
                order.getOrderState().name(),
                order.getAmount(),
                order.getPrice(),
                order.getOrderType().name(),
                order.getId()
        );
        return order.getId();
    }

    @Override
    @Transactional
    public Order retrieveOrder(UUID id) {
        List<Order> messages = jdbcTemplate.query(
                sqlSelectById + id + "'",
                ((rs, rowNum) -> {
                    Order order = new Order();
                    order.setId(UUID.fromString(rs.getString("ID")));
                    order.setOrderState(OrderState.valueOf(rs.getString("ORDER_STATE")));
                    order.setTickerSymbol(rs.getString("TICKER_SYMBOL"));
                    order.setSide(Side.valueOf(rs.getString("SIDE")));
                    order.setOrderType(OrderType.valueOf(rs.getString("ORDER_TYPE")));
                    order.setPrice(rs.getDouble("PRICE"));
                    order.setAmount(rs.getInt("AMOUNT"));
                    order.setOrderDate(rs.getDate("TIME_OF_ORDER").toLocalDate());
                    order.setTraderId(rs.getInt("TRADER_ID"));
                    return order;
                }));
        return messages.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return jdbcTemplate.query(
                sqlSelectAll,
                ((rs, rowNum) -> {
                    Order order = new Order();
                    order.setId(UUID.fromString(rs.getString("ID")));
                    order.setOrderState(OrderState.valueOf(rs.getString("ORDER_STATE")));
                    order.setTickerSymbol(rs.getString("TICKER_SYMBOL"));
                    order.setSide(Side.valueOf(rs.getString("SIDE")));
                    order.setOrderType(OrderType.valueOf(rs.getString("ORDER_TYPE")));
                    order.setPrice(rs.getDouble("PRICE"));
                    order.setAmount(rs.getInt("AMOUNT"));
                    order.setOrderDate(rs.getDate("TIME_OF_ORDER").toLocalDate());
                    order.setTraderId(rs.getInt("TRADER_ID"));
                    return order;
                }));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersBySide(String side) {
        return jdbcTemplate.query(
                sqlSelectBySide + Side.valueOf(side).name() + "\'",
                ((rs, rowNum) -> {
                    Order order = new Order();
                    order.setId(UUID.fromString(rs.getString("ID")));
                    order.setOrderState(OrderState.valueOf(rs.getString("ORDER_STATE")));
                    order.setTickerSymbol(rs.getString("TICKER_SYMBOL"));
                    order.setSide(Side.valueOf(rs.getString("SIDE")));
                    order.setOrderType(OrderType.valueOf(rs.getString("ORDER_TYPE")));
                    order.setPrice(rs.getDouble("PRICE"));
                    order.setAmount(rs.getInt("AMOUNT"));
                    order.setOrderDate(rs.getDate("TIME_OF_ORDER").toLocalDate());
                    order.setTraderId(rs.getInt("TRADER_ID"));
                    return order;
                }));
    }

    @Override
    @Transactional
    public List<Order> getOrdersInProgress(){
        return jdbcTemplate.query(sqlSelectInProgress,
                (rs, rowNum) -> {
                    Order order = new Order();
                    order.setId(UUID.fromString(rs.getString("ID")));
                    order.setOrderState(OrderState.valueOf(rs.getString("ORDER_STATE")));
                    order.setTickerSymbol(rs.getString("TICKER_SYMBOL"));
                    order.setSide(Side.valueOf(rs.getString("SIDE")));
                    order.setOrderType(OrderType.valueOf(rs.getString("ORDER_TYPE")));
                    order.setPrice(rs.getDouble("PRICE"));
                    order.setAmount(rs.getInt("AMOUNT"));
                    order.setOrderDate(rs.getDate("TIME_OF_ORDER").toLocalDate());
                    order.setTraderId(rs.getInt("TRADER_ID"));
                    return order;
                });
    }

    @Override
    public List<Order> getOrdersByParameters(String parameter, String parameter2) {

        String sqlQuery = "";

        if (parameter.equals("ORDER_STATE")) {
            sqlQuery = sqlSelectByState + OrderState.valueOf(parameter2).name() + "\'";
        } else if (parameter.equals("SIDE")) {
            sqlQuery = sqlSelectBySide + Side.valueOf(parameter2).name() + "\'";
        } else if (parameter.equals("ORDER_TYPE")) {
            sqlQuery = sqlSelectByType + OrderType.valueOf(parameter2).name() + "\'";
        }

        return jdbcTemplate.query(sqlQuery,
                ((rs, rowNum) -> {
                    Order order = new Order();
                    order.setId(UUID.fromString(rs.getString("ID")));
                    order.setOrderState(OrderState.valueOf(rs.getString("ORDER_STATE")));
                    order.setTickerSymbol(rs.getString("TICKER_SYMBOL"));
                    order.setSide(Side.valueOf(rs.getString("SIDE")));
                    order.setOrderType(OrderType.valueOf(rs.getString("ORDER_TYPE")));
                    order.setPrice(rs.getDouble("PRICE"));
                    order.setAmount(rs.getInt("AMOUNT"));
                    order.setOrderDate(rs.getDate("TIME_OF_ORDER").toLocalDate());
                    order.setTraderId(rs.getInt("TRADER_ID"));
                    return order;
                }));
    }

    @Override
    public List<Order> getSortedOrdersByPrice(String sorting) {
        return jdbcTemplate.query(
                sqlSortingByPrice + sorting,
                ((rs, rowNum) -> {
                    Order order = new Order();
                    order.setId(UUID.fromString(rs.getString("ID")));
                    order.setOrderState(OrderState.valueOf(rs.getString("ORDER_STATE")));
                    order.setTickerSymbol(rs.getString("TICKER_SYMBOL"));
                    order.setSide(Side.valueOf(rs.getString("SIDE")));
                    order.setOrderType(OrderType.valueOf(rs.getString("ORDER_TYPE")));
                    order.setPrice(rs.getDouble("PRICE"));
                    order.setAmount(rs.getInt("AMOUNT"));
                    order.setOrderDate(rs.getDate("TIME_OF_ORDER").toLocalDate());
                    order.setTraderId(rs.getInt("TRADER_ID"));
                    return order;
                }));
    }

    @Override
    public List<Order> getOrdersByTrader(Integer traderId) {
        return jdbcTemplate.query(
                sqlSelectByTrader + traderId,
                ((rs, rowNum) -> {
                    Order order = new Order();
                    order.setId(UUID.fromString(rs.getString("ID")));
                    order.setOrderState(OrderState.valueOf(rs.getString("ORDER_STATE")));
                    order.setTickerSymbol(rs.getString("TICKER_SYMBOL"));
                    order.setSide(Side.valueOf(rs.getString("SIDE")));
                    order.setOrderType(OrderType.valueOf(rs.getString("ORDER_TYPE")));
                    order.setPrice(rs.getDouble("PRICE"));
                    order.setAmount(rs.getInt("AMOUNT"));
                    order.setOrderDate(rs.getDate("TIME_OF_ORDER").toLocalDate());
                    order.setTraderId(rs.getInt("TRADER_ID"));
                    return order;
                }));
    }

    @Override
    public Integer getTotalOrdersByTrader(Integer traderId) {
        return jdbcTemplate.queryForObject(sqlSelectAmountByTrader + traderId, Integer.class, traderId);
    }

    @Override
    public List<Order> getOrdersByTraderAndStatus(Integer traderId, String status) {
        return jdbcTemplate.query("select * from MARKET_ORDERS where TRADER_ID=" + traderId + " and ORDER_STATE=" + status,
                ((rs, rowNum) -> {
                    Order order = new Order();
                    order.setId(UUID.fromString(rs.getString("ID")));
                    order.setOrderState(OrderState.valueOf(rs.getString("ORDER_STATE")));
                    order.setTickerSymbol(rs.getString("TICKER_SYMBOL"));
                    order.setSide(Side.valueOf(rs.getString("SIDE")));
                    order.setOrderType(OrderType.valueOf(rs.getString("ORDER_TYPE")));
                    order.setPrice(rs.getDouble("PRICE"));
                    order.setAmount(rs.getInt("AMOUNT"));
                    order.setOrderDate(rs.getDate("TIME_OF_ORDER").toLocalDate());
                    order.setTraderId(rs.getInt("TRADER_ID"));
                    return order;
                }));
    }
}
