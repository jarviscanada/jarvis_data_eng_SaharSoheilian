package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterControllerIntTest {

  private TwitterController controller;

  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(
        consumerKey, consumerSecret, accessToken, tokenSecret);
    CrdDao dao = new TwitterDao(httpHelper);
    Service service = new TwitterService(dao);
    controller = new TwitterController(service);
  }

  @Test
  public void postTweet() {
    // invalid input args
    String[] args1 = {"post", "", "32:"};

    try {
      controller.postTweet(args1);
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    // invalid location
    String[] args2 = {"post", "text", "12s:a"};

    try {
      controller.postTweet(args2);
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    // happy test
    String text = "post_new_text" + System.currentTimeMillis();
    String location = "-34:75";
    String[] args = {"post", text, location};

    Tweet tweet = controller.postTweet(args);
    assertNotNull(tweet.getCoordinates());
    assertEquals(Double.valueOf(75), tweet.getCoordinates().getCoordinates().get(0));
    assertEquals(Double.valueOf(-34), tweet.getCoordinates().getCoordinates().get(1));

  }

  @Test
  public void showTweet() {
    // invalid input arguments (tweet id)
    String[] invalidArgs = {"show", ""};

    try {
      controller.showTweet(invalidArgs);
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    // happy test path
    String text = "show_new_text" + System.currentTimeMillis();
    String location = "34:75";
    String[] postArgs = {"post", text, location};

    Tweet postedTweet = controller.postTweet(postArgs);

    String[] showArgs1 = {"show", String.valueOf(postedTweet.getId()), "id,text,favorited"};
    Tweet showTweet1 = controller.showTweet(showArgs1);

    assertNotNull(postedTweet.getCoordinates());
    assertNull(showTweet1.getCoordinates());
    assertEquals(postedTweet.getId(), showTweet1.getId());

    String[] showArgs2 = {"show", String.valueOf(postedTweet.getId())};
    Tweet showTweet2 = controller.showTweet(showArgs2);

    assertNotNull(showTweet2.getCoordinates());
    assertEquals(postedTweet.getText(), showTweet2.getText());

  }

  @Test
  public void deleteTweet() {
    // invalid input arguments (tweet id)
    String[] invalidArgs = {"delete", ""};

    try {
      controller.deleteTweet(invalidArgs);
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    // happy test path
    String text = "delete_new_text" + System.currentTimeMillis();
    String location = "34:75";

    Tweet postedTweet1 = controller.postTweet(new String[]{"post", "1 " + text, location});
    Tweet postedTweet2 = controller.postTweet(new String[]{"post", "2 " + text, location});
    Tweet postedTweet3 = controller.postTweet(new String[]{"post", "3 " + text, location});

    String ids = postedTweet1.getId() + "," + postedTweet2.getId() + "," +
        postedTweet3.getId();
    String[] deleteArgs = {"delete", ids};
    List<Tweet> deletedTweets;

    deletedTweets = controller.deleteTweet(deleteArgs);

    assertEquals(3, deletedTweets.size());
  }
}