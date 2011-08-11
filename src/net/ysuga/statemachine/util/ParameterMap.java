/**
 * GuardParameterMap.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/04
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.util;

import java.util.HashMap;

import net.ysuga.statemachine.StateMachineTagNames;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


/**
 * @author ysuga
 *
 */
public class ParameterMap extends HashMap<String, String> {

	public ParameterMap() {
		super();
	}
	
	public Element toElement(Document xmlDocument) {
		Element paramElement = xmlDocument.createElement(StateMachineTagNames.PARAMETER);
		for(String key : keySet()) {
			String value = get(key);
			Element keyElement = xmlDocument.createElement(key);
			Text valueElement = xmlDocument.createTextNode(value);
			keyElement.appendChild(valueElement);
			paramElement.appendChild(keyElement);
		}
		return paramElement;
	}
	
	static public ParameterMap parseParameterMap(Node node) {
		ParameterMap map = new ParameterMap();
		String name = null;
		String value = null;
		NodeList nodeList = node.getChildNodes();
		for(int i = 0;i < nodeList.getLength();i++) {
			Node childNode = nodeList.item(i);
			if(childNode.getNodeType() == Node.ELEMENT_NODE) {
				name = childNode.getNodeName();
				NodeList grandChildNodeList = childNode.getChildNodes();
				for(int j = 0;j < grandChildNodeList.getLength();j++) {
					Node grandChildNode = grandChildNodeList.item(j);
					value = grandChildNode.getTextContent();
					map.put(name, value);
				}
			} 
		}
		return map;
	}
}
