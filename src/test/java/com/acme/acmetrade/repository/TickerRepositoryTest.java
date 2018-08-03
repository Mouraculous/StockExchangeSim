package com.acme.acmetrade.repository;

import com.acme.acmetrade.TradeApplication;
import com.acme.acmetrade.domain.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.acme.acmetrade.TradeApplication;
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

public class TickerRepositoryTest {

        @Autowired
        private TickerRepository tickerRepository;

        @Before
        public void setUpDataBase(){

            TickerSymbol testTickerSymbol = new TickerSymbol();

            testTickerSymbol.setTickerSymbol("testSymbol");
            testTickerSymbol.setMarketSector("testMarketSector");
            testTickerSymbol.setCompanyName("testCompanyName");

            tickerRepository.addTicker(testTickerSymbol);

        }

        @Test
        public void checkInsertedTicker(){

            List<TickerSymbol> tickersList = tickerRepository.getAllTickers();
            assertThat("ticker list size should be 5, as there are 4 basically added at application start", 5, equalTo(tickersList.size()));


            TickerSymbol testTicker = tickersList.get(4);
            assertThat("ticker Symbol should be testSymbol", "testSymbol", equalTo(testTicker.getTickerSymbol()));
            assertThat("ticker Market Sector should be testMarketSector", "testMarketSector", equalTo(testTicker.getMarketSector()));
            assertThat("ticker Company Name should be testCompanyName", "testCompanyName", equalTo(testTicker.getCompanyName()));

        }
}
