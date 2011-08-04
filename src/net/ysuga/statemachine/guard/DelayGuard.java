package net.ysuga.statemachine.guard;

import java.util.HashMap;
import java.util.Map;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.state.State;

/***
 * DelayGuardクラス．規定時間だけ待つガード
 * 
 * @author ysuga
 *
 */
public class DelayGuard extends AbstractGuard {
 
	private int milliSeconds;
	 
	/**
	 * 規定時間だけ待ちます
	 */
	public boolean operate(State state) {
		try {
			Thread.sleep(milliSeconds);
		} catch( Exception ex ) {
			
		}
		return true;
	}
	
	/**
	 * コンストラクタ
	 * @param name ガードの識別子
	 * @param milliSeconds 待ち時間（単位ms）
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
 
