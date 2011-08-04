package net.ysuga.statemachine.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.Transition;
import net.ysuga.statemachine.exception.InvalidConnectionException;
import net.ysuga.statemachine.guard.Guard;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
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
	
	public String getKind() {
		return kind;
	}
	
	private String name;

	/**
	 * 
	 * <div lang="ja">
	 * @return
	 * </div>
	 * <div lang="en">
	 * @return
	 * </div>
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
	 * </div>
	 * <div lang="en">
	 *
	 * @param name
	 * </div>
	 */
	final public void setName(String name) {
		this.name = name;
	}
	
	
	private Map<String, Transition> transitionMap;

	private ArrayList<StateAction> onEntryActions;
	
	private ArrayList<StateAction> onExitActions;
	
	private ArrayList<StateAction> onOperateActions;
	
	protected StateCondition stateCondition = StateCondition.INACTIVE;
	
	/**
	 * 
	 * <div lang="ja">
	 *
	 * @return
	 * </div>
	 * <div lang="en">
	 *
	 * @return
	 * </div>
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
	 * </div>
	 * <div lang="en">
	 *
	 * @param state
	 * @throws Exception
	 * </div>
	 */
	@Override
	public void setStateCondition(StateCondition state) {
		this.stateCondition = state;
	}
	
	private int x;
	
	/**
	 * @return the x
	 */
	@Override
	public final int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public final void setX(int x) {
		this.x = x;
	}
	
	private int y;

	/**
	 * @return the y
	 */
	@Override
	public final int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public final void setY(int y) {
		this.y = y;
	}
	
	
	/**
	 * 
	 * <div lang="ja">
	 * コンストラクタ
	 * @param name
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * @param name
	 * </div>
	 */
	public DefaultState(String name) {
		this.setName(name);
		this.kind = StateMachineTagNames.DEFAULT_STATE;
		transitionMap = new HashMap<String, Transition>();
		onEntryActions = new ArrayList<StateAction>();
		onExitActions = new ArrayList<StateAction>();
		onOperateActions = new ArrayList<StateAction>();
		setX(0); setY(0);
	}
	

	/**
	 * 
	 * <div lang="ja">
	 *
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 *
	 * @throws Exception
	 * </div>
	 */
	@Override
	public void operate() throws Exception {
		logger.entering(getClass().getName(), "operate", this);
		for (String key : transitionMap.keySet()) {
			Transition transition = transitionMap.get(key);
			if (transition.checkGuard()) {
				transition.transit();
				return;
			}
		}
		onOperate();
	}

	/**
	 * 
	 * <div lang="ja">
	 *
	 * @param xmlDocument
	 * @return
	 * </div>
	 * <div lang="en">
	 *
	 * @param xmlDocument
	 * @return
	 * </div>
	 */
	@Override
	public Element toElement(Document xmlDocument) {
		Element stateElem = xmlDocument.createElement(StateMachineTagNames.STATE);
		stateElem.setAttribute(StateMachineTagNames.NAME, getName());
		stateElem.setAttribute(StateMachineTagNames.INITIAL_STATE, getStateCondition().toString());
		stateElem.setAttribute(StateMachineTagNames.KIND, getKind());
		stateElem.setAttribute(StateMachineTagNames.X, Integer.toString(getX()));
		stateElem.setAttribute(StateMachineTagNames.Y, Integer.toString(getY()));

		Element onEntryElem = xmlDocument.createElement(StateMachineTagNames.ONENTRY);
		for(StateAction action : onEntryActions) {
			Element actionElem = xmlDocument.createElement(StateMachineTagNames.STATEACTION);
			actionElem.setAttribute(StateMachineTagNames.ID, Integer.toString(onEntryActions.indexOf(action)));
			Text text = xmlDocument.createTextNode(action.toString());
			actionElem.appendChild(text);
			onEntryElem.appendChild(actionElem);
		}
		stateElem.appendChild(onEntryElem);

		
		Element onExitElem = xmlDocument.createElement(StateMachineTagNames.ONEXIT);
		for(StateAction action : onExitActions) {
			Element actionElem = xmlDocument.createElement(StateMachineTagNames.STATEACTION);
			actionElem.setAttribute(StateMachineTagNames.ID, Integer.toString(onExitActions.indexOf(action)));
			Text text = xmlDocument.createTextNode(action.toString());
			actionElem.appendChild(text);
			onExitElem.appendChild(actionElem);
		}
		stateElem.appendChild(onExitElem);
	
		Element onOperateElem = xmlDocument.createElement(StateMachineTagNames.ONOPERATE);
		for(StateAction action : onOperateActions) {
			Element actionElem = xmlDocument.createElement(StateMachineTagNames.STATEACTION);
			actionElem.setAttribute(StateMachineTagNames.ID, Integer.toString(onOperateActions.indexOf(action)));
			Text text = xmlDocument.createTextNode(action.toString());
			actionElem.appendChild(text);
			onOperateElem.appendChild(actionElem);
		}
		stateElem.appendChild(onOperateElem);
		
		/**
		Element transitionsElement = xmlDocument.createElement(StateMachineTagNames.TRANSITIONS);
		for (Transition transition : transitionMap.values()) {
			Element transitionElem = transition.toElement(xmlDocument);
			transitionsElement.appendChild(transitionElem);
		}
		stateElem.appendChild(transitionsElement);
		**/
		return stateElem;
	}
	


	/**
	 * 
	 * <div lang="ja">
	 *
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 *
	 * @throws Exception
	 * </div>
	 */
	protected void onOperate() throws Exception {
		logger.entering(getClass().getName(), "onOperate", this);

		for(StateAction action : onOperateActions) {
			action.actionPerformed(this);
		}
	}

	/**
	 * 
	 * <div lang="ja">
	 *
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 *
	 * @throws Exception
	 * </div>
	 */
	@Override
	public void onExit() throws Exception {
		logger.entering(getClass().getName(), "onExit", this);

		for(StateAction action : onExitActions) {
			action.actionPerformed(this);
		}
	}

	/**
	 * 
	 * <div lang="ja">
	 *
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 *
	 * @throws Exception
	 * </div>
	 */
	@Override
	public void onEntry() throws Exception {
		logger.entering(getClass().getName(), "onEntry", this);
		for(StateAction action : onEntryActions) {
			action.actionPerformed(this);
		}
	}


	@Override
	final public Transition getTransition(String name) {
		return transitionMap.get(name);
	}

	@Override
	public void removeTransition(String name) {
		transitionMap.remove(name);
	}

	@Override
	final public void addTransition(Transition transition) {
		transitionMap.put(transition.getName(), transition);
	}

	@Override
	public Map<String, Transition> transitionMap() {
		return transitionMap;
	}

	@Override
	public void connect(String name, State target, Guard guard) throws InvalidConnectionException {
		logger.entering(getClass().getName(), "connect", new Object[]{this, name, target, guard});
		addTransition(new Transition(name, this, target, guard));
	}
	
	
	public String toString() {
		return "State("+getName()+")";
	}
}
