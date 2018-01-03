package primitive;

import core.*;
import material.*;

public abstract class Entity implements ITransformable {
	protected String name;
	private final int id;
	private Material material;
	static private int COUNT;  
	public Entity(String name) {
		this.name = name;
		this.id = COUNT++;
		setMaterial(Material.DEFAULTMATERIAL);
	}
	
	public String getName() {
		return name;
	}
	
	public abstract Intersection intersect(Ray r);


	public Material getMaterial() {
		return material;
	}


	public void setMaterial(Material material) {
		this.material = material;
	}
}
