package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;
import ca.jrvs.apps.trading.TestConfig;
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
public class TraderDaoIntTest {

  @Autowired
  private TraderDao traderDao;

  private Trader trader;

  @Before
  public void insertOne() {
    trader = new Trader();
    Calendar calendar = Calendar.getInstance();
    calendar.set(1985, 1, 8);

    trader.setFirstName("John");
    trader.setLastName("Smith");
    trader.setCountry("Canada");
    trader.setDob(calendar.getTime());
    trader.setEmail("jsmith@trader.com");

    traderDao.save(trader);
  }

  @After
  public void deleteOne() {
    traderDao.deleteAll();
  }

  @Test
  public void save() {
    assertEquals(1, traderDao.count());
    trader.setCountry("US");
    traderDao.save(trader);
    assertEquals(trader.getCountry(), traderDao.findById(1).get().getCountry());
  }

  @Test
  public void saveAll() {
    List<Trader> traderList = new ArrayList<>();
    String[] firstNames = new String[]{"Sara", "Sahar", "Mike"};
    String[] lastNames = new String[]{"Smith", "Soheilian", "Parker"};
    String[] countries = new String[]{"Canada", "Canada", "US"};
    Calendar calendar = Calendar.getInstance();
    calendar.set(1988, 1, 8);

    for (int i = 0; i < firstNames.length; i++) {
      Trader trader = new Trader();

      trader.setFirstName(firstNames[i]);
      trader.setLastName(lastNames[i]);
      trader.setCountry(countries[i]);
      trader.setDob(calendar.getTime());
      trader.setEmail(firstNames[i] + "@" + lastNames[i] + ".com");

      traderList.add(trader);
    }

    traderDao.saveAll(traderList);
    assertEquals(4, traderDao.count());
    assertEquals(traderList.get(0).getFirstName(), traderDao.findById(2).get().getFirstName());
  }

  @Test
  public void findById() {
    Optional<Trader> foundTrader = traderDao.findById(1);

    assertTrue(foundTrader.isPresent());
    assertEquals(trader.getCountry(), foundTrader.get().getCountry());
    assertEquals(trader.getFirstName(), foundTrader.get().getFirstName());
  }

  @Test
  public void existsById() {
    assertTrue(traderDao.existsById(1));
  }

  @Test
  public void findAll() {
    assertEquals(1, traderDao.findAll().size());
  }

  @Test
  public void findAllById() {
    List<Trader> traders = Lists
        .newArrayList(traderDao.findAllById(Arrays.asList(trader.getId(), -1)));
    assertEquals(1, traders.size());
    assertEquals(trader.getCountry(), traders.get(0).getCountry());
  }

  @Test
  public void count() {
    assertEquals(1, traderDao.count());
  }

  @Test
  public void deleteById() {
    assertEquals(1, traderDao.count());

    traderDao.deleteById(1);
    assertEquals(0, traderDao.count());
  }

  @Test
  public void deleteAll() {
    saveAll();
    assertEquals(4, traderDao.count());

    traderDao.deleteAll();
    assertEquals(0, traderDao.count());
  }
}