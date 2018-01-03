package primitive;

import core.*;
import math.*;


public class AxisAlignedBoundingBox extends BoundingVolume {

	private Vector3f minP, maxP;
	
	public AxisAlignedBoundingBox(Vector3f minP, Vector3f maxP) {
		this.minP = minP;
		this.maxP = maxP;
	}


	@Override
	public boolean testIntersect(Ray r) {
		Vector3f dirFrac = new Vector3f(
				1.0f/r.dir.x,
				1.0f/r.dir.y,
				1.0f/r.dir.z);
		
		float t1 = (minP.x - r.origin.x)*dirFrac.x;
		float t2 = (maxP.x - r.origin.x)*dirFrac.x;
		float t3 = (minP.y - r.origin.y)*dirFrac.y;
		float t4 = (maxP.y - r.origin.y)*dirFrac.y;
		float t5 = (minP.z - r.origin.z)*dirFrac.z;
		float t6 = (maxP.z - r.origin.z)*dirFrac.z;
		
		float t=0.0f;
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

	@Override
	public BoundingVolume clone() {
		return new AxisAlignedBoundingBox(minP, maxP);
	}

	@Override
	public void applyTransform(Transformation transform) {
		Matrix4 mat = transform.getMatrix();
		Vector3f p[] = new Vector3f[8];
		p[0] = new Vector3f(minP.x, minP.y, minP.z);
		p[1] = new Vector3f(maxP.x, minP.y, minP.z);
		p[2] = new Vector3f(minP.x, maxP.y, minP.z);
		p[3] = new Vector3f(minP.x, minP.y, maxP.z);
		p[4] = new Vector3f(maxP.x, maxP.y, minP.z);
		p[5] = new Vector3f(minP.x, maxP.y, maxP.z);
		p[6] = new Vector3f(maxP.x, minP.y, maxP.z);
		p[7] = new Vector3f(maxP.x, maxP.y, maxP.z);
		float lbx,lby,lbz;
		float rtx,rty,rtz;
		lbx = lby = lbz = Float.MAX_VALUE;
		rtx = rty = rtz = Float.MIN_VALUE;
		
		for(int i=0; i<8; i++) {
			Vector3f newVertex = mat.mul(p[i], 1.0f);
        	lbx = Math.min(newVertex.x, lbx);
        	rtx = Math.max(newVertex.x, rtx);
        	lby = Math.min(newVertex.y, lby);
        	rty = Math.max(newVertex.y, rty);
        	lbz = Math.min(newVertex.z, lbz);
        	rtz = Math.max(newVertex.z, rtz);
		}
		this.minP = new Vector3f(lbx, lby, lbz);
		this.maxP = new Vector3f(rtx, rty, rtz);
	}
	

}
