package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MarketDataDaoIntTest {

    private MarketDataDao dao;

    @Before
    public void setUp() throws Exception {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://cloud.iexapis.com/v1");
        marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));

        dao = new MarketDataDao(marketDataConfig, cm);
    }

    @Test
    public void findById() {
        String ticker = "AAPL";
        IexQuote iexQuote = dao.findById(ticker).get();
        assertEquals(ticker, iexQuote.getSymbol());
    }

    @Test
    public void findAllById() {
        // happy path
        List<IexQuote> quoteList = dao.findAllById(Arrays.asList("AAPL", "FB"));
        assertEquals(2, quoteList.size());
        assertEquals("AAPL", quoteList.get(0).getSymbol());

        // sad path
        try{
            dao.findAllById(Arrays.asList("AAPL", "FB2"));
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        } catch (Exception ex) {
            fail();
        }
    }
}