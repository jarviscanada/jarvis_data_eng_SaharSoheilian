package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.stylesheets.LinkStyle;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class PositionDaoIntTest {

  @Autowired
  private PositionDao positionDao;

  @Autowired
  private SecurityOrderDao securityOrderDao;

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TraderDao traderDao;

  @Autowired
  private QuoteDao quoteDao;

  private List<SecurityOrder> orders;
  private Account savedAccount;
  private Trader savedTrader;
  private Quote savedQuote;

  @Before
  public void InsertOne() {
    // insert quote
    savedQuote = new Quote();

    savedQuote.setAskPrice(10.2d);
    savedQuote.setAskSize(100);
    savedQuote.setBidPrice(10d);
    savedQuote.setBidSize(110);
    savedQuote.setId("aapl");
    savedQuote.setLastPrice(10.1d);
    quoteDao.save(savedQuote);

    // insert a trader
    savedTrader = new Trader();
    Calendar calendar = Calendar.getInstance();
    calendar.set(1985, 1, 8);

    savedTrader.setFirstName("John");
    savedTrader.setLastName("Smith");
    savedTrader.setCountry("Canada");
    savedTrader.setDob(calendar.getTime());
    savedTrader.setEmail("jsmith@trader.com");
    traderDao.save(savedTrader);

    // insert an account
    savedAccount = new Account();
    savedAccount.setAmount(10000d);
    savedAccount.setTraderId(savedTrader.getId());
    accountDao.save(savedAccount);

    // insert 2 orders
    orders = new ArrayList<>();
    for (int i = 1; i < 3; i++) {
      SecurityOrder order = new SecurityOrder();

      order.setAccountId(savedAccount.getId());
      order.setTicker(savedQuote.getTicker());
      order.setStatus("FILLED");
      order.setPrice(10d + i);
      order.setSize(10 * i);
      order.setNotes("notes");

      securityOrderDao.save(order);

      orders.add(order);
    }
  }

  @After
  public void deleteOne() {
    for (SecurityOrder order : orders)
      securityOrderDao.deleteById(order.getId());

    accountDao.deleteById(savedAccount.getId());
    traderDao.deleteById(savedTrader.getId());
    quoteDao.deleteById(savedQuote.getTicker());
  }

  @Test
  public void findByAccountId() {
    assertEquals(1, positionDao.findByAccountId(1).size());
    assertEquals(savedQuote.getTicker(), positionDao.findByAccountId(1).get(0).getTicker());
  }

  @Test
  public void findByTickerId() {
    assertEquals(savedAccount.getId(), positionDao.findByTickerId(savedQuote.getTicker()).get(0).getAccountId());
  }

  @Test
  public void findAll() {
    assertEquals(1, positionDao.findAll().size());

    int ordersSum = 0;
    for (SecurityOrder order : orders)
      ordersSum += order.getSize();

    int positionSum = positionDao.findAll().get(0).getSum();

    assertEquals(ordersSum, positionSum);
  }

  @Test
  public void count() {
    assertEquals(1, positionDao.count());
  }
}