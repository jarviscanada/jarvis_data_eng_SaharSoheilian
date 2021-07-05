package ca.jrvs.apps.twitter.service;

import static org.mockito.Mockito.when;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.any;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

  @Mock
  CrdDao dao;

  @InjectMocks
  TwitterService twitterService;

  @Test
  public void postTweet() {
    when(dao.create(any())).thenReturn(new Tweet());
    twitterService.postTweet(TweetUtil.buildTweet("test text", 50.0, 0.0));
  }

  @Test
  public void showTweet() {
    String[] fields = {"id", "idStr", "text", "reTweeted"};
    when(dao.findById(any())).thenReturn(new Tweet());
    twitterService.showTweet("1408425639410126858", fields);
  }

  @Test
  public void deleteTweets() {
    String[] ids = {"1408425639410126858", "1408406240036900868", "1408425639410126858"};
    when(dao.deleteById(any())).thenReturn(new Tweet());
    twitterService.deleteTweets(ids);
  }
}