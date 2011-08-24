package net.ysuga.statemachine.guard;

import java.util.HashMap;
import java.util.Map;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.util.ParameterMap;

/***
 * DelayGuard�N���X�D�K�莞�Ԃ����҂K�[�h
 * 
 * @author ysuga
 *
 */
public class DelayGuard extends AbstractGuard {
 
	private long milliSeconds;
	 
	private boolean initFlag;
	
	private long startTime;
	
	/**
	 * �K�莞�Ԃ����҂��܂�
	 */
	public boolean operate(State state) {
		try {
			long currentTime = System.currentTimeMillis();
			if( (currentTime - startTime) > milliSeconds ) {
				return true;
			} else {
				return false;
			}
		} catch( Exception ex ) {
			
		}
		return true;
	}
	
	/**
	 * �R���X�g���N�^
	 * @param name �K�[�h�̎��ʎq
	 * @param milliSeconds �҂����ԁi�P��ms�j
	 */
	public DelayGuard(String name, long milliSeconds) {
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
	public ParameterMap getParameterMap() {
		ParameterMap map = new ParameterMap();
		map.put(INTERVAL, Long.toString(milliSeconds));
		return map;
	}
	 
	
	public void onInitialize(State state) {
		this.startTime = System.currentTimeMillis();
	}
	
	public void onFinalize(State state) {
		
	}

	/**
	 * getInterval
	 * <div lang="ja">
	 * 
	 * @return
	 * </div>
	 * <div lang="en">
	 *
	 * @return
	 * </div>
	 */
	public long getInterval() {
		return milliSeconds;
	}
}
 
