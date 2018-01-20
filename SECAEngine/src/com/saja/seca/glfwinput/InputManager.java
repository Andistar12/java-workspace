package com.saja.seca.glfwinput;

import com.saja.seca.error.SecaException;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author Andy Nguyen
 *
 * The InputManager class facilitates mapping keys to strings, treated as commands. 
 * It comes in handy especially when customizing keys for game controls, i.e. rebinding keys
 * Extending classes have to implement the following methods:
 * void setKeys() - add entries to the hashmap
 * void unknownCommand(SecaException se) - called when getting commands results in a NullPointerException
 * KeyPressManager - see interface explanation
 * 
 * For example, in an extending class, we could add in setKey() an entry to map "W" to "moveForward"
 * Then elsewhere, all we have to do is check isKeyDown("moveForward")
 * Because it's a HashMap, we can change the W to something else (for example E) via setKeyCommand("moveForward", "E");
 * If an unknown command is caught, we could either crash the program (dev purposes)
 * 		Or we could also just ignore the command completely (don't do anything)
 * 
 * InputManager checks/manages the current status of keys
 * KeyPressManager is called when the status of a key is changed
 * We keep these two separate to separate setting up keys vs capturing input
 * 
 * In InputManager and subclasses, 'key' references a keyboard key, not a hashmap key (that would be a command)
 * 
 * Note that all methods except cleanUp() and getters are protected; subclasses have complete control over the InputManager methods
 * Subclasses of InputManager should not need to clean up anything else, but if needed, it can be overriden
 */

public abstract class InputManager {
	
	private HashMap<String, KeyAndStatus> keyCommandMap = new HashMap<String, KeyAndStatus>();
	
	protected InputManager() {
		setKeys();
	}
	
	protected abstract void setKeys(); //Subclasses have to address this
	protected abstract void unknownCommand(SecaException se); //Subclasses have to address this
	
	protected void setKeyCommand(String command, String key) {
		keyCommandMap.put(command, new KeyAndStatus(key));
	}
	protected void removeKeyByCommand(String command) {
		keyCommandMap.remove(command);
	}
	
	protected void setKeyStatus(String command, boolean status) {
		try {
			keyCommandMap.get(command).setStatus(status);
		} catch (NullPointerException npe) {
			unknownCommand(new SecaException(InputManager.class, "Command \'" + command + "\' cannot be found"));
		}
	}
	public boolean getKeyStatus(String command) {
		//This method should be accessed not only by the extending class,
		//But anywhere that it is required
		try {
			return keyCommandMap.get(command).getStatus();
		} catch (NullPointerException npe){
			unknownCommand(new SecaException(InputManager.class, "Command \'" + command + "\' cannot be found"));
			return false;
		}
	}
	
	public String getSetKey(String command) {
		try {
			return keyCommandMap.get(command).getKey();
		} catch (NullPointerException npe) {
			unknownCommand(new SecaException(InputManager.class, "Command \'" + command + "\' cannot be found"));
			return "";
		}
	}
	public String getSetCommand(String key) {
		for (Entry<String, KeyAndStatus> e : keyCommandMap.entrySet()) {
			if (e.getValue().getKey().equals(key)) return e.getKey();
		}
		return "";
	}
	
	public void cleanUp() {
		if (keyCommandMap != null) {
			//We may accidentally call this twice
			keyCommandMap.clear();
			keyCommandMap = null;
		}
	}
	
	@Override
	public String toString() {
		return "SECA InputManager with " + keyCommandMap.size() + " command-key mappings";
	}
}

class KeyAndStatus {
	
	private String key;
	private boolean status = false;
	
	protected KeyAndStatus(String keyName) {
		this.key = keyName;
	}
	
	protected void setStatus(boolean newStatus) { status = newStatus; }
	protected boolean getStatus() { return status; }
	protected String getKey() { return key; }
}
