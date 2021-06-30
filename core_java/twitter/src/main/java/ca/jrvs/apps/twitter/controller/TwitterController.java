package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.util.List;

public class TwitterController implements Controller {

  private static final String COORD_SEP = ":";
  private static final String COMMA = ",";

  private Service service;

  public TwitterController(Service service) {
    this.service = service;
  }

  @Override
  public Tweet postTweet(String[] args) {
    if (args.length != 3)
      throw new IllegalArgumentException(
          "USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");

    String text = args[1];
    String[] location = args[2].split(COORD_SEP);

    if (text.isEmpty() || location.length != 2)
      throw new IllegalArgumentException(
          "Invalid text and/or location format: \"latitude:longitude\"");

    Double lon;
    Double lat;

    try {
      lat = Double.valueOf(location[0]);
      lon = Double.valueOf(location[1]);
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException("Invalid latitude/longitude ", ex);
    }

    Tweet tweet = TweetUtil.buildTweet(text, lon, lat);

    return service.postTweet(tweet);
  }

  @Override
  public Tweet showTweet(String[] args) {
    if (args.length == 1 || args.length > 3 || args[1].isEmpty())
      throw new IllegalArgumentException(
          "USAGE: TwitterCLIApp show \"tweet_id\" [field1,field2,..]");

    String tweetId = args[1];
    String[] fields = null;

    if (args.length == 3)
      if (!args[2].isEmpty())
        fields = args[2].split(COMMA);

    return service.showTweet(tweetId, fields);
  }

  @Override
  public List<Tweet> deleteTweet(String[] args) {
    if (args.length != 2 || args[1].isEmpty())
      throw new IllegalArgumentException(
          "USAGE: TwitterCLIApp delete \"id1[,id2,id3,...]\"");

    String[] ids = args[1].split(COMMA);

    return service.deleteTweets(ids);
  }
}
