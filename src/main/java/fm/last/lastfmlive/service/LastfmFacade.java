package fm.last.lastfmlive.service;

import java.util.List;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.xml.xpath.Jaxp13XPathTemplate;
import org.springframework.xml.xpath.NodeMapper;
import org.springframework.xml.xpath.XPathOperations;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import fm.last.lastfmlive.data.Artist;
import fm.last.lastfmlive.data.Track;
import fm.last.lastfmlive.data.User;

@Repository
public class LastfmFacade {

  private static Logger log = LoggerFactory.getLogger(LastfmFacade.class);

  private static final String wsPrefix = "http://ws.audioscrobbler.com/2.0/?method=";
  private final String apiKey;

  private final XPathOperations xpathTemplate = new Jaxp13XPathTemplate();

  @Autowired
  public LastfmFacade(@Value("${lastfmlive.apiKey}") String apiKey) {
    this.apiKey = apiKey;
  }

  public List<Artist> getTopArtists(String userName) {
    log.info("Getting top artists for user {}", userName);

    RestTemplate restTemplate = new RestTemplate();
    String methodString = "user.gettopartists&user={user}&api_key={apiKey}";
    Source topArtistsXml = restTemplate.getForObject(wsPrefix + methodString, Source.class, userName, apiKey);
    List<Map<String, String>> output = xpathTemplate.evaluate("//artist", topArtistsXml, new MapNodeMapper("name",
        "playcount", "image[@size='large']"));

    log.info("Found {} top artists", output.size());
    return Lists.transform(output, artistFromMap);
  }

  public List<Track> getTopTracks(String userName) {
    log.info("Getting top tracks for user {}", userName);

    RestTemplate restTemplate = new RestTemplate();
    String methodString = "user.gettoptracks&user={user}&api_key={apiKey}";
    Source topArtistsXml = restTemplate.getForObject(wsPrefix + methodString, Source.class, userName, apiKey);
    List<Track> output = xpathTemplate.evaluate("//track", topArtistsXml, new NodeMapper<Track>() {
      public Track mapNode(Node node, int nodeIndex) throws DOMException {
        Source src = new DOMSource(node);
        try {
          String title = xpathTemplate.evaluateAsNodeList("name", src).get(0).getTextContent();
          String artist = xpathTemplate.evaluateAsNodeList("artist/name", src).get(0).getTextContent();
          return new Track(artist, title);
        } catch (Exception e) {
          return null;
        }
      }
    });

    log.info("Found {} top tracks", output.size());
    return output;
  }

  public User getUserInfo(String userName) {
    log.info("Getting user info for user {}", userName);

    RestTemplate restTemplate = new RestTemplate();
    String methodString = "user.getinfo&user={user}&api_key={apiKey}";
    try {
      Source topArtistsXml = restTemplate.getForObject(wsPrefix + methodString, Source.class, userName, apiKey);
      List<Map<String, String>> output = xpathTemplate.evaluate("//user", topArtistsXml, new MapNodeMapper("name",
          "realname", "image[@size='large']", "age", "gender"));
      if (output.size() == 0) {
        log.warn("User info not found.");
        return null;
      }

      log.info("Found user info: {}", output);
      return userFromMap.apply(output.get(0));
    } catch (HttpClientErrorException e) {
      log.warn("User not found '{}'", userName);
      return null;
    }

  }

  public List<String> getTopTags(String artistName) {
    log.info("Getting top tags for artist {}", artistName);

    RestTemplate restTemplate = new RestTemplate();
    String methodString = "artist.gettoptags&artist={artist}&api_key={apiKey}";
    Source topArtistsXml = restTemplate.getForObject(wsPrefix + methodString, Source.class, artistName, apiKey);
    List<Map<String, String>> output = xpathTemplate.evaluate("//tag", topArtistsXml, new MapNodeMapper("name"));

    log.info("Found {} tags", output.size());
    return Lists.transform(output, tagFromMap);
  }

  private static Function<Map<String, String>, Artist> artistFromMap = new Function<Map<String, String>, Artist>() {
    public Artist apply(Map<String, String> arg0) {
      if (!arg0.containsKey("name")) {
        throw new IllegalArgumentException("Map must contain 'name' key");
      }
      Artist a = new Artist();
      a.setName(arg0.get("name"));
      a.setImageUrl(arg0.get("image"));
      a.setUrl(arg0.get("url"));
      return a;
    }
  };

  private static Function<Map<String, String>, User> userFromMap = new Function<Map<String, String>, User>() {
    public User apply(Map<String, String> map) {
      if (!map.containsKey("name")) {
        throw new IllegalArgumentException("Map must contain 'name' key");
      }
      User u = new User(map.get("name"));
      u.setImageUrl(map.get("image"));
      u.setUrl(map.get("url"));
      u.setAge(map.get("age"));
      u.setRealName(map.get("realname"));
      u.setGender(map.get("gender"));
      return u;
    }
  };

  private static Function<Map<String, String>, String> tagFromMap = new Function<Map<String, String>, String>() {

    public String apply(Map<String, String> arg0) {
      return arg0.get("name");
    }
  };

}
