/**
 * StartState.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/04
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.state;

import net.ysuga.statemachine.StateMachineTagNames;


/**
 * @author ysuga
 *
 */
public class StartState extends DefaultState {

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
	public StartState() {
		super(StateMachineTagNames.START);
		stateCondition = StateCondition.ACTIVE;
		initialStateCondition =StateCondition.ACTIVE;
	}

}
