package fm.last.lastfmlive.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fm.last.lastfmlive.TheBrain;

@Controller
@RequestMapping("/public")
public class DisplayController {

  @Autowired
  private TheBrain theBrain;

  private final int artistsToShow = 10;

  @RequestMapping("/")
  public ModelAndView currentTopArtists() {
    Map<String, Object> model = new HashMap<String, Object>();

    model.put("artistChart", theBrain.getArtistList(artistsToShow));
    model.put("users", theBrain.getUserList());
    model.put("tweets", theBrain.getTweets(10));

    return new ModelAndView("currentTopArtists", model);
  }
}
