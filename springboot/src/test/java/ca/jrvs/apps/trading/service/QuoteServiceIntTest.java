package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceIntTest {

  @Autowired
  private QuoteService quoteService;

  @Autowired
  private QuoteDao quoteDao;

  @Before
  public void setUp() {
    quoteDao.deleteAll();
  }

  @Test
  public void updateMarketData() {
    assertEquals(0, quoteDao.count());

    saveQuotes();
    assertEquals(3, quoteService.updateMarketData().size());
  }

  @Test
  public void saveQuotes() {
    List<String> tickers = new ArrayList<>();
    tickers.add("msft");
    tickers.add("z");
    tickers.add("fb");

    List<Quote> quotes = quoteService.saveQuotes(tickers);
    assertEquals(3, quotes.size());
  }

  @Test
  public void saveQuote() {
    quoteService.saveQuote("AAPL");
    assertEquals(1, quoteDao.count());
    assertTrue(quoteDao.existsById("AAPL"));
  }

  @Test
  public void findIexQuoteByTicker() {
    IexQuote iexQuote = quoteService.findIexQuoteByTicker("aapl");
    assertEquals("AAPL", iexQuote.getSymbol());

    // ticker exists but has null quote value
    try {
      quoteService.findIexQuoteByTicker("q");
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    // unknown ticker
    try {
      quoteService.findIexQuoteByTicker("qw");
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }
  }

  @Test
  public void findAllQuotes() {
    List<Quote> emptyQuotes = quoteService.findAllQuotes();
    assertEquals(0, emptyQuotes.size());

    saveQuotes();
    List<Quote> quotes = quoteService.findAllQuotes();
    assertEquals(3, quotes.size());
  }
}