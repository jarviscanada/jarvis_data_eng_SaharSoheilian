package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Entities;
import ca.jrvs.apps.twitter.model.Hashtag;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.model.UserMention;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TweetUtil {

  public static Tweet buildTweet(String s, Double lon, Double lat) {
    if (s == null)
      throw new NullPointerException("Null object not allowed");

    Tweet tweet = new Tweet();

    tweet.setText(s);
    tweet.setCreatedAt(Calendar.getInstance().getTime().toString());
    tweet.setEntities(buildEntities(s));

    if (lat != null && lon != null)
      tweet.setCoordinates(buildCoordinates(lat, lon));

    return tweet;
  }

  private static Coordinates buildCoordinates(Double lat, Double lon) {
    Coordinates coordinates = new Coordinates();
    List<Double> coordinate = new ArrayList<>();
    coordinate.add(lon);
    coordinate.add(lat);

    coordinates.setCoordinates(coordinate);
    coordinates.setType("point");
    return coordinates;
  }

  private static Entities buildEntities(String s) {
    Entities entities = new Entities();
    List<Hashtag> hashtagList = new ArrayList<>();
    List<UserMention> userMentionList = new ArrayList<>();

    // find Hashtags and UserMentions in text and create UserMention and Hashtag objects
    Pattern hashtagPattern = Pattern.compile("#\\w+\\b");
    Pattern mentionPattern = Pattern.compile("@\\w+\\b");
    Matcher hashtagMatcher = hashtagPattern.matcher(s);
    Matcher mentionMatcher = mentionPattern.matcher(s);

    if (hashtagMatcher.find()) {
      do {
        Hashtag hashtag = new Hashtag();
        hashtag.setText(s.substring(hashtagMatcher.start() + 1, hashtagMatcher.end()));
        hashtag.setIndices(new int[]{hashtagMatcher.start() + 1, hashtagMatcher.end()});
        hashtagList.add(hashtag);
      } while (hashtagMatcher.find());
    }


    if (mentionMatcher.find()) {
      do {
        UserMention userMention = new UserMention();
        userMention.setScreenName(s.substring(mentionMatcher.start() + 1, mentionMatcher.end()));
        userMention.setIndices(new int[]{mentionMatcher.start() + 1, mentionMatcher.end()});
        userMentionList.add(userMention);
      } while (mentionMatcher.find());
    }

    entities.setHashtags(hashtagList);
    entities.setUserMentions(userMentionList);

    return entities;
  }
}
