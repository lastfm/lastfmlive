package fm.last.lastfmlive.data;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.ForwardingList;

@Component
public class UserList extends ForwardingList<User> {

  private final List<User> delegate = new LinkedList<User>();

  @Override
  protected List<User> delegate() {
    return delegate;
  }

}
