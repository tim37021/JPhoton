package primitive;

import core.Intersection;
import core.Ray;
import core.Transformation;

public class KdTree extends Entity{
	
	public KdTree(Mesh m) {
		super("kdTree");
		//create tree here
	}
	
	@Override
	public Intersection intersect(Ray r) {
		// skip
		return null;
	}

	@Override
	public void applyTransform(Transformation transform) {
		// TODO Auto-generated method stub
		// it is annoying here
	}
	
}