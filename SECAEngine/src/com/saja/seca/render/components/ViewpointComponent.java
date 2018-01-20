package com.saja.seca.render.components;

import com.saja.seca.math3d.Matrix4f;
import com.saja.seca.math3d.Vector3f;

/**
 * @author Andy Nguyen
 *
 * AKA a Camera, ViewpointComponent has a projection and view matrix
 * ProjM variables: aspect, near plane, far plane, FOV
 * ViewM variables: position, pitch, yaw, roll
 * 
 * ViewpointComponent extends PositionComponent, where buildViewMatrix() is rerouted to buildModelMatrix()
 * The model matrix from PositionComponent is used as a view matrix in ViewpointComponent
 * 
 * Also includes a CameraMode, which can be changed to change the 3D person-view
 * FIRST: view matrix rotates as first person
 * SECOND: view matrix rotates while facing a person
 * THIRD: view matrix rotates with camera behind the person
 * 
 * Changing the ViewpointComponent aspectRatio does not call glViewport
 */

public class ViewpointComponent extends PositionComponent {
	
	public static enum CameraMode { FIRST, SECOND, THIRD };
	
	private float aspectRatio, nearPlane, fieldOfView, farPlane;

	private float camRadiusThirdPerson;
	private CameraMode camMode = CameraMode.FIRST;
	
	private Matrix4f projM = new Matrix4f();
	
	public ViewpointComponent(Vector3f position, float pitch, float yaw, float roll, 
			int width, int height, float nearPlane, float farPlane, float FOV, float camRadiusThirdPerson) {
		super(position, pitch, yaw, roll, new Vector3f(1,1,1));

		this.aspectRatio = width / height;
		this.nearPlane = nearPlane;
		this.farPlane = farPlane;
		this.fieldOfView = FOV;
		this.camRadiusThirdPerson = camRadiusThirdPerson;

		changeAspect(width, height); //Contains a buildProjM();
	}
	
	public Matrix4f getViewMatrix() { return getModelMatrix(); }
	public Matrix4f getProjectionMatrix() { return projM; }
	public Matrix4f getViewProjectionMatrix() { return getProjectionMatrix().multiply(getViewMatrix()); }
	public float getThirdPersonCamRadius() { return camRadiusThirdPerson; }
	public CameraMode getCameraMode() { return camMode; }
	public float getFOV() { return fieldOfView; }
	public float getAspect() { return aspectRatio; }
	public Vector3f getCameraPosition() {
		
		//TODO do
		float pitch = normalizeDegAngle(getPitch() + 90);//-getPitch() + 270;
		float yaw = getYaw();
		pitch = 0;
		System.out.println("SECA ViewpointComponent: Sphere camera pitch is " + pitch);
		System.out.println("SECA ViewpointComponent: Sphere camera yaw is " + yaw);
		//Math.PI/360f * 
		float x = (float) (getThirdPersonCamRadius() * Math.cos(yaw) * Math.sin(pitch * Math.PI / 360));
		float z = (float) (getThirdPersonCamRadius() * Math.cos(pitch * Math.PI / 360));
		float y = (float) (getThirdPersonCamRadius() * Math.sin(getYaw()) * Math.sin(pitch * Math.PI / 360));
		
		
		
		return new Vector3f(x, y, z);
	}
	
	public void incrementPitch(float inc) { setPitch(getPitch() + inc); }
	public void incrementYaw(float inc) { setYaw(getYaw() + inc); }
	public void incrementRoll(float inc) { setRoll(getRoll() + inc); }
	public void incrementFOV(float inc) { setFOV(getFOV() + inc); }
	
	public void moveXZ(float yawDelta, float distance) {
		//Move the specified distance in the direction of yaw
		float turn = -getYaw() + yawDelta;
		//We negate the yaw angle because it is negated when forming the view matrix
		//Honestly it just works idk really
		float dx = distance * (float) Math.cos(Math.toRadians(turn));
		float dz = distance * (float) Math.sin(Math.toRadians(turn));
		setPosition(getPosition().add(new Vector3f(dx, 0, dz)));
	}
	
	public void setPitch(float pitch) {
		//For convenience, pitch is kept between -90 (straight down) and 90 (straight up) in ViewpointComponent
		
		float p = super.normalizeDegAngle(pitch);
		
		if (0 <= p & p <= 180) {
			if (p > 90) p = 90;
		}
		if (180 < p & p < 360){
			if (p < 270) p = 270;
		};
		
		super.setPitch(p);
	}
	public void changeAspect(int width, int height) { setAspect((float)width/height); buildProjectionMatrix(); }
	public void setAspect(float aspect) { this.aspectRatio = aspect; buildProjectionMatrix(); }
	public void setThirdPersonCamRadius(float newRadius) { this.camRadiusThirdPerson = newRadius; buildProjectionMatrix(); }
	public void setCameraMode(CameraMode camMode) { this.camMode = camMode; buildViewMatrix(); }
	public void setFOV(float newFOV) { this.fieldOfView = newFOV; buildProjectionMatrix(); }
	
	public void buildProjectionMatrix() {
		projM = Matrix4f.perspective(fieldOfView, aspectRatio, nearPlane, farPlane);
	}
	
	public void buildModelMatrix() {
		if (projM != null) {
			//When the program is initiated, modelM is called before settings the camera mode
			//We don't want a NullPointerException when we check the camera mode
			Matrix4f viewM = getModelMatrix();
			
			viewM.setIdentity();
			
			switch(this.getCameraMode()) {
			case FIRST:
				viewM = viewM.multiply(Matrix4f.rotate(-getPitch(), 1, 0, 0));
				viewM = viewM.multiply(Matrix4f.rotate(-getYaw(), 0, 1, 0));
				viewM = viewM.multiply(Matrix4f.rotate(-getRoll(), 0, 0, 1));
				viewM = viewM.multiply(Matrix4f.translate(-getPosition().x, -getPosition().y, -getPosition().z));
				break;
			case SECOND:
				viewM = viewM.multiply(Matrix4f.rotate(180, 0, 1, 0));
				viewM = viewM.multiply(Matrix4f.translate(0, 0, getThirdPersonCamRadius()));
					
				viewM = viewM.multiply(Matrix4f.rotate(-getPitch(), 1, 0, 0));
				viewM = viewM.multiply(Matrix4f.rotate(-getYaw(), 0, 1, 0));
				viewM = viewM.multiply(Matrix4f.rotate(-getRoll(), 0, 0, 1));
					
				viewM = viewM.multiply(Matrix4f.translate(-getPosition().x, -getPosition().y, -getPosition().z));
				break;
			case THIRD:
				viewM = viewM.multiply(Matrix4f.translate(0, 0, -getThirdPersonCamRadius()));
				
				viewM = viewM.multiply(Matrix4f.rotate(-getPitch(), 1, 0, 0));
				viewM = viewM.multiply(Matrix4f.rotate(-getYaw(), 0, 1, 0));
				viewM = viewM.multiply(Matrix4f.rotate(-getRoll(), 0, 0, 1));
				
				viewM = viewM.multiply(Matrix4f.translate(-getPosition().x, -getPosition().y, -getPosition().z));
				break;
			}
			super.setModelMatrix(viewM);
		}
	}
	public void buildViewMatrix() {
		//Reroute to model matrix, 
		buildModelMatrix();
	}
	
	@Override
	public void componentCleanUp() {
		super.componentCleanUp();
		projM = null;
	}
	
	@Override
	public String toString() {
		return "SECA ViewpointComponent: "
				+ " position=" + getPosition().toString()
				+ " pitch=" + getPitch()
				+ " yaw=" + getYaw()
				+ " roll=" + getRoll()
				+ " nearPlane=" + nearPlane
				+ " farPlane=" + farPlane 
				+ " FOV=" + getFOV()
				+ " aspectRatio=" + getAspect()
				+ " camRadius=" + camRadiusThirdPerson
				+ " cameraMode=" + getCameraMode().toString();
	}
}
