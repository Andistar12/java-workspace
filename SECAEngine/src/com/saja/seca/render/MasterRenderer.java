package com.saja.seca.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

/**
 * @author Andy
 *
 * Contains static methods for dealing with OpenGL
 * 
 * GLTools deals specifically with data & shader management, like VBOs
 * MasterRenderer deals specifically with rendering management, like the glViewport
 * 
 * Methods in here should be called outside SECA to help initialize and run OpenGL
 */

public class MasterRenderer {
	
	public static void setViewportSize(int width, int height) {
		GL11.glViewport(0, 0, width, height);
	}
	
	public static void prepareRendering(float singleColor) {
		setClearColor(singleColor, singleColor, singleColor);
		prepareRendering();
	}
	public static void prepareRendering(float r, float g, float b) {
		setClearColor(r, g, b);
		prepareRendering();
	}
	public static void setClearColor(float r, float g, float b) {
		GL11.glClearColor(r, g, b, 1.0f);
	}
	public static void prepareRendering() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void finishRendering() {		
		GL30.glBindVertexArray(0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public static void enableDepthTest() { GL11.glEnable(GL11.GL_DEPTH_TEST); }
	public static void disableDepthTest() { GL11.glDisable(GL11.GL_DEPTH_TEST); }
	public static void enableCullFace() { GL11.glEnable(GL11.GL_CULL_FACE); }
	public static void disableCullFace() { GL11.glDisable(GL11.GL_CULL_FACE); }
	public static void enableBlending() { GL11.glEnable(GL11.GL_BLEND); }
	public static void disableBlending() { GL11.glDisable(GL11.GL_BLEND); }

}
