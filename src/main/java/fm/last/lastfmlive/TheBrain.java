/*
 * Copyright 2012 Last.fm
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package fm.last.lastfmlive;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fm.last.lastfmlive.data.Artist;
import fm.last.lastfmlive.data.Chart;
import fm.last.lastfmlive.data.Track;
import fm.last.lastfmlive.data.Tweet;
import fm.last.lastfmlive.data.User;
import fm.last.lastfmlive.service.LastfmFacade;

@Component
public class TheBrain {

  private static Logger log = LoggerFactory.getLogger(TheBrain.class);

  @Autowired
  private LastfmFacade lastfmApi;

  private final Chart<Artist> artistChart = new Chart<Artist>();
  private final Chart<Track> trackChart = new Chart<Track>();
  private final Chart<String> tagChart = new Chart<String>();

  private final List<User> users = new LinkedList<User>();
  private final List<Tweet> liveTweets = new LinkedList<Tweet>();

  public List<User> getUserList() {
    return users;
  }

  public List<Artist> getArtistList(int maxNumber) {
    List<Artist> topList = artistChart.sortedView();
    topList = topList.subList(0, topList.size() < maxNumber ? topList.size() : maxNumber);
    return topList;
  }

  public List<Track> getTrackList(int maxNumber) {
    List<Track> topList = trackChart.sortedView();
    topList = topList.subList(0, topList.size() < maxNumber ? topList.size() : maxNumber);
    return topList;
  }

  public List<Tweet> getTweets(int maxNumber) {
    return liveTweets;
  }

  public List<String> getTags(int maxNumber) {
    List<String> topList = tagChart.sortedView();
    topList = topList.subList(0, topList.size() < maxNumber ? topList.size() : maxNumber);
    return topList;
  }

  public synchronized void addUser(String user) {
    log.info("Adding user {}", user);
    if (!users.contains(new User(user))) {
      User u = lastfmApi.getUserInfo(user);
      if (u != null) {
        users.add(u);

        List<Artist> artists = lastfmApi.getTopArtists(user);
        artistChart.putAll(Chart.autoWeight(artists));

        List<Track> tracks = lastfmApi.getTopTracks(user);
        trackChart.putAll(Chart.autoWeight(tracks));

      } else {
        log.info("User {} could not be found");
      }
    } else {
      log.info("User {} already present", user);
    }
  }

  public synchronized void addTweets(Collection<Tweet> tweets) {
    liveTweets.addAll(tweets);
  }

  public synchronized void addTags(List<String> tags) {
    tagChart.putAll(Chart.autoWeight(tags));
  }

}
