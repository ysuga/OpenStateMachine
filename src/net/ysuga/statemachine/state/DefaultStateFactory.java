/**
 * DefaultStateFactory.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/04
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.state;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.exception.InvalidFSMFileException;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * @author ysuga
 *
 */
public class DefaultStateFactory implements StateFactory {

	private String kind;
	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * </div>
	 */
	public DefaultStateFactory() {
		setKind(StateMachineTagNames.DEFAULT_STATE);
	}

	protected void setKind(String kind) {
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
	public State loadState(Node node) throws InvalidFSMFileException {
		NamedNodeMap attributes = node.getAttributes();
		String name = attributes.getNamedItem(StateMachineTagNames.NAME).getNodeValue();
		StateCondition stateCondition = StateCondition.parseString(attributes.getNamedItem(StateMachineTagNames.INITIAL_STATE).getNodeValue());
		int x = Integer.parseInt(attributes.getNamedItem(StateMachineTagNames.X).getNodeValue());
		int y = Integer.parseInt(attributes.getNamedItem(StateMachineTagNames.X).getNodeValue());
		DefaultState state =  new DefaultState(name);
		state.setX(x);
		state.setY(y);		
		state.setStateCondition(stateCondition);
		return state;
	}

}
