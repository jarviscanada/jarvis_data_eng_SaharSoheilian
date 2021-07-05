package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Before;
import org.junit.Test;

public class TwitterDaoIntTest {

  private TwitterDao twitterDao;

  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    this.twitterDao = new TwitterDao(httpHelper);
  }

  @Test
  public void create() throws Exception {
    String hashtag = "#tag";
    String text = "@mention this is text @mention and this is hashtag " + hashtag +
        " " + System.currentTimeMillis();

    Double lon = -1d;
    Double lat = 1d;

    Tweet tweet = TweetUtil.buildTweet(text, lon, lat);
    System.out.println(JsonUtil.toJson(tweet, true, false));

    Tweet postedTweet = twitterDao.create(tweet);
    System.out.println(JsonUtil.toJson(postedTweet, true, false));

    assertEquals(text, postedTweet.getText());
    assertNotNull(postedTweet.getCoordinates());
    assertEquals(2, postedTweet.getCoordinates().getCoordinates().size());
    assertEquals(lon, postedTweet.getCoordinates().getCoordinates().get(0));
    assertEquals(lat, postedTweet.getCoordinates().getCoordinates().get(1));

    assertTrue(hashtag.contains(tweet.getEntities().getHashtags().get(0).getText()));
  }

  @Test
  public void findById() throws Exception {
    String text = "Tweet for testing findById:)";
    Tweet tweet = twitterDao.create(TweetUtil.buildTweet(text, null, null));

    Tweet findTweet = twitterDao.findById(Long.toString(tweet.getId()));
    System.out.println(JsonUtil.toJson(findTweet, true, false));

    assertEquals(text, findTweet.getText());
    assertNull(findTweet.getCoordinates());

  }

  @Test
  public void deleteById() {
    String text = "Tweet for testing deleteById() @mention #delete.";

    Tweet tweet = twitterDao.create(TweetUtil.buildTweet(text, 34d, -75d));

    assertNotNull(tweet.getId());

    Tweet deletedTweet = twitterDao.deleteById(Long.toString(tweet.getId()));
    assertEquals(text, deletedTweet.getText());
  }
}