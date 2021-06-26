package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TwitterServiceIntTest {

  private TwitterService twitterService;

  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    TwitterDao dao = new TwitterDao(httpHelper);

    this.twitterService = new TwitterService(dao);
  }

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Test
  public void postTweet() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("Tweet Length exceeded 140 characters. Out of range latitude.");

    Double lon = -19d;
    Double lat = 19d;
    String text = "@mention this is text and this is hashtag #tag. " +
        "@mention this is text and this is hashtag #tag " + System.currentTimeMillis();

    Tweet invalidTweet = TweetUtil.buildTweet(text + text, lon, lat*10);
    twitterService.postTweet(invalidTweet);

    Tweet validTweet = TweetUtil.buildTweet(text , lon, lat);
    Tweet postedTweet = twitterService.postTweet(validTweet);
    assertEquals(text, postedTweet.getText());
    assertEquals(lon, postedTweet.getCoordinates().getCoordinates().get(0));

  }

  @Test
  public void showTweet() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("Invalid tweet id");
//    exceptionRule.expectMessage("Invalid fields set");

    String[] invalidFields = {"id", "idStr", "time"};

    twitterService.showTweet("1a", invalidFields);
//    twitterService.showTweet("1408425639410126858", fields);

    String[] validFields = {"id", "idStr", "text", "reTweeted"};

    Tweet tweet = twitterService.showTweet("1408425639410126858", validFields);
    assertEquals("1408425639410126858", tweet.getIdStr());
    assertNull(tweet.getEntities());

  }

  @Test
  public void deleteTweets() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("Invalid tweet id");

    String[] invalidIds = {"1408425639410126858", "1408425467728826376", "00a"};

    twitterService.deleteTweets(invalidIds);

    String[] validIds = {"1408425639410126858", "1408425467728826376", "1408406943950151684"};
    List<Tweet> deletedTweets = twitterService.deleteTweets(validIds);

    assertEquals(3, deletedTweets.size());
    assertNotNull(deletedTweets);
  }
}