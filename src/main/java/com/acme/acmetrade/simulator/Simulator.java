package com.acme.acmetrade.simulator;

import com.acme.acmetrade.domain.Order;
import com.acme.acmetrade.domain.OrderType;
import com.acme.acmetrade.domain.Side;
import com.acme.acmetrade.services.OrderService;
import com.acme.acmetrade.services.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class Simulator{
    @Autowired private OrderService orderService;



    @Scheduled(fixedRate = 70)
    public void addOrder() {
        orderService.storeOrder(randomizeOrder());
    }



    private Order randomizeOrder(){
        Order order = new Order();
        order.setAmount(generateRandom()*10);
        order.setOrderType(randomizeOrderType(generateRandom()));
        order.setPrice(40+generateRandom()*0.1);
        order.setSide(randomizeSide(generateRandom()));
        order.setTickerSymbol(randomizeTickerSymbol(generateRandom()));
        order.setOrderDate(LocalDate.now());
        order.setTraderId(randomizeId(generateRandom()));

        return order;
    }

    private Integer generateRandom(){
        // define the range
        int max = 12;
        int min = 1;
        int range = max - min + 1;
        return (int)(Math.random() * range) + min;
    }

    private OrderType randomizeOrderType(int randomNumber){
        if (randomNumber%2==0){
            return OrderType.LIMIT;
        }
        else return OrderType.MARKET;
    }

    private Side randomizeSide(int randomNumber){
        if (randomNumber%2==0){
            return Side.BUY;
        }else return Side.SELL;
    }

    private String randomizeTickerSymbol(int randomNumber) {
        switch (randomNumber % 4) {
            case 0:

                return "C2137";
            case 1:

                return "EUR";
            case 2:

                return "GMDHOLDING";
            case 3:

                return "USD";
        default:
            return "USD";
        }
    }

    private Integer randomizeId(int randomNumber){
        return 20*randomNumber;
    }

}
