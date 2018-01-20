package com.saja.seca.render.loader;

import java.util.ArrayList;

/**
 * @author Andy Nguyen
 *
 * ModelBlueprint is the set of arrays that are stored before being loaded into OpenGL.
 * It is created from a CustomLoader and used to create a Model
 * 
 * The furthest point can be 0 and this field can be ignored entirely if desired.
 * It is included here because it can only be calculated from a raw vertex array.
 * The furthest point can be used in preliminary collision detection
 * 
 * Uncomment the System.out lines to see what arrays are being read in
 * 
 * cleanUp() is called in Model
 */

public class ModelBlueprint {
	
	private ArrayList<VBO> arrays = new ArrayList<VBO>();
	short[] indices;
	private float furthestPointDistance = 0;

	public ModelBlueprint(short[] indices) {
		this(indices, 0);
	}
	public ModelBlueprint(short[] indices, float furthestPointDistance) {
		//System.out.println("SECA ModelBlueprint: Array \'indices\' loaded " + java.util.Arrays.toString(indices));
		this.indices = indices;
		setFurthestPoint(furthestPointDistance);
	}

	public void addArray(String name, float[] array, int elementSize) {
		//System.out.println("SECA ModelBlueprint: \'Array \'" + name + "\' loaded " + java.util.Arrays.toString(array));
		arrays.add(new VBO(name, array, elementSize));
	}

	public ArrayList<VBO> getFloatArrays() { return arrays; }
	public short[] getIndicesArray() { return indices; }

	public void setFurthestPoint(float p) { furthestPointDistance = p; }
	public float getFurthestDistance() { return furthestPointDistance; }

	public void cleanUp() {
		arrays.clear();
		indices = null;
	}
	
	@Override
	public String toString() {
		return "SECA ModelBlueprint with " + (arrays.size() + 1) + " arrays"; //Add 1 for indices array
	}
}