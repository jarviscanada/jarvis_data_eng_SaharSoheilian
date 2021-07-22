package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import com.sun.org.apache.xpath.internal.operations.Quo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.junit.After;
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
public class QuoteDaoIntTest {

  @Autowired
  private QuoteDao quoteDao;

  private Quote savedQuote;

  @Before
  public void insertOne() {
    savedQuote = new Quote();

    savedQuote.setAskPrice(10d);
    savedQuote.setAskSize(10);
    savedQuote.setBidPrice(10.2d);
    savedQuote.setBidSize(10);
    savedQuote.setId("aapl");
    savedQuote.setLastPrice(10.1d);
    quoteDao.save(savedQuote);
  }

  @After
  public void deleteOne() {
    quoteDao.deleteById(savedQuote.getId());
  }

  @Test
  public void save() {
    assertEquals(1, quoteDao.count());
  }

  @Test
  public void saveAll() {
    List<Quote> quoteList = new ArrayList<>();
    String[] tickers = new String[]{"fb", "googl", "msft"};

    for (int i = 1; i < tickers.length + 1; i++) {
      Quote quote = new Quote();

      quote.setAskPrice(10d * i);
      quote.setAskSize(10 * i);
      quote.setBidPrice(10.2d * i);
      quote.setBidSize(10 * i);
      quote.setId(tickers[i-1]);
      quote.setLastPrice(10.1d * i);

      quoteList.add(quote);
    }

    quoteDao.saveAll(quoteList);
    assertEquals(4, quoteDao.count());
  }

  @Test
  public void findById() {
    Optional<Quote> quote = quoteDao.findById("aapl");

    assertTrue(quote.isPresent());
    assertEquals("aapl", quote.get().getTicker());
  }

  @Test
  public void existsById() {
    assertTrue(quoteDao.existsById("aapl"));
  }

  @Test
  public void findAll() {
    assertEquals(1, quoteDao.findAll().size());
  }

  @Test
  public void count() {
    assertEquals(1, quoteDao.count());
  }

  @Test
  public void deleteById() {
    quoteDao.deleteById("qq");
    assertEquals(1, quoteDao.count());

    quoteDao.deleteById("aapl");
    assertEquals(0, quoteDao.count());

  }

  @Test
  public void deleteAll() {
    saveAll();
    assertEquals(4, quoteDao.count());

    quoteDao.deleteAll();
    assertEquals(0, quoteDao.count());
  }
}