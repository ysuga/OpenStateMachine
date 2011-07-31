package net.ysuga.statemachine;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * @author Yuki Suga (ysuga.net)
 * @brief State Machine System Framework
 * @date 2011/05/13
 * @license see lisence.txt
 * @copyright ysuga.net 2011, all rights reserved.
 * 
 */
public class StateMachine implements ModelElement {
	
	private String name;
	 
	private Map<String, State> stateMap;
	

	protected Element rootElement;

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
			if(state.getState().equals(StateCondition.ACTIVE)) {
				state.operate();
			}
		}
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
		this.setName(name);
		stateMap = new HashMap<String, State>();
		
		// ドキュメントビルダーを作成
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(true);
		DocumentBuilder db = null;
		db = dbf.newDocumentBuilder();
		xmlDocument = db.newDocument();
	}
	

	public void save(File file) {
		rootElement = xmlDocument.createElement(StateMachineTagNames.FSM);
		rootElement.setAttribute("Date", new Date().toString());
		rootElement.setAttribute(StateMachineTagNames.NAME, getName());
		xmlDocument.appendChild(rootElement);
		
		for(State state : stateMap.values()) {
			rootElement.appendChild(state.toElement(xmlDocument));
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
 
