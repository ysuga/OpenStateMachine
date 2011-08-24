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
 *�@State��\���ŏ�ʂ̃C���^�[�t�F�[�X�D��{����ɃA�N�Z�X
 * </div>
 * <div lang="en">
 *�@State Interface.
 * </div>
 * @author ysuga
 *
 */
public interface State extends ModelElement {


	/**
	 * 
	 * getKind
	 * <div lang="ja">
	 * State�̎�ނ𕶎���Ŏ擾����D
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
	 * State�̖��O�̎擾
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
	 * State�ɏ�������Transition��Guard��]�����܂��DGuard�̕]�������͎����Ɉˑ����܂��D
	 * 
	 * @return ����Guard��true��Transition�����������ꍇ��true��Ԃ�
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
	 * State��ACTIVE�ɂȂ��StateMachine�ɂ���ČĂ΂�܂��DACTIVE�ɂȂ�����ŌĂ΂�܂��D
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
	 * State��INACTIVE�ɂȂ��StateMachine�ɂ���ČĂ΂�܂��D
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
	 * operate�����s����āCINACTIVE������Ȃ������ꍇ��StateMachine�ɂ���ČĂ΂�܂��D
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
	 * ���݂�StateCondition���擾���܂��D
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
	 * StateCondition��ݒ肵�܂��D�������CupdateStateCondition���Ă΂��܂ōX�V����܂���D
	 * @param state
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 *�@Set New StateCondition, but StateCondition is not updated until updateStateCondition is called.
	 * @param state
	 * @throws Exception
	 * </div>
	 */
	public abstract void setStateCondition(StateCondition state) throws Exception;
	
	/**
	 * 
	 * updateStateCondition
	 * <div lang="ja">
	 * StateCondition���X�V���܂��D
	 * @return�@ACTIVE�������ꍇ�ɂ�true��Ԃ��܂��D
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 *�@Update StateCondition.
	 * @return true if state is activated.
	 * @throws Exception
	 * </div>
	 */
	public abstract boolean updateStateCondition() throws Exception;
	
	/**
	 * 
	 * getInitialStateCondition
	 * <div lang="ja">
	 * ������StateCondition���擾���܂��DStateMachine���I��������Ԃł́CState�͏�����StateCondition�ɕύX����܂��D
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
	 * ������StateCondition��ݒ肵�܂��DStateMachine���I��������Ԃł́CState�͏�����StateCondition�ɕύX����܂��D
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
	 * guard�Ŏw�肵���K�[�h�ŁCtarget�Ŏw�肵��State�ɑ΂���Transition�𒣂�܂��D
	 * 
	 * @param name
	 * @param target
	 * @param guard
	 * @throws InvalidConnectionException guard��null�̏ꍇ�Ctransition�̖��O������State���ŏd�Ȃ�ꍇ�ɗ�O�𓊂��܂��D
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
	 * Transition���폜���܂��D
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
	 * Transition�𖼑O�Ō������Ď擾���܂��D
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
	 * State�̐}��̈ʒu���擾���܂��D
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
	 * State�̐}��̈ʒu��ݒ肵�܂��D
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
	 * XML��Element�ւ̕ۑ����s���܂��D
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
	 * State����������܂��D������Transition��State�ɑ΂��ďz�Q�Ƃ����̂ŁC���̃��\�b�h�ŉ������ď����܂��D
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
	 * OnOperate�̒���ɌĂ΂��StateAction�������߂����X�g�̎擾�DList�̏����ǂ����StateMachine�ɂ���ČĂ΂��D
	 * @return
	 * </div>
	 * <div lang="en">
	 *�@StateActionList which collects StateActions called just after OnOperate.
	 * StateActions are called in the same order of the List.
	 * @return
	 * </div>
	 */
	public abstract StateActionList getOnOperateActionList();

	/**
	 * 
	 * getOnExitActionList
	 * <div lang="ja">
	 * OnExit�̒���ɌĂ΂��StateAction�������߂����X�g�̎擾�DList�̏����ǂ����StateMachine�ɂ���ČĂ΂��D
	 * @return
	 * </div>
	 * <div lang="en">
	 *�@StateActionList which collects StateActions called just after OnExit.
	 * StateActions are called in the same order of the List.
	 * @return
	 * </div>
	 */
	public abstract StateActionList getOnExitActionList();

	/**
	 * 
	 * getOnEntryActionList
	 * <div lang="ja">
	 * OnEntry�̒���ɌĂ΂��StateAction�������߂����X�g�̎擾�DList�̏����ǂ����StateMachine�ɂ���ČĂ΂��D
	 * @return
	 * </div>
	 * <div lang="en">
	 *�@StateActionList which collects StateActions called just after OnEntry.
	 * StateActions are called in the same order of the List.
	 * @return
	 * </div>
	 */
	public abstract StateActionList getOnEntryActionList();
	
	

}
