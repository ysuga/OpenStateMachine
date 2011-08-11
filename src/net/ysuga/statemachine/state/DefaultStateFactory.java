/**
 * DefaultStateFactory.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/04
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.state;

import java.util.HashMap;
import java.util.Map;

import net.ysuga.statemachine.StateMachine;
import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.exception.InvalidFSMFileException;
import net.ysuga.statemachine.state.action.StateAction;
import net.ysuga.statemachine.state.action.StateActionFactory;
import net.ysuga.statemachine.state.action.StateActionFactoryManager;
import net.ysuga.statemachine.state.action.StateActionList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author ysuga
 * 
 */
public class DefaultStateFactory implements StateFactory {

	private String kind;

	/**
	 * <div lang="ja"> コンストラクタ </div> <div lang="en"> Constructor </div>
	 */
	public DefaultStateFactory() {
		setKind(StateMachineTagNames.DEFAULT_STATE);
	}

	protected void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * <div lang="ja">
	 * 
	 * @return </div> <div lang="en">
	 * @return </div>
	 */
	@Override
	final public String getKind() {
		return kind;
	}

	/**
	 * <div lang="ja">
	 * 
	 * @param element
	 * @return
	 * @throws Exception
	 *             </div> <div lang="en">
	 * @param element
	 * @return
	 * @throws Exception
	 *             </div>
	 */
	@Override
	public State loadState(Node node) throws InvalidFSMFileException {
		NamedNodeMap attributes = node.getAttributes();
		String name = attributes.getNamedItem(StateMachineTagNames.NAME)
				.getNodeValue();
		StateCondition stateCondition = StateCondition.parseString(attributes
				.getNamedItem(StateMachineTagNames.CONDITION)
				.getNodeValue());
		StateCondition initialStateCondition = StateCondition.parseString(attributes
				.getNamedItem(StateMachineTagNames.INITIAL_STATE)
				.getNodeValue());
		int x = Integer.parseInt(attributes
				.getNamedItem(StateMachineTagNames.X).getNodeValue());
		int y = Integer.parseInt(attributes
				.getNamedItem(StateMachineTagNames.X).getNodeValue());

		DefaultState state = new DefaultState(name);
		
		state.setX(x);
		state.setY(y);
		state.setStateCondition(stateCondition);
		state.setInitialStateCondition(initialStateCondition);
		
		NodeList childNodeList = node.getChildNodes();
		for (int i = 0; i < childNodeList.getLength(); i++) {
			Node childNode = childNodeList.item(i);
			StateActionList actionList = null;
			if (childNode.getNodeName().equals(StateMachineTagNames.ONENTRY)) {
				actionList = state.getOnEntryActionList();
			} else if (childNode.getNodeName().equals(
					StateMachineTagNames.ONEXIT)) {
				actionList = state.getOnEntryActionList();
			} else if (childNode.getNodeName().equals(
					StateMachineTagNames.ONOPERATE)) {
				actionList = state.getOnEntryActionList();
			} else {
				continue;
			}
			
			NodeList grandChildNodeList = childNode.getChildNodes();
			for(int j = 0;j < grandChildNodeList.getLength();j++) {
				Node grandChildNode = grandChildNodeList.item(j);
				Map<Integer, StateAction> actionMap = new HashMap<Integer, StateAction>();
				if(grandChildNode.getNodeName().equals(StateMachineTagNames.STATEACTION)) {
					int order = Integer.parseInt(grandChildNode.getAttributes().getNamedItem(StateMachineTagNames.ORDER).getNodeValue());
					String kind = grandChildNode.getTextContent();
					StateActionFactory factory = StateActionFactoryManager.getInstance().get(kind);
					if(factory == null) {
						throw new InvalidFSMFileException();
					}
					StateAction action = factory.createStateAction(grandChildNode);
					actionMap.put(new Integer(order), action);
				}
				
				for(int k = 0;k < actionMap.size();k++) {
					StateAction action = actionMap.get(new Integer(k));
					actionList.add(action);
				}
			}

		}


		return state;
	}
}
