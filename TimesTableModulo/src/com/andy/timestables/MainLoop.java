package com.andy.timestables;

import com.andy.timestables.loader.OBJPNGLoader;
import com.andy.timestables.loader.Settings;
import com.saja.seca.error.MessagePopup;
import com.saja.seca.error.SecaException;
import com.saja.seca.glfwinput.GLWindow;
import com.saja.seca.render.GLTools;
import com.saja.seca.render.MasterRenderer;
import com.saja.seca.render.loader.ResLoader;

import java.awt.*;

/**
 * @author Andy Nguyen
 * 
 * We write this in OpenGL to easily port to Android and mobile.
 * It's actually a little overkill for this project :P
 *
 */

public class MainLoop {
	
	public static final String console_prefix = "TimesTables MainLoop: ";
	public static final int screen_size = 500;
	public static final int point_count = 500;
	
	GLWindow myWindow;
	Inputs myInputs;
	ResLoader resLoader;
	LineRenderer lineR;
	
	ColorIterator colors;
	GameSpeedManager gsm;
	
	LineSystem ls;
	Settings settings;
	
	public static void main(String args[]) {
		System.out.println(console_prefix + "Initiating");
		MainLoop ml = new MainLoop();
		ml.loop();
		ml.cleanUp();
		System.out.println(console_prefix + "Finished");
		System.exit(0);
	}
	
	public MainLoop() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					settings = new Settings(point_count);
					settings.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		myInputs = new Inputs();
		try {
			myWindow = new com.saja.seca.glfwinput.GLWindow(screen_size, screen_size, "Times Tables Modulo", "3.3", myInputs);
			myWindow.setVSync(false);
		} catch (SecaException e) {
			e.printFullStackTrace();
			MessagePopup.popupError(e);
			System.exit(-1);
		}
		MasterRenderer.setViewportSize(myWindow.getWindowWidth(), myWindow.getWindowHeight());
		checkGLErrors();

		try {
			lineR = new LineRenderer();
			resLoader = new ResLoader(new OBJPNGLoader());
			ls = new LineSystem(resLoader, lineR, point_count, 6f);
		} catch (SecaException se) {
			se.printFullStackTrace();
			MessagePopup.popupError(se);
			System.exit(-1);
		}
		
		gsm = new GameSpeedManager();
		colors = new ColorIterator();
		
		checkGLErrors();
	}
	
	public void loop() {
		while (!myWindow.windowCloseRequest() && !settings.quitFlag()) {
			calculate();
			render();
			myWindow.updateWindowFrame();
			checkGLErrors();
		}
	}
	
	public void calculate() {
		if (settings != null) {
			float speed = gsm.updateSpeed();
			
			if (settings.resetFlag()) {
				settings.resetResetFlag();
				ls.resetCurrentMultiplier();;
			}
			
			colors.updateColor(settings.getColorSpeed() * speed);
			ls.update(settings.getMultiplier() * speed);
			
			if (myInputs.menuRequest()) {
				settings.setVisible(true);
				myInputs.resetMenuRequest();
			}
			if (myInputs.pauseRequest()) {
				settings.pause();
				myInputs.resetPauseRequest();
				settings.setVisible(true);
			}
			
			settings.update(ls.getCurrentMultiplier(), gsm.getFPS());
		}
	}
	
	public void render() {
		if (settings.blackBG()) {
			MasterRenderer.prepareRendering(0,0,0);
		} else {
			MasterRenderer.prepareRendering(1,1,1);
		}
		
		lineR.useRenderer();
		lineR.setColor(colors.getCurrentColor());
		ls.render();
		lineR.unuseRenderer();
		
		MasterRenderer.finishRendering();
	}
	
	public void cleanUp() {
		myWindow.cleanUp();
		myInputs.cleanUp();
		resLoader.cleanUp();
		lineR.cleanUp();
		ls.cleanUp();

		settings.dispose();
		
		checkGLErrors();
	}
	
	public void checkGLErrors() {
		try {
			GLTools.checkGLError();
		} catch (SecaException se) {
			se.printFullStackTrace();
			MessagePopup.popupError(se);
			System.exit(-1);
		}
	}
	
}
