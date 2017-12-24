package core;

import math.*;

public final class Intersection {
	public Vector3f point;
	public Vector3f normal;
	public final float t;
	
	public Intersection(float t, Vector3f point, Vector3f normal) {
		this.point = point;
		this.normal = normal;
		this.t = t;
	}
	
	@Override
	public String toString() {
		return new String("Point:"+point+", Normal:"+normal);
	}

}
