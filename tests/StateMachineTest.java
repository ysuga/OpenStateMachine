import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import net.ysuga.statemachine.StateMachine;
import net.ysuga.statemachine.exception.InvalidConnectionException;
import net.ysuga.statemachine.guard.DelayGuard;
import net.ysuga.statemachine.guard.NullGuard;
import net.ysuga.statemachine.state.ExitState;
import net.ysuga.statemachine.state.StartState;
import net.ysuga.statemachine.state.DefaultState;
import net.ysuga.statemachine.state.State;

/**
 * StateMachineTest.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/04
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */

/**
 * @author ysuga
 *
 */
public class StateMachineTest {

	/**
	 * <div lang="ja">
	 *
	 * @param args
	 * </div>
	 * <div lang="en">
	 *
	 * @param args
	 * </div>
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		StateMachine myMachine;
		try {
			myMachine = new StateMachine("MyMachine");
			State start = new StartState();
			State exit  = new ExitState();
			myMachine.add(start);
			myMachine.add(exit);
			
			State state1 = new DefaultState("State1");
			State state2 = new DefaultState("State2");
			
			start.connect("startTransition", state1, new NullGuard("startGuard"));
			state1.connect("myTransition1", state2, new DelayGuard("myGuard1", 500));
			state2.connect("myTransition2", state1, new DelayGuard("myGuard2", 300));
			state2.connect("exitTransition", exit, new DelayGuard("exitGuard", 5000));

			myMachine.add(state1);
			myMachine.add(state2);
			File temp = new File("temp.xml");
			myMachine.save(temp);
		} catch (ParserConfigurationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (InvalidConnectionException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
