package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class TwitterService implements Service {

  private CrdDao dao;
  private static final int TWEET_MAX_LENGTH = 140;

  @Autowired
  public TwitterService(CrdDao dao) {
    this.dao = dao;
  }

  @Override
  public Tweet postTweet(Tweet tweet) {
    //validate tweet
    validatePostTweet(tweet);

    return (Tweet) dao.create(tweet);
  }

  private void validatePostTweet(Tweet tweet) {
    String errorStr = "";

    Double lon = tweet.getCoordinates().getCoordinates().get(0);
    Double lat = tweet.getCoordinates().getCoordinates().get(1);

    if (tweet.getText().length() > TWEET_MAX_LENGTH)
      errorStr = "Tweet Length exceeded 140 characters.";

    if(lat < -90d || lat > 90d)
      errorStr += " Out of range latitude.";

    if(lon < -180d || lat > 180d)
      errorStr += " Out of range longitude.";

    if (!errorStr.isEmpty())
      throw new IllegalArgumentException(errorStr);
  }

  @Override
  public Tweet showTweet(String id, String[] fields) {
    Tweet tweet;
    validateTweetId(id);

    if (fields != null) {
      validateTweetFields(fields);
      tweet = setNullFields((Tweet) dao.findById(id), fields);
    } else
      tweet = (Tweet) dao.findById(id);

    return tweet;
  }

  private void validateTweetFields(String[] fields) {
    Field[] tweetFields = ClassUtil.getDeclaredFields(Tweet.class);

    for (String field : fields){
      if(Arrays.stream(tweetFields).noneMatch(f -> f.getName().equals(field)))
        throw new IllegalArgumentException("Invalid fields set");
    }
  }

  private Tweet setNullFields(Tweet tweet, String[] fields)  {
    Tweet resultTweet;
    Set<String> fieldsSet = new HashSet<>(Arrays.asList(fields));

    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> tweetMap = mapper.convertValue(tweet, Map.class);

    tweetMap.keySet().stream().filter(key -> !fieldsSet.contains(key))
        .forEach(k -> tweetMap.put(k, null));

    try {
      String jsonMap = JsonUtil.toJson(tweetMap, true, false);
      resultTweet = JsonUtil.toObjectFromJson(jsonMap, Tweet.class);
    } catch (IOException ex) {
      throw new IllegalArgumentException("Error on mapping null fields", ex);
    }

    return resultTweet;
  }

  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    List<Tweet> tweetsList = new ArrayList<>();

    for(String id : ids)
      validateTweetId(id);

    for(String id : ids) {
      Tweet tweet;
      tweet = (Tweet) dao.deleteById(id);
      tweetsList.add(tweet);
    }

    return tweetsList;
  }

  private void validateTweetId(String id) {
    try {
      Long.parseLong(id);
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException("Invalid tweet id");
    }
  }
}
