/**
 * StateAction.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/07/31
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine;

/**
 * @author ysuga
 *
 */
public interface StateAction {
	
	/**
	 * 
	 * <div lang="ja">
	 *
	 * @param state
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 *
	 * @param state
	 * @throws Exception
	 * </div>
	 */
	public void actionPerformed(State state) throws Exception;
	
	public String toString();

}
