/**
 * StateActionFactory.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/06
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.state.action;

import net.ysuga.statemachine.exception.InvalidFSMFileException;

import org.w3c.dom.Node;

/**
 * 
 * <div lang="ja">
 *
 * </div>
 * <div lang="en">
 *
 * </div>
 * @author ysuga
 *
 */
public interface StateActionFactory {

	public String getKind();
	
	public StateAction createStateAction();
	
	public StateAction createStateAction(Node node) throws InvalidFSMFileException;
}
