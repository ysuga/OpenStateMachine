/**
 * StateAction.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/07/31
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.state.action;

import net.ysuga.statemachine.exception.InvalidFSMFileException;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.util.ParameterMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


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
	
	public String getKind();
	
	public Element toElement(Document xmlDocument);
	
	public ParameterMap getParameterMap();
	
	/**
	 * 
	 * setParameterMap
	 * <div lang="ja">
	 * 
	 * @param parameterMap
	 * </div>
	 * <div lang="en">
	 *
	 * @param parameterMap
	 * </div>
	 */
	abstract public void setParameterMap(ParameterMap parameterMap);

	public void load(Node node) throws InvalidFSMFileException ;
}
