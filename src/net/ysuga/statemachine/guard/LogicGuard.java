package net.ysuga.statemachine.guard;

public interface LogicGuard extends Guard {
 
	public abstract Guard[] getChildGuards();
}
 
