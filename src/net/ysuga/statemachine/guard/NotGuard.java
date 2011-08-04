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
public class NotGuard extends AbstractLogicGuard {
 

	/*
	 * @return if the first child guard is true, return false.
	 * (non-Javadoc)
	 * @see net.ysuga.statemachine.AbstractLogicGuard#operate(net.ysuga.statemachine.State)
	 */
	public boolean operate(State state) throws Exception {
		return !getChildGuards()[0].operate(state);
	}
	
	/*
	 * Constructor.
	 * 
	 * @param name
	 * @param childGuard
	 */
	public NotGuard(String name, Guard childGuard) {
		super(name, StateMachineTagNames.NOT, new Guard[]{childGuard});
	}
	 
}
 
