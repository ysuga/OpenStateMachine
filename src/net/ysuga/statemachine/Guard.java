package net.ysuga.statemachine;


/**
 * Guard condition.
 * 
 * operate method is called by Transition class. If operate is true, transition is activated and active state is changed.
 * 
 * @author Yuki Suga (ysuga.net)
 * @brief State Machine System Framework
 * @date 2011/05/13
 * @license see lisence.txt
 * @copyright ysuga.net 2011, all rights reserved.
 * 
 */
public interface Guard extends ModelElement {
 
	/**
	 * @brief Check the guard condition is true or false.
	 */
	public abstract boolean operate(State state);
	/**
	 * @brief get the name of this element
	 * @return naem of element.
	 */
	public abstract String getName();
}
 
