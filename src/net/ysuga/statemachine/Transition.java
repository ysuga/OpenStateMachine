package net.ysuga.statemachine;

import java.awt.Point;
import java.util.ArrayList;

import net.ysuga.statemachine.guard.Guard;

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
public class Transition implements ModelElement {

	private String name;

	private State to;

	private State from;
	
	private Guard guard;


	private ArrayList<Point> pivotList;
	
	public boolean transit() throws Exception {
		from.setState(StateCondition.INACTIVE);
		to.setState(StateCondition.ACTIVE);
		return true;
	}

	final public boolean checkGuard() throws Exception  {
		return guard.operate(from);
	}

	final public String getName() {
		return name;
	}

	public Transition(String name, State from, State to, Guard guard) {
		this.name = name;
		this.from = from;
		this.to = to;
		this.guard = guard;
	}

	
	public Element toElement(Document xmlDocument) {
		Element transitionElem = xmlDocument.createElement(StateMachineTagNames.TRANSITION);
		transitionElem.setAttribute(StateMachineTagNames.NAME, getName());
		transitionElem.setAttribute(StateMachineTagNames.TO,  to.getName());

		Element guardElem = guard.toElement(xmlDocument);
		transitionElem.appendChild(guardElem);
		
		Element pivotListElem = xmlDocument.createElement(StateMachineTagNames.PIVOTLIST);
		pivotListElem.setAttribute(StateMachineTagNames.SIZE, Integer.toString(pivotList.size()));
		for(Point p : pivotList) {
			Element pivotElem = xmlDocument.createElement(StateMachineTagNames.PIVOT);
			pivotElem.setAttribute(StateMachineTagNames.INDEX, Integer.toString(pivotList.indexOf(p)));
			pivotElem.setAttribute("x", Integer.toString(p.x));
			pivotElem.setAttribute("y", Integer.toString(p.y));
			pivotListElem.appendChild(pivotElem);
		}
		transitionElem.appendChild(pivotListElem);
		return transitionElem;
	}
	/**
	 */
	public void remove() {
		from = null;
		to = null;
		guard = null;
	}

	final public void setName(String name) {
		this.name = name;
	}

	final public Guard getGuard() {
		return guard;
	}

}
