package fm.last.lastfmlive.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fm.last.lastfmlive.TheBrain;

@Controller
@RequestMapping("/user")
public class UserEntryController {

  @Autowired
  private TheBrain theBrain;

  @RequestMapping("/")
  public ModelAndView userEntry() {
    return new ModelAndView("userEntry", "users", theBrain.getUserList());
  }

  @RequestMapping("add")
  public ModelAndView userEntryAdd(@RequestParam String user) {
    theBrain.addUser(user);
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("status", "User " + user + " was added successfully");
    model.put("users", theBrain.getUserList());
    return new ModelAndView("userEntry", model);
  }

}
