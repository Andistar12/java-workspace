package com.saja.seca.error;

/**
 * @author Andy Nguyen
 *
 * Extending Exception (vs RuntimeException) makes this a checked exception, forcing a try/catch somewhere else
 * 
 * The constructor with two args is used when the user cannot do anything about the error
 * The constructor with three args is used when the user can do something about the error
 * 
 * The constructor has an original class arg because the class throwing the error is not 
 * 		necessarily the class that the error originates in. (GLTools gives Renderer.class sometimes)
 * 
 * Message is the message displayed to the user (whether he/she can do anything or not)
 * FullMessage is the location of the error and the programmer-friendly description
 * printFullStackTrace() prints the full message and the stacktrace
 * toString returns the FullMessage
 */

public class SecaException extends Exception {
	private static final long serialVersionUID = 1L;

	public static final String default_user_error = "An error occured. Program will terminate...";
	
	private String fullMessage;
	private String classOrig;
	
	public SecaException(Class<?> c, String message) {
		//This constructor is used when the error cannot be resolved by the user
		this(c, message, default_user_error);
	}
	
	public SecaException(Class<?> c, String message, String userMessage) {
		//This constructor is used when the error may be able to be resolved by the user.
		super(userMessage);
		this.fullMessage = "Error in " + c.getName() + ": " +  message;
		classOrig = c.getName();
	}
	
	public String getFullMessage() { return fullMessage; }
	
	public void printFullStackTrace() {
		System.err.println("\n" + fullMessage);
		this.printStackTrace();
	}
	
	public String getClassOrigError() { return classOrig; }
	
	// fullMessage is different from toString()
}
