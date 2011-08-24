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
import java.util.Iterator;

import net.ysuga.statemachine.exception.InvalidConnectionException;
import net.ysuga.statemachine.exception.InvalidFSMFileException;
import net.ysuga.statemachine.guard.Guard;
import net.ysuga.statemachine.state.action.StateActionList;
import net.ysuga.statemachine.transition.Transition;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 
 * <div lang="ja">
 *　Stateを表す最上位のインターフェース．基本これにアクセス
 * </div>
 * <div lang="en">
 *　State Interface.
 * </div>
 * @author ysuga
 *
 */
public interface State extends ModelElement {


	/**
	 * 
	 * getKind
	 * <div lang="ja">
	 * Stateの種類を文字列で取得する．
	 * @return
	 * </div>
	 * <div lang="en">
	 * Get Kind of State. 
	 * StateMachine Library can accept hetero geneous state objects.
	 * @return
	 * </div>
	 */
	public abstract String getKind();
	
	/**
	 * 
	 * getName
	 * <div lang="ja">
	 * Stateの名前の取得
	 * @return
	 * </div>
	 * <div lang="en">
	 *
	 * @return
	 * </div>
	 */
	public abstract String getName();
	

	/**
	 * 
	 * operate
	 * <div lang="ja">
	 * Stateに所属するTransitionのGuardを評価します．Guardの評価順序は実装に依存します．
	 * 
	 * @return もしGuardがtrueでTransitionが発生した場合はtrueを返す
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 *
	 * @return true if any guard is true
	 * @throws Exception
	 * </div>
	 */
	public abstract boolean operate() throws Exception;
	
	/**
	 * 
	 * onEntry
	 * <div lang="ja">
	 * StateがACTIVEになるとStateMachineによって呼ばれます．ACTIVEになった後で呼ばれます．
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 *
	 * @throws Exception
	 * </div>
	 */
	public abstract void onEntry() throws Exception;

	/**
	 * 
	 * onExit
	 * <div lang="ja">
	 * StateがINACTIVEになるとStateMachineによって呼ばれます．
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 *
	 * @throws Exception
	 * </div>
	 */
	public abstract void onExit() throws Exception;
	
	/**
	 * 
	 * onOperate
	 * <div lang="ja">
	 * operateが実行されて，INACTIVE化されなかった場合にStateMachineによって呼ばれます．
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 *
	 * @throws Exception
	 * </div>
	 */
	public abstract void onOperate() throws Exception;
	
	/**
	 * 
	 * getStateCondition
	 * <div lang="ja">
	 * 現在のStateConditionを取得します．
	 * @return
	 * </div>
	 * <div lang="en">
	 * Get current StateCondition
	 * @return
	 * </div>
	 */
	public abstract StateCondition getStateCondition();
	
	/**
	 * 
	 * setStateCondition
	 * <div lang="ja">
	 * StateConditionを設定します．ただし，updateStateConditionが呼ばれるまで更新されません．
	 * @param state
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 *　Set New StateCondition, but StateCondition is not updated until updateStateCondition is called.
	 * @param state
	 * @throws Exception
	 * </div>
	 */
	public abstract void setStateCondition(StateCondition state) throws Exception;
	
	/**
	 * 
	 * updateStateCondition
	 * <div lang="ja">
	 * StateConditionを更新します．
	 * @return　ACTIVE化した場合にはtrueを返します．
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 *　Update StateCondition.
	 * @return true if state is activated.
	 * @throws Exception
	 * </div>
	 */
	public abstract boolean updateStateCondition() throws Exception;
	
	/**
	 * 
	 * getInitialStateCondition
	 * <div lang="ja">
	 * 初期のStateConditionを取得します．StateMachineが終了した状態では，Stateは初期のStateConditionに変更されます．
	 * @return
	 * </div>
	 * <div lang="en">
	 * Get Initial StateCondition. If StateMachine is halt, State's condition is configured to Initial StateCondition.
	 * @return
	 * </div>
	 */
	public abstract StateCondition getInitialStateCondition();
	
	/**
	 * 
	 * getInitialStateCondition
	 * <div lang="ja">
	 * 初期のStateConditionを設定します．StateMachineが終了した状態では，Stateは初期のStateConditionに変更されます．
	 * @return
	 * </div>
	 * <div lang="en">
	 * Set Initial StateCondition. If StateMachine is halt, State's condition is configured to Initial StateCondition.
	 * @return
	 * </div>
	 */
	public abstract void setInitialStateCondition(StateCondition state);
	
	/**
	 * 
	 * connect
	 * <div lang="ja">
	 * guardで指定したガードで，targetで指定したStateに対してTransitionを張ります．
	 * 
	 * @param name
	 * @param target
	 * @param guard
	 * @throws InvalidConnectionException guardがnullの場合，transitionの名前が同一State内で重なる場合に例外を投げます．
	 * </div>
	 * <div lang="en">
	 *
	 * @param name
	 * @param target
	 * @param guard
	 * @throws InvalidConnectionException
	 * </div>
	 */
	public abstract void connect(String name, State target, Guard guard)
			throws InvalidConnectionException;

	/**
	 * 
	 * removeTransition
	 * <div lang="ja">
	 * Transitionを削除します．
	 * @param name
	 * </div>
	 * <div lang="en">
	 * Remove Transition.
	 * @param name
	 * </div>
	 */
	public abstract void removeTransition(String name);

	/**
	 * 
	 * getTransition
	 * <div lang="ja">
	 * Transitionを名前で検索して取得します．
	 * @param name
	 * @return
	 * </div>
	 * <div lang="en">
	 * getter function for transition by name
	 * @param name
	 * @return
	 * </div>
	 */
	public abstract Transition getTransition(String name);

	/**
	 * 
	 * getTransitionIterator
	 * <div lang="ja">
	 * 
	 * @return
	 * </div>
	 * <div lang="en">
	 *
	 * @return
	 * </div>
	 */
	public abstract Iterator<Transition> getTransitionIterator();
	
	/**
	 * 
	 * getLocation
	 * <div lang="ja">
	 * Stateの図上の位置を取得します．
	 * @return
	 * </div>
	 * <div lang="en">
	 *
	 * @return
	 * </div>
	 */
	public abstract Point getLocation();
	
	/**
	 * 
	 * setLocation
	 * <div lang="ja">
	 * Stateの図上の位置を設定します．
	 * @param p
	 * </div>
	 * <div lang="en">
	 *
	 * @param p
	 * </div>
	 */
	public abstract void setLocation(Point p);

	/**
	 * 
	 * toElement
	 * <div lang="ja">
	 * XMLのElementへの保存を行います．
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

	public abstract void load(Node node) throws InvalidFSMFileException ;
	
	/**
	 * 
	 * vanish
	 * <div lang="ja">
	 * Stateを消し去ります．内部のTransitionがStateに対して循環参照を持つので，このメソッドで解除して消します．
	 * </div>
	 * <div lang="en">
	 *
	 * </div>
	 */
	public abstract void vanish();

	/**
	 * 
	 * getOnOperateActionList
	 * <div lang="ja">
	 * OnOperateの直後に呼ばれるStateActionをおさめたリストの取得．Listの順序どおりにStateMachineによって呼ばれる．
	 * @return
	 * </div>
	 * <div lang="en">
	 *　StateActionList which collects StateActions called just after OnOperate.
	 * StateActions are called in the same order of the List.
	 * @return
	 * </div>
	 */
	public abstract StateActionList getOnOperateActionList();

	/**
	 * 
	 * getOnExitActionList
	 * <div lang="ja">
	 * OnExitの直後に呼ばれるStateActionをおさめたリストの取得．Listの順序どおりにStateMachineによって呼ばれる．
	 * @return
	 * </div>
	 * <div lang="en">
	 *　StateActionList which collects StateActions called just after OnExit.
	 * StateActions are called in the same order of the List.
	 * @return
	 * </div>
	 */
	public abstract StateActionList getOnExitActionList();

	/**
	 * 
	 * getOnEntryActionList
	 * <div lang="ja">
	 * OnEntryの直後に呼ばれるStateActionをおさめたリストの取得．Listの順序どおりにStateMachineによって呼ばれる．
	 * @return
	 * </div>
	 * <div lang="en">
	 *　StateActionList which collects StateActions called just after OnEntry.
	 * StateActions are called in the same order of the List.
	 * @return
	 * </div>
	 */
	public abstract StateActionList getOnEntryActionList();
	
	

}
