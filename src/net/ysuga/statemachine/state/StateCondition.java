package net.ysuga.statemachine.state;


/**
 * 
 * <div lang="ja">
 *�@StateCondition�DState�̏�Ԃ�\���܂��DACTIVE��INACTIVE�Ƃ�����̃I�u�W�F�N�g���������܂���D
 * ���ׂĂ�State�͂����ւ̎Q�Ƃ����݂̂ł��D
 * </div>
 * <div lang="en">
 *�@StateCondition. Condition of State. ACTIVE | INACTIVE. 
 * This class has only two objects. Every State has the reference to these two.
 * </div>
 * @author ysuga
 *
 */
public class StateCondition {
 	 
	private static final int ACTIVE_VALUE = 0;
	private static final int INACTIVE_VALUE = 1;

	/**
	 * ACTIVE��Ԃ��w��StateCondition�̃I�u�W�F�N�g
	 */
	public static final StateCondition ACTIVE = new StateCondition(ACTIVE_VALUE);
	
	/**
	 * INACTIVE��Ԃ��w��StateCondition�̃I�u�W�F�N�g
	 */
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
	 * toString method.
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
	
	/**
	 * 
	 * <div lang="ja">
	 *
	 * @param str
	 * @return
	 * </div>
	 * <div lang="en">
	 *
	 * @param str
	 * @return
	 * </div>
	 */
	static public StateCondition parseString(String str) {
		if(str.equals("ACTIVE")) {
			return StateCondition.ACTIVE;
		} else {
			return StateCondition.INACTIVE;
		}
	}
}
 
