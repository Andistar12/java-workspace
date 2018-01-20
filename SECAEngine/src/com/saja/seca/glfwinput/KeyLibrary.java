package com.saja.seca.glfwinput;

import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author Andy Nguyen
 *
 * KeyLibrary maps all GLFW keys to a string (can't really do chars because of "F11", "ENTER", and etc)
 * Note that the constructor and cleanUp() is protected; all GLWindows create/manage one KeyLibrary by default
 * Note that mouse buttons are registered as keys
 * 
 * http://www.glfw.org/docs/latest/group__keys.html reference when making
 * 
 * cleanUp() is called by GlWindow
 */

//Keys left out - left super, keypad =, last/menu (GLFW_KEY_LAST, GLFW_KEY_MENU)
//Keys left out - right-click (computer button), unknown/int'l


public class KeyLibrary {
	private HashMap<String, Integer> keyMap;
		
	private long windowHandle;
	
	public int getKeyStatus(String key) {
		key = key.toUpperCase();
		if (!keyExists(key)) {
			//System.out.println("SECA KeyLibrary: Warning - Key \'" + key + "\' does not exist");
			return GLFW.GLFW_RELEASE;
		}
		return GLFW.glfwGetKey(windowHandle, getKeyNumber(key));
	}
	
	public boolean isKeyDown(String key) {
		key = key.toUpperCase();
		if (!keyExists(key)) {
			//System.out.println("SECA KeyLibrary: Warning - Key \'" + key + "\' does not exist");
			return false;
		}
		
		if (key.contains("MOUSE")) 	return (GLFW.glfwGetMouseButton(windowHandle, getKeyNumber(key)) == GLFW.GLFW_TRUE);
		
		//If the current state of the key is releasing the key, return false, else return true
		return (getKeyStatus(key) == GLFW.GLFW_RELEASE) ? false : true;
	}
	
	public boolean keyExists(String key) {
		return keyMap.containsKey(key.toUpperCase());
	}
	
	public int getKeyNumber(String key) {
		return keyMap.get(key.toUpperCase());
	}
	public String getKeyString(int glfwKey) {
		for (Entry<String, Integer> e : keyMap.entrySet()) {
			if (e.getValue().equals(glfwKey)) return e.getKey();
		}
		return "";
	}
	
	protected KeyLibrary(long window) {
		this.windowHandle = window;
		keyMap = new HashMap<String, Integer>();
		
		keyMap.put("0", GLFW.GLFW_KEY_0);
		keyMap.put("1", GLFW.GLFW_KEY_1);
		keyMap.put("2", GLFW.GLFW_KEY_2);
		keyMap.put("3", GLFW.GLFW_KEY_3);
		keyMap.put("4", GLFW.GLFW_KEY_4);
		keyMap.put("5", GLFW.GLFW_KEY_5);
		keyMap.put("6", GLFW.GLFW_KEY_6);
		keyMap.put("7", GLFW.GLFW_KEY_7);
		keyMap.put("8", GLFW.GLFW_KEY_8);
		keyMap.put("9", GLFW.GLFW_KEY_9);

		keyMap.put("A", GLFW.GLFW_KEY_A);
		keyMap.put("B", GLFW.GLFW_KEY_B);
		keyMap.put("C", GLFW.GLFW_KEY_C);
		keyMap.put("D", GLFW.GLFW_KEY_D);
		keyMap.put("E", GLFW.GLFW_KEY_E);
		keyMap.put("F", GLFW.GLFW_KEY_F);
		keyMap.put("G", GLFW.GLFW_KEY_G);
		keyMap.put("H", GLFW.GLFW_KEY_H);
		keyMap.put("I", GLFW.GLFW_KEY_I);
		keyMap.put("J", GLFW.GLFW_KEY_J);
		keyMap.put("K", GLFW.GLFW_KEY_K);
		keyMap.put("L", GLFW.GLFW_KEY_L);
		keyMap.put("M", GLFW.GLFW_KEY_M);
		keyMap.put("N", GLFW.GLFW_KEY_N);
		keyMap.put("O", GLFW.GLFW_KEY_O);
		keyMap.put("P", GLFW.GLFW_KEY_P);
		keyMap.put("Q", GLFW.GLFW_KEY_Q);
		keyMap.put("R", GLFW.GLFW_KEY_R);
		keyMap.put("S", GLFW.GLFW_KEY_S);
		keyMap.put("T", GLFW.GLFW_KEY_T);
		keyMap.put("U", GLFW.GLFW_KEY_U);
		keyMap.put("V", GLFW.GLFW_KEY_V);
		keyMap.put("W", GLFW.GLFW_KEY_W);
		keyMap.put("X", GLFW.GLFW_KEY_X);
		keyMap.put("Y", GLFW.GLFW_KEY_Y);
		keyMap.put("Z", GLFW.GLFW_KEY_Z);

		keyMap.put("F1", GLFW.GLFW_KEY_F1);
		keyMap.put("F2", GLFW.GLFW_KEY_F2);
		keyMap.put("F3", GLFW.GLFW_KEY_F3);
		keyMap.put("F4", GLFW.GLFW_KEY_F4);
		keyMap.put("F5", GLFW.GLFW_KEY_F5);
		keyMap.put("F6", GLFW.GLFW_KEY_F6);
		keyMap.put("F7", GLFW.GLFW_KEY_F7);
		keyMap.put("F8", GLFW.GLFW_KEY_F8);
		keyMap.put("F9", GLFW.GLFW_KEY_F9);
		keyMap.put("F10", GLFW.GLFW_KEY_F10);
		keyMap.put("F11", GLFW.GLFW_KEY_F11);
		keyMap.put("F12", GLFW.GLFW_KEY_F12);
		keyMap.put("F13", GLFW.GLFW_KEY_F13);//Do we really need F13+?
		keyMap.put("F14", GLFW.GLFW_KEY_F14);
		keyMap.put("F15", GLFW.GLFW_KEY_F15);
		keyMap.put("F16", GLFW.GLFW_KEY_F16);
		keyMap.put("F17", GLFW.GLFW_KEY_F17);
		keyMap.put("F18", GLFW.GLFW_KEY_F18);
		keyMap.put("F19", GLFW.GLFW_KEY_F19);
		keyMap.put("F20", GLFW.GLFW_KEY_F20);
		keyMap.put("F21", GLFW.GLFW_KEY_F21);
		keyMap.put("F22", GLFW.GLFW_KEY_F22);
		keyMap.put("F23", GLFW.GLFW_KEY_F23);
		keyMap.put("F24", GLFW.GLFW_KEY_F24);
		keyMap.put("F25", GLFW.GLFW_KEY_F25);

		keyMap.put("'", GLFW.GLFW_KEY_APOSTROPHE);
		keyMap.put(",", GLFW.GLFW_KEY_COMMA);
		keyMap.put("-", GLFW.GLFW_KEY_MINUS);
		keyMap.put(".", GLFW.GLFW_KEY_PERIOD);
		keyMap.put("/", GLFW.GLFW_KEY_SLASH);
		keyMap.put(";", GLFW.GLFW_KEY_SEMICOLON);
		keyMap.put("=", GLFW.GLFW_KEY_EQUAL);
		keyMap.put("[", GLFW.GLFW_KEY_LEFT_BRACKET);
		keyMap.put("]", GLFW.GLFW_KEY_RIGHT_BRACKET);
		keyMap.put("\\", GLFW.GLFW_KEY_BACKSLASH); //Maybe remove this one?
		keyMap.put("`", GLFW.GLFW_KEY_GRAVE_ACCENT);
		keyMap.put("LEFT CTRL", GLFW.GLFW_KEY_LEFT_CONTROL);
		keyMap.put("RIGHT CTRL", GLFW.GLFW_KEY_RIGHT_CONTROL);
		keyMap.put("LEFT ALT", GLFW.GLFW_KEY_LEFT_ALT);
		keyMap.put("RIGHT ALT", GLFW.GLFW_KEY_RIGHT_ALT);

		keyMap.put("SPACE", GLFW.GLFW_KEY_SPACE);
		keyMap.put("ESC", GLFW.GLFW_KEY_ESCAPE);
		keyMap.put("ENTER", GLFW.GLFW_KEY_ENTER);
		keyMap.put("LEFT SHIFT", GLFW.GLFW_KEY_LEFT_SHIFT);
		keyMap.put("RIGHT SHIFT", GLFW.GLFW_KEY_RIGHT_SHIFT);
		keyMap.put("TAB", GLFW.GLFW_KEY_TAB);
		keyMap.put("BACKSPACE", GLFW.GLFW_KEY_BACKSPACE);
		keyMap.put("CAPS LOCK", GLFW.GLFW_KEY_CAPS_LOCK);

		keyMap.put("ARROW RIGHT", GLFW.GLFW_KEY_RIGHT);
		keyMap.put("ARROW LEFT", GLFW.GLFW_KEY_LEFT);
		keyMap.put("ARROW UP", GLFW.GLFW_KEY_UP);
		keyMap.put("ARROW DOWN", GLFW.GLFW_KEY_DOWN);

		keyMap.put("INS", GLFW.GLFW_KEY_INSERT);
		keyMap.put("DEL", GLFW.GLFW_KEY_DELETE);
		keyMap.put("HOME", GLFW.GLFW_KEY_HOME);
		keyMap.put("END", GLFW.GLFW_KEY_END);
		keyMap.put("PG UP", GLFW.GLFW_KEY_PAGE_UP);
		keyMap.put("PG DOWN", GLFW.GLFW_KEY_PAGE_DOWN);

		keyMap.put("SCRL LOCK", GLFW.GLFW_KEY_SCROLL_LOCK);
		keyMap.put("NUM LOCK", GLFW.GLFW_KEY_NUM_LOCK);
		keyMap.put("PRT SCN", GLFW.GLFW_KEY_PRINT_SCREEN);
		keyMap.put("PAUSE", GLFW.GLFW_KEY_PAUSE);

		keyMap.put("KP 0", GLFW.GLFW_KEY_KP_0);
		keyMap.put("KP 1", GLFW.GLFW_KEY_KP_1);
		keyMap.put("KP 2", GLFW.GLFW_KEY_KP_2);
		keyMap.put("KP 3", GLFW.GLFW_KEY_KP_3);
		keyMap.put("KP 4", GLFW.GLFW_KEY_KP_4);
		keyMap.put("KP 5", GLFW.GLFW_KEY_KP_5);
		keyMap.put("KP 6", GLFW.GLFW_KEY_KP_6);
		keyMap.put("KP 7", GLFW.GLFW_KEY_KP_7);
		keyMap.put("KP 8", GLFW.GLFW_KEY_KP_8);

		keyMap.put("KP .", GLFW.GLFW_KEY_KP_DECIMAL);
		keyMap.put("KP +", GLFW.GLFW_KEY_KP_ADD);
		keyMap.put("KP -", GLFW.GLFW_KEY_KP_SUBTRACT);
		keyMap.put("KP *", GLFW.GLFW_KEY_KP_MULTIPLY);
		keyMap.put("KP /", GLFW.GLFW_KEY_KP_DIVIDE);
		keyMap.put("KP ENTER", GLFW.GLFW_KEY_KP_ENTER);

		keyMap.put("MOUSE 1", GLFW.GLFW_MOUSE_BUTTON_1); //Left
		keyMap.put("MOUSE 2", GLFW.GLFW_MOUSE_BUTTON_2); //Right
		keyMap.put("MOUSE 3", GLFW.GLFW_MOUSE_BUTTON_3); //Scroll wheel click
		keyMap.put("MOUSE 4", GLFW.GLFW_MOUSE_BUTTON_4);
		keyMap.put("MOUSE 5", GLFW.GLFW_MOUSE_BUTTON_5);
		keyMap.put("MOUSE 6", GLFW.GLFW_MOUSE_BUTTON_6);
		keyMap.put("MOUSE 7", GLFW.GLFW_MOUSE_BUTTON_7);
		keyMap.put("MOUSE 8", GLFW.GLFW_MOUSE_BUTTON_8);

		//Keys left out - left super, keypad =, last/menu (GLFW_KEY_LAST, GLFW_KEY_MENU)
		//Keys left out - right-click (computer button), unknown/int'l
	}
	
	protected void cleanUp() {
		keyMap.clear();
	}
	
	@Override
	public String toString() {
		return "SECA KeyLibrary with " + keyMap.size() + " keys";
	}
}
