package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;


public class TwitterCLIApp {

  private Controller controller;

  public TwitterCLIApp(Controller controller) {
    this.controller = controller;
  }

  public static void main(String[] args) {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(
        consumerKey, consumerSecret, accessToken, tokenSecret);
    CrdDao dao = new TwitterDao(httpHelper);
    Service service = new TwitterService(dao);
    Controller controller = new TwitterController(service);
    TwitterCLIApp app = new TwitterCLIApp(controller);

    app.run(args);
  }

  public void run(String[] args) {
    if (args.length == 0)
      throw new IllegalArgumentException(
          "USAGE: TwitterAppCLI post|show|delete [options]");

    switch (args[0].toLowerCase()) {
      case "post":
        printTweet(controller.postTweet(args));
      break;
      case "show":
        printTweet(controller.showTweet(args));
      break;
      case "delete":
        controller.deleteTweet(args).forEach(this::printTweet);
      break;
      default:
        throw new IllegalArgumentException(
            "USAGE: TwitterAppCLI post|show|delete [options]");
    }
  }

  private void printTweet(Tweet tweet) {
    try {
      String jsonStr = JsonUtil.toJson(tweet, true, false);
      System.out.println(jsonStr);
    } catch (JsonProcessingException ex) {
      throw new RuntimeException("Unable to convert to JSON ", ex);
    }
  }

}
