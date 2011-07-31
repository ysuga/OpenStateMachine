package net.ysuga.statemachine.guard;

import net.ysuga.statemachine.State;
import net.ysuga.statemachine.StateMachineTagNames;


/**
 * @author Yuki Suga (ysuga.net)
 * @brief State Machine System Framework
 * @date 2011/05/13
 * @license see lisence.txt
 * @copyright ysuga.net 2011, all rights reserved.
 * 
 */
public class OrGuard extends AbstractLogicGuard {
 
	/*
	 * @return if at least one child guard is true, return true.
	 * (non-Javadoc)
	 * @see net.ysuga.statemachine.AbstractLogicGuard#operate(net.ysuga.statemachine.State)
	 */
	public boolean operate(State state) throws Exception {
		for(Guard g : getChildGuards()) {
			if(g.operate(state)) {
				return true;
			}
		}
		return false;
	}
	 
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param condition
	 */
	public OrGuard(String name, Guard[] condition) {
		super(name, StateMachineTagNames.OR, condition);
	}
	 
}
 
