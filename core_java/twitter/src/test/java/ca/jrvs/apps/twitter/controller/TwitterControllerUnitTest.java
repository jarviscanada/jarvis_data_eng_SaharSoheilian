package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

  @Mock
  Service service;

  @InjectMocks
  TwitterController controller;

  @Test
  public void postTweet() {
    try {
      controller.postTweet(new String[]{"post", "unitTestPost", "-20"});
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    try {
      controller.postTweet(new String[]{"post", "unitTestPost", "-10:23sa"});
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    when(service.postTweet(any())).thenReturn(new Tweet());
    controller.postTweet(new String[]{"post", "unitTestPost", "10:-10"});
  }

  @Test
  public void showTweet() {
    try {
      controller.showTweet(new String[]{"show", ""});
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    when(service.showTweet(any(), any())).thenReturn(new Tweet());
    controller.showTweet(new String[]{"show", "1408425639410126858"});
    controller.showTweet(new String[]{"show", "1408425639410126858", "id,text,entities"});
  }

  @Test
  public void deleteTweet() {
    try {
      controller.deleteTweet(new String[]{"delete", ""});
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    when(service.deleteTweets(any())).thenReturn(new ArrayList<Tweet>());
    controller.deleteTweet(new String[]{"delete", "1408425639410126858,1408406240036900868"});
  }
}