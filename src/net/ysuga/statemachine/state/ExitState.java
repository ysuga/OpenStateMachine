/**
 * ExitState.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/04
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.state;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.exception.InvalidConnectionException;
import net.ysuga.statemachine.guard.Guard;

/**
 * @author ysuga
 *
 */
public class ExitState extends DefaultState {

	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * @param name
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * @param name
	 * </div>
	 */
	public ExitState() {
		super(StateMachineTagNames.EXIT);
	}
	
	/**
	 * 
	 * <div lang="ja">
	 * @param name
	 * @param target
	 * @param guard
	 * </div>
	 * <div lang="en">
	 * @param name
	 * @param target
	 * @param guard
	 * </div>
	 */
	@Override
	public void connect(String name, State target, Guard guard) throws InvalidConnectionException {
		throw new InvalidConnectionException();
	}

}
