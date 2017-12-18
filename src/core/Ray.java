package core;

import math.*;

public class Ray {
	public Vector3f origin;
	public Vector3f dir;
	
	public Ray(Vector3f origin, Vector3f dir) {
		this.origin = origin;
		this.dir = dir;
	}
	
	@Override
	public String toString() {
		return new String("Origin: " + origin + ", Direction:" + dir);
	}
}
