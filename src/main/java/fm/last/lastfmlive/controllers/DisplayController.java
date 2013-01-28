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
