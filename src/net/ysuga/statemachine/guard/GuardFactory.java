/**
 * GuardFactory.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/07/31
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.guard;

import net.ysuga.statemachine.exception.InvalidFSMFileException;

/**
 * @author ysuga
 *
 */
public interface GuardFactory {
	
	/**
	 * 
	 * <div lang="ja">
	 *
	 * @param element
	 * @return
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 *
	 * @param element
	 * @return
	 * @throws Exception
	 * </div>
	 */
	public Guard loadGuard(org.w3c.dom.Node node) throws InvalidFSMFileException;
	
	public String getKind();
	
	public GuardProfile getGuardProfile();
}
