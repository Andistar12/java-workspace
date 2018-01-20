package com.saja.demo.logicEngine;

import com.saja.demo.gameEngine.worldGen.WorldGenerator;
import com.saja.seca.math3d.Vector3f;

/**
 * @author Andy Nguyen
 *
 * Used for testing wacky equations. Turns out, this can be use as a 3D graphing calc!
 */

public class TestWorld implements WorldGenerator {

	public static final float normalDelta = 0.0002f;
		
	public TestWorld() { }
	
	@Override
	public float getHeight(float x, float z) {
		
		//Let's have some fun
		
		//return (float) (x * x - z * z); //Hyperbola warning
		//return (float) (Math.cos(x * x + z * z));
		//return (float) (10 / (1 + 0.5 * Math.exp( -2 * (Math.abs(x) + Math.abs(z)) ) ) );
		
		return (float) (Math.sin((x * x + z * z) * 0.1)); //Concentric circles kinda
				
		/*
		r.setSeed((long) (Math.floor(x) + Math.floor(z) * 14));
		return r.nextInt(100000) / 100000f;
		*/
	}

	@Override
	public Vector3f getNormal(float x, float z) {		
		//Look it up. This just works
		Vector3f b = new Vector3f(x, getHeight(x, z + normalDelta), z + normalDelta);
		Vector3f a = new Vector3f(x, getHeight(x, z), z);
		Vector3f c = new Vector3f(x + normalDelta, getHeight(x + normalDelta, z), z);
		
		Vector3f normal = b.subtract(a).cross(c.subtract(a));
		normal = normal.normalize();
		
		return normal;
	}

	@Override
	public void cleanUp() { }

}
