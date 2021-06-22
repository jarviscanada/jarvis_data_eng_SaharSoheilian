package ca.jrvs.apps.twitter.dao.helper;

import static org.junit.Assert.*;

import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

public class TwitterHttpHelperTest {

  TwitterHttpHelper twitterHttpHelper;

  @Before
  public void setUp() throws Exception {
    // get twitter api secret keys
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    twitterHttpHelper = new TwitterHttpHelper(consumerKey, consumerSecret,
        accessToken, tokenSecret);
  }

  @Test
  public void httpPost() throws Exception {
    URI post_uri = new URI("https://api.twitter.com/1.1/statuses/update.json?status=tweet_from_HttpHelper");
    HttpResponse postResponse = twitterHttpHelper.httpPost(post_uri);

    System.out.println("Post result: ");
    System.out.println(EntityUtils.toString(postResponse.getEntity()));
  }

  @Test
  public void httpGet() throws Exception {
    URI get_uri = new URI("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=Soheil50119034");
    HttpResponse getResponse = twitterHttpHelper.httpGet(get_uri);

    System.out.println("Get result: ");
    System.out.println(EntityUtils.toString(getResponse.getEntity()));
  }
}