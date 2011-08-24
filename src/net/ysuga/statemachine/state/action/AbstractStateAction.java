/**
 * AbstractStateAction.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/06
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.state.action;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.exception.InvalidFSMFileException;
import net.ysuga.statemachine.util.ParameterMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author ysuga
 *
 */
public abstract class AbstractStateAction implements StateAction {

	private String kind;
	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * </div>
	 */
	public AbstractStateAction(String kind) {
		this.kind = kind;
	}


	/**
	 * <div lang="ja">
	 * @return
	 * </div>
	 * <div lang="en">
	 * @return
	 * </div>
	 */
	@Override
	final public String getKind() {
		return kind;
	}

	/**
	 * <div lang="ja">
	 * @param xmlDocument
	 * @return
	 * </div>
	 * <div lang="en">
	 * @param xmlDocument
	 * @return
	 * </div>
	 */
	@Override
	public Element toElement(Document xmlDocument) {
		Element elem = xmlDocument.createElement(StateMachineTagNames.STATEACTION);
		elem.setAttribute(StateMachineTagNames.KIND, getKind());
		ParameterMap map = getParameterMap();
		elem.appendChild(map.toElement(xmlDocument));
		return elem;
	}
	
	@Override
	public void load(Node node)  throws InvalidFSMFileException {
		NamedNodeMap attr = node.getAttributes();
		String kind = attr.getNamedItem(StateMachineTagNames.KIND).getNodeValue();
		this.kind = kind;
		
		ParameterMap parameterMap = null;
		NodeList childNodeList = node.getChildNodes();
		for(int i = 0;i < childNodeList.getLength();i++) {
			Node childNode = childNodeList.item(i);
			if(childNode.getNodeName().equals(StateMachineTagNames.PARAMETER)) {
				parameterMap = ParameterMap.parseParameterMap(childNode);
				setParameterMap(parameterMap);
			}
		}
	}
}
