package com.andy.timestables;

import com.saja.seca.error.MessagePopup;
import com.saja.seca.error.SecaException;
import com.saja.seca.glfwinput.GLWindow;
import com.saja.seca.glfwinput.InputManager;
import com.saja.seca.glfwinput.KeyPressManager;

public class Inputs extends InputManager implements KeyPressManager {
	
	private boolean pauseReq = false, menuReq = false;
	
	@Override
	public void keyPress(GLWindow win, String keyName) { }

	@Override
	public void keyRepeat(GLWindow win, String keyName) { }

	@Override
	public void keyRelease(GLWindow win, String keyName) {
		if (keyName.equals(this.getSetKey("togglePause"))) {
			pauseReq = true;
		}
		if (keyName.equals(this.getSetKey("openMenu"))) {
			menuReq = true;
		}
	}

	@Override
	public void mouseScrollWheel(GLWindow win, double dx, double dy) { }

	@Override
	public void mouseCursorPos(GLWindow win, double xpos, double ypos, int windowWidth, int windowHeight) { }

	@Override
	protected void setKeys() {
		super.setKeyCommand("togglePause", "MOUSE 1");
		super.setKeyCommand("openMenu", "MOUSE 2");
	}

	@Override
	protected void unknownCommand(SecaException se) {
		se.printFullStackTrace();
		MessagePopup.popupError(se);
		System.exit(-1);
	}

	public boolean pauseRequest() { return pauseReq; }
	public boolean menuRequest() { return menuReq; }
	public void resetPauseRequest() { pauseReq = false; }
	public void resetMenuRequest() { menuReq = false; }
	
}
