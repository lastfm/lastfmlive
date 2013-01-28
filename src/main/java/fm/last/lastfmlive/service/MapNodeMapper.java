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
