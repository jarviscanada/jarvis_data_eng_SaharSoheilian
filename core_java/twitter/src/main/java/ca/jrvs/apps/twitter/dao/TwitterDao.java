package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TwitterDao implements CrdDao<Tweet, String> {

  //URI constants
  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DELETE_PATH = "/1.1/statuses/destroy/";

  //URI symbols
  private static final String QUERY_SYM = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  //Response code
  private static final int HTTP_OK = 200;

  private HttpHelper httpHelper;

  @Autowired
  public TwitterDao(HttpHelper httpHelper) {
    this.httpHelper = httpHelper;
  }

  @Override
  public Tweet create(Tweet entity) {
    URI uri;
    try {
      uri = getPostUri(entity);
    } catch (URISyntaxException | UnsupportedEncodingException ex) {
      throw new IllegalArgumentException("Unable to create Post uri; invalid tweet entity", ex);
    }

    HttpResponse response = httpHelper.httpPost(uri);
    return parseResponseBody(response, HTTP_OK);
  }

  /**
   * create post uri for the input entity
   */
  private URI getPostUri(Tweet entity) throws URISyntaxException, UnsupportedEncodingException {
    URI uri;

    if (entity.getCoordinates() != null)
      uri = new URI(API_BASE_URI + POST_PATH + QUERY_SYM +
          "status" + EQUAL + URLEncoder.encode(entity.getText() , StandardCharsets.UTF_8.toString())
          + AMPERSAND + "lat" + EQUAL + entity.getCoordinates().getCoordinates().get(1)
          + AMPERSAND + "long" + EQUAL + entity.getCoordinates().getCoordinates().get(0));
    else
      uri = new URI(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL +
          URLEncoder.encode(entity.getText() , StandardCharsets.UTF_8.toString()));

    return uri;
  }

  @Override
  public Tweet findById(String s) {
    URI uri;
    try{
      uri = getGetByIdUri(s);
    } catch (URISyntaxException ex) {
      throw new RuntimeException("Unable to create Get uri", ex);
    }

    HttpResponse response = httpHelper.httpGet(uri);
    return parseResponseBody(response, HTTP_OK);
  }

  /**
   * create GET uri for input tweet id
   */
  private URI getGetByIdUri(String tweetId) throws URISyntaxException {
    return new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + tweetId);
  }

  @Override
  public Tweet deleteById(String s) {
    URI uri;
    try {
      uri = getDeleteUri(s);
    } catch (URISyntaxException ex) {
      throw new IllegalArgumentException("Unable to create Delete uri", ex);
    }

    HttpResponse response = httpHelper.httpPost(uri);
    return parseResponseBody(response, HTTP_OK);
  }

  /**
   * create DELETE uri for input Tweet id
   */
  private URI getDeleteUri(String tweetId) throws URISyntaxException {
    return new URI(API_BASE_URI + DELETE_PATH + tweetId + ".json");
  }

  /**
   * Check response status code and convert the response to Tweet
   *
   * @param response
   * @param expectedStatus
   * @return Tweet object
   */
  protected Tweet parseResponseBody(HttpResponse response, int expectedStatus) {
    int status = response.getStatusLine().getStatusCode();

    if (status != expectedStatus){
      try {
        System.out.println(EntityUtils.toString(response.getEntity()));
      } catch (IOException ex) {
        System.out.println("Unable to convert the response entity to string");
      }
      throw new RuntimeException("Unexpected status code: " + status);
    }

    if (response.getEntity() == null) {
      throw new RuntimeException("Empty response body");
    }

    //Convert response entity to string
    String jsonString;
    try {
      jsonString = EntityUtils.toString(response.getEntity());
    } catch (IOException ex) {
      throw new RuntimeException("Unable to convert the response entity to string", ex);
    }

    Tweet tweet;
    try {
      tweet = JsonUtil.toObjectFromJson(jsonString, Tweet.class);
    } catch (IOException ex) {
      throw new RuntimeException("Unable to convert JSON to object", ex);
    }

    return tweet;
  }
}
