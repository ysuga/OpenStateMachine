package net.ysuga.statemachine.guard;

import java.util.HashMap;
import java.util.Map;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.state.State;

/***
 * DelayGuard�N���X�D�K�莞�Ԃ����҂K�[�h
 * 
 * @author ysuga
 *
 */
public class DelayGuard extends AbstractGuard {
 
	private int milliSeconds;
	 
	/**
	 * �K�莞�Ԃ����҂��܂�
	 */
	public boolean operate(State state) {
		try {
			Thread.sleep(milliSeconds);
		} catch( Exception ex ) {
			
		}
		return true;
	}
	
	/**
	 * �R���X�g���N�^
	 * @param name �K�[�h�̎��ʎq
	 * @param milliSeconds �҂����ԁi�P��ms�j
	 */
	public DelayGuard(String name, int milliSeconds) {
		super(name, StateMachineTagNames.DELAY);
		this.milliSeconds = milliSeconds;
	}

	final static public String INTERVAL = "interval"; 
	/**
	 * <div lang="ja">
	 * @return
	 * </div>
	 * <div lang="en">
	 * @return
	 * </div>
	 */
	@Override
	public GuardParameterMap getParameterMap() {
		GuardParameterMap map = new GuardParameterMap();
		map.put(INTERVAL, Integer.toString(milliSeconds));
		return map;
	}
	 
}
 
