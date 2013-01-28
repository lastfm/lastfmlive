package fm.last.lastfmlive.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import fm.last.lastfmlive.TheBrain;
import fm.last.lastfmlive.data.Tweet;

@Component
public class TwitterClient {

  private static Logger log = LoggerFactory.getLogger(TwitterClient.class);

  private static final String searchUrl = "http://search.twitter.com/search.json?q=";

  @Autowired
  private TheBrain theBrain;

  private final Set<Tweet> collectedTweets = new HashSet<Tweet>();

  @Value("${lastfmlive.hashtag}")
  private String searchHashTag;

  @Value("${lastfmlive.twitterEnabled}")
  private boolean twitterEnabled;

  @Scheduled(fixedDelay = 30000)
  public void checkTwitter() {
    if (!twitterEnabled) {
      return;
    }
    log.info("Checking twitter for new tweets.");

    Set<Tweet> newTweets = new HashSet<Tweet>(checkForHashTaggedTweets(searchHashTag));
    newTweets.removeAll(collectedTweets);

    collectedTweets.addAll(newTweets);
    theBrain.addTweets(newTweets);
    addUsersFromTweets(newTweets);

    log.info("Tweets found: {}", newTweets.size());
  }

  private void addUsersFromTweets(Set<Tweet> newTweets) {
    for (Tweet tweet : newTweets) {
      log.info("Checking tweet {} for Last.fm usernames", tweet.getMessage());
      for (String word : tweet.getMessage().split(" ")) {
        log.info("Tokenizing tweet. {}", word);
        if (word.startsWith("+")) {
          log.info("Attempting to add user" + word.substring(1));
          theBrain.addUser(word.substring(1));
        }
      }
    }
  }

  private static List<Tweet> checkForHashTaggedTweets(String hashTag) {
    log.debug("Searching Twitter for tweets containing #" + hashTag);

    RestTemplate restTemplate = new RestTemplate();
    Map<?, ?> tweets = restTemplate.getForObject(searchUrl + "{hashTag}", Map.class, "#" + hashTag);
    List<?> results = (List<?>) tweets.get("results");

    log.info("Tweets found: {}", results.size());
    return Lists.transform(results, ExtractTweet.INSTANCE);
  }

  private static enum ExtractTweet implements Function<Object, Tweet> {
    INSTANCE;
    public Tweet apply(Object tweet) {
      Map<?, ?> tweetMap = (Map<?, ?>) tweet;
      return new Tweet((String) tweetMap.get("text"), (String) tweetMap.get("from_user"));
    }
  };

}
