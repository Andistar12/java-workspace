package com.saja.seca.back;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * @author Andy Nguyen
 *
 * The definition of an Entity, in the back engine of SECA
 * An Entity is a collection of Components, and the proper getters/setters/adders/etc
 * Subclasses of Entity deal with Component organization and such
 * 
 * Two methods have to be declared in subclasses:
 * isComponentAddable(Component c); - This method decides what types of components are able to be added to the Entity
 * entityCleanUp(); - This method cleans up the entity. Note that only cleanUp() has public access; cleanUp() calls entityCleanUp()
 * 
 * The methods & structure in System are similar to Entity, as well as use of Reflection
 * 
 * Calling cleanUp() on a System also cleans up all its entities and components
 * 
 * Note that none of the classes in the back package can be created, only subclasses of it can
 */

public abstract class Entity {
	private ArrayList<Component> components = new ArrayList<Component>();
	
	public abstract boolean isComponentAddable(Component c); //subclasses must address this
	
	public int componentCount() {
		return components.size();
	}
	
	public void addComponent(Component c) {
		//Setting the referenced entity of the new component is already done here
		//There's no need to do it elsewhere
		if ( isComponentAddable(c) ) components.add(c);
		c.setEntity(this);
	}
	public void removeComponent(Component c) {
		components.remove(c);
	}
	public void removeAllComponents(Class<?> componentType) {
		for (Component c : components) {
			if (componentType.isInstance(c)) removeComponent(c);
		}
	}
	
	public Component getFirstComponentInstance(Class<?> componentType) {
		Iterator<Component> comps = components.iterator();
		Component c;
		while (comps.hasNext()) {
			c = comps.next();
			if (componentType.isInstance(c)) return c;
		}
		return null;
	}
	public Component[] getAllComponent() { return components.toArray(new Component[0]); }
	public Component[] getAllComponents(Class<?> componentType) {
		ArrayList<Component> compsToReturn = new ArrayList<Component>();
		
		Iterator<Component> comps = components.iterator();
		Component c;
		while (comps.hasNext()) {
			c = comps.next();
			if (componentType.isInstance(c)) compsToReturn.add(c);
		}
		
		return compsToReturn.toArray(new Component[0]);
	}
	public Component getRandomComponent(Class<?> componentType, Random randomGen) {
		//A generator has to be supplied, each entity will not have its own Random
		Component[] comps = getAllComponents(componentType);
		return comps[randomGen.nextInt(comps.length)];
	}
	
	protected abstract void entityCleanUp(); //Subclasses must address this
	protected void clearComponents() {
		for (Component c : components) c.cleanUp();
		components.clear();
	}
	public void cleanUp() {
		entityCleanUp();
		clearComponents();
	}
	
	@Override
	public String toString() {
		return "SECA Entity with " + components.size() + " components";
	}
}
