package com.cif.behavior;

/**
 * This is an abstract class that is used to represent an action that you can perform at a specific event.<br>
 * You can also add and update arguments if the action needs objects to run.
 * @author Joakim
 *
 */
public abstract class Action {
	String name;
	protected Object[] arguments;
	
	/**
	 * 
	 * @param name - just an identifier, doesn't need to be extremely explaining
	 */
	public Action(String name){
		this.name = name;
	}
	
	/**
	 * This method executes the action
	 */
	public abstract void execute();
	
	/**
	 * 
	 * @return
	 * the name of the action
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Sets all the arguments for the Action
	 * @param args - all the arguments for the action
	 */
	public void setArguments(Object[] args){
		this.arguments = args;
	}
	
	/**
	 * Replaces an object that exists in the arguments
	 * @param index - the index of the object you want to replace
	 * @param obj - the object you want to replace the object with
	 */
	public void replaceArgument(int index, Object obj){
		this.arguments[index] = obj;
	}
}
