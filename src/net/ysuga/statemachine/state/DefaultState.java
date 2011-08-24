package net.ysuga.statemachine.state;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.exception.InvalidConnectionException;
import net.ysuga.statemachine.exception.InvalidFSMFileException;
import net.ysuga.statemachine.guard.Guard;
import net.ysuga.statemachine.state.action.StateAction;
import net.ysuga.statemachine.state.action.StateActionFactory;
import net.ysuga.statemachine.state.action.StateActionFactoryManager;
import net.ysuga.statemachine.state.action.StateActionList;
import net.ysuga.statemachine.transition.Transition;
import net.ysuga.statemachine.transition.TransitionMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Default State. Default State for State Machine. No state action, no functions
 * are installed.
 * 
 * @author Yuki Suga (ysuga.net)
 * @brief State Machine System Framework
 * @date 2011/05/13
 * @license see lisence.txt
 * @copyright ysuga.net 2011, all rights reserved.
 * 
 */
public class DefaultState implements State {

	private static Logger logger;
	static {
		logger = Logger.getLogger(DefaultState.class.getName());
	}

	private String kind;

	/**
	 * 
	 * <div lang="ja">
	 * 
	 * @return </div> <div lang="en">
	 * @return </div>
	 */
	@Override
	public String getKind() {
		return kind;
	}

	/**
	 * 
	 * setKind <div lang="ja">
	 * 
	 * @param kind
	 *            </div> <div lang="en">
	 * 
	 * @param kind
	 *            </div>
	 */
	protected void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * 
	 */
	private String name;

	/**
	 * 
	 * <div lang="ja">
	 * 
	 * @return </div> <div lang="en">
	 * @return </div>
	 */
	@Override
	final public String getName() {
		return name;
	}

	/**
	 * 
	 * <div lang="ja">
	 * 
	 * @param name
	 *            </div> <div lang="en">
	 * 
	 * @param name
	 *            </div>
	 */
	final public void setName(String name) {
		this.name = name;
	}

	private TransitionMap transitionMap;

	public Iterator<Transition> getTransitionIterator() {
		ArrayList<Transition> transitionList = new ArrayList<Transition>(
				transitionMap.values());

		return transitionList.iterator();
	}

	private StateActionList onEntryActions;

	@Override
	public StateActionList getOnEntryActionList() {
		return onEntryActions;
	}

	private StateActionList onExitActions;

	@Override
	public StateActionList getOnExitActionList() {
		return onExitActions;
	}

	private StateActionList onOperateActions;

	@Override
	public StateActionList getOnOperateActionList() {
		return onOperateActions;
	}

	protected StateCondition stateCondition = StateCondition.INACTIVE;
	protected StateCondition stateConditionBuffer = null;

	protected StateCondition initialStateCondition = StateCondition.INACTIVE;

	final public StateCondition getInitialStateCondition() {
		return initialStateCondition;
	}

	public void setInitialStateCondition(StateCondition condition) {
		initialStateCondition = condition;
	}

	/**
	 * 
	 * <div lang="ja">
	 * 
	 * @return </div> <div lang="en">
	 * 
	 * @return </div>
	 */
	@Override
	public StateCondition getStateCondition() {
		return stateCondition;
	}

	/**
	 * 
	 * <div lang="ja">
	 * 
	 * @param state
	 * @throws Exception
	 *             </div> <div lang="en">
	 * 
	 * @param state
	 * @throws Exception
	 *             </div>
	 */
	@Override
	public void setStateCondition(StateCondition state) {
		this.stateConditionBuffer = state;
	}

	public void setStateConditionImmediately(StateCondition state)
			throws Exception {
		this.stateCondition = state;
	}

	@Override
	public boolean updateStateCondition() throws Exception {
		if (stateConditionBuffer != null) {
			setStateConditionImmediately(stateConditionBuffer);
			stateConditionBuffer = null;
			if (getStateCondition().equals(StateCondition.ACTIVE)) {
				return true;
			}
		}
		return false;
	}

	private Point location;

	/**
	 * <div lang="ja">
	 * 
	 * @return </div> <div lang="en">
	 * @return </div>
	 */
	@Override
	public Point getLocation() {
		return location;
	}

	/**
	 * 
	 * <div lang="ja">
	 * 
	 * @param x
	 * @param y
	 *            </div> <div lang="en">
	 * @param y
	 *            </div>
	 */
	public final void setLocation(Point p) {
		setLocation(p.x, p.y);
	}

	/**
	 * 
	 * <div lang="ja">
	 * 
	 * @param x
	 * @param y
	 *            </div> <div lang="en">
	 * @param x
	 * @param y
	 *            </div>
	 */
	public final void setLocation(int x, int y) {
		x = x < 0 ? 0 : x;
		y = y < 0 ? 0 : y;
		location = new Point(x, y);
	}

	/**
	 * 
	 * <div lang="ja"> コンストラクタ
	 * 
	 * @param name
	 *            </div> <div lang="en"> Constructor
	 * @param name
	 *            </div>
	 */
	public DefaultState(String name) {
		this.setName(name);
		this.kind = StateMachineTagNames.DEFAULT_STATE;
		transitionMap = new TransitionMap();
		onEntryActions = new StateActionList();
		onExitActions = new StateActionList();
		onOperateActions = new StateActionList();
		setLocation(0, 0);
	}

	/**
	 * load <div lang="ja">
	 * 
	 * @param node
	 * @throws InvalidFSMFileException
	 *             </div> <div lang="en">
	 * 
	 * @param node
	 * @throws InvalidFSMFileException
	 *             </div>
	 */
	@Override
	public void load(Node node) throws InvalidFSMFileException {
		NamedNodeMap attributes = node.getAttributes();
		String name = attributes.getNamedItem(StateMachineTagNames.NAME)
				.getNodeValue();
		StateCondition stateCondition = StateCondition.parseString(attributes
				.getNamedItem(StateMachineTagNames.CONDITION).getNodeValue());
		StateCondition initialStateCondition = StateCondition
				.parseString(attributes.getNamedItem(
						StateMachineTagNames.INITIAL_STATE).getNodeValue());
		int x = Integer.parseInt(attributes
				.getNamedItem(StateMachineTagNames.X).getNodeValue());
		int y = Integer.parseInt(attributes
				.getNamedItem(StateMachineTagNames.Y).getNodeValue());

		this.setName(name);
		this.kind = StateMachineTagNames.DEFAULT_STATE;
		transitionMap = new TransitionMap();
		onEntryActions = new StateActionList();
		onExitActions = new StateActionList();
		onOperateActions = new StateActionList();
		setLocation(x, y);
		setStateCondition(stateCondition);
		setInitialStateCondition(initialStateCondition);

		NodeList childNodeList = node.getChildNodes();
		for (int i = 0; i < childNodeList.getLength(); i++) {
			Node childNode = childNodeList.item(i);
			StateActionList actionList = null;
			if (childNode.getNodeName().equals(StateMachineTagNames.ONENTRY)) {
				actionList = getOnEntryActionList();
			} else if (childNode.getNodeName().equals(
					StateMachineTagNames.ONEXIT)) {
				actionList = getOnEntryActionList();
			} else if (childNode.getNodeName().equals(
					StateMachineTagNames.ONOPERATE)) {
				actionList = getOnEntryActionList();
			} else {
				continue;
			}

			NodeList grandChildNodeList = childNode.getChildNodes();
			for (int j = 0; j < grandChildNodeList.getLength(); j++) {
				Node grandChildNode = grandChildNodeList.item(j);
				Map<Integer, StateAction> actionMap = new HashMap<Integer, StateAction>();
				if (grandChildNode.getNodeName().equals(
						StateMachineTagNames.STATEACTION)) {
					int order = Integer.parseInt(grandChildNode.getAttributes()
							.getNamedItem(StateMachineTagNames.ORDER)
							.getNodeValue());
					String kind = grandChildNode.getTextContent();
					StateActionFactory factory = StateActionFactoryManager
							.getInstance().get(kind);
					if (factory == null) {
						throw new InvalidFSMFileException();
					}
					StateAction action = factory
							.createStateAction(grandChildNode);
					actionMap.put(new Integer(order), action);
				}

				for (int k = 0; k < actionMap.size(); k++) {
					StateAction action = actionMap.get(new Integer(k));
					actionList.add(action);
				}
			}

		}
	}

	/**
	 * 
	 * <div lang="ja">
	 * 
	 * @throws Exception
	 *             </div> <div lang="en">
	 * 
	 * @throws Exception
	 *             </div>
	 */
	@Override
	public boolean operate() throws Exception {
		logger.entering(getClass().getName(), "operate", this);
		for (String key : transitionMap.keySet()) {
			Transition transition = transitionMap.get(key);
			if (transition.checkGuard()) {
				transition.transit();
				return true;
			}
		}
		// onOperate();
		return false;
	}

	/**
	 * 
	 * <div lang="ja">
	 * 
	 * @param xmlDocument
	 * @return </div> <div lang="en">
	 * 
	 * @param xmlDocument
	 * @return </div>
	 */
	@Override
	public Element toElement(Document xmlDocument) {
		Element stateElem = xmlDocument
				.createElement(StateMachineTagNames.STATE);
		stateElem.setAttribute(StateMachineTagNames.NAME, getName());
		stateElem.setAttribute(StateMachineTagNames.INITIAL_STATE,
				getInitialStateCondition().toString());
		stateElem.setAttribute(StateMachineTagNames.CONDITION,
				getStateCondition().toString());
		stateElem.setAttribute(StateMachineTagNames.KIND, getKind());
		Point p = getLocation();
		stateElem.setAttribute(StateMachineTagNames.X, Integer.toString(p.x));
		stateElem.setAttribute(StateMachineTagNames.Y, Integer.toString(p.y));

		Element onEntryElem = xmlDocument
				.createElement(StateMachineTagNames.ONENTRY);
		for (StateAction action : getOnEntryActionList()) {
			Element actionElem = action.toElement(xmlDocument);
			actionElem.setAttribute(StateMachineTagNames.ORDER,
					Integer.toString(onEntryActions.indexOf(action)));
			onEntryElem.appendChild(actionElem);
		}
		stateElem.appendChild(onEntryElem);

		Element onExitElem = xmlDocument
				.createElement(StateMachineTagNames.ONEXIT);
		for (StateAction action : getOnExitActionList()) {
			Element actionElem = action.toElement(xmlDocument);
			actionElem.setAttribute(StateMachineTagNames.ORDER,
					Integer.toString(onExitActions.indexOf(action)));
			onExitElem.appendChild(actionElem);
		}
		stateElem.appendChild(onExitElem);

		Element onOperateElem = xmlDocument
				.createElement(StateMachineTagNames.ONOPERATE);
		for (StateAction action : getOnOperateActionList()) {
			Element actionElem = action.toElement(xmlDocument);
			actionElem.setAttribute(StateMachineTagNames.ORDER,
					Integer.toString(onOperateActions.indexOf(action)));
			onOperateElem.appendChild(actionElem);
		}
		stateElem.appendChild(onOperateElem);

		/**
		 * Element transitionsElement =
		 * xmlDocument.createElement(StateMachineTagNames.TRANSITIONS); for
		 * (Transition transition : transitionMap.values()) { Element
		 * transitionElem = transition.toElement(xmlDocument);
		 * transitionsElement.appendChild(transitionElem); }
		 * stateElem.appendChild(transitionsElement);
		 **/
		return stateElem;
	}

	/**
	 * 
	 * <div lang="ja">
	 * 
	 * @throws Exception
	 *             </div> <div lang="en">
	 * 
	 * @throws Exception
	 *             </div>
	 */
	public void onOperate() throws Exception {
		logger.entering(getClass().getName(), "onOperate", this);

		for (StateAction action : onOperateActions) {
			action.actionPerformed(this);
		}
	}

	/**
	 * 
	 * <div lang="ja">
	 * 
	 * @throws Exception
	 *             </div> <div lang="en">
	 * 
	 * @throws Exception
	 *             </div>
	 */
	@Override
	public void onExit() throws Exception {
	}

	/**
	 * 
	 * <div lang="ja">
	 * 
	 * @throws Exception
	 *             </div> <div lang="en">
	 * 
	 * @throws Exception
	 *             </div>
	 */
	@Override
	public void onEntry() throws Exception {
	}

	@Override
	final public Transition getTransition(String name) {
		return transitionMap.get(name);
	}

	/**
	 * 
	 * removeTransition <div lang="ja"> Transitionを削除します．
	 * 
	 * @param name
	 *            </div> <div lang="en"> Remove Transition.
	 * @param name
	 *            </div>
	 */
	@Override
	public void removeTransition(String name) {
		transitionMap.remove(name);
	}

	/**
	 * 
	 * connect <div lang="ja">
	 * guardで指定したガードで，targetで指定したStateに対してTransitionを張ります．
	 * 
	 * @param name
	 * @param target
	 * @param guard
	 * @throws InvalidConnectionException
	 *             guardがnullの場合，transitionの名前が同一State内で重なる場合に例外を投げます． </div>
	 *             <div lang="en"> 　Add Transition
	 * @param name
	 *            Name of Transition. Do not duplicate the name in one State
	 * @param target
	 *            Traget State
	 * @param guard
	 *            Guard class object
	 * @throws InvalidConnectionException
	 *             if guard is null or name is duplicated in one state. </div>
	 */
	@Override
	public void connect(String name, State target, Guard guard)
			throws InvalidConnectionException {
		logger.entering(getClass().getName(), "connect", new Object[] { this,
				name, target, guard });
		if (transitionMap.containsKey(name) || guard == null) {
			throw new InvalidConnectionException();
		}
		transitionMap.put(name, new Transition(name, this, target, guard));
	}

	public String toString() {
		return "State(" + getName() + ")";
	}

	/**
	 * 
	 * <div lang="ja"> </div> <div lang="en"> </div>
	 */
	@Override
	public void vanish() {
		Set<String> keySet = transitionMap.keySet();
		for (String key : keySet) {
			Transition transition = transitionMap.get(key);
			transition.vanish();
			transitionMap.remove(transition);
		}
	}

}
