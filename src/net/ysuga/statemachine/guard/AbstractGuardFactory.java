/**
 * AbstractGuardFactory.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/07/31
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.guard;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.exception.InvalidFSMFileException;
import net.ysuga.statemachine.util.ParameterMap;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author ysuga
 *
 */
public abstract class AbstractGuardFactory implements GuardFactory {

	private String kind;
	
	public String getKind() { return kind; }
	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * </div>
	 */
	public AbstractGuardFactory(String kind) {
		this.kind = kind;
	}
	
	public String parseKind(Node node) {
		return node.getAttributes().getNamedItem(StateMachineTagNames.KIND).getNodeValue();
	}
	
	public String parseName(Node node) {
		return node.getAttributes().getNamedItem(StateMachineTagNames.NAME).getNodeValue();
	}
	
	/**
	 * <div lang="ja">
	 * @param element
	 * @return
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 * @param element
	 * @return
	 * @throws Exception
	 * </div>
	 */
	@Override
	public Guard loadGuard(Node node) throws InvalidFSMFileException {
		String name = parseName(node);
		NodeList childNodeList = node.getChildNodes();
		ParameterMap map = null;
		for(int i = 0;i < childNodeList.getLength();i++) {
			Node childNode = childNodeList.item(i);
			if(childNode.getNodeName().equals(StateMachineTagNames.PARAMETER)) {
				map = ParameterMap.parseParameterMap(childNode);
			}
		}
		return createGuard(name, map);
	}
	
	/**
	 * 
	 * <div lang="ja">
	 *
	 * @param name
	 * @param parameterMap
	 * @return
	 * </div>
	 * <div lang="en">
	 *
	 * @param name
	 * @param parameterMap
	 * @return
	 * </div>
	 */
	public abstract Guard createGuard(String name, ParameterMap parameterMap);
	
	public String toString() {
		return "GuardFactory:" + getKind();
	}
}
