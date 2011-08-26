/**
 * StateMachineFactory.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/26
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import net.ysuga.statemachine.exception.InvalidFSMFileException;

import org.xml.sax.SAXException;

/**
 * <div lang="ja">
 * 
 * </div> <div lang="en">
 * 
 * </div>
 * 
 * @author ysuga
 * 
 */
public interface StateMachineFactory {
	public StateMachine createStateMachine(String name)
			throws ParserConfigurationException;

	public StateMachine createStateMachine(File file)
			throws InvalidFSMFileException, ParserConfigurationException,
			SAXException, IOException;

}
