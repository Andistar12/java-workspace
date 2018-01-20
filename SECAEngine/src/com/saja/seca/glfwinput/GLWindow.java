package com.saja.seca.glfwinput;

import com.saja.seca.error.SecaException;
import com.saja.seca.math3d.Vector2f;
import org.lwjgl.Version;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * @author Andy Nguyen
 * 
 * A Window created using GLFW. "https://www.lwjgl.org/guide" is where much of this code originates
 * Note - the window is not resizable (yet)
 * 
 * 
 * Methods:
 * constructor GLWindow(int width, int height, String title, String version, KeyPressManager kdm)
 * void setGLOnCurrentThread()
 * long getWindowHandle()
 * int getWindowWidth()
 * int getWindowHeight()
 * float getAspectRatio()
 * 
 * void setVSync(boolean vsyncOn) 
 * void toggleVSync() 
 * boolean isVSyncOn() 
 * DEPRECATED void setSwapInterval(int x) - should not be used except when testing
 * 
 * boolean isMouseHidden()
 * void hideMouse() 
 * void showMouse()
 * void toggleMouse()
 * void recenterMouse()
 * Vector2f getNormalizedCursorPos()
 * 
 * boolean windowCloseRequest() 
 * void swapBuffers()
 * void pollEvents()
 * void updateWindowFrame()
 * 
 * String getClipboard()
 * boolean isLeftMouseDown()
 * boolean isRightMouseDown()
 * int getKeyStatus(String key)
 * boolean isKeyDown(String key)
 * 
 * void cleanUp(); has to be called once finished
 */

//TODO cleanup, fix callbacks
public class GLWindow {
	
	public static final String console_prefix = "SECA GLWindow: ";
	
	public static final int DEFAULT_GLVERSION_MAJOR = 3, DEFAULT_GLVERSION_MINOR = 3; //Default OpenGL to 3.3
	
	private long windowHandle;
	
	private int width, height;
	private boolean mouseHidden = false;
	private boolean vsyncOn = true; //Default true
	
	private int mouseXPos, mouseYPos;
	
	private GLFWErrorCallback errorCallback;
	private GLFWScrollCallback mouseScrollCallback;
	private GLFWMouseButtonCallback mouseClickCallback;
	private GLFWCursorPosCallback mousePosCallback;
	private GLFWKeyCallback keyCallback;
	private KeyLibrary keys;
	
	private final GLWindow thisInstance;
	
	public GLWindow(int width, int height, String title, String version, final KeyPressManager kdm) throws SecaException {
		this.width = width;
		this.height = height;
		
		System.out.print(console_prefix + "Creating GLFW window... ");
		
		//This calculates the major and minor versions specified by the version string
		//It splits at anything that is not a digit
		String[] nums = version.split("\\D");
		int major = DEFAULT_GLVERSION_MAJOR, minor = DEFAULT_GLVERSION_MINOR;
		try {
			major = Integer.parseInt(nums[0]);
			minor = Integer.parseInt(nums[1]);
		} catch (NumberFormatException nfe) {} //Stick with the default, swallow error if neither worked
		
		
		GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

		if (GLFW.glfwInit() != GLFW.GLFW_TRUE) throw new SecaException(this.getClass(), "Unable to initialize GLFW");

		GLFW.glfwDefaultWindowHints(); // optional, the current window hints are already the default
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // the window will stay hidden after creation
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE); // the window will NOT be resizable
		GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4); // 4x antialiasing
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, major); //Set major and minor GL version
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, minor);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE); // To make MacOS happy
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE); // We don't want the old OpenGL

		windowHandle = GLFW.glfwCreateWindow(width, height, title, NULL, NULL);
		if (windowHandle == NULL) throw new SecaException(this.getClass(), "Failed to create a GLFW window");
		
		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		// Center our window on screen
		GLFW.glfwSetWindowPos(windowHandle, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

		// Make the GLFW.glfw & OpenGL context current
		GLFW.glfwMakeContextCurrent(windowHandle);

		setVSync(vsyncOn);

		// Make the window visible
		GLFW.glfwShowWindow(windowHandle);
		keys = new KeyLibrary(windowHandle);
		
		//Send input data to the input receiver
		thisInstance = this; //As long as we don't create a new GLWindow() here, we're good to go
		GLFW.glfwSetKeyCallback(windowHandle, this.keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long winHandle, int key, int scancode, int action, int mods) {
				switch(action) {
				case GLFW.GLFW_PRESS:
					kdm.keyPress(thisInstance, keys.getKeyString(key));
					break;
				case GLFW.GLFW_REPEAT:
					kdm.keyRepeat(thisInstance, keys.getKeyString(key));
					break;
				case GLFW.GLFW_RELEASE:
					kdm.keyRelease(thisInstance, keys.getKeyString(key));
					break;
				}
			}
		});
		GLFW.glfwSetMouseButtonCallback(windowHandle, this.mouseClickCallback = new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				switch(action) {
				case GLFW.GLFW_PRESS:
					kdm.keyPress(thisInstance, keys.getKeyString(button));
					break;
				case GLFW.GLFW_REPEAT:
					kdm.keyRepeat(thisInstance, keys.getKeyString(button));
					break;
				case GLFW.GLFW_RELEASE:
					kdm.keyRelease(thisInstance, keys.getKeyString(button));
					break;
				}
			}
		});
		GLFW.glfwSetScrollCallback(windowHandle, this.mouseScrollCallback = new GLFWScrollCallback() {
			@Override
			public void invoke(long window, double xoffset, double yoffset) {
				kdm.mouseScrollWheel(thisInstance, xoffset, yoffset);
			}
		});
		GLFW.glfwSetCursorPosCallback(windowHandle, this.mousePosCallback = new GLFWCursorPosCallback() {
			@Override
			public void invoke(long window, double xpos, double ypos) {
				mouseXPos = (int) xpos;
				mouseYPos = (int) ypos;
				kdm.mouseCursorPos(thisInstance, xpos, ypos, getWindowWidth(), getWindowHeight());
			}
		});
		GL.createCapabilities();

		System.out.println("initiated"
				+ " with OpenGL version " + major + "." + minor
				+ ", LWJGL 3 (Build " + Version.getVersion() + ")");
	}
	public void setGLOnCurrentThread() {
		// This line is critical for LWJGL's inter-operation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
	}
	public long getWindowHandle() { return windowHandle; }
	public int getWindowWidth() { return width; }
	public int getWindowHeight() { return height; }
	public float getAspectRatio() { return width / height; }
	
	public void setVSync(boolean setVsyncOn) {
		//V-sync - 0 is unlimited, any other number: FPS = monitorRefreshRate / number
		vsyncOn = setVsyncOn;
		if (isVSyncOn()) {
			GLFW.glfwSwapInterval(1);
		} else {
			GLFW.glfwSwapInterval(0);
		}
	}
	public void toggleVSync() {	setVSync(!isVSyncOn()); }
	public boolean isVSyncOn() { return vsyncOn; }
	@Deprecated
	public void setSwapInterval(int x) { GLFW.glfwSwapInterval(x); }
	
	public boolean isMouseHidden() { return mouseHidden; }
	public void hideMouse() {
		recenterMouse();
		GLFW.glfwSetInputMode(windowHandle, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
		mouseHidden = true;
	}
	public void showMouse() {
		GLFW.glfwSetInputMode(windowHandle, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
		mouseHidden = false;
	}
	public void toggleMouse() {
		if (isMouseHidden()) showMouse();
		else hideMouse();
	}
	public void recenterMouse() {
		GLFW.glfwSetCursorPos(windowHandle, getWindowWidth() / 2, getWindowHeight() / 2);		
		mouseXPos = getWindowWidth() / 2;
		mouseYPos = getWindowHeight() / 2;
	}
	public Vector2f getCursorPos() {
		return new Vector2f(mouseXPos, mouseYPos);
	}
	public Vector2f getNormalizedCursorPos() {
		//For OpenGL use
		Vector2f pos = getCursorPos();
		//GLFW windows are defined with upper-left as (0,0)
		float x = (2.0f * pos.x) / getWindowWidth() - 1f;
		float y = (-2.0f * pos.y) / getWindowHeight() + 1f;
		return new Vector2f(x, y);
	}
	
	public boolean windowCloseRequest() {
		return GLFW.glfwWindowShouldClose(windowHandle) == GLFW.GLFW_TRUE;
	}
	public void swapBuffers() {
		GLFW.glfwSwapBuffers(windowHandle); //Swap the color buffers
	}
	public void pollEvents() {
		GLFW.glfwPollEvents(); //Poll for user input
	}
	public void updateWindowFrame() {
		swapBuffers();
		pollEvents();
	}
	
	public String getClipboard() {
		String clipBoard = GLFW.glfwGetClipboardString(windowHandle);
		if (clipBoard == null) return "";
		return clipBoard;
	}
	public boolean isLeftMouseDown() {
		return (GLFW.glfwGetMouseButton(windowHandle, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_TRUE);
	}
	public boolean isRightMouseDown() {
		return (GLFW.glfwGetMouseButton(windowHandle, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_TRUE);
	}
	
	public int getKeyStatus(String keyName) {
		return keys.getKeyStatus(keyName);
	}
	public boolean isKeyDown(String keyName) {
		return keys.isKeyDown(keyName);
	}
	
	public void cleanUp() {
		//GLFW.glfwHideWindow(windowHandle);
		keys.cleanUp();
		
		GLFW.glfwDestroyWindow(windowHandle);
		//GLFW.glfwTerminate(); //This destroys ALL GLFW windows
		
		//If the user didn't set a callback, swallow any null pointers
		try {
			errorCallback.release();
		} catch (NullPointerException npe) {}
		try {
			mouseScrollCallback.release();
		} catch (NullPointerException npe) {}
		try {
			mouseClickCallback.release();
		} catch (NullPointerException npe) {}
		try {
			mousePosCallback.release();
		} catch (NullPointerException npe) {}
		try {
			keyCallback.release();
		} catch (NullPointerException npe) {}
	}
	
	@Override
	public String toString() {
		return "SECA GLWindow (handle " + getWindowHandle() + ") on LWJGL 3 (Build " + Version.getVersion() + ")";
	}
}
