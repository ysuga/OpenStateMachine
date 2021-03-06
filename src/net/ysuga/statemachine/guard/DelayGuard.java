package net.ysuga.statemachine.guard;

import java.util.HashMap;
import java.util.Map;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.util.ParameterMap;

/***
 * DelayGuardクラス．規定時間だけ待つガード
 * 
 * @author ysuga
 *
 */
public class DelayGuard extends AbstractGuard {
 
	private long milliSeconds;
	 
	private boolean initFlag;
	
	private long startTime;
	
	/**
	 * 規定時間だけ待ちます
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
	 * コンストラクタ
	 * @param name ガードの識別子
	 * @param milliSeconds 待ち時間（単位ms）
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
 
