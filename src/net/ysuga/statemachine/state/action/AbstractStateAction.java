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
import net.ysuga.statemachine.util.ParameterMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
	
	abstract public ParameterMap getParameterMap();

}
