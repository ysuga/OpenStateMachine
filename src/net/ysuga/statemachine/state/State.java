/**
 * State.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/04
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.state;

import java.awt.Point;
import java.util.Map;

import net.ysuga.statemachine.exception.InvalidConnectionException;
import net.ysuga.statemachine.guard.Guard;
import net.ysuga.statemachine.transition.Transition;
import net.ysuga.statemachine.transition.TransitionMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author ysuga
 *
 */
public interface State extends ModelElement {

	public abstract void connect(String name, State target, Guard guard)
			throws InvalidConnectionException;

	public abstract void addTransition(Transition transition);

	public abstract void removeTransition(String name);

	public abstract Transition getTransition(String name);

	public abstract Element toElement(Document xmlDocument);

	public abstract void operate() throws Exception;

	public abstract int getY();
	
	public abstract int getX();
	
	public abstract void setLocation(int x, int y);
	
	public abstract void setLocation(Point p);

	public abstract void setStateCondition(StateCondition state) throws Exception;

	public abstract void setStateConditionImmediately(StateCondition state) throws Exception;
	
	public abstract void updateStateCondition() throws Exception;
	
	public abstract StateCondition getStateCondition();

	public abstract StateCondition getInitialStateCondition();
	
	public abstract String getName();

	public abstract Map<String, Transition> transitionMap();

	public abstract void onEntry() throws Exception;

	public abstract void onExit() throws Exception;
	
	public abstract void vanish();
	
	public abstract String getKind();

	/**
	 * <div lang="ja">
	 *
	 * @return
	 * </div>
	 * <div lang="en">
	 *
	 * @return
	 * </div>
	 */
	public abstract Point getLocation();
	
	public abstract TransitionMap getTransitionMap();

}
