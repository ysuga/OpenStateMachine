package net.ysuga.statemachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

import net.ysuga.statemachine.exception.InvalidConnectionException;
import net.ysuga.statemachine.exception.InvalidFSMFileException;
import net.ysuga.statemachine.exception.InvalidStateNameException;
import net.ysuga.statemachine.guard.AndGuardFactory;
import net.ysuga.statemachine.guard.DelayGuardFactory;
import net.ysuga.statemachine.guard.ExorGuardFactory;
import net.ysuga.statemachine.guard.Guard;
import net.ysuga.statemachine.guard.GuardFactory;
import net.ysuga.statemachine.guard.GuardFactoryManager;
import net.ysuga.statemachine.guard.NotGuardFactory;
import net.ysuga.statemachine.guard.NullGuardFactory;
import net.ysuga.statemachine.guard.OrGuardFactory;
import net.ysuga.statemachine.state.DefaultStateFactory;
import net.ysuga.statemachine.state.State;
import net.ysuga.statemachine.state.StateCollection;
import net.ysuga.statemachine.state.StateCondition;
import net.ysuga.statemachine.state.StateFactory;
import net.ysuga.statemachine.state.StateFactoryManager;
import net.ysuga.statemachine.state.action.LoggingStateActionFactory;
import net.ysuga.statemachine.state.action.MessageBoxStateActionFactory;
import net.ysuga.statemachine.state.action.StateAction;
import net.ysuga.statemachine.state.action.StateActionFactoryManager;
import net.ysuga.statemachine.transition.Transition;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * <div lang="ja"> 　ステートマシンの本体．
 * 
 * StateCollectionクラスメンバでStateを保持します． StateごとにTransitionを保持しています．＞State参照
 * addメソッドをStateを追加します． removeメソッドでStateを削除します．
 * TransitionはStateのconnectメソッドで追加します． 
 * startメソッドで実行を開始します．
 * operateメソッドでactiveなステートを実行します．
 * stopメソッドで実行を停止します．
 * 
 * </div> <div lang="en">
 * 
 * </div>
 * 
 * @author Yuki Suga (ysuga.net)
 * @brief State Machine System Framework
 * @date 2011/05/13
 * @license see lisence.txt
 * @copyright ysuga.net 2011, all rights reserved.
 */
public class StateMachine {

	private static Logger logger;
	static {
		logger = Logger.getLogger(StateMachine.class.getName());
	}

	/**
	 * Name of StateMachine
	 */
	private String name;

	/**
	 * 
	 * getName <div lang="ja"> StateMachineの名前の設定
	 * 
	 * @return </div> <div lang="en"> getter for Name of StateMachine
	 * @return </div>
	 */
	final public String getName() {
		return name;
	}

	/**
	 * 
	 * setName <div lang="ja">
	 * 
	 * @param name
	 *            </div> <div lang="en"> 　setter for name of StateMachine
	 * @param name
	 *            </div>
	 */
	final public void setName(String name) {
		this.name = name;
	}

	private StateCollection stateCollection;

	/**
	 * 
	 * getStateSet <div lang="ja">
	 * 
	 * @return </div> <div lang="en">
	 * 
	 * @return </div>
	 */
	final public StateCollection getStateCollection() {
		return stateCollection;
	}

	/**
	 * 
	 * <div lang="ja"> コンストラクタ </div> <div lang="en"> Constructor </div>
	 */
	private StateMachine() {
		executionMutex = new Object();
		stateCollection = new StateCollection();

		GuardFactoryManager.add(new AndGuardFactory());
		GuardFactoryManager.add(new OrGuardFactory());
		GuardFactoryManager.add(new ExorGuardFactory());
		GuardFactoryManager.add(new NotGuardFactory());
		GuardFactoryManager.add(new DelayGuardFactory());
		GuardFactoryManager.add(new NullGuardFactory());

		StateFactoryManager.add(new DefaultStateFactory());

		StateActionFactoryManager.add(new LoggingStateActionFactory());
		StateActionFactoryManager.add(new MessageBoxStateActionFactory());
	}

	/**
	 * 
	 * <div lang="ja"> コンストラクタ．名前のみ設定する空のStateMachineを作成
	 * 
	 * @param name
	 * @throws ParserConfigurationException
	 *             </div> <div lang="en"> Constructor. Construct Empty State
	 *             Machine.
	 * @param name
	 * @throws ParserConfigurationException
	 *             </div>
	 */
	public StateMachine(String name) throws ParserConfigurationException {
		this();
		this.setName(name);
		logger.entering(StateMachine.class.getName(), "init", name);
	}

	public static final int HALT = 0;
	public static final int OPERATING = 1;
	public static final int SUSPEND = 2;

	private Object executionMutex;
	private int executionState = HALT;

	
	final public int getExecutionState() {
		synchronized(executionMutex) {
			return executionState;
		}
	}

	public void start() {
		reset();
		synchronized(executionMutex) {
			for(State state : stateCollection) {
				if(state.getStateCondition().equals(StateCondition.ACTIVE)) {
					try {
						state.onEntry();
					} catch (Exception e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
					Iterator<Transition> i = state.getTransitionIterator();
					while(i.hasNext()) {
						Transition t = i.next();
						t.onInitialize(state);
					}
				}
			}
			
			executionState = OPERATING;
		}
	}

	public void stop() {
		synchronized(executionMutex) {
			executionState = HALT;
		}
	}
	
	public void suspend() {
		synchronized(executionMutex) {
			if(executionState == OPERATING) {
				executionState = SUSPEND;
			}
		}
	}

	public void resume() {
		synchronized(executionMutex) {
			executionState = OPERATING;
		}
	}
	
	/**
	 * 
	 * <div lang="ja"> コンストラクタ．ファイルからロードする．
	 * 
	 * @param file
	 * @throws InvalidFSMFileException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 *             </div> <div lang="en"> Constructor. Load from FSM file.
	 * @param file
	 * @throws InvalidFSMFileException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 *             </div>
	 */
	public StateMachine(File file) throws InvalidFSMFileException,
			ParserConfigurationException, SAXException, IOException {
		this();
		logger.entering(getClass().getName(), "init", file);

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		db = dbf.newDocumentBuilder();
		Document xmlDocument = db.parse(file);
		Element rootElement = xmlDocument.getDocumentElement();
		if (!rootElement.getNodeName().equals(StateMachineTagNames.FSM)) {
			logger.warning("Invalid State Machine File");
			throw new InvalidFSMFileException();
		}
		NamedNodeMap attributes = rootElement.getAttributes();
		String name = attributes.getNamedItem(StateMachineTagNames.NAME)
				.getNodeValue();
		this.setName(name);

		Node transitionsNode = null;
		Node statesNode = null;

		NodeList nodeList = rootElement.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeName().equals(StateMachineTagNames.STATES)) {
				// Load States
				statesNode = node;
			} else if (node.getNodeName().equals(
					StateMachineTagNames.TRANSITIONS)) {
				transitionsNode = node;
			}
		}

		if (statesNode != null) {
			loadStates(statesNode);
			if (transitionsNode != null) {
				loadTransitions(transitionsNode);
				return;
			}
		}

		throw new InvalidFSMFileException();
	}

	/**
	 * 
	 * operate <div lang="ja"> ステートマシンの実行．このメソッドをmainなどで周期的に呼ぶ必要がある．
	 * 
	 * 内部ではアクティブなStateクラスのoperateを実行する．現行ではforで繰り返しているが，folkを使った場合の挙動を考えた場合，
	 * マルチスレッド化する必要があると思う．
	 * 
	 * @throws Exception
	 *             </div> <div lang="en">
	 * 
	 * @throws Exception
	 *             </div>
	 */
	public void operate() throws Exception {
		if (getExecutionState() == OPERATING) {
			for (State state : stateCollection) {
				if (state.getStateCondition().equals(StateCondition.ACTIVE)) {
					if (!state.operate()) {
						state.onOperate();
						for (StateAction stateAction : state
								.getOnOperateActionList()) {
							stateAction.actionPerformed(state);
						}
					} else {
						Iterator<Transition> i = state.getTransitionIterator();
						while(i.hasNext()) {
							Transition t = i.next();
							t.onFinalize(state);
						}
						state.onExit();
						for (StateAction stateAction : state
								.getOnExitActionList()) {
							stateAction.actionPerformed(state);
						}
					}
				}
			}
			// StateCondition is not updated immediately in order to prevent the
			// state which is activated in this turn from operated.
			// このターンでアクティブ化されたStateがoperateされないように，StateConditionはupdateされるまではactive化しません．
			for (State state : stateCollection) {
				if (state.updateStateCondition()) {
					state.onEntry();
					Iterator<Transition> i = state.getTransitionIterator();
					while(i.hasNext()) {
						Transition t = i.next();
						t.onInitialize(state);
					}					
					for (StateAction stateAction : state.getOnEntryActionList()) {
						stateAction.actionPerformed(state);
					}
				}
			}
		}
	}

	/**
	 * 
	 * <div lang="ja">
	 * 
	 * @return </div> <div lang="en">
	 * @return </div>
	 */
	public String toString() {
		return "StateMachine(" + getName() + ")";
	}

	/**
	 * 
	 * save <div lang="ja">
	 * 
	 * @param file
	 * @throws TransformerFactoryConfigurationError
	 * @throws FileNotFoundException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 *             </div> <div lang="en">
	 * 
	 * @param file
	 * @throws TransformerFactoryConfigurationError
	 * @throws FileNotFoundException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 *             </div>
	 */
	public void save(File file) throws TransformerFactoryConfigurationError,
			FileNotFoundException, TransformerException,
			ParserConfigurationException {
		logger.entering(getClass().getName(), "save",
				new Object[] { this, file });

		// ドキュメントビルダーを作成
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(true);
		DocumentBuilder db = null;
		db = dbf.newDocumentBuilder();
		Document xmlDocument = db.newDocument();

		Element rootElement = xmlDocument
				.createElement(StateMachineTagNames.FSM);
		rootElement.setAttribute("Date", new Date().toString());
		rootElement.setAttribute("Version", "3.0");
		rootElement.setAttribute(StateMachineTagNames.NAME, getName());
		xmlDocument.appendChild(rootElement);

		Element statesElement = xmlDocument
				.createElement(StateMachineTagNames.STATES);
		Element transitionsElement = xmlDocument
				.createElement(StateMachineTagNames.TRANSITIONS);
		rootElement.appendChild(statesElement);
		rootElement.appendChild(transitionsElement);
		for (State state : this.getStateCollection()) {
			statesElement.appendChild(state.toElement(xmlDocument));

			// TransitionMap transitionMap = state.getTransitionMap();
			Iterator<Transition> i = state.getTransitionIterator();
			while (i.hasNext()) {
				Transition transition = i.next();
				transitionsElement.appendChild(transition
						.toElement(xmlDocument));
			}
		}

		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty("indent", "yes");
		t.transform(new javax.xml.transform.dom.DOMSource(xmlDocument),
				new javax.xml.transform.stream.StreamResult(
						new java.io.FileOutputStream(file)));
	}

	/**
	 * 
	 * loadStates <div lang="ja">
	 * 
	 * @param node
	 * @throws InvalidFSMFileException
	 *             </div> <div lang="en">
	 * 
	 * @param node
	 * @throws InvalidFSMFileException
	 *             </div>
	 */
	public void loadStates(Node node) throws InvalidFSMFileException {
		logger.entering(getClass().getName(), "loadStates", this);
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node childNode = nodeList.item(i);
			if (childNode.getNodeName().equals(StateMachineTagNames.STATE)) {
				NamedNodeMap attributes = childNode.getAttributes();
				String kind = attributes
						.getNamedItem(StateMachineTagNames.KIND).getNodeValue();
				StateFactory factory = StateFactoryManager.getInstance().get(
						kind);
				if (factory == null) {
					logger.severe("unknown state kind (" + kind + ")");
					throw new InvalidFSMFileException();
				}
				State state = factory.loadState(childNode);
				try {
					add(state);
				} catch (InvalidStateNameException ex) {
					throw new InvalidFSMFileException();
				}
			}
		}
	}

	/**
	 * 
	 * loadTransitions <div lang="ja">
	 * 
	 * @param node
	 * @throws InvalidFSMFileException
	 *             </div> <div lang="en">
	 * 
	 * @param node
	 * @throws InvalidFSMFileException
	 *             </div>
	 */

	private void loadTransitions(Node node) throws InvalidFSMFileException {
		logger.entering(getClass().getName(), "loadTransitions", this);
		NodeList childNodeList = node.getChildNodes();
		for (int i = 0; i < childNodeList.getLength(); i++) {
			Node childNode = childNodeList.item(i);
			if (childNode.getNodeName().equals(StateMachineTagNames.TRANSITION)) {
				NamedNodeMap attributes = childNode.getAttributes();

				String name = attributes
						.getNamedItem(StateMachineTagNames.NAME).getNodeValue();

				String sourceName = attributes.getNamedItem(
						StateMachineTagNames.FROM).getNodeValue();
				String targetName = attributes.getNamedItem(
						StateMachineTagNames.TO).getNodeValue();
				State sourceState = getState(sourceName);
				State targetState = getState(targetName);
				Guard guard = null;
				NodeList grandChildNodeList = childNode.getChildNodes();
				for (int j = 0; j < grandChildNodeList.getLength(); j++) {
					Node grandChildNode = grandChildNodeList.item(j);
					if (grandChildNode.getNodeName().equals(
							StateMachineTagNames.GUARD)) {
						NamedNodeMap grandChildAttributes = grandChildNode
								.getAttributes();
						String kind = grandChildAttributes.getNamedItem(
								StateMachineTagNames.KIND).getNodeValue();
						GuardFactory guardFactory = GuardFactoryManager
								.getInstance().get(kind);
						if (guardFactory == null) {
							logger.severe("unknown guard kind (" + kind + ")");
							throw new InvalidFSMFileException();
						}
						guard = guardFactory.loadGuard(grandChildNode);
					}
				}
				try {
					sourceState.connect(name, targetState, guard);
				} catch (InvalidConnectionException e) {
					e.printStackTrace();
					throw new InvalidFSMFileException();
				}

			}
		}
	}

	/**
	 * 
	 * getState <div lang="ja"> 名前からStateの取得
	 * 
	 * @param name
	 * @return </div> <div lang="en"> get State by Name
	 * @param name
	 * @return </div>
	 */
	final public State getState(String name) {
		for (State state : stateCollection) {
			if (state.getName().equals(name)) {
				return state;
			}
		}
		// return stateMap.get(name);
		return null;
	}

	/**
	 * 
	 * add <div lang="ja">
	 * 
	 * @param state
	 * @throws InvalidStateNameException
	 *             </div> <div lang="en">
	 * 
	 * @param state
	 * @throws InvalidStateNameException
	 *             </div>
	 */
	final public void add(State state) throws InvalidStateNameException {
		logger.entering(getClass().getName(), "add",
				new Object[] { this, state });
		for (State ownedState : stateCollection) {
			if (ownedState.getName().equals(state.getName())) {
				throw new InvalidStateNameException();
			}
		}
		stateCollection.add(state);
		/*
		 * if (stateMap.get(state.getName()) != null) { throw new
		 * InvalidStateNameException(); }
		 * 
		 * stateMap.put(state.getName(), state);
		 */
	}

	/**
	 * 
	 * remove <div lang="ja">
	 * 
	 * @param state
	 * @return </div> <div lang="en">
	 * 
	 * @param state
	 * @return </div>
	 */
	final public State remove(State state) {
		logger.entering(getClass().getName(), "remove", new Object[] { this,
				state });
		// return stateMap.remove(state.getName());
		if (stateCollection.contains(state)) {
			// throw new Invalid
			stateCollection.remove(state);
			return state;
		}
		return null;
	}

	/**
	 * <div lang="ja">
	 * 
	 * @param selectedState
	 * @param state
	 *            </div> <div lang="en">
	 * 
	 * @param selectedState
	 * @param state
	 *            </div>
	 */
	public void replace(State selectedState, State state)
			throws InvalidStateNameException {
		State oldState = remove(selectedState);
		try {
			add(state);
			state.setLocation(oldState.getLocation());

			Iterator<Transition> i = oldState.getTransitionIterator();
			while (i.hasNext()) {
				Transition transition = i.next();
				try {
					state.connect(transition.getName(),
							transition.getTargetState(), transition.getGuard());
				} catch (InvalidConnectionException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}

			for (State stateBuf : stateCollection) {
				Iterator<Transition> i2 = stateBuf.getTransitionIterator();
				while (i2.hasNext()) {
					Transition transition = i2.next();
					// for(Transition transition :
					// stateBuf.getTransitionMap().values()) {
					if (transition.getTargetState().equals(oldState)) {
						transition.setTargetState(state);
					}
				}
			}
		} catch (InvalidStateNameException ex) {
			add(oldState);
			throw ex;
		}
	}

	/**
	 * reset
	 * <div lang="ja">
	 * 
	 * </div>
	 * <div lang="en">
	 *
	 * </div>
	 */
	public void reset() {
		synchronized(executionMutex) {
			executionState = HALT;
			for(State state : stateCollection) {
				try {
					state.setStateCondition(state.getInitialStateCondition());
					state.updateStateCondition();
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		}
	}
}

