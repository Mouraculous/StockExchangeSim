package com.acme.acmetrade.simulator;


import org.springframework.context.annotation.Bean;

import java.util.List;

public class SimulatorConfig {
    private List stocksList;
    private int speed;

    public List getStocksList() {
        return stocksList;
    }

    public void setStocksList(List stocksList) {
        this.stocksList = stocksList;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public SimulatorConfig(List stocksList, int speed) {
        this.stocksList = stocksList;
        this.speed = speed;
    }
}
