package com.saja.demo.gameEngine.worldGen;

import com.saja.seca.math3d.Vector3f;

/**
 * @author Andy Nguyen
 * 
 * All class that implement this will be able to generate a world
 * The TerrainSystem and TerrainChunk classes take in a WorldGenerator as constructor args
 * 
 * Using this, we can customize what kind of world is loaded in and generated.
 * We can generate a world with Random, read in a world through heightmaps,
 * 		or even make getHeight() return a constant for a flat world
 */

public interface WorldGenerator {
	
	public float getHeight(float x, float z);
	
	public Vector3f getNormal(float x, float z);
	
	public void cleanUp(); //For example, if we had a Random object we could clear it here
}
