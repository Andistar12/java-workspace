package com.saja.demo.logicEngine.systems;

import com.saja.demo.gameEngine.entities.TerrainChunk;
import com.saja.demo.gameEngine.worldGen.WorldGenerator;
import com.saja.demo.renderers.TerrainRenderer;
import com.saja.seca.back.Entity;
import com.saja.seca.back.System;
import com.saja.seca.error.SecaException;
import com.saja.seca.math3d.Vector3f;
import com.saja.seca.render.loader.ResLoader;

import java.util.Arrays;

/**
 * @author Andy
 *
 * Just a collection of TerrainChunks. Lots of Maths haha
 */

public class TerrainSystem extends System {

	public static final String console_prefix = "DEMO TerrainSystem: ";
	
	private WorldGenerator wg;
	private TerrainRenderer tr;
	private ResLoader resl;
	private int size;
	
	public TerrainSystem(WorldGenerator wg, TerrainRenderer tr, ResLoader resl, int size, Vector3f initPos) throws SecaException {
		this.wg = wg;
		this.tr = tr;
		this.resl = resl;
		this.size = size;
		
		guarenteeTilePrescence(initPos);
	}

	@Override
	public boolean isEntityAddable(Entity e) {
		if (TerrainChunk.class.isInstance(e)) return true;
		return false;
	}

	private void createTile(Vector3f pos) throws SecaException {
		TerrainChunk t = new TerrainChunk(tr, resl, "grass", wg, pos, size);
		//	public TerrainTile(TerrainRenderer renderer, ResLoader r, String textureName, WorldGenerator wg, Vector3f position, int size) {
		addEntity(t);
	}
	
	private TerrainChunk[] getTiles() {
		Entity[] e = this.getAllEntities(TerrainChunk.class);
		TerrainChunk[] tiles = Arrays.copyOf(e, e.length, TerrainChunk[].class);
		return tiles;
	}
	
	public void guarenteeTilePrescence(Vector3f pos) throws SecaException {
		TerrainChunk[] tiles = getTiles();
		
		for (TerrainChunk t : tiles) {
			//The position is in the tile, so we are done 
			if (t.isPositionInTile(pos)) return;
		}
		Vector3f newPos = grid(size, pos);
		createTile(newPos);
	}
	
	public void render() {
		TerrainChunk[] tiles = getTiles();
		
		for (TerrainChunk t : tiles) {
			t.draw();
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
		
		return new Vector3f(x, 0, z);
	}

	@Override
	protected void systemCleanUp() {
		wg.cleanUp();
		tr = null;
		resl = null;
	} 
	
}
