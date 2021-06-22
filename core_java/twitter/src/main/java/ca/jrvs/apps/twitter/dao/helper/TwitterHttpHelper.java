package ca.jrvs.apps.twitter.dao.helper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;

public class TwitterHttpHelper implements HttpHelper {

  // dependencies are specified as private member variables
  private OAuthConsumer consumer;
  private HttpClient httpClient;

  public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken,
      String tokenSecret) {
    consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    consumer.setTokenWithSecret(accessToken, tokenSecret);

    httpClient = new DefaultHttpClient(); // HttpClientBuilder.create().build();
  }

  @Override
  public HttpResponse httpPost(URI uri) {
    try {
      return executeHttpRequest(HttpMethod.POST, uri, null);
    } catch (OAuthException | IOException ex) {
      throw new RuntimeException("Failed to execute HTTP POST ", ex);
    }
  }

  @Override
  public HttpResponse httpGet(URI uri) {
    try {
      return executeHttpRequest(HttpMethod.GET, uri, null);
    } catch (OAuthException | IOException ex) {
      throw new RuntimeException("Failed to execute HTTP GET ", ex);
    }
  }

  private HttpResponse executeHttpRequest(HttpMethod method, URI uri,
      StringEntity stringEntity)
      throws OAuthException, IOException {

    if (method == HttpMethod.GET) {
      HttpGet request = new HttpGet(uri);
      consumer.sign(request);
      return httpClient.execute(request);
    }

    else if (method == HttpMethod.POST) {
      HttpPost request = new HttpPost(uri);
      if (stringEntity != null)
        request.setEntity(stringEntity);
      consumer.sign(request);
      return httpClient.execute(request);
    }

    else if (method == HttpMethod.DELETE) {
      return null;
    }

    else if(method == HttpMethod.PATCH) {
      return null;
    }

    else
      throw new IllegalArgumentException("Unknown HTTP method: " + method);
  }

  public static void main(String[] args) throws Exception {
    // get twitter api secret keys
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper(consumerKey, consumerSecret,
        accessToken, tokenSecret);

    URI post_uri = new URI("https://api.twitter.com/1.1/statuses/update.json?status=tweet_from_HttpHelper");
    HttpResponse postResponse = twitterHttpHelper.httpPost(post_uri);

    System.out.println(EntityUtils.toString(postResponse.getEntity()));

    URI get_uri = new URI("https://api.twitter.com/2/users/by/username/Soheil50119034");
    HttpResponse getResponse = twitterHttpHelper.httpGet(get_uri);

    System.out.println(EntityUtils.toString(getResponse.getEntity()));
  }
}
