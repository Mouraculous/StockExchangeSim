package com.acme.acmetrade;

import com.acme.acmetrade.simulator.SimulatorConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.List;
@SpringBootApplication
@EnableScheduling
public class TradeSimulatorApplication {

    public static void main(String[] args)  {
        SpringApplication.run(TradeSimulatorApplication.class, args);



    }

    private static SimulatorConfig getSimulatorConfig() {
        String[] stocks = {"C2137","GMDHOLDING","USD","EUR"};
        List stocksList = Arrays.asList(stocks);
        int speed  =70;
        return new SimulatorConfig(stocksList,speed);
    }
}


