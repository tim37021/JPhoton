package primitive;

import core.Intersection;
import core.Ray;
import math.Vector3f;


public class AxisAlignedBoundingBox extends BoundingVolume {

	public AxisAlignedBoundingBox(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Intersection intersect(Ray r) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean meshIsIntersect(Mesh m, Ray r) {
		
		Vector3f dirFrac = new Vector3f(
				1.0f/r.dir.x,
				1.0f/r.dir.y,
				1.0f/r.dir.z);
		
		float lbx,lby,lbz;
		float rtx,rty,rtz;
		lbx = lby = lbz = Float.MAX_VALUE;
		rtx = rty = rtz = -Float.MAX_VALUE;

		for(Vector3f point : m.triangles) {
			if(point.x > lbx)  lbx = point.x;
			else rtx = point.x;
			
			if(point.y > lby)  lby = point.y;
			else rty = point.y;
			
			if(point.z > lby)  lbz = point.z;
			else rtz = point.z;
		}
		
		float t1 = (lbx - r.origin.x)*dirFrac.x;
		float t2 = (rtx - r.origin.x)*dirFrac.x;
		float t3 = (lby - r.origin.y)*dirFrac.y;
		float t4 = (rty - r.origin.y)*dirFrac.y;
		float t5 = (lbz - r.origin.z)*dirFrac.z;
		float t6 = (rtz - r.origin.z)*dirFrac.z;
		
		float t;
		float tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
		float tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));
		
		if (tmax < 0)
		{
		    t = tmax;
		    return false;
		}

		// if tmin > tmax, ray doesn't intersect AABB
		if (tmin > tmax)
		{
		    t = tmax;
		    return false;
		}

		t = tmin;
		return true;

	}
	
}
