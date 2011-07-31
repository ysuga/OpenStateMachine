/**
 * StateFactory.java
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
public interface StateFactory {

	/**
	 * 
	 * <div lang="ja">
	 *
	 * @param element
	 * @return
	 * @throws Element
	 * </div>
	 * <div lang="en">
	 *
	 * @param element
	 * @return
	 * @throws Element
	 * </div>
	 */
	public State loadState(org.w3c.dom.Element element) throws Exception;
}
