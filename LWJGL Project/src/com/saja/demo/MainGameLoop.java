package com.saja.demo;

import com.saja.demo.gameEngine.entities.Cube;
import com.saja.demo.gameEngine.entities.GuiEntity;
import com.saja.demo.gameEngine.entities.Player;
import com.saja.demo.gameEngine.worldGen.WorldGenerator;
import com.saja.demo.loader.GuiTile;
import com.saja.demo.loader.OBJPNGLoader;
import com.saja.demo.logicEngine.managers.FogDayNightManager;
import com.saja.demo.logicEngine.managers.GameSpeedManager;
import com.saja.demo.logicEngine.managers.InputSetup;
import com.saja.demo.logicEngine.systems.*;
import com.saja.demo.renderers.GameEntityRenderer;
import com.saja.demo.renderers.GuiRenderer;
import com.saja.demo.renderers.TerrainRenderer;
import com.saja.seca.error.MessagePopup;
import com.saja.seca.error.SecaException;
import com.saja.seca.glfwinput.GLWindow;
import com.saja.seca.math3d.Matrix4f;
import com.saja.seca.math3d.Vector3f;
import com.saja.seca.render.GLTools;
import com.saja.seca.render.MasterRenderer;
import com.saja.seca.render.components.ViewpointComponent.CameraMode;
import com.saja.seca.render.loader.ResLoader;

import java.io.IOException;

/**
 * @Author Andy Nguyen
 * 
 * Starts and ends programs here
 * The methods are in chronological order of execution
 * 
 * My intent is to have no direct references to GLFW/LWJGL/OpenGL here.
 * SECA is supposed to manage all of that
 */

public class MainGameLoop {
	
	public static final String console_prefix = "DEMO MainGameLoop: ";
	
	private GLWindow window;
	private GameEntityRenderer entityRenderer;
	private TerrainRenderer terrainRenderer;
	private GuiRenderer guiRenderer;
	private ResLoader loader;

	private Player player;
	
	private InGameGuiSystem gameHUD;
	private PausedGuiSystem pausedHUD;
	private CubeSystem manyCubes;
	private TerrainSystem terrainsSystem;
	
	private InputSetup inputs;
	private MovementSystem moveManager;
	
	//These do not need to be cleaned up [ they do not have a cleanUp() anyways ]
	private WorldGenerator worldGen;
	private FogDayNightManager timeManager;
	private GameSpeedManager gsm;
	
	public static void main(String[] args) { //Begin execution
		System.out.println(console_prefix + "Initiating project DEMO using engine SECA");
		new MainGameLoop();
		System.out.println(console_prefix + "Finished");
		System.exit(0);
	}
	
	public MainGameLoop() { //init		
		inputs = new InputSetup(); //Sets up the command->key string map
		try {
			window = new GLWindow(1366, 768, "SECA Engine Tester", "3.3", inputs);
			// constructor GLWindow(int width, int height, String title, String version)
			//When we pass the input var to the window, callbacks are set up
		} catch (SecaException e) {
			e.printFullStackTrace();
			MessagePopup.popupError(e);
			System.exit(-1);
		}
		//window.setGLOnCurrentThread();
		window.setVSync(false);
		//window.setSwapInterval(4);//For testing per-frame, we limit the max frames
		
		MasterRenderer.setViewportSize(window.getWindowWidth(), window.getWindowHeight());
		MasterRenderer.enableDepthTest();
		MasterRenderer.enableCullFace();
		
		try {
			terrainRenderer = new TerrainRenderer();
			entityRenderer = new GameEntityRenderer();
			guiRenderer = new GuiRenderer();

			setUpResLoader();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			MessagePopup.popupError(ioe);
			System.exit(-1);
		} catch (SecaException se) {
			se.printFullStackTrace();
			MessagePopup.popupError(se);
			System.exit(-1);
		}
		
		player = new Player(new Vector3f(4f, 4f, 4f), 0, 0, 0, window.getWindowWidth(), window.getWindowHeight(), 0.1f, 50f, 90, 10f);
		moveManager = new MovementSystem(inputs);
		//	public Player(Vector3f pos, float pitch, float yaw, float roll, int width, int height, float nearPlane, float farPlane, int FOV) {
		
		manyCubes = new CubeSystem(loader);
		gameHUD = new InGameGuiSystem(loader, player.getViewpointComponent().getAspect());
		pausedHUD = new PausedGuiSystem(loader, player.getViewpointComponent().getAspect());
		worldGen = new com.saja.demo.logicEngine.TestWorld();
		worldGen = new com.saja.demo.logicEngine.FlatWorld();
		worldGen = new com.saja.demo.logicEngine.LogisticGrowth();
		
		moveManager.addEntity(player);
		
		try {
			terrainsSystem = new TerrainSystem(worldGen, terrainRenderer, loader, 32, new Vector3f(0,0,0));
			//	public TerrainSystem(WorldGenerator wg, TerrainRenderer tr, ResLoader resl, int size, Vector3f initPos) {
		} catch (SecaException se) {
			se.printFullStackTrace();
			MessagePopup.popupError(se);
			System.exit(-1);
		}
		
		timeManager = new FogDayNightManager(000);
		gsm = new GameSpeedManager(false); //Display fps in console or not	
		
		checkGLError(); //Error here means error in init, i.e. shader creation

		run();
		cleanUp();
	}
	
	public void setUpResLoader() {
		loader = new ResLoader(new OBJPNGLoader());
		
		try {
			loader.loadModel("cube", "res/obj/cube.obj", entityRenderer);
			loader.loadModel("stall", "res/obj/stall.obj", entityRenderer);
			loader.loadModel("cup", "res/obj/cup.obj", entityRenderer);
			loader.loadModel("ship", "res/obj/xwingshreck.obj", entityRenderer);
			loader.createModel(GuiEntity.model_name, new GuiTile(), guiRenderer);
		} catch (SecaException se) {
			se.printFullStackTrace();
			MessagePopup.popupError(se);
			System.exit(-1);
		}
		
		//TODO scan directory and make a list of things to be loaded in
		
		try {
			loader.loadTexture("grass", "res/img/grass.png");
			loader.loadTexture("dice", "res/img/dice.png");
			loader.loadTexture("clockface", "res/img/clockface.png");
			loader.loadTexture("clockhand", "res/img/clockhand.png");
			loader.loadTexture("crosshair", "res/img/crosshair.png");
			loader.loadTexture("fly", "res/img/fly.png");
			
			loader.loadTexture("shreked", "res/img/hell.png");
			
			loader.loadTexture("gui_info", "res/img/gui/info.png");
			loader.loadTexture("gui_play", "res/img/gui/play.png");
			loader.loadTexture("gui_question", "res/img/gui/question.png");
			loader.loadTexture("gui_reset", "res/img/gui/reset.png");
			loader.loadTexture("gui_settings", "res/img/gui/settings.png");
			loader.loadTexture("gui_exit", "res/img/gui/x.png");

		} catch (SecaException se) {
			se.printFullStackTrace();
			MessagePopup.popupError(se);
			System.exit(-1);
		}
	}
	
	public void run() { //Loop
		System.out.println(console_prefix + "Begin calculate/render loop...\n");		
		/*
		LogisticGrowth lg = (LogisticGrowth) worldGen;
		
		System.out.println(console_prefix + "Normal for point (1, 0) is " + lg.test(1, 0));
		System.out.println(console_prefix + "Normal for point (0, 1) is " + lg.test(0, 1));
		System.out.println(console_prefix + "Normal for point (-1, 0) is " + lg.test(-1, 0));
		System.out.println(console_prefix + "Normal for point (0, -1) is " + lg.test(0, -1));
		*/
		while (!window.windowCloseRequest() && !pausedHUD.isQuitRequest()) {
			calculate();
			render();
			window.updateWindowFrame();
			checkGLError(); //Error here means error in the loop, i.e. uniforming things up
		} 
	}

	public void reset() {
		player.getViewpointComponent().setPosition(new Vector3f(4f, 4f, 4f));
		player.getViewpointComponent().setPitch(0);
		player.getViewpointComponent().setYaw(0);
		player.getViewpointComponent().setRoll(0);
		player.getViewpointComponent().setCameraMode(CameraMode.FIRST);
		
		timeManager.reset();
		
		moveManager.reset();
		
		manyCubes.removeAllEntities(Cube.class);
	}
	
	public void calculate() { //Loop - calculate
		float speed = gsm.updateSpeed();
		
		moveManager.update(worldGen, manyCubes, speed); //Also updates the player
		
		try {
			terrainsSystem.guarenteeTilePrescence(player.getViewpointComponent().getPosition());
			//Make sure we are standing on something
		} catch (SecaException se) {
			se.printFullStackTrace();
			MessagePopup.popupError(se);
			System.exit(-1);
		}
		
		manyCubes.setMouseCube(player, worldGen, gameHUD);
		
		if (inputs.mouseClickRequest()) {
			if (inputs.isMouseLocked()) {
				manyCubes.requestPlace(player, loader, worldGen);
				System.out.println("DEMO MainGameLoop: Player camvec is " + player.getViewpointComponent().getCameraPosition().toString());
			} else {
				pausedHUD.checkPosition(window.getNormalizedCursorPos(), player.getViewpointComponent().getAspect());
				
				if (pausedHUD.isResumeRequest()) {
					System.out.println(console_prefix + "Resume request");
					window.hideMouse();
					inputs.setMouseLockMode(true);
					pausedHUD.resetResumeRequest();
				}
				if (pausedHUD.isResetRequest()) {
					System.out.println(console_prefix + "Reset Request");
					reset();
					window.hideMouse();
					inputs.setMouseLockMode(true);
					pausedHUD.resetResetRequest();
				}
				if (pausedHUD.isOptionsRequest()) {
					System.out.println(console_prefix + "Options request");
					MessagePopup.popupInfo("Options", "Options not yet implemented!");
					pausedHUD.resetOptionsRequest();
				}
				if (pausedHUD.isInfoRequest()) {
					System.out.println(console_prefix + "Info request");
					pausedHUD.resetInfoRequest();
					MessagePopup.popupInfo("Information & About", "SECA Engine Tester/DEMO by Andy Nguyen\nCurrently in developement, v1.0a");
				}
				if (pausedHUD.isHelpRequest()) {
					System.out.println(console_prefix + "Help request");
					MessagePopup.popupInfo("Help", 
							"Place and remove cubes on a terrain. Cubes will always be placed on the terrain.\n"
							+ "When the cursor is green, place a cube. When the cursor is red, remove the cube.\n"
									
							+ "\nKeys:" 
							+ "\nMove forward - " + inputs.getSetKey("moveForward")
							+ "\nMove backwards - " + inputs.getSetKey("moveBackward")
							+ "\nMove left - " + inputs.getSetKey("moveLeft")
							+ "\nMove right - " + inputs.getSetKey("moveRight")
							+ "\nJump (fly up) - " + inputs.getSetKey("jump")
							+ "\nCrouch (fly down) - " + inputs.getSetKey("crouch")
							
							+ "\n\nPlace cube - " + inputs.getSetKey("mouseClick")
							+ "\nRemove cube - " + inputs.getSetKey("deleteReq")
							
							+ "\n\nToggle fly mode - " + inputs.getSetKey("toggleFly")
							+ "\nToggle time - " + inputs.getSetKey("deltaTime")
							+ "\nToggle camera/view mode - " + inputs.getSetKey("toggleView")
							+ "\nPause - " + inputs.getSetKey("toggleMouse")	
					);
					pausedHUD.resetHelpRequest();
				}
				if (pausedHUD.isQuitRequest()) {
					System.out.println(console_prefix + "Quit request");
				}
			}
			inputs.resetMouseClick();
		}
		if (inputs.deleteReq()) {
			inputs.resetDeleteReq();
			manyCubes.requestDelete(player);
		}
		switch (inputs.getViewMode()) {
		case 0:
			player.getViewpointComponent().setCameraMode(CameraMode.FIRST);
			break;
		case 1:
			player.getViewpointComponent().setCameraMode(CameraMode.SECOND);
			break;
		case 2:
			player.getViewpointComponent().setCameraMode(CameraMode.THIRD);
			break;
		}
		
		if (inputs.isTimeGoing() & inputs.isMouseLocked()) timeManager.update(speed);
	}
	
	public void render() { //Loop - render
		
		MasterRenderer.prepareRendering(timeManager.getCurrentColor());

		Matrix4f viewProjM = player.getViewpointComponent().getViewProjectionMatrix();
		Vector3f playerPos = player.getViewpointComponent().getPosition();

		terrainRenderer.useRenderer();
		terrainRenderer.setLightPosition(playerPos.x, playerPos.y + 3, playerPos.z);
		terrainRenderer.setSkyColor(timeManager.getCurrentColor(), timeManager.getCurrentColor(), timeManager.getCurrentColor());
		terrainRenderer.setViewProjectionMatrix(viewProjM);
		terrainRenderer.setCameraLoc(playerPos.x, playerPos.y, playerPos.z);
		terrainRenderer.setSunLocation(0, 4, 0, 0.01f);
		terrainsSystem.render();
		terrainRenderer.unuseRenderer();
		
		entityRenderer.useRenderer();
		entityRenderer.setCameraLoc(playerPos.x, playerPos.y + 3, playerPos.z);
		entityRenderer.setLightPos(playerPos.x, playerPos.y, playerPos.z);
		entityRenderer.setEntityColor(1, 1, 1); //Default to, safety net	
		entityRenderer.setSkyColor(timeManager.getCurrentColor(), timeManager.getCurrentColor(), timeManager.getCurrentColor());
		entityRenderer.setViewProjectionMatrix(viewProjM);
		entityRenderer.setSunLocation(0, 4, 0, 0.01f);
		manyCubes.renderCubes(player.getViewpointComponent().getCameraMode() != CameraMode.FIRST);
		entityRenderer.unuseRenderer();
		
		guiRenderer.useRenderer();
		guiRenderer.setAspectRatio(player.getViewpointComponent().getAspect());
		gameHUD.draw(timeManager.getClockRotation(), inputs.isFlyingToggled());
		if (!window.isMouseHidden()) pausedHUD.draw();
		guiRenderer.unuseRenderer();
		
		MasterRenderer.finishRendering();
	}
	
	public void cleanUp() { //Cleanup
		//I don't remember what needs and doesn't need to be cleaned up
		//Just call everything, hope for the best
		System.out.println();
		System.out.println(console_prefix + "Cleaning up...");
		
		manyCubes.cleanUp();
		player.cleanUp();
		gameHUD.cleanUp();
		
		terrainRenderer.cleanUp();
		entityRenderer.cleanUp();
		guiRenderer.cleanUp();
		
		loader.cleanUp();
		inputs.cleanUp();
		moveManager.cleanUp();
		window.cleanUp();
		
		try {
			GLTools.checkGLError();
		} catch (SecaException se) {
			se.printFullStackTrace();
			MessagePopup.popupError(se);
			System.exit(-1);
		} //Error here means error in cleanup, i.e. deleting shaders and VBOs
	}
	
	public void checkGLError() {
		try {
			GLTools.checkGLError();
		} catch (SecaException se) {
			se.printFullStackTrace();
			MessagePopup.popupError(se);
			System.exit(-1);
		}
	}
}
