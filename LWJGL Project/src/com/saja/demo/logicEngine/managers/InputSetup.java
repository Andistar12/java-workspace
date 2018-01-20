package com.saja.demo.logicEngine.managers;

import com.saja.seca.error.MessagePopup;
import com.saja.seca.error.SecaException;
import com.saja.seca.glfwinput.GLWindow;
import com.saja.seca.glfwinput.InputManager;
import com.saja.seca.glfwinput.KeyPressManager;

/**
 * @author Andy Nguyen
 * 
 * Extends InputManager from SECA input package. 
 * Binds certain 'commands' to keys, in which those keys can be changed during execution
 * 
 * InputSetup deals strictly with setting up and getting player input, specifically setting up commands.
 * 		It should not hold any game-related fields
 * PlayerSystem deals strictly with accessing and manipulating those commands. 
 * 		It holds player constants and statuses, but deals nothing with the actual key pressed (like F11)
 */

public class InputSetup extends InputManager implements KeyPressManager {
	
	private float[] mouseDelta = new float[2];
	
	private int currentColor = 0;
	private int viewMode = 0;
	
	public InputSetup() {
		super();
	}

	@Override
	public void keyRepeat(GLWindow win, String keyName) {}

	@Override
	public void keyPress(GLWindow win, String keyName) {
		//Only check if the game is going
		if (isMouseLocked()) {
			checkGameInputs(keyName, true); //If any game keys are down, set their value to true
			
			if (this.getSetCommand(keyName).contains("color")) {
				//One of the keys changes the color of the cube
				if (keyName.equals(this.getSetKey("color1"))) currentColor = 0;
				else if (keyName.equals(this.getSetKey("color2"))) currentColor = 1;
				else if (keyName.equals(this.getSetKey("color3"))) currentColor = 2;
				else if (keyName.equals(this.getSetKey("color4"))) currentColor = 3;
				else if (keyName.equals(this.getSetKey("color5"))) currentColor = 4;
				else if (keyName.equals(this.getSetKey("color6"))) currentColor = 5;
				else if (keyName.equals(this.getSetKey("color7"))) currentColor = 6;
				else if (keyName.equals(this.getSetKey("color8"))) currentColor = 7;
				else if (keyName.equals(this.getSetKey("color9"))) currentColor = 8;
				else if (keyName.equals(this.getSetKey("color10"))) currentColor = 9;
			}
		}
	}
	
	@Override
	public void keyRelease(GLWindow win, String keyName) {
		//If the key bound to toggleMouse was released, toggle the status of seeing the mouse
		this.keyCheck(keyName, "toggleMouse", !this.getKeyStatus("toggleMouse"));
		
		if (getKeyStatus("toggleMouse")) {
			win.hideMouse();
		} else {
			win.showMouse();
		}
		
		keyCheck(keyName, "toggleFly", !getKeyStatus("toggleFly"));
		keyCheck(keyName, "deltaTime", !getKeyStatus("deltaTime"));

		if (keyName.equals(this.getSetKey("mouseClick"))) {
			setKeyStatus("mouseClick", true);
		}
		if (keyName.equals(this.getSetKey("deleteReq"))) {
			setKeyStatus("deleteReq", true);
		}
		
		if (keyName.equals(getSetKey("toggleView"))) {
			viewMode++;
			if (viewMode >= 3) viewMode = 0;
		}
		
		checkGameInputs(keyName, false); //If the game mouse is released (paused), we still need to check when the keys are lifted, if any
	}

	private void checkGameInputs(String keyName, boolean status) {
		//These keys are either down or up
		this.keyCheck(keyName, "moveForward", status);
		this.keyCheck(keyName, "moveBackward", status);
		this.keyCheck(keyName, "moveLeft", status);
		this.keyCheck(keyName, "moveRight", status);
		this.keyCheck(keyName, "jump", status);
		this.keyCheck(keyName, "crouch", status);

	}
	
	@Override
	public void mouseScrollWheel(GLWindow win, double dx, double dy) { }

	@Override
	public void mouseCursorPos(GLWindow window, double xpos, double ypos, int width, int height) {
		if (isMouseLocked()) {
			mouseDelta[0] = (float) (xpos - (window.getWindowWidth() / 2));
			mouseDelta[1] = (float) (ypos - (window.getWindowHeight() / 2));
			//The extra value is the sensitivity
			mouseDelta[0] *= 12f;
			mouseDelta[1] *= 12f;
			
			window.recenterMouse();
		}
	}
	
	@Override
	public void setKeys() {
		this.setKeyCommand("moveForward", "W");
		this.setKeyCommand("moveBackward", "S");
		this.setKeyCommand("moveLeft", "A");
		this.setKeyCommand("moveRight", "D");
		this.setKeyCommand("toggleMouse", "ESC");
		this.setKeyCommand("toggleFly", "F");
		this.setKeyCommand("jump", "SPACE");
		this.setKeyCommand("crouch", "LEFT SHIFT");
		this.setKeyCommand("mouseClick", "MOUSE 1");
		this.setKeyCommand("deleteReq", "MOUSE 2");
		
		this.setKeyCommand("color1", "1");
		this.setKeyCommand("color2", "2");
		this.setKeyCommand("color3", "3");
		this.setKeyCommand("color4", "4");
		this.setKeyCommand("color5", "5");
		this.setKeyCommand("color6", "6");
		this.setKeyCommand("color7", "7");
		this.setKeyCommand("color8", "8");
		this.setKeyCommand("color9", "9");
		this.setKeyCommand("color10", "0");

		this.setKeyCommand("deltaTime", "T");
		this.setKeyCommand("toggleView", "F5");
	}
	
	private void keyCheck(String keyName, String command, boolean finalState) {
		//If keyName is equal to the key bound to the command,
		//		Set the status of that key to finalState 
		if (keyName.equals(getSetKey(command))) {
			this.setKeyStatus(command, finalState);
		}
	}
	
	public void resetCameraMode() {
		viewMode = 0;
	}
	
	public float[] getMouseDelta() { return mouseDelta; }
	public void resetMouseDelta() { mouseDelta[0] = 0; mouseDelta[1] = 0; }
	
	public boolean isMouseLocked() { return getKeyStatus("toggleMouse"); }
	public void setMouseLockMode(boolean status) { this.setKeyStatus("toggleMouse", status); }
	
	public int getCurrentColor() { return currentColor; }
	public boolean isTimeGoing() { return getKeyStatus("deltaTime"); }
	
	public int getViewMode() { return viewMode; }
	
	public boolean mouseClickRequest() { return getKeyStatus("mouseClick"); }
	public void resetMouseClick() { setKeyStatus("mouseClick", false); }
	public boolean deleteReq() { return getKeyStatus("deleteReq"); }
	public void resetDeleteReq() { setKeyStatus("deleteReq", false); }
	public boolean isFlyingToggled() {	return getKeyStatus("toggleFly"); }

	@Override
	protected void unknownCommand(SecaException se) {
		se.printFullStackTrace();
		MessagePopup.popupError(se);
		System.exit(-1);
	}
}
