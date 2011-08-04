/**
 * AndGuardFactory.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/07/31
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.guard;

import net.ysuga.statemachine.StateMachineTagNames;

import org.w3c.dom.Node;

/**
 * @author ysuga
 *
 */
public class ExorGuardFactory extends AbstractLogicGuardFactory {

	
	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * @param kind
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * @param kind
	 * </div>
	 */
	public ExorGuardFactory() {
		super(StateMachineTagNames.EXOR);
	}

	/**
	 * <div lang="ja">
	 * @param name
	 * @param guards
	 * @return
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 * @param name
	 * @param guards
	 * @return
	 * @throws Exception
	 * </div>
	 */
	@Override
	public Guard createGuard(String name, Guard[] guards) throws Exception {
		return new ExorGuard(name, guards);
	}



}
