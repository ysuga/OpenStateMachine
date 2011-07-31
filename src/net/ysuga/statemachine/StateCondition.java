package net.ysuga.statemachine;


/**
 * @author Yuki Suga (ysuga.net)
 * @brief State Machine System Framework
 * @date 2011/05/13
 * @license see lisence.txt
 * @copyright ysuga.net 2011, all rights reserved.
 * 
 */
public class StateCondition {
 	 
	public static final int ACTIVE_VALUE = 0;
	public static final int INACTIVE_VALUE = 1;

	public static final StateCondition ACTIVE = new StateCondition(ACTIVE_VALUE);
	public static final StateCondition INACTIVE = new StateCondition(INACTIVE_VALUE);
	
	private int state;
	
	/**
	 * 
	 * @param value
	 */
	private StateCondition(int value) {
		this.state = value;
	}
	 

	/**
	 * 
	 * @param stateCondition
	 */
	public boolean equals(Object stateCondition) {
		if(((StateCondition)stateCondition).state == this.state) {
			return true;
		}
		return false;
	}
	
	@Override
	/**
	 * 
	 * <div lang="ja">
	 * @return
	 * </div>
	 * <div lang="en">
	 * @return
	 * </div>
	 */
	public String toString() {
		if(state == ACTIVE_VALUE) {
			return "ACTIVE";
		} else {
			return "INACTIVE";
		}
	}
}
 
