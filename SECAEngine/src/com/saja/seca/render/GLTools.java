package com.saja.seca.render;

import com.saja.seca.error.SecaException;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * @author Andy
 *
 * Static methods dealing with OpenGL. 
 * I want to keep as many calls to OpenGL into one file as I can.
 * This will make it easier to port to other platforms
 * 
 * GLTools deals specifically with data & shader management, like VBOs
 * MasterRenderer deals specifically with rendering management, like the glViewport
 * 
 * Methods in here should only be called from the render package of SECA
 */

public class GLTools {

	public static void checkGLError() throws SecaException{
		int error = GL11.glGetError();
		if (error != GL11.GL_NO_ERROR) {
			String msg = "OpenGL error:  ";
			switch (error) {
			case GL11.GL_INVALID_OPERATION:
				msg += "GL_INVALID_OPERATION";
				break;
			case GL11.GL_INVALID_ENUM:
				msg += "GL_INVALID_ENUM";
				break;
			case GL11.GL_INVALID_VALUE:
				msg += "GL_INVALID_VALUE";
				break;
			case GL11.GL_STACK_OVERFLOW:
				msg += "GL_STACK_OVERFLOW";
				break;
			case GL11.GL_STACK_UNDERFLOW:
				msg += "GL_STACK_UNDERFLOW";
				break;
			case GL11.GL_OUT_OF_MEMORY:
				msg += "GL_OUT_OF_MEMORY";
				break;
			}
			throw new SecaException(GLTools.class, msg);
		}
	}

	public static int createFloatVBO(float[] data, int bufferType, int dataUsage) {
		int vboID = GL15.glGenBuffers();
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data).flip();

		GL15.glBindBuffer(bufferType, vboID);
		GL15.glBufferData(bufferType, buffer, dataUsage);

		GL15.glBindBuffer(bufferType, 0);
		return vboID;

	}
	public static int createShortVBO(short[] data, int bufferType, int dataUsage) {
		int vboID = GL15.glGenBuffers();
		
		bufferShortVBO(data, bufferType, dataUsage, vboID);
		
		return vboID;
	}
	public static void bufferShortVBO(short[] data, int bufferType, int dataUsage, int vboID) {
		ShortBuffer buffer = BufferUtils.createShortBuffer(data.length);
		buffer.put(data).flip();

		GL15.glBindBuffer(bufferType, vboID);
		GL15.glBufferData(bufferType, buffer, dataUsage);
		
		GL15.glBindBuffer(bufferType, 0);
	}
	
	public static int createAndBindVAO() {
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	public static int createShader(int shaderType, String shaderCode) throws SecaException {
		//Should only be called by the Renderer class
		
		//Make sure we're dealing with vertex or fragment shaders here
		if (shaderType != GL20.GL_VERTEX_SHADER	&& shaderType != GL20.GL_FRAGMENT_SHADER)
			throw new SecaException(Renderer.class, "Error creating shader: Bad type");
		if (shaderCode == null || shaderCode.equals(""))
			throw new SecaException(Renderer.class, "Error creating shader: Null code");

		int shaderInGL = GL20.glCreateShader(shaderType);

		if (shaderInGL != 0) {
			GL20.glShaderSource(shaderInGL, shaderCode);
			GL20.glCompileShader(shaderInGL);

			ByteBuffer compileStatus = ByteBuffer.allocateDirect(4);
			GL20.glGetShaderiv(shaderInGL, GL20.GL_COMPILE_STATUS, compileStatus);
			if (compileStatus.getInt(0) == 0) {
				//We throw a RuntimeException later already
				System.err.println(GL20.glGetShaderInfoLog(shaderInGL));
				GL20.glDeleteShader(shaderInGL);
				shaderInGL = 0;
			}
		}
		if (shaderInGL == 0) {
			throw new SecaException(Renderer.class, "Error creating shader: Failed shader compilation");
		} else {
			return shaderInGL;
		}
	}
	
	public static void linkProgram(int programID) throws SecaException {
		//Should only be called by the Renderer class
		
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		
		//Check on the link status
		ByteBuffer linkStatus = ByteBuffer.allocateDirect(4);
		GL20.glGetProgramiv(programID, GL20.GL_LINK_STATUS, linkStatus);
		if (linkStatus.getInt(0) == 0) {
			GL20.glDeleteProgram(programID);
			programID = 0;
		}
		if (programID == 0) throw new SecaException(Renderer.class, "Error creating program");
	}
	
	//Given a VBO, bind point in a shader, and size, bind it as a float
	public static void floatBufferVertexArrayBind(int vbo, int bindPoint, int elementSize) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL20.glVertexAttribPointer(bindPoint, elementSize, GL11.GL_FLOAT, false, 0, 0);
	}
}
