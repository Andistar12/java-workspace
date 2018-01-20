package com.saja.seca.back;

/**
 * @author Andy Nguyen
 *
 * The definition of a Component, in the back engine of SECA
 * A Component just has one Entity to reference 
 * Subclasses of Component deal with manipulation of its data, as well as interaction 
 * 		with other Components in the linked Entity. 
 * There is no update() of any sort because game/logic engines deal with that
 * Component is just a framework class, like the rest of the back package
 * 
 * One method has to be declared in subclasses:
 * componentCleanUp(); - This method cleans up the component. Note that only cleanUp() has public access
 * 
 * Calling cleanUp() on a System also cleans up all its entities and components
 * 
 * Note that none of the classes in the back package can be created, only subclasses of it can
 */

public abstract class Component {

	private Entity entity;
		
	public Entity getEntity() { return entity; }
	public void setEntity(Entity e) { entity = e; }
	
	protected abstract void componentCleanUp(); //Subclasses must address this
	protected void clearEntity() {
		entity = null;
	}
	public void cleanUp() {
		clearEntity();
		componentCleanUp();
	}
	
	@Override
	public String toString() {
		if (entity == null) return "SECA Component with no set entity";
		return "SECA Component with one set entity";
	}
}
