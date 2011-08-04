package net.ysuga.statemachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
import net.ysuga.statemachine.state.StateCondition;
import net.ysuga.statemachine.state.StateFactory;
import net.ysuga.statemachine.state.StateFactoryManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * @author Yuki Suga (ysuga.net)
 * @brief State Machine System Framework
 * @date 2011/05/13
 * @license see lisence.txt
 * @copyright ysuga.net 2011, all rights reserved.
 * 
 */
public class StateMachine implements ModelElement {
	
	private static Logger logger;
	static {
		logger = Logger.getLogger(StateMachine.class.getName());
	}
	private String name;
	 
	private Map<String, State> stateMap;
	

	//protected Element rootElement;

	private Document xmlDocument;
	
	
	/**
	 * <div lang="ja">
	 *
	 * @throws Exception
	 * </div>
	 * <div lang="en">
	 *
	 * @throws Exception
	 * </div>
	 */
	public void operate() throws Exception {
		for(String name : stateMap.keySet()) {
			State state = stateMap.get(name);
			if(state.getStateCondition().equals(StateCondition.ACTIVE)) {
				state.operate();
			}
		}
	}
	
	private StateMachine() {
		stateMap = new HashMap<String, State>();
		
		GuardFactoryManager.add(new AndGuardFactory());
		GuardFactoryManager.add(new OrGuardFactory());
		GuardFactoryManager.add(new ExorGuardFactory());
		GuardFactoryManager.add(new NotGuardFactory());
		GuardFactoryManager.add(new DelayGuardFactory());
		GuardFactoryManager.add(new NullGuardFactory());
		
		StateFactoryManager.add(new DefaultStateFactory());
	}

	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * @param name
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * @param name
	 * </div>
	 * @throws ParserConfigurationException 
	 */
	public StateMachine(String name) throws ParserConfigurationException {
		this();
		this.setName(name);
		logger.entering(StateMachine.class.getName(), "init", name);
		
		// ドキュメントビルダーを作成
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(true);
		DocumentBuilder db = null;
		db = dbf.newDocumentBuilder();
		xmlDocument = db.newDocument();
	}
	

	public String toString() {
		return "StateMachine(" + getName() + ")";
	}
	
	public StateMachine(File file) throws InvalidFSMFileException, ParserConfigurationException, SAXException, IOException {
		this();
		logger.entering(getClass().getName(), "init", file);
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		db = dbf.newDocumentBuilder();
		xmlDocument = db.parse(file);
		Element rootElement = xmlDocument.getDocumentElement();
		if (!rootElement.getNodeName().equals(StateMachineTagNames.FSM)) {
			logger.warning("Invalid State Machine File");
			throw new InvalidFSMFileException();
		}
		NamedNodeMap attributes = rootElement.getAttributes();
		String name = attributes.getNamedItem(StateMachineTagNames.NAME).getNodeValue();
		this.setName(name);
		
		Node transitionsNode = null;
		Node statesNode = null;

		NodeList nodeList = rootElement.getChildNodes();
		for(int i = 0;i < nodeList.getLength();i++) {
			Node node = nodeList.item(i);
			if(node.getNodeName().equals(StateMachineTagNames.STATES)) {
				// Load States
				statesNode = node;
			} else if(node.getNodeName().equals(StateMachineTagNames.TRANSITIONS)) {
				transitionsNode = node;
			}
		}
		
		if(statesNode != null) {
			loadStates(statesNode);
			if(transitionsNode != null) {
				loadTransitions(transitionsNode);
				return;
			}
		}
		
		throw new InvalidFSMFileException();
	}
	
	
	/**
	 * 
	 * <div lang="ja">
	 *
	 * @param file
	 * @throws TransformerFactoryConfigurationError
	 * @throws FileNotFoundException
	 * @throws TransformerException
	 * </div>
	 * <div lang="en">
	 *
	 * @param file
	 * @throws TransformerFactoryConfigurationError
	 * @throws FileNotFoundException
	 * @throws TransformerException
	 * </div>
	 */
	public void save(File file) throws TransformerFactoryConfigurationError, FileNotFoundException, TransformerException {
		logger.entering(getClass().getName(), "save", new Object[]{this, file});
		Element rootElement = xmlDocument.createElement(StateMachineTagNames.FSM);
		rootElement.setAttribute("Date", new Date().toString());
		rootElement.setAttribute("Version", "3.0");
		rootElement.setAttribute(StateMachineTagNames.NAME, getName());
		xmlDocument.appendChild(rootElement);
		
		Element statesElement = xmlDocument.createElement(StateMachineTagNames.STATES);
		Element transitionsElement = xmlDocument.createElement(StateMachineTagNames.TRANSITIONS);
		rootElement.appendChild(statesElement);
		rootElement.appendChild(transitionsElement);
		for(State state : stateMap.values()) {
			statesElement.appendChild(state.toElement(xmlDocument));
			
			Map<String, Transition> transitionMap = state.transitionMap();
			for(Transition transition : transitionMap.values()) {
				transitionsElement.appendChild(transition.toElement(xmlDocument));
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
	 * <div lang="ja">
	 *
	 * @param element
	 * </div>
	 * <div lang="en">
	 *
	 * @param element
	 * </div>
	 * @throws InvalidFSMFileException 
	 */
	public void loadStates(Node node) throws InvalidFSMFileException {
		logger.entering(getClass().getName(), "loadStates", this);
		NodeList nodeList = node.getChildNodes();
		for(int i = 0;i < nodeList.getLength();i++) {
			Node childNode = nodeList.item(i);
			if(childNode.getNodeName().equals(StateMachineTagNames.STATE)) {
				NamedNodeMap attributes = childNode.getAttributes();
				String kind = attributes.getNamedItem(StateMachineTagNames.KIND).getNodeValue();
				StateFactory factory = StateFactoryManager.getInstance().get(kind);
				if(factory == null) {
					logger.severe("unknown state kind ("+kind+")");
					throw new InvalidFSMFileException();
				}
				State state = factory.loadState(childNode);
				add(state);
			}
		}
	}
	
	/**
	 * 
	 * <div lang="ja">
	 *
	 * @param element
	 * </div>
	 * <div lang="en">
	 *
	 * @param element
	 * </div>
	 * @throws InvalidFSMFileException 
	 * @throws InvalidConnectionException 
	 */
	public void loadTransitions(Node node) throws InvalidFSMFileException {
		logger.entering(getClass().getName(), "loadTransitions", this);
		NodeList childNodeList = node.getChildNodes();
		for(int i = 0;i < childNodeList.getLength();i++) {
			Node childNode = childNodeList.item(i);
			if(childNode.getNodeName().equals(StateMachineTagNames.TRANSITION)) {
				NamedNodeMap attributes = childNode.getAttributes();
				
				String name = attributes.getNamedItem(StateMachineTagNames.NAME).getNodeValue();
				
				String sourceName = attributes.getNamedItem(StateMachineTagNames.FROM).getNodeValue();
				String targetName = attributes.getNamedItem(StateMachineTagNames.TO).getNodeValue();
				State sourceState = getState(sourceName);
				State targetState = getState(targetName);
				Guard guard = null;
				NodeList grandChildNodeList = childNode.getChildNodes();
				for(int j = 0;j < grandChildNodeList.getLength();j++) {
					Node grandChildNode = grandChildNodeList.item(j);
					if(grandChildNode.getNodeName().equals(StateMachineTagNames.GUARD)){
						NamedNodeMap grandChildAttributes = grandChildNode.getAttributes();
						String kind = grandChildAttributes.getNamedItem(StateMachineTagNames.KIND).getNodeValue();
						GuardFactory guardFactory = GuardFactoryManager.getInstance().get(kind);
						if(guardFactory == null) {
							logger.severe("unknown guard kind ("+kind+")");
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
	 * <div lang="ja">
	 * @return
	 * </div>
	 * <div lang="en">
	 * @return
	 * </div>
	 */
	public String getName() {
		return name;
	}
	 
	/**
	 * <div lang="ja">
	 *
	 * @param name
	 * </div>
	 * <div lang="en">
	 *
	 * @param name
	 * </div>
	 */
	public void setName(String name) {
		this.name = name;
	}
	 
	/**
	 * <div lang="ja">
	 *
	 * @param name
	 * @return
	 * </div>
	 * <div lang="en">
	 *
	 * @param name
	 * @return
	 * </div>
	 */
	final public State getState(String name) {
		return stateMap.get(name);
	}
	 
	/**
	 * <div lang="ja">
	 *
	 * @param state
	 * </div>
	 * <div lang="en">
	 *
	 * @param state
	 * </div>
	 */
	final public void add(State state) {
		logger.entering(getClass().getName(), "add", new Object[]{this, state});
		stateMap.put(state.getName(), state);
	}
	 
	/**
	 * <div lang="ja">
	 *
	 * @param state
	 * </div>
	 * <div lang="en">
	 *
	 * @param state
	 * </div>
	 */
	final public void remove(State state) {
		logger.entering(getClass().getName(), "remove", new Object[]{this, state});
		stateMap.remove(state.getName());
	}
	 
	/**
	 * <div lang="ja">
	 *
	 * @return
	 * </div>
	 * <div lang="en">
	 *
	 * @return
	 * </div>
	 */
	final public Map<String, State> getStateMap() {
		return stateMap;
	}
	 
}
 
