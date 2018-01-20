package com.andy.timestables;

import com.saja.seca.error.SecaException;
import com.saja.seca.math3d.Vector3f;
import com.saja.seca.render.Renderer;

public class LineRenderer extends Renderer {

	protected LineRenderer() throws SecaException {
		super("lines", "#version 330 core\n"
				+ "in vec2 in_position;"
				+ ""
				+ "void main(void) {"
				+ "gl_Position = vec4(in_position.x, in_position.y, 0.0, 1.0);"
				+ "}", 
				"#version 330 core\n"
				+ "uniform vec3 u_color;"
				+ ""
				+ "out vec4 out_color;"
				+ "void main(void) {"
				+ "	out_color = vec4(u_color, 1.0);"
				+ "}", 1);
	}

	public void setColor(Vector3f color) {
		super.uniform3f("u_color", color);
	}
	
	@Override
	protected void bindAttribsInShaders() {
		super.bindAttribLoc(0, "in_position");
		super.bindAttribLoc(1, "u_color");
	}

	@Override
	protected void addAttribsToHash() {
		super.addAttribLoc("in_position");
		super.addUniformLoc("u_color");
	}

	@Override
	public void enableAttribs() {
		super.enableAttrib("in_position");
	}

	@Override
	public void disableAttribs() {
		super.disableAttrib("in_position");		
	}
	
}
