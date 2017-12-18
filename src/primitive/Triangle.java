package primitive;

import core.Intersection;
import core.Ray;
import math.Vector3f;

public class Triangle extends Entity {
    public Vector3f v0;   
    public Vector3f v1;   
    public Vector3f v2;   

    public Triangle(Vector3f v0, Vector3f v1, Vector3f v2) {
        super("triangle");
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
    }

	@Override
	public Intersection intersect(Ray r) {
        Vector3f v0v1 = v1.sub(v0);
        Vector3f v0v2 = v2.sub(v0);

        Vector3f N = v0v1.crossProduct(v0v2);

        // The ray and plane are parallel or not
        float almostZero = 0.0f; // TODO: to be set 
        float nDotDir = N.dot(r.dir);
        if(Math.abs(nDotDir) < almostZero)
            return null;

        float d = N.dot(v0);
        float t = (N.dot(r.origin) + d) / nDotDir;

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
        
        return new Intersection(P, N.normalized());

	}

    static public Triangle CreateTriangle(String cmd) {
        // TODO
    	return null;
    }

}
