package ca.jrvs.apps.twitter.dao.helper;


import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

public class TwitterHttpHelperTest {

  private TwitterHttpHelper twitterHttpHelper;

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
    URI postUri = new URI("https://api.twitter.com/1.1/statuses/update.json?status=sahar+text+coord&lat=1.0&long=-1.0");
    HttpResponse postResponse = twitterHttpHelper.httpPost(postUri);

    System.out.println("Post result: ");
    System.out.println(EntityUtils.toString(postResponse.getEntity()));
  }

  @Test
  public void httpGet() throws Exception {
    URI getUri = new URI("https://api.twitter.com/1.1/statuses/show.json?id=1408154412053368832");
    HttpResponse getResponse = twitterHttpHelper.httpGet(getUri);

    System.out.println("Get result: ");
    System.out.println(EntityUtils.toString(getResponse.getEntity()));
  }
}