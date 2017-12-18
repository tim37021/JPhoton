package core;

import math.*;

public final class Intersection {
	public Vector3f point;
	public Vector3f normal;
	
	public Intersection(Vector3f point, Vector3f normal) {
		this.point = point;
		this.normal = normal;
	}
	
	@Override
	public String toString() {
		return new String("Point:"+point+", Normal:"+normal);
	}

}
