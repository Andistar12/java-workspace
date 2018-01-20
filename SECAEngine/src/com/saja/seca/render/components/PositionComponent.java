package com.saja.seca.render.components;

import com.saja.seca.back.Component;
import com.saja.seca.math3d.Matrix4f;
import com.saja.seca.math3d.Vector3f;

/**
 * @author Andy Nguyen
 * 
 * PositionComponent is a model matrix (Matrix4f, position, Vector3f scale, pitch, yaw, roll)
 */

public class PositionComponent extends Component {
		
	private Vector3f position = new Vector3f();
	private float pitch, yaw, roll;
	private Vector3f scale;
	private Matrix4f modelM = new Matrix4f();
	
	public PositionComponent() {
		this (new Vector3f(0,0,0), 0, 0, 0, new Vector3f(1,1,1));
	}
	public PositionComponent(Vector3f position, float pitch, float yaw, float roll, Vector3f scale) {
		this.position = position;
		this.pitch = normalizeDegAngle(pitch);
		this.yaw = normalizeDegAngle(yaw);
		this.roll = normalizeDegAngle(roll);
		this.scale = scale;
		
		buildModelMatrix();
	}
	
	//Keep an angle between [0, 360)
	protected static float normalizeDegAngle(float angle) {
		float mod = angle % 360;
		if (mod < 0)
			mod += 360;

		return mod;
	}
	public Vector3f simulateMoveXZ(float yawDelta, float distance) {
		//Move the specified distance in the direction of yaw
		float turn = -getYaw() + yawDelta;
		//We negate the yaw angle because it is negated when forming the view matrix
		//Honestly it just works idk really
		float dx = distance * (float) Math.cos(Math.toRadians(turn));
		float dz = distance * (float) Math.sin(Math.toRadians(turn));
		return new Vector3f(dx, 0, dz);
		//setPosition(getPosition().add(new Vector3f(dx, 0, dz)));
	}
	
	public void incrementPitch(float pitch) { setPitch(getPitch() + pitch); }
	public void incrementYaw(float yaw) { setYaw(getYaw() + yaw); }
	public void incrementRoll(float roll) { setRoll(getRoll() + roll); }
	public void incrementScale(Vector3f scale) { setScale(getScale().add(scale)); }
	public void incrementPosition(Vector3f position) { setPosition(getPosition().add(position)); }
	
	public float getPitch() { return pitch; }
	public float getYaw() { return yaw; }
	public float getRoll() { return roll; }
	public Vector3f getScale() { return scale;	}
	public Vector3f getPosition() { return position; }
	public Matrix4f getModelMatrix() { return modelM; }
	
	public void setPitch(float pitch) { this.pitch = normalizeDegAngle(pitch); buildModelMatrix(); }
	public void setYaw(float yaw) { this.yaw = normalizeDegAngle(yaw); buildModelMatrix(); }
	public void setRoll(float roll) { this.roll = normalizeDegAngle(roll); buildModelMatrix(); }
	public void setPosition(Vector3f newPos) { this.position = newPos; buildModelMatrix(); }
	public void setScale(Vector3f scale) { this.scale = scale; buildModelMatrix(); }
	public void setScale(float x, float y, float z) { this.scale = new Vector3f(x,y,z); buildModelMatrix(); }
	public void setRotations(float pitch, float yaw, float roll) {
		this.pitch = normalizeDegAngle(pitch);
		this.yaw = normalizeDegAngle(yaw);
		this.roll = normalizeDegAngle(roll);
		buildModelMatrix();
	}
	//For the ViewpointComponent
	protected void setModelMatrix(Matrix4f m) { modelM = m; }
	
	public void buildModelMatrix() {
		modelM.setIdentity();
		//Matrix multiplication works backwards
		modelM = modelM.multiply(Matrix4f.translate(position.x, position.y, position.z));
		modelM = modelM.multiply(Matrix4f.scale(scale.x, scale.y, scale.z));
		modelM = modelM.multiply(Matrix4f.rotate(pitch, 1, 0, 0));
		modelM = modelM.multiply(Matrix4f.rotate(yaw, 0, 1, 0));
		modelM = modelM.multiply(Matrix4f.rotate(roll, 0, 0, 1));
	}
	
	@Override
	public void componentCleanUp() {
		modelM = null;
	}
	
	@Override 
	public String toString() {
		return "SECA PositionComponent: "
				+ " position=" + getPosition().toString()
				+ " pitch=" + getPitch()
				+ " yaw=" + getYaw()
				+ " roll=" + getRoll()
				+ " scale=" + getScale().toString();
	}
}
