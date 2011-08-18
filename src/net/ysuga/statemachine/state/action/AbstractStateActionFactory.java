/**
 * AbstractStateActionFactory.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/06
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.state.action;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.util.ParameterMap;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author ysuga
 *
 */
public abstract class AbstractStateActionFactory implements StateActionFactory {

	private String kind;
	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * </div>
	 */
	public AbstractStateActionFactory(String kind) {
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
	final public StateAction createStateAction(Node node) {
		ParameterMap parameterMap = null;
		NodeList childNodeList = node.getChildNodes();
		for(int i = 0;i < childNodeList.getLength();i++) {
			Node childNode = childNodeList.item(i);
			if(childNode.getNodeName().equals(StateMachineTagNames.PARAMETER)) {
				parameterMap = ParameterMap.parseParameterMap(childNode);
			}
		}
		return buildStateAction(parameterMap);
	}

	public abstract StateAction buildStateAction(ParameterMap parameterMap);

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
		return this.kind;
	}

}
