package cn.cube.base.third.wx;

import cn.cube.base.core.util.LoggerUtils;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.Map;

/**
 * User: rizenguo
 * Date: 2014/11/1
 * Time: 14:06
 */
public class XMLParser {
    private static final Logger logger = LoggerUtils.getLogger(XMLParser.class);

    public static Map<String, String> getMapFromXML(String xmlString){
        Map<String, String> map = Maps.newHashMap();
        try {
            DocumentBuilder builder = newDocumentBuilder();
            InputStream is = WxApi.getStringStream(xmlString);
            Document document = builder.parse(is);

            NodeList allNodes = document.getFirstChild().getChildNodes();
            Node node;
            int i = 0;
            while (i < allNodes.getLength()) {
                node = allNodes.item(i);
                if (node instanceof Element) {
                    map.put(node.getNodeName(), node.getTextContent());
                }
                i++;
            }
        } catch (Exception e) {
            logger.error("xml to map fail", e);
        }
        return map;

    }

    private static DocumentBuilder newDocumentBuilder() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
        String feature = "http://apache.org/xml/features/disallow-doctype-decl";
        factory.setFeature(feature, true);
        feature = "http://xml.org/sax/features/external-general-entities";
        factory.setFeature(feature, false);
        feature = "http://xml.org/sax/features/external-parameter-entities";
        factory.setFeature(feature, false);

        feature = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
        factory.setFeature(feature, false);

        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);

        return factory.newDocumentBuilder();
    }


}
