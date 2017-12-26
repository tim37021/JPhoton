package primitive;

import core.Intersection;
import core.Ray;
import math.MathUtils;
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
	    return MathUtils.triangleIntersection(v0, v1, v2, r);
	}

    static public Triangle CreateTriangle(String cmd) {
        // TODO
    	return null;
    }

}
