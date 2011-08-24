/**
 * LoggingStateActionFactory.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/23
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.state.action;

import net.ysuga.statemachine.StateMachineTagNames;

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
public class MessageBoxStateActionFactory extends AbstractStateActionFactory {

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
	public MessageBoxStateActionFactory() {
		super(StateMachineTagNames.MESSAGEBOXACTION);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
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
	public StateAction createStateAction() {
		return new MessageBoxStateAction();
	}

}
