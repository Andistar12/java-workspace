package com.saja.demo.loader;

import com.saja.seca.render.loader.ModelBlueprint;

/**
 * @author Andy Nguyen
 *
 * GuiTile is the VBOs for a GUI rectangle that can be drawn
 * This class is an example of how ModelBlueprints can be made from code.
 * Arrays of data are added with a corresponding name
 * Then somewhere else, a new instance of this class can be made and sent to a ResLoader
 */

public class GuiTile extends ModelBlueprint {

	public GuiTile() {
		super(new short[] { 0, 2, 1, 2, 3, 1 }, 0); //Indices, collisionRadius which we can ignore

		this.addArray("in_position", new float[] { -0.5f, 0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f}, 2);
		this.addArray("in_textureCoord", new float[] { 0f, 0f, 1f, 0f, 0f, 1f, 1f, 1f }, 2);
	}

}
