package net.ysuga.statemachine;

import java.util.Map;


/**
 * @author Yuki Suga (ysuga.net)
 * @brief State Machine System Framework
 * @date 2011/05/13
 * @license see lisence.txt
 * @copyright ysuga.net 2011, all rights reserved.
 * 
 */
public class StateMachine implements ModelElement {
 
	private String name;
	 
	private Map<String, State> stateMap;
	
	public void operate() {
		for(String name : stateMap.keySet()) {
			State state = stateMap.get(name);
			if(state.getState().equals(StateCondition.ACTIVE)) {
				state.operate();
			}
		}
	}
	 
	/*
	 * コンストラクタ
	 */
	public StateMachine(String name) {
		this.setName(name);
	}
	 
	public String getName() {
		return name;
	}
	 
	public void setName(String name) {
		this.name = name;
	}
	 
	final public State getState(String name) {
		return stateMap.get(name);
	}
	 
	final public void add(State state) {
		stateMap.put(state.getName(), state);
	}
	 
	final public void remove(State state) {
		stateMap.remove(state.getName());
	}
	 
	final public Map getStateMap() {
		return stateMap;
	}
	 
}
 
