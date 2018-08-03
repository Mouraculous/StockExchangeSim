package com.acme.acmetrade.repository;

import com.acme.acmetrade.domain.TickerSymbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@Component
public class TickerRepository implements TickerDAO {

    private static final String sqlInsert = "insert into " +
            "TICKERS(TICKER_SYMBOL, COMPANY_NAME, MARKET_SECTOR) " +
            "values (?,?,?)";
    private static final String sqlSelectAll = "select * from TICKERS";
    private static final String sqlSelectBySymbol = "select * from TICKERS where TICKER_SYMBOL = '";

    private final JdbcTemplate jdbcTemplate;

    public TickerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void addTicker(TickerSymbol tickerSymbol) {
        jdbcTemplate
                .update(
                        sqlInsert,
                        tickerSymbol.getTickerSymbol(),
                        tickerSymbol.getCompanyName(),
                        tickerSymbol.getMarketSector()
                );
    }

    @Override
    @Transactional
    public TickerSymbol retriveTicker(String tickerSymbol) {
        List<TickerSymbol> tickerSymbols = jdbcTemplate.query("select * from TICKERS where TICKER_SYMBOL = '" + tickerSymbol + "'",
                ((rs, rowNum) -> {
                    TickerSymbol tickerSym = new TickerSymbol();
                    tickerSym.setTickerSymbol(rs.getString("TICKER_SYMBOL"));
                    tickerSym.setCompanyName(rs.getString("COMPANY_NAME"));
                    tickerSym.setMarketSector(rs.getString("MARKET_SECTOR"));
                    return tickerSym;
                }));
        return tickerSymbols.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TickerSymbol> getAllTickers() {
        return jdbcTemplate.query(
                sqlSelectAll,
                ((rs, rowNum) -> {
                    TickerSymbol tickerSymbol = new TickerSymbol();
                    tickerSymbol.setTickerSymbol(rs.getString("TICKER_SYMBOL"));
                    tickerSymbol.setCompanyName(rs.getString("COMPANY_NAME"));
                    tickerSymbol.setMarketSector(rs.getString("MARKET_SECTOR"));
                    return tickerSymbol;
                }));
    }
}
