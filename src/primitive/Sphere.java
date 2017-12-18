package primitive;

import core.Intersection;
import core.Ray;
import core.Vector3f;

public class Sphere extends Entity {
	public float radius;
	public Vector3f center;
	static private int id;  
	public Sphere(float radius, Vector3f center) {
		super("sphere");
		this.radius = radius;
		this.center = center;
	}
	
	@Override
	public Intersection intersect(Ray r) {
		// check if the ray hit the bounding volume
		
		
		return null;
		
	}
	
	static public Sphere CreateSphere(String cmd) {
		// TODO
		return null;
	}

}
