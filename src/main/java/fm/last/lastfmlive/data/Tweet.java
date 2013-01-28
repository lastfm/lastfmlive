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

package fm.last.lastfmlive.data;

public class Tweet {

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((message == null) ? 0 : message.hashCode());
    result = prime * result + ((twitterUser == null) ? 0 : twitterUser.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Tweet other = (Tweet) obj;
    if (message == null) {
      if (other.message != null) {
        return false;
      }
    } else if (!message.equals(other.message)) {
      return false;
    }
    if (twitterUser == null) {
      if (other.twitterUser != null) {
        return false;
      }
    } else if (!twitterUser.equals(other.twitterUser)) {
      return false;
    }
    return true;
  }

  public String getTwitterUser() {
    return twitterUser;
  }

  public void setTwitterUser(String twitterUser) {
    this.twitterUser = twitterUser;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  private String twitterUser;
  private String message;

  public Tweet(String message, String twitterUser) {
    this.message = message;
    this.twitterUser = twitterUser;
  }

}
