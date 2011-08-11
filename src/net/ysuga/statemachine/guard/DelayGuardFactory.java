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
import net.ysuga.statemachine.util.ParameterMap;


/**
 * @author ysuga
 *
 */
public class DelayGuardFactory extends AbstractGuardFactory {
	
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
	public DelayGuardFactory() {
		super(StateMachineTagNames.DELAY);
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
	public Guard createGuard(String name, ParameterMap parameterMap) {
		String interval = parameterMap.get(DelayGuard.INTERVAL);
		return new DelayGuard(name, Integer.parseInt(interval));
	}


}
