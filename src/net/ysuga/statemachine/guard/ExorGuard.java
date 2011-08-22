package net.ysuga.statemachine.guard;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.exception.InvalidGuardException;
import net.ysuga.statemachine.state.State;


/**
 * @author Yuki Suga (ysuga.net)
 * @brief State Machine System Framework
 * @date 2011/05/13
 * @license see lisence.txt
 * @copyright ysuga.net 2011, all rights reserved.
 * 
 */
public class ExorGuard extends AbstractLogicGuard {
 
	/**
	 * ExOr operation
	 * 
	 * @return If all child guards are true, return false. Or if all child guards are false, return false. Else, return true.
	 * @throws Exception 
	 */
	public boolean operate(State state) throws Exception {
		int falseCount = 0;
		for(Guard g : getChildGuards()) {
			if(!g.operate(state)) {
				falseCount++;
			}
		}
		if(falseCount == 0 || falseCount == getChildGuards().length) {
			return false;
		}
		return true;
	}
	 
	
	/**
	 * Constructor 
	 * 
	 * @param name
	 * @param condition
	 * @throws InvalidGuardException 
	 */
	public ExorGuard(String name, Guard[] condition) throws InvalidGuardException {
		super(name, StateMachineTagNames.EXOR, condition);
	}
	 
}
 
