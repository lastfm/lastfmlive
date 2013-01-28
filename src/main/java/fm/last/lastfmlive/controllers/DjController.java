package fm.last.lastfmlive.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fm.last.lastfmlive.TheBrain;

@Controller
@RequestMapping("/dj")
public class DjController {

  @Autowired
  TheBrain theBrain;

  @RequestMapping("/")
  public ModelAndView djView() {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("artists", theBrain.getArtistList(100));
    model.put("tracks", theBrain.getTrackList(100));
    model.put("tags", theBrain.getTags(30));
    model.put("tweets", theBrain.getTweets(10));
    return new ModelAndView("djView", model);
  }
}
