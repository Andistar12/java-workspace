package com.saja.seca.back;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * @author Andy Nguyen
 * 
 * The definition of a System, in the back engine of SECA
 * An System is a collection of Entities, and the proper getters/setters/adders/etc
 * Subclasses of Entity deal with Entity organization and such
 * 
 * Two methods have to be declared in subclasses:
 * isEntityAddable(Entity e); - This method decides what types of components are able to be added to the Entity
 * systemCleanUp(); - This method cleans up the system. Note that only cleanUp() has public access
 * 
 * The methods & structure in System are similar to Entity, as well as use of Reflection
 * 
 * Calling cleanUp() on a System also cleans up all its entities and components
 * 
 * Note that none of the classes in the back package can be created, only subclasses of it can
 */

public abstract class System {
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public abstract boolean isEntityAddable(Entity e); //Subclasses have to address this
	
	public int entityCount() {
		return entities.size();
	}
	
	public void addEntity(Entity e) {
		if ( isEntityAddable(e) ) entities.add(e);
	}
	public void removeEntity(Entity e) {
		entities.remove(e);
	}
	public void removeAllEntities(Class<?> entityType) {
		Iterator<Entity> ents = entities.iterator();
		Entity e;
		while (ents.hasNext()) {
			e = ents.next();
			if (entityType.isInstance(e)) ents.remove();
		}
	}
	
	public Entity getFirstEntityInstance(Class<?> entityType) {
		Iterator<Entity> ents = entities.iterator();
		Entity e;
		while (ents.hasNext()) {
			e = ents.next();
			if (entityType.isInstance(e)) return e;
		}
		return null;
	}
	public Entity[] getAllEntities() { return entities.toArray(new Entity[0]); }
	public Entity[] getAllEntities(Class<?> entityType) {
		ArrayList<Entity> entsToReturn = new ArrayList<Entity>();
		
		Iterator<Entity> ents = entities.iterator();
		Entity e;
		while (ents.hasNext()) {
			e = ents.next();
			if (entityType.isInstance(e)) entsToReturn.add(e);
		}
		
		return entsToReturn.toArray(new Entity[0]);
	}
	public Entity getRandomEntity(Class<?> entityType, Random randomGen) {
		//A generator has to be supplied, each entity will not have its own Random
		Entity[] ents = getAllEntities(entityType);
		return ents[randomGen.nextInt(ents.length)];
	}
	
	protected abstract void systemCleanUp();
	protected void clearEntities() {
		entities.clear();
	}
	public void cleanUp() {
		systemCleanUp();
		for (Entity e : entities) e.cleanUp();
		clearEntities();
	}
	
	@Override
	public String toString() {
		return "SECA System with " + entities.size() + " entities";
	}
}
