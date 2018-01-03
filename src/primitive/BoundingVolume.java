package primitive;

import core.ITransformable;
import core.Ray;

public abstract class BoundingVolume implements ITransformable {

	public BoundingVolume() {

	}
	
	public abstract boolean testIntersect(Ray r);
	public abstract BoundingVolume clone();
}
