/**
 * AbstractLogicGuardFactory.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/07/31
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.guard;

import java.util.ArrayList;

import net.ysuga.statemachine.StateMachineTagNames;
import net.ysuga.statemachine.exception.InvalidFSMFileException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author ysuga
 *
 */
public abstract class AbstractLogicGuardFactory implements GuardFactory {

	public String parseKind(Node node) {
		return node.getAttributes().getNamedItem(StateMachineTagNames.KIND).getNodeValue();
	}
	
	public String parseName(Node node) {
		return node.getAttributes().getNamedItem(StateMachineTagNames.NAME).getNodeValue();
	}
	
	private String kind;
	
	public String getKind() { return kind; }
	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * </div>
	 */
	public AbstractLogicGuardFactory(String kind) {
		this.kind = kind;
	}
	
	
	/**
	 * 
	 * <div lang="ja">
	 *
	 * @param element
	 * @return
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 *
	 * @param element
	 * @return
	 * @throws Exception
	 * </div>
	 */
	public Guard[] parseChild(Node node) throws InvalidFSMFileException {
		GuardFactoryManager manager = GuardFactoryManager.getInstance();
		ArrayList<Guard> guardList = new ArrayList<Guard>();
		NodeList nodeList = node.getChildNodes();
		for(int i = 0;i < nodeList.getLength();i++) {
			Node childNode = nodeList.item(i);
			if(childNode.getNodeValue().equals(StateMachineTagNames.GUARD)) {
				String kind = childNode.getAttributes().getNamedItem(StateMachineTagNames.KIND).getNodeValue();
				GuardFactory factory = manager.get(kind);
				Guard guard = factory.loadGuard(node);
				guardList.add(guard);
			}
		}
		
		return (Guard[])guardList.toArray();
	}
	
	/**
	 * <div lang="ja">
	 * @param element
	 * @return
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 * @param element
	 * @return
	 * @throws Exception
	 * </div>
	 */
	@Override
	public Guard loadGuard(Node node) throws InvalidFSMFileException {
		Guard[] guards = parseChild(node);
		String name = parseName(node);
		try {
		return createGuard(name, guards);
		} catch (Exception e) {
			throw new InvalidFSMFileException();
		}
	}
	
	public abstract Guard createGuard(String name, Guard[] guards) throws Exception;

	public String toString() {
		return "LogicGuardFactory:" + getKind();
	}
}
