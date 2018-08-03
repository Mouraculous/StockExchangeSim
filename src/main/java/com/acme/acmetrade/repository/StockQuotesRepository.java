package com.acme.acmetrade.repository;

import com.acme.acmetrade.domain.StockQuote;
import com.acme.acmetrade.domain.TickerSymbol;
import com.acme.acmetrade.services.TickerService;
import com.acme.acmetrade.services.TickerServiceImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class StockQuotesRepository implements StockQuotesDAO {

    private final JdbcTemplate jdbcTemplate;

    public StockQuotesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String sqlAddQuery = "insert into STOCK_QUOTES(ID, ASK_PRICE, BID_PRICE, LAST_PRICE, AMOUNT_TRADED, TICKER, TIME_OF_ORDER) values(?,?,?,?,?,?,?);";
    private static final String sqlPutQuery = "update STOCK_QUOTES set AMOUNT = ?, LAST_PRICE = ? where ID = ?";
    private static final String sqlGetAllQuery = "select * from STOCK_QUOTES INNER JOIN TICKERS ON TICKERS.TICKER_SYMBOL = STOCK_QUOTES.TICKER";
    private static final String sqlGetMostRecentQuery = "select top 1 * from STOCK_QUOTES INNER JOIN TICKERS ON TICKER_SYMBOL = TICKER WHERE TICKER = '";
    private static final String sqlOrderByQuery = "' order by TIME_OF_ORDER desc;";
    private static final String sqlJoinQuery = "select  * from STOCK_QUOTES INNER JOIN TICKERS ON TICKER_SYMBOL = TICKER WHERE TICKER = '";
    private static final String sqlLatestQuotesQuery = "SELECT m1.*, TICKERS.COMPANY_NAME, TICKERS.MARKET_SECTOR FROM STOCK_QUOTES m1 " +
            "LEFT JOIN STOCK_QUOTES m2 ON (m1.TICKER = m2.TICKER AND m1.TIME_OF_ORDER < m2.TIME_OF_ORDER )" +
            "LEFT JOIN TICKERS ON m1.TICKER = TICKERS.TICKER_SYMBOL"+
            "WHERE m2.ID IS NULL;";

    @Override
    @Transactional
    public StockQuote addStockQuote(StockQuote stockQuote) {
         jdbcTemplate.update(sqlAddQuery,
                stockQuote.getId(),
                stockQuote.getAskPrice(),
                stockQuote.getBidPrice(),
                stockQuote.getLastTradedPrice(),
                stockQuote.getAmountTraded(),
                stockQuote.getTickerSymbol().getTickerSymbol(),
                stockQuote.getTimestamp()
                );

         return stockQuote;
    }

    @Override
    @Transactional
    public UUID updateStockQoute(StockQuote stockQuote) {
        jdbcTemplate.update(sqlPutQuery,
                stockQuote.getAmountTraded(),
                stockQuote.getLastTradedPrice(),
                stockQuote.getId()
        );
        return stockQuote.getId();
    }

    @Override
    @Transactional
    public List<StockQuote> getAllStockQuotes() {
        return jdbcTemplate.query(sqlGetAllQuery,
                (rs, rowNum) -> {
                    StockQuote stockQuote = new StockQuote();
                    TickerSymbol tickerSymbol = new TickerSymbol();
                    stockQuote.setId(UUID.fromString(rs.getString("ID")));
                    stockQuote.setTimestamp(rs.getTimestamp("TIME_OF_ORDER").toLocalDateTime());
                    stockQuote.setAmountTraded(rs.getInt("AMOUNT_TRADED"));
                    stockQuote.setAskPrice(rs.getDouble("ASK_PRICE"));
                    stockQuote.setBidPrice(rs.getDouble("BID_PRICE"));
                    stockQuote.setLastTradedPrice(rs.getDouble("LAST_PRICE"));
                    tickerSymbol.setTickerSymbol(rs.getString("TICKER_SYMBOL"));
                    tickerSymbol.setCompanyName(rs.getString("COMPANY_NAME"));
                    tickerSymbol.setMarketSector(rs.getString("MARKET_SECTOR"));
                    stockQuote.setTickerSymbol(tickerSymbol);
                    return stockQuote;
                });
    }

    @Override
    @Transactional
    public List<StockQuote> getLatestStockQuotes() {
        return jdbcTemplate.query(sqlLatestQuotesQuery,
                (rs, rowNum) -> {
                    StockQuote stockQuote = new StockQuote();
                    TickerSymbol tickerSymbol = new TickerSymbol();
                    stockQuote.setId(UUID.fromString(rs.getString("ID")));
                    stockQuote.setTimestamp(rs.getTimestamp("TIME_OF_ORDER").toLocalDateTime());
                    stockQuote.setAmountTraded(rs.getInt("AMOUNT_TRADED"));
                    stockQuote.setAskPrice(rs.getDouble("ASK_PRICE"));
                    stockQuote.setBidPrice(rs.getDouble("BID_PRICE"));
                    stockQuote.setLastTradedPrice(rs.getDouble("LAST_PRICE"));
                    tickerSymbol.setTickerSymbol(rs.getString("TICKER"));
                    tickerSymbol.setCompanyName(rs.getString("COMPANY_NAME"));
                    tickerSymbol.setMarketSector(rs.getString("MARKET_SECTOR"));
                    stockQuote.setTickerSymbol(tickerSymbol);
                    return stockQuote;
                });
    }

    @Override
    @Transactional
    public List<StockQuote> getQuotesBySymbol(TickerSymbol tickerSymbol) {
        List<StockQuote> messages = jdbcTemplate.query(sqlJoinQuery + tickerSymbol.getTickerSymbol() + sqlOrderByQuery,
                (rs, rowNum) -> {
                    StockQuote stockQuote = new StockQuote();
                    stockQuote.setId(UUID.fromString(rs.getString("ID")));
                    stockQuote.setTimestamp(rs.getTimestamp("TIME_OF_ORDER").toLocalDateTime());
                    stockQuote.setAmountTraded(rs.getInt("AMOUNT_TRADED"));
                    stockQuote.setAskPrice(rs.getDouble("ASK_PRICE"));
                    stockQuote.setBidPrice(rs.getDouble("BID_PRICE"));
                    stockQuote.setLastTradedPrice(rs.getDouble("LAST_PRICE"));
                    stockQuote.setTickerSymbol(tickerSymbol);
                    return stockQuote;
                });
        return messages;
    }

    @Override
    @Transactional
    public StockQuote getMostRecentQuote(TickerSymbol tickerSymbol) {
        List<StockQuote> messages = jdbcTemplate.query(
                sqlGetMostRecentQuery + tickerSymbol.getTickerSymbol() + sqlOrderByQuery,
                (rs, rowNum) -> {
                    StockQuote stockQuote = new StockQuote();
                    stockQuote.setId(UUID.fromString(rs.getString("ID")));
                    stockQuote.setTimestamp(rs.getTimestamp("TIME_OF_ORDER").toLocalDateTime());
                    stockQuote.setAmountTraded(rs.getInt("AMOUNT_TRADED"));
                    stockQuote.setAskPrice(rs.getDouble("ASK_PRICE"));
                    stockQuote.setBidPrice(rs.getDouble("BID_PRICE"));
                    stockQuote.setLastTradedPrice(rs.getDouble("LAST_PRICE"));
                    stockQuote.setTickerSymbol(tickerSymbol);
                    return stockQuote;
                });
        return messages.get(0);
    }

    @Override
    @Transactional
    public List<StockQuote> getStockQuotesSubset(LocalDate from, LocalDate to){
        return null;
    }
}
