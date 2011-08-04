import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import net.ysuga.statemachine.StateMachine;
import net.ysuga.statemachine.exception.InvalidFSMFileException;

/**
 * StateMachineLoadTest.java
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
public class StateMachineLoadTest {

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
		try {
			StateMachine myMachine = new StateMachine(new File("temp.xml"));
			myMachine.operate();
		} catch (InvalidFSMFileException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		} catch (Exception e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}

	}

}
