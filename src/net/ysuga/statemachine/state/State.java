/**
 * State.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/04
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.state;

import java.util.Map;

import net.ysuga.statemachine.Transition;
import net.ysuga.statemachine.exception.InvalidConnectionException;
import net.ysuga.statemachine.guard.Guard;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author ysuga
 *
 */
public interface State {

	public abstract void connect(String name, State target, Guard guard)
			throws InvalidConnectionException;

	public abstract void addTransition(Transition transition);

	public abstract void removeTransition(String name);

	public abstract Transition getTransition(String name);

	public abstract Element toElement(Document xmlDocument);

	public abstract void operate() throws Exception;

	public abstract int getY();

	public abstract int getX();

	public abstract void setStateCondition(StateCondition state) throws Exception;

	public abstract StateCondition getStateCondition();

	public abstract String getName();

	public abstract Map<String, Transition> transitionMap();

	public abstract void onEntry() throws Exception;

	public abstract void onExit() throws Exception;

}
