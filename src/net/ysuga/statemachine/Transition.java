package net.ysuga.statemachine;

/**
 * @author Yuki Suga (ysuga.net)
 * @brief State Machine System Framework
 * @date 2011/05/13
 * @license see lisence.txt
 * @copyright ysuga.net 2011, all rights reserved.
 * 
 */
public class Transition implements ModelElement {

	private String name;

	private State to;

	private State from;

	private Guard guard;

	public boolean transit() {
		from.setState(StateCondition.INACTIVE);
		to.setState(StateCondition.ACTIVE);
		return true;
	}

	final public boolean checkGuard() {
		return guard.operate(from);
	}

	final public String getName() {
		return name;
	}

	public Transition(String name, State from, State to, Guard guard) {
		this.name = name;
		this.from = from;
		this.to = to;
		this.guard = guard;
	}

	/**
	 */
	public void remove() {
		from = null;
		to = null;
		guard = null;
	}

	final public void setName(String name) {
		this.name = name;
	}

	final public Guard getGuard() {
		return guard;
	}

}
