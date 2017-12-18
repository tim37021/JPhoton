package primitive;

import core.*;

public abstract class Entity {
	protected String name;
	private final int id;
	static private int COUNT;  
	public Entity(String name) {
		this.name = name;
		this.id = COUNT++;
	}
	public abstract Intersection intersect(Ray r);
}
