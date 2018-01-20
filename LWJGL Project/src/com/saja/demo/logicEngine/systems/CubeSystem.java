package com.saja.demo.logicEngine.systems;

import com.saja.demo.gameEngine.components.ColorComponent;
import com.saja.demo.gameEngine.entities.Cube;
import com.saja.demo.gameEngine.entities.Player;
import com.saja.demo.gameEngine.worldGen.WorldGenerator;
import com.saja.seca.back.Entity;
import com.saja.seca.back.System;
import com.saja.seca.math3d.Vector3f;
import com.saja.seca.render.components.PositionComponent;
import com.saja.seca.render.components.ViewpointComponent;
import com.saja.seca.render.loader.ResLoader;

import java.util.Arrays;

/**
 * @author Andy Nguyen
 *
 * Just a collection of Cubes
 */

public class CubeSystem extends System {

	Cube playerCube;
	float distance = 4;
	
	public CubeSystem(ResLoader resl) {
		super();
		playerCube = new Cube(resl);
		//Fix z-fighting a little, make the player cube a bit bigger
		PositionComponent pc = (PositionComponent) playerCube.getFirstComponentInstance(PositionComponent.class);
		pc.setScale(1.05f, 1.05f, 1.05f);
	}
	
	@Override
	public boolean isEntityAddable(Entity e) {
		if (Cube.class.isInstance(e)) return true;
		return false;	
	}

	public void setMouseCube(Player p, WorldGenerator wg, InGameGuiSystem iggs) {
		ViewpointComponent playerVPC = p.getViewpointComponent();
		Vector3f newPos = playerVPC.getPosition();
		PositionComponent cubeVpc = (PositionComponent) playerCube.getFirstComponentInstance(PositionComponent.class);

		newPos = grid(1, newPos);
		newPos.y = playerVPC.getPosition().y - MovementSystem.player_height + 0.5f;
		
		cubeVpc.setPosition(newPos);
		
		if (isSpotTaken(newPos)) {
			playerCube.setColor(new Vector3f(3,0,0));
			ColorComponent cc = (ColorComponent) iggs.getCrossHair().getFirstComponentInstance(ColorComponent.class);
			cc.setColor(3, 0, 0);
		} else {
			playerCube.setColor(new Vector3f(0,3,0));
			ColorComponent cc = (ColorComponent) iggs.getCrossHair().getFirstComponentInstance(ColorComponent.class);
			cc.setColor(0, 3, 0);
		}
	}
	
	private static Vector3f grid(int size, Vector3f loc) {
		//Given an size of square, and location, return the center of a square
		//That the given loc would be in, in relation to the predetermined grid
		//The origin of the grid is assumed to be (0,0)
		
		//I made the formula myself, but I barely know how it even works
		float x = (float) Math.floor(loc.x / size + 0.5f);
		float z = (float) Math.floor(loc.z / size + 0.5f);
		x *= size;
		z *= size;
		
		return new Vector3f(x, loc.y, z);
	}
	
	public boolean isSpotTaken(Vector3f position) {
		//Only takes into acct x and z
		
		Vector3f pos = grid(1, position);
		
		Entity[] e = this.getAllEntities(Cube.class);
		Cube[] cubes = Arrays.copyOf(e, e.length, Cube[].class);
		for (Cube c : cubes) {
			PositionComponent pc = (PositionComponent) c.getFirstComponentInstance(PositionComponent.class);
			if ((pc.getPosition().x == pos.x) &
				(pc.getPosition().z == pos.z)) {
				return true;
			}
		}
		return false;
	}
	
	public void requestPlace(Player p, ResLoader resl, WorldGenerator wg) {		
		//If the spot is not taken, go ahead and add one
		Vector3f pos = ((PositionComponent) playerCube.getFirstComponentInstance(PositionComponent.class)).getPosition();
		if (!isSpotTaken(pos)) {
			Cube c = new Cube(resl);
			PositionComponent cubeVpc = (PositionComponent) playerCube.getFirstComponentInstance(PositionComponent.class);
			PositionComponent newCubeVpc = (PositionComponent) c.getFirstComponentInstance(PositionComponent.class);
			
			cubeVpc.getPosition().y = wg.getHeight(cubeVpc.getPosition().x, cubeVpc.getPosition().z) + 0.5f;
			newCubeVpc.setPosition(grid(1, cubeVpc.getPosition()));
			c.setColor(new Vector3f(1,1,1));
			this.addEntity(c);
		}
	}
	public void requestDelete(Player p) {
		//If a cube is present, remove it
		Vector3f pos = ((PositionComponent) playerCube.getFirstComponentInstance(PositionComponent.class)).getPosition();
		Entity[] e = this.getAllEntities(Cube.class);
		Cube[] cubes = Arrays.copyOf(e, e.length, Cube[].class);
		for (Cube c : cubes) {
			PositionComponent pc = (PositionComponent) c.getFirstComponentInstance(PositionComponent.class);
			if ((pc.getPosition().x == pos.x) &
				(pc.getPosition().z == pos.z)) {
				this.removeEntity(c);
				return;
			}
		}
	}
	
	public void renderCubes(boolean renderPlayer) {
		playerCube.draw();
		Entity[] e = this.getAllEntities(Cube.class);
		Cube[] cubes = Arrays.copyOf(e, e.length, Cube[].class);
		for (Cube c : cubes) {
			c.draw();
		}
		//playerCube.draw();
	}
	
	public void setDistance(float distance) { this.distance = distance; }
	public float getDistance() { return distance; }
	public Cube getPlayerCube() { return playerCube; }
	
	public void addCube(ResLoader resL, Vector3f pos) {
		Cube c = new Cube(resL);
		PositionComponent pc = (PositionComponent) c.getFirstComponentInstance(PositionComponent.class);
		pc.setPosition(pos);
		this.addEntity(c);
	}
	
	@Override
	protected void systemCleanUp() {
		playerCube.cleanUp();
		//Note that the player cube is not stored with the other entities
	}

}
