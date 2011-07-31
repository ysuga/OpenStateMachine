/**
 * GuardFactoryManager.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/07/31
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.guard;

import java.util.HashMap;
import java.util.Map;


/**
 * @author ysuga
 *
 */
public class GuardFactoryManager {

	Map<String, GuardFactory> guardFactoryMap;
	
	static private GuardFactoryManager instance;
	
	static final public GuardFactoryManager getInstance() {
		if(instance == null) {
			instance = new GuardFactoryManager();
		}
		
		return instance;
	}
	
	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * </div>
	 */
	private GuardFactoryManager() {
		super();
		guardFactoryMap = new HashMap<String, GuardFactory>();
		
		add(new AndGuardFactory());
		add(new OrGuardFactory());
		add(new ExorGuardFactory());
		add(new NotGuardFactory());
	}

	/**
	 * <div lang="ja">
	 * @param kind
	 * @param factory
	 * </div>
	 * <div lang="en">
	 * @param kind
	 * @param factory
	 * </div>
	 */
	public void add(GuardFactory factory) {
		guardFactoryMap.put(factory.getKind(), factory);
	}

	/**
	 * <div lang="ja">
	 * @param kind
	 * @return
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 * @param kind
	 * @return
	 * @throws Exception
	 * </div>
	 */
	public GuardFactory get(String kind) throws Exception {
		GuardFactory factory = guardFactoryMap.get(kind);
		return factory;
	}

	/**
	 * <div lang="ja">
	 * @param kind
	 * </div>
	 * <div lang="en">
	 * @param kind
	 * </div>
	 */
	public void remove(String kind) {
		guardFactoryMap.remove(kind);
	}

	/**
	 * <div lang="ja">
	 * @param factory
	 * </div>
	 * <div lang="en">
	 * @param factory
	 * </div>
	 */
	public void remove(GuardFactory factory) {
		guardFactoryMap.remove(factory.getKind());
	}

}
