/**
 * StateMachineExecutionThread.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/24
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine;

import java.util.logging.Logger;

/**
 * <div lang="ja">
 *
 * </div>
 * <div lang="en">
 *
 * </div>
 * @author ysuga
 *
 */
public class StateMachineExecutionThread extends Thread {

	static private Logger logger = Logger.getLogger(StateMachineExecutionThread.class.getName());
	
	private StateMachine stateMachine;
	/**
	 * <div lang="ja">
	 * コンストラクタ
	 * </div>
	 * <div lang="en">
	 * Constructor
	 * </div>
	 */
	public StateMachineExecutionThread(StateMachine stateMachine) {
		this.stateMachine = stateMachine;
	}

	@Override
	public void start() {
		startExecution();
	}
	
	public void startExecution() {
		super.start();
	}
	
	private boolean stopFlag;
	
	private boolean suspendFlag;
	
	public boolean isSuspend() {
		return suspendFlag;
	}
	
	public void stopExecution() {
		stopFlag = true;
		try {
			super.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void suspendExecution() {
		suspendFlag = true;
	}
	
	public synchronized void resumeExecution() {
		suspendFlag = false;
		notify();
	}
	
	private synchronized void goSuspend() throws InterruptedException {
		wait();
	}
	
	@Override
	public void run() {
		stateMachine.reset();
		try {
			stateMachine.start();
		} catch (Exception e) {
			logger.severe("Exception occurred when starting StateMachine");
			for(StackTraceElement elem : e.getStackTrace()) {
				logger.severe(elem.toString());
			}
			stopFlag = true;
		}
		while(!stopFlag) {
			try {
				if(!suspendFlag) {
					stateMachine.operate();
				} else {
					stateMachine.suspend();
					goSuspend();
					stateMachine.resume();
				}
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				System.out.println("Exception Occurred during Execution of StateMachineExecutionThread.");
				e.printStackTrace();
			}
		}
		stateMachine.stop();
	}
}
