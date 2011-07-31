/**
 * AbstractGuardFactory.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/07/31
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.guard;

import net.ysuga.statemachine.StateMachineTagNames;

import org.w3c.dom.Node;

/**
 * @author ysuga
 *
 */
public abstract class AbstractGuardFactory implements GuardFactory {

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
	public AbstractGuardFactory(String kind) {
		this.kind = kind;
	}
	
	public String parseKind(Node node) {
		return node.getAttributes().getNamedItem(StateMachineTagNames.KIND).getNodeValue();
	}
	
	public String parseName(Node node) {
		return node.getAttributes().getNamedItem(StateMachineTagNames.NAME).getNodeValue();
	}
}
