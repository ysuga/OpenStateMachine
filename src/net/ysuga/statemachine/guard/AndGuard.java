package net.ysuga.statemachine.guard;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.state.State;


/**
 * @author Yuki Suga (ysuga.net)
 * @brief State Machine System Framework
 * @date 2011/05/13
 * @license see lisence.txt
 * @copyright ysuga.net 2011, all rights reserved.
 * 
 */
public class AndGuard extends AbstractLogicGuard {
 
	/**
	 * operate guard.
	 * 
	 * 
	 * @return If all of child guards are true, return true. If at least one child guard is false, return false. 
	 * @throws Exception 
	 */
	public boolean operate(State state) throws Exception {
		for(Guard g : getChildGuards()) {
			if(!g.operate(state)) {
				return false;
			}
		}
		return true;
	}
	 
	/**
	 * Constructor 
	 * 
	 * @param name
	 * @param condition
	 */
	public AndGuard(String name, Guard[] condition) {
		super(name, StateMachineTagNames.AND, condition);
	}
	 
}
 
