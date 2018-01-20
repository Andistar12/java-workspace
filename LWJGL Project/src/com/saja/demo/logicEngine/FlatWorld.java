package com.saja.demo.logicEngine;

import com.saja.demo.gameEngine.worldGen.WorldGenerator;
import com.saja.seca.math3d.Vector3f;

/**
 * @author Andy Nguyen
 *
 * Implements the WorldGenerator, making it able to generate a world
 * Hence the name, this world is completely flat with normals going straight up
 */

public class FlatWorld implements WorldGenerator {

	@Override
	public float getHeight(float x, float z) {
		//Can be changed but make sure the number stays constant
		return 0;
	}

	@Override
	public Vector3f getNormal(float x, float z) {
		//Straight up on the y axis
		return new Vector3f(0,1,0);
	}

	@Override
	public void cleanUp() {}
	
}
