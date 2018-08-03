package com.acme.acmetrade.matchingengine;

import com.acme.acmetrade.domain.*;
import com.acme.acmetrade.services.OrderService;
import com.acme.acmetrade.services.StockQuotesService;
import com.acme.acmetrade.services.TickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MatchingEngine {

    private List<Order> orders;
    private List<StockQuote> stockQuotes;
    private List<StockQuote> newStockQuotes;
    private Map<String, Integer> sqMap;
    private Map<String, Integer> transactionCount;

    @Autowired
    private OrderService orderService;
    @Autowired
    private TickerService tickerService;
    @Autowired
    private StockQuotesService quoteService;


    @Scheduled(fixedRate = 10000)
    public void matchOrders() throws Exception {
        setUp();
        findMatches();
        updateOrders();
        publishStockQuotes();
    }

    private void setUp(){
        orders = new LinkedList<>(orderService.getOrdersInProgress());
        stockQuotes = new LinkedList<>(quoteService.getAllStockQuotes());
        newStockQuotes = new LinkedList<>();
        sqMap = new HashMap<>();
        transactionCount = new HashMap<>();
    }

    private void findMatches(){
        for (Order o : orders){
            for (int i = orders.indexOf(o) + 1; i < orders.size(); i++){
                Order current = orders.get(i);
                if (o.isMatchingOrder(current) && !o.equals(current)){
                    try{
                    o.setOrderState(OrderState.FULFILLED);
                    orders.get(i).setOrderState(OrderState.FULFILLED);
                    updateStockQuotes(o, orders.get(i));
                } catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

    }

    private void updateOrders() throws Exception {
        for(Order order : orders) orderService.updateOrder(order);
    }

    private void updateStockQuotes(Order o, Order o1){
        StockQuote existing = findStockQuote(o.getTickerSymbol(), newStockQuotes);

        if(existing == null) createNewStockQuote(o, o1);
        else updateStockQuote(o, o1, existing);
    }

    private void createNewStockQuote(Order o, Order o1){
        StockQuote sq = findStockQuote(o.getTickerSymbol(), stockQuotes);

        sq.setLastTradedPrice(o.getSide() == Side.SELL ? o.getPrice() : o1.getPrice());
        sq.setAskPrice(o.getSide() == Side.SELL ? o.getPrice() : o1.getPrice());
        sq.setAskPrice(o.getSide() == Side.BUY ? o.getPrice() : o1.getPrice());
        sq.setAmountTraded(o.getAmount());

        newStockQuotes.add(sq);
        sqMap.put(o.getTickerSymbol(), newStockQuotes.indexOf(sq));
        transactionCount.put(o.getTickerSymbol(), 1);
    }

    private void updateStockQuote(Order o, Order o1, StockQuote existing){
        Integer count = transactionCount.get(o.getTickerSymbol());

        existing.setAskPrice(o.getSide() == Side.SELL
                ? (o.getPrice() * count + existing.getAskPrice()) / (count + 1)
                : (o1.getPrice() * count + existing.getAskPrice()) / (count + 1));

        existing.setBidPrice(o.getSide() == Side.BUY
                ? (o.getPrice() * count + existing.getAskPrice()) / (count + 1)
                : (o1.getPrice() * count + existing.getAskPrice()) / (count + 1));

        existing.setAmountTraded(existing.getAmountTraded() + o.getAmount());

        newStockQuotes.set(sqMap.get(o.getTickerSymbol()), existing);
        transactionCount.replace(o.getTickerSymbol(), count + 1);
    }

    private void publishStockQuotes(){
        for(StockQuote sq : newStockQuotes) quoteService.addStockQuote(sq);
    }

    private StockQuote findStockQuote(String tickerSymbol, List<StockQuote> collection){
        for(StockQuote t : collection){
            if(t.getTickerSymbol().getTickerSymbol().equals(tickerSymbol))
                return t;
        }
        return null;
    }
}
