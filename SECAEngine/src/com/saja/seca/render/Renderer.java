package com.saja.seca.render;

import com.saja.seca.error.SecaException;
import com.saja.seca.math3d.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.util.HashMap;

/**
 * @author Andy
 *
 * One generic Renderer/program. This class hold a programID and two shaders
 * 
 * All extending classes have to implement the following methods:
 * void bindAttribs(); - Binds each attrib/uniform to a specific number
 * void addAttribsToHash() - Pulls out the bind point (number) of each attrib/uniform
 * void enableAttribs(); - Enable "in_" attribs
 * void disableAttribs(); - Disable "in_" attribs
 * 
 * Calling cleanUp() is a must in order to clear the program and shaders in OpenGL
 */

public abstract class Renderer {
	
	public static final String console_prefix = "SECA Renderer";
	
	private HashMap<String, Integer> binds = new HashMap<String, Integer>();
	
	private int programID = 0, vertexShaderID = 0, fragmentShaderID = 0;
	private String name;
	
	protected Renderer(String rendererName, String vertexShaderCode, String fragmentShaderCode, int vertexAttribs) throws SecaException {
		
		System.out.print(console_prefix + ": Creating renderer \'" + rendererName + "\'... ");
		
		if (vertexAttribs > GL20.GL_MAX_VERTEX_ATTRIBS) {
			throw new SecaException(Renderer.class, "Shader name \'" + rendererName + "\' with " + vertexAttribs 
					+ " attribs exceeds this computer's limit of " + GL20.GL_MAX_VERTEX_ATTRIBS + " attribs");
		}
		
		programID = GL20.glCreateProgram();
		this.name = rendererName;
		
		//Make sure the vertex and fragment shader code is valid
		if (vertexShaderCode.equals("") | vertexShaderCode == null) throw new SecaException(this.getClass(), "Bad input for vertex shader \'" + rendererName + "\'");
		if (fragmentShaderCode.equals("") | fragmentShaderCode == null) throw new SecaException(this.getClass(), "Bad input for fragment shader \'" + rendererName + "\'");

		// Load vertex and fragment code into OpenGL program
		vertexShaderID = GLTools.createShader(GL20.GL_VERTEX_SHADER, vertexShaderCode);
		fragmentShaderID = GLTools.createShader(GL20.GL_FRAGMENT_SHADER, fragmentShaderCode);
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);

		//You have to bind attribs, then link program, then get attribs from the program. Already tested
		bindAttribsInShaders(); //Subclasses have to address this
		GLTools.linkProgram(programID); //Will throw SecaException
		addAttribsToHash(); //Subclasses have to address this
		
		System.out.println("created (programID " + programID + ").");
	}

	//Use - super.bindAttribLoc(int location, String name);
	protected abstract void bindAttribsInShaders();
	
	//Use - super.addAttribsLoc(String name), super.addUniformLoc(String name)
	protected abstract void addAttribsToHash();
	
	protected void bindAttribLoc(int bindLocation, String attribName) {
		GL20.glBindAttribLocation(programID, bindLocation, attribName);
	}
	protected void addAttribLoc(String attribName) {
		binds.put(attribName, new Integer(GL20.glGetAttribLocation(getProgramID(), attribName)));
	}
	protected void addUniformLoc(String uniformName) {
		binds.put(uniformName, new Integer(GL20.glGetUniformLocation(getProgramID(), uniformName)));
	}
	
	//Use - super.enableAttrib(x), super.disableAttrib(x);
	public abstract void enableAttribs();
	public abstract void disableAttribs(); //Only needed on GLES
	
	protected void enableAttrib(String attrib) { 
		GL20.glEnableVertexAttribArray(getBindLocation(attrib));
	}
	protected void disableAttrib(String attrib) { 
		GL20.glDisableVertexAttribArray(getBindLocation(attrib));
	}
	
	//Uniform things up to the shader
	protected void uniformTexture(String bindLocation, int textureID) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL20.glUniform1i(getBindLocation(bindLocation), 0);
	}
	protected void uniform1f(String location, float v) {
		GL20.glUniform1f(getBindLocation(location), v);
	}
	protected void uniform1i(String location, int i) {
		GL20.glUniform1i(getBindLocation(location), i);
	}
	protected void uniform2f(String location, Vector2f v) {
		uniform2f(location, v.x, v.y);
	}
	protected void uniform2f(String location, float x, float y) {
		GL20.glUniform2f(getBindLocation(location), x, y);
	}
	protected void uniform2i(String location, int x, int y) {
		GL20.glUniform2i(getBindLocation(location), x, y);
	}
	protected void uniform3f(String location, Vector3f v) {
		uniform3f(location, v.x, v.y, v.z);
	}
	protected void uniform3f(String location, float x, float y, float z) { 
		GL20.glUniform3f(getBindLocation(location), x, y, z);
	}
	protected void uniform3i(String location, int x, int y, int z) {
		GL20.glUniform3i(getBindLocation(location), x, y, z);
	}
	protected void uniform4f(String location, Vector4f v) {
		uniform4f(location, v.x, v.y, v.z, v.w);
	}
	protected void uniform4f(String location, float x, float y, float z, float w) {
		GL20.glUniform4f(getBindLocation(location), x, y, z, w);
	}
	protected void uniform4f(String location, int x, int y, int z, int w) {
		GL20.glUniform4i(getBindLocation(location), x, y, z, w);
	}
	protected void uniformMatrix2f(String location, Matrix2f m) {
		GL20.glUniformMatrix2fv(getBindLocation(location), false, m.getBuffer());
	}
	protected void uniformMatrix3f(String location, Matrix3f m) {
		GL20.glUniformMatrix3fv(getBindLocation(location), false, m.getBuffer());
	}
	protected void uniformMatrix4f(String location, Matrix4f m) {
		GL20.glUniformMatrix4fv(getBindLocation(location), false, m.getBuffer());
	}
	
	public void useRenderer() { GL20.glUseProgram(getProgramID()); }
	public void unuseRenderer() {} //Note - Can be overriden
	public int getProgramID() { return programID; }
	public String getRendererName() { return name; }
	
	//Used mainly by the Model class
	public int getBindLocation(String name) {
		if (binds.get(name) == null) return -1; //This is an invalid location to bind to
		return binds.get(name).intValue();
	}
	
	public void cleanUp() {
		GL20.glDetachShader(getProgramID(), vertexShaderID);
		GL20.glDetachShader(getProgramID(), fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(getProgramID());
		vertexShaderID = 0;
		fragmentShaderID = 0;
		programID = 0; //OpenGL ignores 0 when deleting
	}
	
	@Override
	public String toString() {
		return console_prefix + " with name \'" + getRendererName() + "\' and programID " + getProgramID();
	}

}
