package com.saja.demo.logicEngine.systems;

import com.saja.demo.gameEngine.entities.GuiEntity;
import com.saja.seca.back.Entity;
import com.saja.seca.back.System;
import com.saja.seca.math3d.Vector3f;
import com.saja.seca.render.components.PositionComponent;
import com.saja.seca.render.loader.ResLoader;

import java.util.Arrays;

/**
 * @author Andy Nguyen
 *
 * The GUI that shows up in game.
 * We can roll it all up into one class pretty efficiently :)
 */

public class InGameGuiSystem extends System {

	private GuiEntity clockHand;
	private GuiEntity flyIcon;
	private GuiEntity crossHair;
	
	public InGameGuiSystem(ResLoader r, float screenAspectRatio) {
		//Temporary var
		PositionComponent pc;
		
		//Crosshair
		crossHair = new GuiEntity(r, "crosshair");
		pc = (PositionComponent) crossHair.getFirstComponentInstance(PositionComponent.class);
		pc.setScale(0.075f, 0.075f, 0);
		pc.setPosition(new Vector3f(0,0,0));
		this.addEntity(crossHair);
		
		//Clock - face
		GuiEntity clockFace = new GuiEntity(r, "clockface");
		pc = (PositionComponent) clockFace.getFirstComponentInstance(PositionComponent.class);
		pc.setScale(0.3f, 0.3f, 0);
		pc.setPosition(new Vector3f(1.0f - 0.3f / 2f, 1.0f / screenAspectRatio - 0.3f / 2f, 0));
		this.addEntity(clockFace);
		
		//Clock - hand
		clockHand = new GuiEntity(r, "clockhand");
		pc = (PositionComponent) clockHand.getFirstComponentInstance(PositionComponent.class);
		pc.setScale(0.3f, 0.3f, 0);
		pc.setPosition(new Vector3f(1.0f - 0.3f / 2f, 1.0f / screenAspectRatio - 0.3f / 2f, 0));
		this.addEntity(clockHand);
		
		//Fly
		flyIcon = new GuiEntity(r, "fly");
		pc = (PositionComponent) flyIcon.getFirstComponentInstance(PositionComponent.class);
		pc.setScale(0.15f, 0.15f, 0);	
		pc.setPosition(new Vector3f(0f, 1.0f / screenAspectRatio - 0.15f / 2f, 0f));
	}
	
	public GuiEntity getCrossHair() { return crossHair; }
	
	public void draw(float clockRotation, boolean flyStatus) {
		//The clock hand moves every iteration
		PositionComponent pc = (PositionComponent) clockHand.getFirstComponentInstance(PositionComponent.class);
		pc.setRoll(clockRotation);
		
		Entity[] e = getAllEntities(GuiEntity.class);
		GuiEntity[] guis = Arrays.copyOf(e, e.length, GuiEntity[].class);
		for (GuiEntity ge : guis) {
			ge.draw();
		}
		
		if (flyStatus) flyIcon.draw(); //The fly icon changes
	}
	
	@Override
	public boolean isEntityAddable(Entity e) {
		if (GuiEntity.class.isInstance(e)) return true;
		return false;
	}

	@Override
	protected void systemCleanUp() {}

}
