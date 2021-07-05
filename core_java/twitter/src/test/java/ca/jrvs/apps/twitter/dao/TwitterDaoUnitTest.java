package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.isNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

  @Mock
  HttpHelper mockHttpHelper;

  @InjectMocks
  TwitterDao twitterDao;


  @Test
  public void create() throws Exception {
    //test failed request
    String hashtag = "#abc";
    String text = "@someone sometext " + hashtag + " " + System.currentTimeMillis();
    Double lon = 1d;
    Double lat = -1d;

    when(mockHttpHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));

    try {
      twitterDao.create(TweetUtil.buildTweet(text, lon, lat));
      fail();
    } catch (RuntimeException ex) {
      assertTrue(true);
    }

    // Test happy path
    // don't want to  call parseResponseBody,
    // so create a spyDao which can fake parseResponseBody return value
    String tweetJsonStr = "{\n"
        + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
        + "   \"id\":1097607853932564480,\n"
        + "   \"id_str\":\"1097607853932564480\",\n"
        + "   \"text\":\"test with loc223\",\n"
        + "   \"entities\":{\n"
        + "      \"hashtags\":[],"
        + "      \"user_mentions\":[]"
        + "   },\n"
        + "   \"coordinates\":null,"
        + "   \"retweet_count\":0,\n"
        + "   \"favorite_count\":0,\n"
        + "   \"favorited\":false,\n"
        + "   \"retweeted\":false\n"
        + "}";

    when(mockHttpHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(twitterDao);
    Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);

    //mock parseResponseBody
    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
    Tweet tweet = spyDao.create(TweetUtil.buildTweet(text, lon, lat));

    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void findById() throws Exception {
    String id = "1408425639410126858";

    when(mockHttpHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock"));

    try {
      twitterDao.findById(id);
      fail();
    } catch (RuntimeException ex) {
      assertTrue(true);
    }

    String tweetJsonStr = "{\n"
        + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
        + "   \"id\":1097607853932564480,\n"
        + "   \"id_str\":\"1097607853932564480\",\n"
        + "   \"text\":\"test with loc223\",\n"
        + "   \"entities\":{\n"
        + "      \"hashtags\":[],"
        + "      \"user_mentions\":[]"
        + "   },\n"
        + "   \"coordinates\":null,"
        + "   \"retweet_count\":0,\n"
        + "   \"favorite_count\":0,\n"
        + "   \"favorited\":false,\n"
        + "   \"retweeted\":false\n"
        + "}";

    when(mockHttpHelper.httpGet(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(twitterDao);
    Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());

    Tweet tweet = spyDao.findById(id);

    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void deleteById() throws Exception {
    String id = "1408425639410126858";

    when(mockHttpHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));

    try {
      twitterDao.deleteById(id);
      fail();
    } catch (RuntimeException ex) {
      assertTrue(true);
    }

    String tweetJsonStr = "{\n"
        + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
        + "   \"id\":1097607853932564480,\n"
        + "   \"id_str\":\"1097607853932564480\",\n"
        + "   \"text\":\"test with loc223\",\n"
        + "   \"entities\":{\n"
        + "      \"hashtags\":[],"
        + "      \"user_mentions\":[]"
        + "   },\n"
        + "   \"coordinates\":null,"
        + "   \"retweet_count\":0,\n"
        + "   \"favorite_count\":0,\n"
        + "   \"favorited\":false,\n"
        + "   \"retweeted\":false\n"
        + "}";

    when(mockHttpHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(twitterDao);
    Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());

    Tweet tweet = spyDao.deleteById(id);

    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }
}