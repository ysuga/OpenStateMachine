/**
 * StateFactoryManager.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/04
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.state;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.ysuga.statemachine.guard.GuardFactory;
import net.ysuga.statemachine.guard.GuardFactoryManager;

/**
 * @author ysuga
 *
 */
public class StateFactoryManager {
	static Logger logger;
	
	static {
		logger = Logger.getLogger("net.ysuga.statemachine.state.StateFactoryManager");
	}
	Map<String, StateFactory> stateFactoryMap;
	
	static private StateFactoryManager instance;
	
	static final public StateFactoryManager getInstance() {
		if(instance == null) {
			instance = new StateFactoryManager();
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
	private StateFactoryManager() {
		super();
		stateFactoryMap = new HashMap<String, StateFactory>();
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
	static public void add(StateFactory factory) {
		StateFactoryManager manager = StateFactoryManager.getInstance();
		logger.entering(StateFactoryManager.class.getName(), "add", factory);
		manager.stateFactoryMap.put(factory.getKind(), factory);
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
	public StateFactory get(String kind) {
		StateFactory factory = stateFactoryMap.get(kind);
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
		stateFactoryMap.remove(kind);
	}

	/**
	 * <div lang="ja">
	 * @param factory
	 * </div>
	 * <div lang="en">
	 * @param factory
	 * </div>
	 */
	public void remove(StateFactory factory) {
		stateFactoryMap.remove(factory.getKind());
	}
}
