package net.ysuga.statemachine.guard;

import java.util.Map;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.util.ParameterMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;


/**
 * @author Yuki Suga (ysuga.net)
 * @brief State Machine System Framework
 * @date 2011/05/13
 * @license see lisence.txt
 * @copyright ysuga.net 2011, all rights reserved.
 * 
 */
public abstract class AbstractGuard implements Guard {
 
	private String name;
	 
	private String kind;
	/**
	 * Constructor. 
	 * 
	 * name of guard is initialized in this method.
	 * @param name
	 */
	public AbstractGuard(String name, String kind) {
		this.name = name;
		this.kind = kind;
	}
	 
	
	/**
	 * Get Name of guard.
	 * 
	 * @return name of this guard.
	 */
	final public String getName() {
		return name;
	}
	 
	/**
	 * Get Name of guard.
	 * 
	 * @return name of this guard.
	 */
	final public String getKind() {
		return kind;
	}
	
	/**
	 * @throws Exception 
	 * @see net.ysuga.statemachine.guard.Guard#operate(net.ysuga.statemachine.state.DefaultState)
	 */
	abstract public boolean operate(State state) throws Exception;
	

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
		element.setAttribute(StateMachineTagNames.NAME, getName());
		element.setAttribute(StateMachineTagNames.KIND, getKind());
		Element paramElement = getParameterMap().toElement(xmlDocument);
		element.appendChild(paramElement);
		
		return element;
	}
	 
	
	abstract public ParameterMap getParameterMap();
	
	public String toString() {
		return "Guard:"+getKind()+"("+getName() + ")";
	}
}
 
