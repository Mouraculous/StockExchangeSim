package com.acme.acmetrade.repository;
import com.acme.acmetrade.TradeApplication;
import com.acme.acmetrade.domain.*;
import com.acme.acmetrade.users.Trader;
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

public class TraderRepositoryTest {

    @Autowired
    private TraderRepository traderRepository;

    @Before
    public void setUpTraderDatabase() {
        Trader testTrader = new Trader();


        testTrader.setAddress("testAddress");
        testTrader.setEmail("testEmail");
        testTrader.setId(69);
        testTrader.setName("testName");
        testTrader.setPhone("testPhone");


        traderRepository.addTrader(testTrader);


    }

    @Test
    public void checkAddedTrader(){

        List<Trader> traderList = traderRepository.getAllTraders();
        assertThat("trader list should have size equal to three as there are two traders added by default", 3, equalTo(traderList.size()));

        Trader firstTestTrader = traderList.get(2);

        assertThat("trader ID should be 69", 69, equalTo(firstTestTrader.getId()));
        assertThat("trader name should be testName", "testName", equalTo(firstTestTrader.getName()));


    }






}
