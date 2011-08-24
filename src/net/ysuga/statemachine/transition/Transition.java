package net.ysuga.statemachine.transition;

import java.awt.Point;
import java.util.logging.Logger;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.guard.Guard;
import net.ysuga.statemachine.state.ModelElement;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.state.StateCondition;

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

	static Logger logger;
	static {
		logger = Logger.getLogger(Transition.class.getName());
	}
	private String name;

	private State to;
	
	final public State getTargetState() {
		return to;
	}

	private State from;
	
	final public State getSourceState() {
		return from;
	}
	
	private Guard guard;

	private PivotList pivotList;
	
	final public PivotList getPivotList() {
		return pivotList;
	}
	
	public boolean transit() throws Exception {
		logger.entering(getClass().getName(), "transit", this);
		from.setStateCondition(StateCondition.INACTIVE);
		to.setStateCondition(StateCondition.ACTIVE);
		return true;
	}

	final public boolean checkGuard() throws Exception  {
		logger.entering(getClass().getName(), "checkGuard", this);
		boolean ret = guard.operate(from);
		logger.exiting(getClass().getName(), "checkGuard", ret);
		return ret;
	}

	final public String getName() {
		return name;
	}

	/**
	 * 
	 * <div lang="ja">
	 * コンストラクタ
	 * @param name
	 * @param from
	 * @param to
	 * @param guard
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * @param name
	 * @param from
	 * @param to
	 * @param guard
	 * </div>
	 */
	public Transition(String name, State from, State to, Guard guard) {
		this.name = name;
		this.from = from;
		this.to = to;
		this.guard = guard;
		this.pivotList = new PivotList();
	}

	public String toString() {
		return "Transition(" + getName() + ")";
	}
	
	public Element toElement(Document xmlDocument) {
		Element transitionElem = xmlDocument.createElement(StateMachineTagNames.TRANSITION);
		transitionElem.setAttribute(StateMachineTagNames.NAME, getName());
		transitionElem.setAttribute(StateMachineTagNames.FROM, from.getName());
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
	public void vanish() {
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

	/**
	 * <div lang="ja">
	 *
	 * @param state
	 * </div>
	 * <div lang="en">
	 *
	 * @param state
	 * </div>
	 */
	public void setSourceState(State state) {
		from = state;
	}

	/**
	 * <div lang="ja">
	 *
	 * @param state
	 * </div>
	 * <div lang="en">
	 *
	 * @param state
	 * </div>
	 */
	public void setTargetState(State state) {
		to = state;
	}

	/**
	 * onFinalize
	 * <div lang="ja">
	 * 
	 * @param state
	 * </div>
	 * <div lang="en">
	 *
	 * @param state
	 * </div>
	 */
	public void onFinalize(State state) {
		this.guard.onFinalize(state);
	}

	public void onInitialize(State state) {
		this.guard.onInitialize(state);
	}

}
