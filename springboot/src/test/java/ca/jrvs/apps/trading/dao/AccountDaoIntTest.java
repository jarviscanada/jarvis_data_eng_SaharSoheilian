package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
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
public class AccountDaoIntTest {

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TraderDao traderDao;

  private Account savedAccount;
  private Trader trader;

  @Before
  public void insertOne() {
    // insert a trader
    trader = new Trader();
    Calendar calendar = Calendar.getInstance();
    calendar.set(1985, 1, 8);

    trader.setFirstName("John");
    trader.setLastName("Smith");
    trader.setCountry("Canada");
    trader.setDob(calendar.getTime());
    trader.setEmail("jsmith@trader.com");
    traderDao.save(trader);

    // insert an account
    savedAccount = new Account();
    savedAccount.setAmount(10000d);
    savedAccount.setTraderId(trader.getId());

    accountDao.save(savedAccount);
  }

  @After
  public void deleteOne() {
    accountDao.findAll().forEach(account -> accountDao.deleteById(account.getId()));
    traderDao.deleteById(trader.getId());
  }

  @Test
  public void save() {
    assertEquals(1, accountDao.count());
    savedAccount.setAmount(2000d);
    accountDao.save(savedAccount);
    assertEquals(savedAccount.getAmount(), accountDao.findById(1).get().getAmount());
  }

  @Test
  public void saveAll() {
    List<Account> accountList = new ArrayList<>();
    Double[] amounts = new Double[]{1000d, 2000d, 3000d};

    for (int i = 0; i < amounts.length; i++) {
      Account account = new Account();

      account.setTraderId(trader.getId());
      account.setAmount(amounts[i]);

      accountList.add(account);
    }

    accountDao.saveAll(accountList);
    assertEquals(4, accountDao.count());
    assertEquals(accountList.get(0).getAmount(), accountDao.findById(2).get().getAmount());
  }

  @Test
  public void findById() {
    Optional<Account> account = accountDao.findById(1);

    assertTrue(account.isPresent());
    assertEquals(savedAccount.getAmount(), account.get().getAmount());
    assertEquals(savedAccount.getTraderId(), account.get().getTraderId());
  }

  @Test
  public void existsById() {
    assertTrue(accountDao.existsById(1));
  }

  @Test
  public void findAll() {
    assertEquals(1, accountDao.findAll().size());
  }

  @Test
  public void findAllById() {
    List<Account> accounts = Lists
        .newArrayList(accountDao.findAllById(Arrays.asList(savedAccount.getId(), -1)));
    assertEquals(1, accounts.size());
    assertEquals(savedAccount.getAmount(), accounts.get(0).getAmount());
  }

  @Test
  public void count() {
    assertEquals(1, accountDao.count());
  }

  @Test
  public void deleteById() {
    assertEquals(1, accountDao.count());

    accountDao.deleteById(1);
    assertEquals(0, accountDao.count());
  }

  @Test
  public void deleteAll() {
    saveAll();
    assertEquals(4, accountDao.count());

    accountDao.deleteAll();
    assertEquals(0, accountDao.count());
  }
}