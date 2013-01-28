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
