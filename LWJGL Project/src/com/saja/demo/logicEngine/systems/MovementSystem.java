package com.saja.demo.logicEngine.systems;

import com.saja.demo.gameEngine.worldGen.WorldGenerator;
import com.saja.demo.logicEngine.managers.InputSetup;
import com.saja.seca.back.Entity;
import com.saja.seca.back.System;
import com.saja.seca.math3d.Vector3f;
import com.saja.seca.render.components.PositionComponent;

/**
 * @author Andy Nguyen
 *
 * All players added to this system will be the same
 * 
 * InputSetup deals strictly with setting up and getting player input, specifically setting up commands.
 * 		It should not hold any game-related fields
 * PlayerSystem deals strictly with accessing and manipulating those commands. 
 * 		It holds player constants and statuses, but deals nothing with the actual key pressed (like F11)
 */

public class MovementSystem extends System {
	
	public static enum Movement { FORWARD, STILL, BACKWARD };
	
	public static final float move_speed = 3f;
	public static final float GRAVITY = 9f;
	public static final float INIT_VELOCITY = 3.5f;
	public static final float player_height = 1f; //How high above the ground
	
	private Movement moveForw = Movement.STILL, moveLeft = Movement.STILL;
	
	private boolean isAirborne = false;
	private float jumpVelocity = 0;	
	
	private InputSetup ips;
	
	public MovementSystem(InputSetup ips) {
		super();
		this.ips = ips;
	}
	
	@Override
	public boolean isEntityAddable(Entity e) {
		//Needs to have at least one PositionComponent
		if (e.getAllComponents(PositionComponent.class).length > 0) return true;
		return false;
	}

	public void update(WorldGenerator heights, CubeSystem cs, float gameSpeed) {
		//First, we check the keys
		if (ips.isMouseLocked()) {
			//Mouse is hidden in-game, so we check inputs
			if (ips.getKeyStatus("jump") && !isAirborne) {
				jumpVelocity = INIT_VELOCITY;
				isAirborne = true;
			}
			if (ips.getMouseDelta()[0] != 0) {				
				for (Entity e : getAllEntities()) {
					PositionComponent pc = (PositionComponent) e.getFirstComponentInstance(PositionComponent.class);
					if (pc != null) pc.setYaw(pc.getYaw() - ips.getMouseDelta()[0] * gameSpeed);
				}
			}
			if (ips.getMouseDelta()[1] != 0) {
				for (Entity e : getAllEntities()) {
					PositionComponent pc = (PositionComponent) e.getFirstComponentInstance(PositionComponent.class);
					if (pc != null) pc.setPitch(pc.getPitch() - ips.getMouseDelta()[1] * gameSpeed);
				}
			}
			ips.resetMouseDelta();
		} else {
			//Mouse is showing, so ignore movement/etc
			moveForw = Movement.STILL;
			moveLeft = Movement.STILL;
		}
		
		//Now we do movement. We do this regardless of whether the window has focus or not
		//Lateral movement is X,Z movement, while jump is Y movement
		for (Entity e : getAllEntities()) {
			PositionComponent pc = (PositionComponent) e.getFirstComponentInstance(PositionComponent.class);
			updateLateralMove(pc, cs, gameSpeed);
			updateHeight(pc, heights, gameSpeed);
		}
	}
	
	private Movement forwardOrBack() {	
		if (ips.getKeyStatus("moveForward") && ips.getKeyStatus("moveBackward")) return Movement.STILL;
		if (ips.getKeyStatus("moveForward")) return Movement.FORWARD;
		if (ips.getKeyStatus("moveBackward")) return Movement.BACKWARD;
		return Movement.STILL;
	}
	private Movement leftOrRight() {
		if (ips.getKeyStatus("moveLeft") && ips.getKeyStatus("moveRight")) return Movement.STILL;
		if (ips.getKeyStatus("moveLeft")) return Movement.FORWARD;
		if (ips.getKeyStatus("moveRight")) return Movement.BACKWARD;
		return Movement.STILL;
	}
	
	private void updateLateralMove(PositionComponent pc, CubeSystem cs, float gameSpeed) {
		Vector3f moveVector = new Vector3f(0,0,0);
		switch(forwardOrBack()) {
		case FORWARD:
			moveVector = pc.simulateMoveXZ(-90, move_speed * gameSpeed);
			break;
		case BACKWARD:
			moveVector = pc.simulateMoveXZ(+90, move_speed * gameSpeed);
			break;
		case STILL:
			break;
		}
		/*
		if (cs.isSpotTaken(pc.getPosition()) || ips.isFlyingToggled()) {
			//Player is currently inside a cube or flying, go ahead
			pc.setPosition(pc.getPosition().add(moveVector));
		} else {
			//Player is not inside a cube
			if (!cs.isSpotTaken(pc.getPosition().add(moveVector))) pc.setPosition(pc.getPosition().add(moveVector));
		}
		*/
		pc.incrementPosition(moveVector);
		switch (leftOrRight()) {
		case FORWARD:
			moveVector = pc.simulateMoveXZ(+180, move_speed * gameSpeed);
			break;
		case BACKWARD:
			moveVector = pc.simulateMoveXZ(0, move_speed * gameSpeed); //There's no -180 here haha
			break;
		case STILL:
			break;
		}
		/*
		if (cs.isSpotTaken(pc.getPosition()) || ips.isFlyingToggled()) {
			//Player is currently inside a cube or flying, go ahead
			pc.setPosition(pc.getPosition().add(moveVector));
		} else {
			//Player is not inside a cube
			if (!cs.isSpotTaken(pc.getPosition().add(moveVector))) pc.setPosition(pc.getPosition().add(moveVector));
		}
		*/
		pc.incrementPosition(moveVector);
	}
	
	private void updateHeight(PositionComponent pc, WorldGenerator heights, float gameSpeed) {
		float height = pc.getPosition().y;
		
		if (ips.isFlyingToggled()) {
			//Flying ignores height
			if (ips.getKeyStatus("jump")) height += move_speed * gameSpeed;
			if (ips.getKeyStatus("crouch")) height -= move_speed * gameSpeed;
			jumpVelocity = 0;
		} else {
			//Game speed is quadratic here, pretty cool
			jumpVelocity -= GRAVITY * gameSpeed;
			height += jumpVelocity * gameSpeed;
			
			if (height < heights.getHeight(pc.getPosition().x, pc.getPosition().z) + player_height) {
				//We are under the ground
				height = heights.getHeight(pc.getPosition().x, pc.getPosition().z) + player_height;
				isAirborne = false;
				jumpVelocity = 0;
			}
		}
		pc.getPosition().y = height;
		pc.buildModelMatrix();
	}
	
	public void reset() {
		ips.resetMouseClick();
		moveForw = Movement.STILL;
		moveLeft = Movement.STILL;
		isAirborne = false;
		jumpVelocity = 0;
		ips.resetCameraMode();
	}
	
	public Movement isMoveForw() { return moveForw; }
	public Movement isMoveLeft() { return moveLeft; }
	public boolean isAirborne() { return isAirborne; }

	public boolean mouseClickRequest() { return ips.getKeyStatus("mouseClick"); }
	public void resetMouseClickRequest() { ips.resetMouseClick(); }
	
	@Override
	protected void systemCleanUp() { 
		ips.cleanUp();
	}

}
