/**
 * StateFactoryManager.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/04
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine.state.action;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.ysuga.statemachine.guard.GuardFactory;
import net.ysuga.statemachine.guard.GuardFactoryManager;

/**
 * @author ysuga
 *
 */
public class StateActionFactoryManager {
	static Logger logger;
	
	static {
		logger = Logger.getLogger("net.ysuga.statemachine.state.StateFactoryManager");
	}
	Map<String, StateActionFactory> stateFactoryMap;
	
	static private StateActionFactoryManager instance;
	
	static final public StateActionFactoryManager getInstance() {
		if(instance == null) {
			instance = new StateActionFactoryManager();
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
	private StateActionFactoryManager() {
		super();
		stateFactoryMap = new HashMap<String, StateActionFactory>();
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
	static public void add(StateActionFactory factory) {
		StateActionFactoryManager manager = StateActionFactoryManager.getInstance();
		logger.entering(StateActionFactoryManager.class.getName(), "add", factory);
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
	public StateActionFactory get(String kind) {
		StateActionFactory factory = stateFactoryMap.get(kind);
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
	public void remove(StateActionFactory factory) {
		stateFactoryMap.remove(factory.getKind());
	}
}
