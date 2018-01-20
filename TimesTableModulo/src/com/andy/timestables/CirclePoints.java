package com.andy.timestables;

import com.saja.seca.back.Component;
import com.saja.seca.back.Entity;
import com.saja.seca.error.SecaException;
import com.saja.seca.render.GLTools;
import com.saja.seca.render.components.DrawableModelComponent;
import com.saja.seca.render.loader.ModelBlueprint;
import com.saja.seca.render.loader.ResLoader;
import org.lwjgl.opengl.GL15;

public class CirclePoints extends Entity {

	private final int vertices;
	
	public CirclePoints(int vertices, ResLoader r, LineRenderer lr) throws SecaException {
		this.vertices = vertices;
		
		float[] vertexArray = new float[vertices * 2];
		short[] indices = new short[vertices * 2];
		
		for (int i = 0; i < vertices; i++) {
			float angle = (float) (2 * Math.PI / vertices * i);
			vertexArray[2*i] = (float) Math.cos(angle);
			vertexArray[2*i + 1] = (float) Math.sin(angle);
			indices[2 * i] = (short) i;
			indices[2 * i + 1] = (short) (i + 1);
		}
		indices[vertices * 2 - 1] = 0;
		
		ModelBlueprint mb = new ModelBlueprint( indices );
		mb.addArray("in_position", vertexArray, 2);
		String name = r.createUniqueModelName("line");

		r.createModel(name, mb, lr);

		this.addComponent(new DrawableModelComponent(r, name));
	}
	
	public void changeIndices(float multiplier) {
		short[] indices = new short[vertices * 2];
		
		for (int i = 0; i < vertices; i++) {
			indices[2 * i] = (short) i;
			float ans = i * multiplier;
			ans %= vertices;
			indices[2 * i + 1] = (short) ans;
		}
		
		DrawableModelComponent mc = (DrawableModelComponent) this.getFirstComponentInstance(DrawableModelComponent.class);
		int indicesID = mc.getModel().getIndicesID();
		GLTools.bufferShortVBO(indices, GL15.GL_ELEMENT_ARRAY_BUFFER, GL15.GL_STREAM_DRAW, indicesID);
	}
	
	public void render() {
		DrawableModelComponent mc = (DrawableModelComponent) this.getFirstComponentInstance(DrawableModelComponent.class);
		mc.drawLines();
	}

	@Override
	public boolean isComponentAddable(Component c) {
		return true;
	}

	@Override
	protected void entityCleanUp() { }
}
