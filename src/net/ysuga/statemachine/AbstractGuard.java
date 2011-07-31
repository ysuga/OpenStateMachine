package net.ysuga.statemachine;


/**
 * @author Yuki Suga (ysuga.net)
 * @brief State Machine System Framework
 * @date 2011/05/13
 * @license see lisence.txt
 * @copyright ysuga.net 2011, all rights reserved.
 * 
 */
public abstract class AbstractGuard implements Guard {
 
	private String name;
	 
	/**
	 * Constructor. 
	 * 
	 * name of guard is initialized in this method.
	 * @param name
	 */
	public AbstractGuard(String name) {
		this.name = name;
	}
	 
	
	/**
	 * Get Name of guard.
	 * 
	 * @return name of this guard.
	 */
	final public String getName() {
		return name;
	}
	 
	/**
	 * @see net.ysuga.statemachine.Guard#operate(net.ysuga.statemachine.State)
	 */
	abstract public boolean operate(State state);
	 
}
 
