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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fm.last.lastfmlive.data.Artist;
import fm.last.lastfmlive.service.LastfmFacade;

@Component
public class ArtistTagger {

  @Autowired
  private LastfmFacade lastfmFacade;

  @Autowired
  private TheBrain theBrain;

  @Scheduled(fixedDelay = 30000)
  public void tagArtists() {
    List<Artist> artists = new ArrayList<Artist>(theBrain.getArtistList(50));
    for (Artist a : artists) {
      if (a.getTags() != null) {
        continue;
      }
      List<String> tags = lastfmFacade.getTopTags(a.getName());
      tags = tags.subList(0, tags.size() > 5 ? 5 : tags.size());
      a.setTags(tags);
      theBrain.addTags(tags);
    }
  }
}
