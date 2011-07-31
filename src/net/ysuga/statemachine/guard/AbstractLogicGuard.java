package net.ysuga.statemachine.guard;

import net.ysuga.statemachine.State;
import net.ysuga.statemachine.StateMachineTagNames;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * @author Yuki Suga (ysuga.net)
 * @brief State Machine System Framework
 * @date 2011/05/13
 * @license see lisence.txt
 * @copyright ysuga.net 2011, all rights reserved.
 * 
 */
public abstract class AbstractLogicGuard implements LogicGuard {
 
	private String name;
	
	private String kind;
	 
	private Guard[] guards;
	 
	/**
	 * @return Name of this element
	 */
	public String getName() {
		return name;
	}
	
	public String getKind() {
		return kind;
	}
	 
	/**
	 * @brief constructor
	 * @param name Name of this element
	 */
	public AbstractLogicGuard(String name, String kind, Guard[] childGuards) {
		this.name = name;
		this.kind = kind;
		this.guards = childGuards;
	}
	 
	/**
	 * 
	 * @return child guard
	 */
	public Guard[] getChildGuards() {
		return guards;
	}
	 
	 
	/**
	 * @see net.ysuga.statemachine.guard.Guard#operate(net.ysuga.statemachine.State)
	 */
	abstract public boolean operate(State state) throws Exception ;
	
	@Override
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
	public Element toElement(Document xmlDocument) {
		Element element = xmlDocument.createElement(StateMachineTagNames.GUARD);
		element.setAttribute(StateMachineTagNames.KIND, getKind());
		for(Guard guard : getChildGuards()) {
			element.appendChild(guard.toElement(xmlDocument));
		}
		return element;
	}
	 
	
}
 
