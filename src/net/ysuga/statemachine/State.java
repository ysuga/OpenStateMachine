package net.ysuga.statemachine;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class State implements ModelElement {

	private String name;

	private Map<String, Transition> transitionMap;

	private ArrayList<StateAction> onEntryActions;
	
	private ArrayList<StateAction> onExitActions;
	
	private StateCondition stateCondition;
	
	
	private int x;
	private int y;

	/**
	 * @return the x
	 */
	public final int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public final void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public final int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public final void setY(int y) {
		this.y = y;
	}

	public void operate() throws Exception {
		for (String key : transitionMap.keySet()) {
			Transition transition = transitionMap.get(key);
			if (transition.checkGuard()) {
				transition.transit();
				return;
			}
		}
		onOperate();
	}

	public Element toElement(Document xmlDocument) {
		Element stateElem = xmlDocument.createElement(StateMachineTagNames.STATE);
		stateElem.setAttribute(StateMachineTagNames.NAME, getName());
		stateElem.setAttribute(StateMachineTagNames.INITIAL_STATE, getState().toString());
		stateElem.setAttribute("x", Integer.toString(getX()));
		stateElem.setAttribute("y", Integer.toString(getY()));
		/**
		Map<String, String> map = state.getProfileMap();
		if (map != null) {
			Set<String> keySet = map.keySet();
			for (String key : keySet) {
				String value = map.get(key);
				Element keyElem = xmlDocument.createElement(key);
				Text textElem = xmlDocument.createTextNode(value);
				keyElem.appendChild(textElem);
				stateElem.appendChild(keyElem);
			}
		}
		**/

		Element onEntryElem = xmlDocument.createElement(StateMachineTagNames.ONENTRY);
		for(StateAction action : onEntryActions) {
			Element actionElem = xmlDocument.createElement(StateMachineTagNames.STATEACTION);
			actionElem.setAttribute(StateMachineTagNames.ID, Integer.toString(onEntryActions.indexOf(action)));
			Text text = xmlDocument.createTextNode(action.toString());
			actionElem.appendChild(text);
			onEntryElem.appendChild(actionElem);
		}
		stateElem.appendChild(onEntryElem);

		
		Element onExittingElem = xmlDocument.createElement(StateMachineTagNames.ONEXIT);
		for(StateAction action : onExitActions) {
			Element actionElem = xmlDocument.createElement(StateMachineTagNames.STATEACTION);
			actionElem.setAttribute(StateMachineTagNames.ID, Integer.toString(onExitActions.indexOf(action)));
			Text text = xmlDocument.createTextNode(action.toString());
			actionElem.appendChild(text);
			onExittingElem.appendChild(actionElem);
		}
		stateElem.appendChild(onExittingElem);
	
		for (Transition transition : transitionMap.values()) {
			Element transitionElem = transition.toElement(xmlDocument);
			stateElem.appendChild(transitionElem);
		}

		return stateElem;
	}
	
	public StateCondition getState() {
		return stateCondition;
	}

	public void setState(StateCondition state) throws Exception {
		if (this.getState().equals(StateCondition.INACTIVE)
				&& state.equals(StateCondition.ACTIVE)) {
			onEntered();
		} else if (this.getState().equals(StateCondition.ACTIVE)
				&& state.equals(StateCondition.INACTIVE)) {
			onExit();
		}
	}

	protected void onOperate() {

	}

	protected void onExit() throws Exception {

	}

	protected void onEntered() throws Exception {

	}

	final public String getName() {
		return name;
	}

	final public void setName(String name) {
		this.name = name;
	}

	public State(String name) {
		this.setName(name);
		transitionMap = new HashMap<String, Transition>();
	}

	public Transition getTransition(String name) {
		return transitionMap.get(name);
	}

	public void removeTransition(String name) {
		transitionMap.remove(name);
	}

	public void addTransition(Transition transition) {
		transitionMap.put(transition.getName(), transition);
	}

	public Map<String, Transition> transitionMap() {
		return transitionMap;
	}

}
