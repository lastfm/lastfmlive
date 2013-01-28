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

package fm.last.lastfmlive.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.dom.DOMSource;

import org.springframework.xml.xpath.Jaxp13XPathTemplate;
import org.springframework.xml.xpath.NodeMapper;
import org.springframework.xml.xpath.XPathOperations;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class MapNodeMapper implements NodeMapper<Map<String, String>> {
  private final String[] xpaths;
  private final XPathOperations xpathTemplate = new Jaxp13XPathTemplate();

  public MapNodeMapper(String... xpaths) {
    this.xpaths = xpaths;
  }

  public Map<String, String> mapNode(Node arg0, int arg1) throws DOMException {
    Map<String, String> result = new HashMap<String, String>();
    for (String xpath : xpaths) {
      List<Node> nodes = xpathTemplate.evaluateAsNodeList(xpath, new DOMSource(arg0));
      if (nodes.size() != 0) {
        Element e = (Element) nodes.get(0);
        result.put(e.getNodeName(), e.getTextContent());
      }
    }
    return result;
  }
}
