package net.ysuga.statemachine.guard;

import net.ysuga.statemachine.ModelElement;
import net.ysuga.statemachine.State;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Guard condition.
 * 
 * operate method is called by Transition class. If operate is true, transition is activated and active state is changed.
 * 
 * @author Yuki Suga (ysuga.net)
 * @brief State Machine System Framework
 * @date 2011/05/13
 * @license see lisence.txt
 * @copyright ysuga.net 2011, all rights reserved.
 * 
 */
public interface Guard extends ModelElement {
 
	/**
	 * @throws Exception 
	 * @brief Check the guard condition is true or false.
	 */
	public abstract boolean operate(State state) throws Exception;
	/**
	 * @brief get the name of this element
	 * @return naem of element.
	 */
	public abstract String getName();
	
	public abstract String getKind();
	
	/**
	 * <div lang="ja">
	 *
	 * @param xmlDocument
	 * @return
	 * </div>
	 * <div lang="en">
	 *
	 * @param xmlDocument
	 * @return
	 * </div>
	 */
	public abstract Element toElement(Document xmlDocument);
}
 
