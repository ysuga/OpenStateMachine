/**
 * NullGuard.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/04
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.guard;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.util.ParameterMap;

/**
 * @author ysuga
 *
 */
public class NullGuard extends AbstractGuard {

	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * @param name
	 * @param kind
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * @param name
	 * @param kind
	 * </div>
	 */
	public NullGuard(String name) {
		super(name, StateMachineTagNames.NULLGUARD);
	}

	/**
	 * <div lang="ja">
	 * @param state
	 * @return
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 * @param state
	 * @return
	 * @throws Exception
	 * </div>
	 */
	@Override
	public boolean operate(State state) throws Exception {
		return true;
	}

	/**
	 * <div lang="ja">
	 * @return
	 * </div>
	 * <div lang="en">
	 * @return
	 * </div>
	 */
	@Override
	public ParameterMap getParameterMap() {
		return new ParameterMap();
	}

	public void onInitialize(State state) {
	}
	
	public void onFinalize(State state) {	
	}
}
