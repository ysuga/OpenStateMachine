/**
 * LoggingStateAction.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/23
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.state.action;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.util.ParameterMap;

/**
 * <div lang="ja">
 *
 * </div>
 * <div lang="en">
 *
 * </div>
 * @author ysuga
 *
 */
public class LoggingStateAction extends AbstractStateAction {

	static private String MESSAGE = "Message"; 
	private String msg = "LoggingStateAction";
	
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
	public LoggingStateAction() {
		super(StateMachineTagNames.LOGGINGACTION);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * <div lang="ja">
	 * @param state
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 * @param state
	 * @throws Exception
	 * </div>
	 */
	@Override
	public void actionPerformed(State state) throws Exception {
		System.out.println("State(name=" + state.getName() + " kind=" + state.getKind() + ") -" + msg);
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
		ParameterMap pMap = new ParameterMap();
		pMap.put(MESSAGE, msg);
		return pMap;
	}

	/**
	 * <div lang="ja">
	 * @param parameterMap
	 * </div>
	 * <div lang="en">
	 * @param parameterMap
	 * </div>
	 */
	@Override
	public void setParameterMap(ParameterMap parameterMap) {
		this.msg = parameterMap.get(MESSAGE);
	}

}
