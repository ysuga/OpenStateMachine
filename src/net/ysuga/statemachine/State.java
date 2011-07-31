package net.ysuga.statemachine;

import java.util.HashMap;
import java.util.Map;

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

	private StateCondition stateCondition;

	public void operate() {
		for (String key : transitionMap.keySet()) {
			Transition transition = transitionMap.get(key);
			if (transition.checkGuard()) {
				transition.transit();
				return;
			}
		}
		onOperate();
	}

	public StateCondition getState() {
		return stateCondition;
	}

	public void setState(StateCondition state) {
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

	protected void onExit() {

	}

	protected void onEntered() {

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
