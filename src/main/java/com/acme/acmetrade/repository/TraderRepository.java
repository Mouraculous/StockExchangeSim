package com.acme.acmetrade.repository;

import com.acme.acmetrade.users.Trader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class TraderRepository implements TraderDAO {

    private final static String sqlInsert = "insert into TRADERS(ID, NAME, EMAIL, PHONE, ADDRESS) values (?,?,?,?,?)";
    private final static String sqlSelectById = "select * from TRADERS where ID = '";
    private final static String sqlSelectAll = "select * from TRADERS";
    private final static String sqlDelete = "DELETE FROM TRADERS WHERE ID = ?";
    private final static String sqlGetOrdersCount = "SELECT COUNT(*) FROM MARKET_ORDERS WHERE TRADER_ID = ?";

    private final JdbcTemplate jdbcTemplate;

    public TraderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void addTrader(Trader trader) {
        jdbcTemplate
                .update(
                        sqlInsert,
                        trader.getId(),
                        trader.getName(),
                        trader.getEmail(),
                        trader.getPhone(),
                        trader.getAddress()
                );
    }

    @Override
    @Transactional
    public Trader retrieveTrader(int id) {
        List<Trader> messages = jdbcTemplate.query(
                sqlSelectById + id + "'",
                ((rs, rowNum) -> {
                    Trader trader = new Trader();
                    trader.setId(rs.getInt("ID"));
                    trader.setName(rs.getString("NAME"));
                    trader.setEmail(rs.getString("EMAIL"));
                    trader.setAddress(rs.getString("ADDRESS"));
                    return trader;
                }));
        if (messages.size() > 0) return messages.get(0);
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trader> getAllTraders() {
        return jdbcTemplate.query(
                sqlSelectAll,
                ((rs, rowNum) -> {
                    Trader trader = new Trader();
                    trader.setId(rs.getInt("ID"));
                    trader.setAddress(rs.getString("ADDRESS"));
                    trader.setEmail(rs.getString("EMAIL"));
                    trader.setName((rs.getString("NAME")));
                    trader.setPhone(rs.getString("PHONE"));

                    return trader;
                }));
    }

    @Override
    @Transactional(readOnly = true)
    public String deleteTrader(int id) {
        if (getCountOfOpenOrdersForTrader(id) > 0) {
            jdbcTemplate.update(sqlDelete, id);

            return "Trader with " + id + "deleted";
        } else {
            return "You can't delete";
        }
    }

    private int getCountOfOpenOrdersForTrader(int id) {
        Integer countOfOpenMarketOrdersForTrader
                = jdbcTemplate
                .queryForObject(
                        sqlGetOrdersCount,
                        Integer.class,
                        id
                );

        if (countOfOpenMarketOrdersForTrader == null) {
            countOfOpenMarketOrdersForTrader = 0;
        }

        return countOfOpenMarketOrdersForTrader;
    }
}




