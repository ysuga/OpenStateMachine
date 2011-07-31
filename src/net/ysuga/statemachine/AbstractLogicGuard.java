package net.ysuga.statemachine;


/**
 * @author Yuki Suga (ysuga.net)
 * @brief State Machine System Framework
 * @date 2011/05/13
 * @license see lisence.txt
 * @copyright ysuga.net 2011, all rights reserved.
 * 
 */
public abstract class AbstractLogicGuard implements LogicGuard {
 
	private String name;
	 
	private Guard[] guards;
	 
	/**
	 * @return Name of this element
	 */
	public String getName() {
		return name;
	}
	 
	/**
	 * @brief constructor
	 * @param name Name of this element
	 */
	public AbstractLogicGuard(String name, Guard[] childGuards) {
		this.name = name;
		this.guards = childGuards;
	}
	 
	/**
	 * 
	 * @return child guard
	 */
	public Guard[] getChildGuards() {
		return guards;
	}
	 
	 
	/**
	 * @see net.ysuga.statemachine.Guard#operate(net.ysuga.statemachine.State)
	 */
	abstract public boolean operate(State state);
}
 
