/**
 * DelayGuardFactory.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/04
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.guard;

import java.util.Map;

import net.ysuga.statemachine.StateMachineTagNames;


/**
 * @author ysuga
 *
 */
public class NullGuardFactory extends AbstractGuardFactory {

	/**
	 * <div lang="ja">
	 * �R���X�g���N�^
	 * @param kind
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * @param kind
	 * </div>
	 */
	public NullGuardFactory() {
		super(StateMachineTagNames.NULLGUARD);
	}

	/**
	 * <div lang="ja">
	 * @param name
	 * @param parameterMap
	 * @return
	 * </div>
	 * <div lang="en">
	 * @param name
	 * @param parameterMap
	 * @return
	 * </div>
	 */
	@Override
	public Guard createGuard(String name, GuardParameterMap parameterMap) {
		return new NullGuard(name);
	}


}