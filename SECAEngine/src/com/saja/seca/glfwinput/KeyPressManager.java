package com.saja.seca.glfwinput;

/**
 * @author Andy Nguyen
 * 
 * The KeyPressManager interface facilitates taking in input from the user
 * More specifically, it detects when a key is pressed, repeated, and released
 * (In LWJGL a key registers three states: PRESS, REPEAT, and RELEASE.)
 * GLWindow creates callbacks based on each of the methods below
 * Both mouse and keyboard press data is sent to the KeyPressManager given to GLWindow
 * (The mouse is considered a button in SECA)
 * 
 * InputManager checks/manages the current status of keys
 * KeyPressManager is called when the status of a key is changed
 * We keep these two separate to separate setting up keys vs capturing input
 */

public interface KeyPressManager {

	public void keyPress(GLWindow win, String keyName);
	
	public void keyRepeat(GLWindow win, String keyName);
	
	public void keyRelease(GLWindow win, String keyName);
	
	public void mouseScrollWheel(GLWindow win, double dx, double dy);
	
	public void mouseCursorPos(GLWindow win, double xpos, double ypos, int windowWidth, int windowHeight);
	
}
