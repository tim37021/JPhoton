package primitive;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.Intersection;
import core.Ray;
import core.Transformation;
import math.Vector3f;

public class Sphere extends Entity {
	public float radius;
	public Vector3f center;
	static private int id;
	static Pattern regex;
	static {
	
		regex = Pattern.compile("\\(([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+)\\) ([+-]?[0-9]*[.]?[0-9]+)");
	}
	
	public Sphere(String name, float radius, Vector3f center) {
		super(name);
		this.radius = radius;
		this.center = center;
	}
	
	@Override
	public Intersection intersect(Ray r) {
		/*
		// check if the ray hit the bounding volume
		// S = o-c
		Vector3f s = r.origin.sub(center);
		float s2 = s.dot(r.dir);
		float d2 = r.dir.length2();
		float D = 4*(s2*s2 - d2*(s.length2() - radius*radius));
		if(D < 0)
			return null;
		float t1 = (2*s2-(float)Math.sqrt(D))/(2*d2);
		float t2 = (2*s2+(float)Math.sqrt(D))/(2*d2);
		float t = t1;
		if(t1 < 0)
			t = t2;
		
        // P = orig + t * dir
        Vector3f P = new Vector3f(t * r.dir.x,
                                  t * r.dir.y,
                                  t * r.dir.z).add(r.origin);

        Vector3f N = P.sub(center).normalized();
		*/
    	float t;
    	Vector3f l = center.sub(r.origin);
    	float s = l.dot(r.dir);
    	float l2 = l.length2();
    	float r2 = radius*radius;

    	if(s<0 && l2 > r2)
    		return null;
    	float m2 = l2 - s*s;
    	if(m2>r2)
    		return null;
    	float q = (float)Math.sqrt(r2 - m2);
    	if(l2>r2)
    		t = s - q;
    	else
    		t = s + q;

        Vector3f P = new Vector3f(t * r.dir.x,
                t * r.dir.y,
                t * r.dir.z).add(r.origin);

		Vector3f N = P.sub(center).normalized();
        
		if(N.dot(r.dir) > 0) {
			System.out.println("You shoot inside the ball.");
		}
		
		return new Intersection(t, P, N);
		
	}
	
	// spawn sphere (x y z) r
	// (x y z) r
	static public Sphere create(String cmd) {
		String name = cmd.split(" ")[0];
		cmd = cmd.substring(name.length()+1);
		Matcher m = regex.matcher(cmd);
		if(m.find()) {
			float x = Float.parseFloat(m.group(1));
			float y = Float.parseFloat(m.group(2));
			float z = Float.parseFloat(m.group(3));
			float r = Float.parseFloat(m.group(4));
			
			return new Sphere(name, r, new Vector3f(x, y, z));
			
		}
		return null;

	}
	
	static public void main(String args[]) {
		
	}

	@Override
	public void applyTransform(Transformation transform) {
		// TODO Auto-generated method stub
		
	}

}
