/**
 * StateMachineExecutionThread.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/24
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine;

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

	private StateMachine stateMachine;
	/**
	 * <div lang="ja">
	 * �R���X�g���N�^
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
		stateMachine.start();
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
				// TODO �����������ꂽ catch �u���b�N
				System.out.println("Exception Occurred during Execution of StateMachineExecutionThread.");
				e.printStackTrace();
			}
		}
		stateMachine.stop();
	}
}
