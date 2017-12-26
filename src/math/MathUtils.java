package math;

import java.util.Random;

import core.Intersection;
import core.Ray;

public class MathUtils {
	public static final float DIV_1_PI = 1.0f/(float)Math.PI;
	public static final float MUL_2_PI = 2.0f*(float)Math.PI;
	public static final float EPSILON = 1e-4f;
	public static Random rand;
	
	static {
		rand = new Random();
	};
	public static Vector3f clamp(Vector3f v, float min, float max) {
		float x = Math.min(Math.max(v.x, min), max);
		float y = Math.min(Math.max(v.y, min), max);
		float z = Math.min(Math.max(v.z, min), max);
		return new Vector3f(x, y, z);
	}
	public static Vector3f uniformHemisphereSampling(float u, float v) {
		float phi = 2*MUL_2_PI*v;
		float T = (float)Math.sqrt(1-(1-u)*(1-u));
	    return new Vector3f((float)(Math.cos(phi)*T), (float)(Math.sin(phi)*T), 1-u);
	}
	
	// TBN * vec
	public static Vector3f changeBasis(Vector3f T, Vector3f B, Vector3f N, Vector3f vec) {
		float x = T.x * vec.x + B.x * vec.y + N.x * vec.z;
		float y = T.y * vec.x + B.y * vec.y + N.y * vec.z;
		float z = T.z * vec.x + B.z * vec.y + N.z * vec.z;
		return new Vector3f(x, y, z);
	}
	
    public static Intersection triangleIntersection(Vector3f v0, Vector3f v1, Vector3f v2, Ray r) {
        Vector3f v0v1 = v1.sub(v0);
        Vector3f v0v2 = v2.sub(v0);

        Vector3f N = v0v1.crossProduct(v0v2);

        // The ray and plane are parallel or not
        float almostZero = 0.0f; // TODO: to be set 
        float nDotDir = N.dot(r.dir);
        if(Math.abs(nDotDir) < almostZero)
            return null;

        float d = -N.dot(v0);
        float t = -(N.dot(r.origin) + d) / nDotDir;

        if(t < 0.0)
            return null;

        // P = orig + t * dir
        Vector3f P = new Vector3f(t * r.dir.x,
                                  t * r.dir.y,
                                  t * r.dir.z).add(r.origin);

        // inside-outside test
        Vector3f C;

        // edge 0 
        Vector3f edge0 = v1.sub(v0);
        Vector3f vp0 = P.sub(v0);
        C = edge0.crossProduct(vp0);
        if(N.dot(C) < 0)
            return null;

        // edge 1
        Vector3f edge1 = v2.sub(v1);
        Vector3f vp1 = P.sub(v1);
        C = edge1.crossProduct(vp1);
        if(N.dot(C) < 0)
            return null;

        // edge 2
        Vector3f edge2 = v0.sub(v2);
        Vector3f vp2 = P.sub(v2);
        C = edge2.crossProduct(vp2);

        if(N.dot(C) < 0)
            return null;
        
        // normal vector shold reverse to ray direction
        Vector3f rN = N.dot(r.dir) < 0 ? N : new Vector3f(-N.x ,-N.y, -N.z);
        return new Intersection(t, P, rN.normalized());
    }
}
