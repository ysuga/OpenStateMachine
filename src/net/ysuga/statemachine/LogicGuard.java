package net.ysuga.statemachine;

public interface LogicGuard extends Guard {
 
	public abstract Guard[] getChildGuards();
}
 
