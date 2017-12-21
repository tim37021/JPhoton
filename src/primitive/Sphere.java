package primitive;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.Intersection;
import core.Ray;
import math.Vector3f;

public class Sphere extends Entity {
	public float radius;
	public Vector3f center;
	static private int id;
	static Pattern regex;
	static {
		regex = Pattern.compile("\\(([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+)\\) ([+-]?[0-9]*[.]?[0-9]+)");
	}
	
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
	
	// (x y z) r
	static public Sphere CreateSphere(String cmd) {
		Matcher m = regex.matcher(cmd);
		if(m.find()) {
			float x = Float.parseFloat(m.group(1));
			float y = Float.parseFloat(m.group(2));
			float z = Float.parseFloat(m.group(3));
			float r = Float.parseFloat(m.group(4));
			
			return new Sphere(r, new Vector3f(x, y, z));
			
		}
		return null;

	}
	
	static public void main(String args[]) {
		
	}

}
