package com.saja.demo.logicEngine;

import com.saja.demo.gameEngine.worldGen.WorldGenerator;
import com.saja.seca.math3d.Matrix4f;
import com.saja.seca.math3d.Vector2f;
import com.saja.seca.math3d.Vector3f;
import com.saja.seca.math3d.Vector4f;

/**
 * @author Andy Nguyen
 * 
 * Logistic growth model, see the comments below
 *
 */

public class LogisticGrowth implements WorldGenerator {

	public static final float normalDelta = 0.0002f;
	
	public static final float L = 8f; //Carrying capacity
	public static final float C = 1.5f; //Based on initial capacity
	public static final float k = 0.07f;
	
	@Override
	public float getHeight(float x, float z) {
		/*
		 * We chuck the distance formula at the logistic growth model, haha
		 * Square rooting is too expensive to be run every frame, so leaving it squared is fine
		 * 		(especially with the constant k in the formula, square root does not affect much)
		 * 
		 * Logistic growth comes from the differential growth model:
		 * dP/dt = kP(1 - P/L)
		 * And integrating that gives us the logistic growth model:
		 * P = L / (1 + C*e^(-k * t))
		 * Where L is the carrying capacity, k is a growth constant, 
		 * 		t is our population count, and P is the population function
		 * The constant of integration ends up being represented by C in this formula
		 * Given an initial capacity I, C = L / I - 1
		 */
		
		//Distance from origin to this point, should always be in the range [0, infinity)
		float distance = x * x + z * z; 
		
		return (float) (L / (1 + C*Math.exp(-k * distance)));
	}

	@Override
	public Vector3f getNormal(float x, float z) {
		//TODO calculate normal from derivative
		/*
		 * Conventional cross-product of two orthogonal vectors, both with start point being (x, z)
		 * Works with basically any generated world  (ignoring rapid oscillation and other non-differentiable planes of course)
		 */
		
		Vector3f b = new Vector3f(x, getHeight(x, z + normalDelta), z + normalDelta);
		Vector3f a = new Vector3f(x, getHeight(x, z), z);
		Vector3f c = new Vector3f(x + normalDelta, getHeight(x + normalDelta, z), z);
		
		Vector3f normal = b.subtract(a).cross(c.subtract(a));
		normal = normal.normalize();
		
		return normal;
	}
	
	public Vector3f test(float x, float z) {
		/*
		 * The traditional method, especially for procedurally-generated terrain, uses
		 * 		the normalized cross-product of two orthogonal vectors, both with start point (x, getHeight(x,z), z)
		 * 
		 * But since we have a differential equation, we can use that instead for near-perfect accuracy
		 * We calculate the current height, and then use that to find our slope for the point.
		 * We then calculate the line created from that slope, then 
		 * We then convert that 2D slope into a 3D coordinate via vector/matrix rotations.
		 * 
		 * The differential growth model: dP/dt = kP(1 - P/L)
		 */
		
		//Currently a WIP that calculates normals based on the differential equation,
		//Rather than the conventional vector cross-product
		
		float height = getHeight(x, z);
		float distance = x * x + z * z;
		
		float derivative = k * height * (1 - height / L); //Slope
		
		if (derivative == 0) {
			//For a completely flat line, our new inverted vector will be straight up
			derivative = -1000; //Basically infinity as far as slopes are concerned
		} else {
			derivative = 1 / -derivative; //Invert the slope to make the normal
		}
		//Current normal slope of the line, working
		
		//Equation of our vector line:
		//f(a) = derivative * (a - distance) + height
		//Now we figure out two points on the line
		// point1 = (0, f(0), 0));
		// point2 = (1, f(1), 0));
		
		Vector3f point1 = new Vector3f(0, derivative * (0-distance) + height, 0);
		Vector3f point2 = new Vector3f(1, derivative * (1-distance) + height, 0);
		
		Vector3f vec = point1.subtract(point2);
		vec = vec.normalize();
		//Unrotated normal, working
		
		Matrix4f rotationMartrix = new Matrix4f();
		
		//We rotate based on the unit circle
		Vector2f initialPosition = new Vector2f(1,0);
		Vector2f ours = new Vector2f(x, z);
		ours = ours.normalize();
		
		//Take the dot product  of ours and the initial, that will be our angle of rotation
		System.out.println("Test: Normalized = " + ours.toString());
		System.out.println("Test: " + Math.acos(initialPosition.dot(ours)));
		float rotationDot = Math.abs(initialPosition.dot(ours));
		rotationMartrix = Matrix4f.rotate((float) (rotationDot / Math.PI * 180), 0, 1, 0);
		
		Vector4f newVec = rotationMartrix.multiply(new Vector4f(vec.x, vec.y, vec.z, 1.0f));
		
		return new Vector3f(newVec.x, newVec.y, newVec.z);
	}
	
	@Override
	public void cleanUp() {
		// Nothing to clean up
	}
}


