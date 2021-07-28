package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import java.util.Calendar;
import org.hamcrest.core.IsAnything;
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
public class TraderAccountServiceIntTest {

  private TraderAccountView savedView;

  @Autowired
  private TraderAccountService traderAccountService;

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TraderDao traderDao;


  @Before
  public void InsertOne() {
    accountDao.deleteAll();
    traderDao.deleteAll();

    Trader trader = new Trader();
    Calendar calendar = Calendar.getInstance();
    calendar.set(1985, 1, 8);
    trader.setFirstName("Sahar");
    trader.setLastName("Soheilian");
    trader.setCountry("Canada");
    trader.setEmail("s@gmail.com");
    trader.setDob(calendar.getTime());


    savedView = traderAccountService.createTraderAndAccount(trader);
  }

  @Test
  public void createTraderAndAccount() {
    assertEquals(1, accountDao.count());
    assertEquals(1, traderDao.count());
    assertEquals(savedView.getAccount().getId(), savedView.getTrader().getId());

    Trader trader = new Trader();

    try {
      traderAccountService.createTraderAndAccount(trader);
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    trader.setId(1);

    try {
      traderAccountService.createTraderAndAccount(trader);
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }
  }

  @Test
  public void deleteTraderById() {
    assertEquals(1, accountDao.count());
    assertEquals(1, traderDao.count());

    traderAccountService.deleteTraderById(savedView.getTrader().getId());

    assertEquals(0, accountDao.count());
    assertEquals(0, traderDao.count());
  }

  @Test
  public void deposit() {
    try {
      traderAccountService.deposit(savedView.getTrader().getId(), 0.0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    Double fund = 100.0;
    Account updatedAccount = traderAccountService.deposit(savedView.getTrader().getId(), fund);
    savedView.setAccount(updatedAccount);
    assertEquals(fund, savedView.getAccount().getAmount());
  }

  @Test
  public void withdraw() {
    Double amount = 0.0;
    assertEquals(amount, savedView.getAccount().getAmount());

    try {
      traderAccountService.withdraw(savedView.getTrader().getId(), 50.0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    Double credit = 100.0;
    Double newAmount = 70.0;

    Account depositedAccount = traderAccountService.deposit(savedView.getTrader().getId(), credit);
    savedView.setAccount(depositedAccount);

    Account withdrawAccount = traderAccountService.withdraw(savedView.getTrader().getId(), 30.0);
    savedView.setAccount(withdrawAccount);

    assertEquals(newAmount, savedView.getAccount().getAmount());
  }
}