package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
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
public class SecurityOrderDaoIntTest {

  @Autowired
  private SecurityOrderDao securityOrderDao;

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TraderDao traderDao;

  @Autowired
  private QuoteDao quoteDao;

  private SecurityOrder savedSecurityOrder;
  private Account savedAccount;
  private Trader savedTrader;
  private Quote savedQuote;

  @Before
  public void insertOne(){
    // insert quote
    savedQuote = new Quote();

    savedQuote.setAskPrice(10d);
    savedQuote.setAskSize(10);
    savedQuote.setBidPrice(10.2d);
    savedQuote.setBidSize(10);
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

    // insert security order
    savedSecurityOrder = new SecurityOrder();
    savedSecurityOrder.setAccountId(savedAccount.getId());
    savedSecurityOrder.setTicker(savedQuote.getTicker());
    savedSecurityOrder.setStatus("FILLED");
    savedSecurityOrder.setPrice(100d);
    savedSecurityOrder.setSize(10000);
    savedSecurityOrder.setNotes("notes");

    securityOrderDao.save(savedSecurityOrder);
  }

  @After
  public void deleteOne(){
    securityOrderDao.deleteAll();
    accountDao.deleteById(savedAccount.getId());
    traderDao.deleteById(savedTrader.getId());
    quoteDao.deleteById(savedQuote.getTicker());
  }

  @Test
  public void save() {
    assertEquals(1, securityOrderDao.count());
    savedSecurityOrder.setPrice(99.9d);
    securityOrderDao.save(savedSecurityOrder);
    assertEquals(savedSecurityOrder.getPrice(), securityOrderDao.findById(1).get().getPrice());
  }

  @Test
  public void saveAll() {
    List<SecurityOrder> orderList = new ArrayList<>();
    Integer[] sizes = new Integer[]{1000, 1500, 2000};
    Double[] prices = new Double[]{100d, 99d, 99.8d};

    for (int i = 0; i < 3; i++) {
      SecurityOrder order = new SecurityOrder();

      order.setAccountId(savedAccount.getId());
      order.setTicker(savedQuote.getTicker());
      order.setSize(sizes[i]);
      order.setPrice(prices[i]);
      order.setStatus("order status " + i);
      order.setNotes("order notes " + i);

      orderList.add(order);
    }

    securityOrderDao.saveAll(orderList);
    assertEquals(4, securityOrderDao.count());
    assertEquals(orderList.get(0).getPrice(), securityOrderDao.findById(2).get().getPrice());
  }

  @Test
  public void findById() {
    Optional<SecurityOrder> order = securityOrderDao.findById(1);

    assertTrue(order.isPresent());
    assertEquals(savedSecurityOrder.getPrice(), order.get().getPrice());
    assertEquals(savedSecurityOrder.getAccountId(), order.get().getAccountId());
  }

  @Test
  public void existsById() {
    assertTrue(securityOrderDao.existsById(1));
  }

  @Test
  public void findAll() {
    assertEquals(1, securityOrderDao.findAll().size());
  }

  @Test
  public void findAllById() {
    List<SecurityOrder> orders = Lists
        .newArrayList(securityOrderDao.findAllById(Arrays.asList(savedSecurityOrder.getId(), -1)));
    assertEquals(1, orders.size());
    assertEquals(savedSecurityOrder.getTicker(), orders.get(0).getTicker());
  }

  @Test
  public void count() {
    assertEquals(1, securityOrderDao.count());
  }

  @Test
  public void deleteById() {
    assertEquals(1, securityOrderDao.count());

    securityOrderDao.deleteById(1);
    assertEquals(0, securityOrderDao.count());
  }

  @Test
  public void deleteAll() {
    saveAll();
    assertEquals(4, securityOrderDao.count());

    securityOrderDao.deleteAll();
    assertEquals(0, securityOrderDao.count());
  }
}