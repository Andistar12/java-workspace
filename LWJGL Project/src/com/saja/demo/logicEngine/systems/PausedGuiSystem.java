package com.saja.demo.logicEngine.systems;

import com.saja.demo.gameEngine.entities.GuiButton;
import com.saja.demo.gameEngine.entities.GuiEntity;
import com.saja.seca.back.Entity;
import com.saja.seca.math3d.Vector2f;
import com.saja.seca.math3d.Vector3f;
import com.saja.seca.render.components.PositionComponent;
import com.saja.seca.render.loader.ResLoader;

import java.util.Arrays;

/**
 * @author Andy Nguyen
 *
 * A collection of GuiButtons, this class is the HUD for the pause menu
 */

public class PausedGuiSystem extends com.saja.seca.back.System {

	public boolean isResumeRequest() { return resumeRequest; }
	public void resetResumeRequest() { resumeRequest = false; }
	public boolean isResetRequest() { return resetRequest; }
	public void resetResetRequest() { resetRequest = false; }
	public boolean isOptionsRequest() { return optionsRequest; }
	public void resetOptionsRequest() { optionsRequest = false; }
	public boolean isInfoRequest() { return infoRequest; }
	public void resetInfoRequest() { infoRequest = false; }
	public boolean isHelpRequest() { return helpRequest; }
	public void resetHelpRequest() { helpRequest = false; }
	public boolean isQuitRequest() { return quitRequest; }
	public void resetQuitRequest() { quitRequest = false; }


	private boolean resumeRequest = false, resetRequest = false, optionsRequest = false,
			infoRequest = false, helpRequest = false, quitRequest = false;
	
	public PausedGuiSystem(ResLoader r, float screenAspectRatio) {
		super();
		
		//Temp
		GuiButton gb;
		PositionComponent pc;
		
		//Resume
		gb = new GuiButton(r, "gui_play") {
			@Override
			public void buttonAction() {
				resumeRequest = true;
			}
		};
		pc = (PositionComponent) gb.getFirstComponentInstance(PositionComponent.class);
		pc.setPosition(new Vector3f(-0.5f, 0.25f,0));
		pc.setScale(0.25f, 0.25f, 0);
		this.addEntity(gb);
		
		//Reset
		gb = new GuiButton(r, "gui_reset") {
			@Override
			public void buttonAction() {
				resetRequest = true;
			}
		};
		pc = (PositionComponent) gb.getFirstComponentInstance(PositionComponent.class);
		pc.setPosition(new Vector3f(0f, 0.25f,0));
		pc.setScale(0.25f, 0.25f, 0);
		this.addEntity(gb);
		
		//Options
		gb = new GuiButton(r, "gui_settings") {
			@Override
			public void buttonAction() {
				optionsRequest = true;
			}
		};
		pc = (PositionComponent) gb.getFirstComponentInstance(PositionComponent.class);
		pc.setPosition(new Vector3f(0.5f, 0.25f,0));
		pc.setScale(0.25f, 0.25f, 0);
		this.addEntity(gb);
		
		//Info
		gb = new GuiButton(r, "gui_info") {
			@Override
			public void buttonAction() {
				infoRequest = true;
			}
		};
		pc = (PositionComponent) gb.getFirstComponentInstance(PositionComponent.class);
		pc.setPosition(new Vector3f(-0.5f, -0.25f,0));
		pc.setScale(0.25f, 0.25f, 0);
		this.addEntity(gb);
		
		//Help
		gb = new GuiButton(r, "gui_question") {
			@Override
			public void buttonAction() {
				helpRequest = true;
			}
		};
		pc = (PositionComponent) gb.getFirstComponentInstance(PositionComponent.class);
		pc.setPosition(new Vector3f(0f, -0.25f,0));
		pc.setScale(0.25f, 0.25f, 0);
		this.addEntity(gb);
		
		//Quit
		gb = new GuiButton(r, "gui_exit") {
			@Override
			public void buttonAction() {
				quitRequest = true;
			}
		};
		pc = (PositionComponent) gb.getFirstComponentInstance(PositionComponent.class);
		pc.setPosition(new Vector3f(0.5f, -0.25f,0));
		pc.setScale(0.25f, 0.25f, 0);
		this.addEntity(gb);
	}
	
	@Override
	public boolean isEntityAddable(Entity e) {
		if (GuiButton.class.isInstance(e)) return true;
		return false;
	}
	
	public void checkPosition(Vector2f normalizedMouseLoc, float aspectRatio) {
		GuiButton[] buttons = getButtons();
		
		for (GuiButton gb : buttons) {
			if (gb.checkInput(normalizedMouseLoc, aspectRatio)) {
				gb.click();
			};
		}
	}

	private GuiButton[] getButtons() {
		Entity[] e = getAllEntities(GuiEntity.class);
		GuiButton[] guis = Arrays.copyOf(e, e.length, GuiButton[].class);
		return guis;
	}
	
	public void draw() {		
		GuiButton[] guis = getButtons();
		for (GuiButton gb : guis) {
			gb.draw();
		}
	}
	
	
	@Override
	protected void systemCleanUp() { }

}
