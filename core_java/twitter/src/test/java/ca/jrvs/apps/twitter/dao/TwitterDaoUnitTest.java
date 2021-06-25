package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoTest {

  TwitterDao twitterDao;

  @Mock
  HttpHelper httpHelper;
  @Mock
  Tweet tweet;

  @Before
  public void setUp() throws Exception {
    twitterDao = new TwitterDao(httpHelper);
  }

  @Test
  public void create() {
    Mockito.when(tweet.getText()).thenReturn("new status to be posted!");
    System.out.println(twitterDao.create(tweet).toString());
  }

  @Test
  public void findById() {
  }

  @Test
  public void deleteById() {
  }
}