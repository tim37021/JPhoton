package primitive;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.*;
import math.*;

public class AxisAlignedRectangle extends Entity {

	static public final int XY_PLANE = 0;
	static public final int YZ_PLANE = 1;
	static public final int XZ_PLANE = 2;
	static private final Vector3f[] NORMALS = new Vector3f[] {new Vector3f(0, 0, 1), new Vector3f(1, 0, 0), new Vector3f(0, 1, 0)};
	
	private int plane;
	private Vector2f regionMin, regionMax;
	private float point;
	
	private static Pattern regex;
	static {
		regex = Pattern.compile("(XY|YZ|XZ) \\(([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+)\\) \\(([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+)\\) ([+-]?[0-9]*[.]?[0-9]+)");
	}
	
	
	public AxisAlignedRectangle(String name, int plane, Vector2f regionMin, Vector2f regionMax, float point) {
		super(name);
		// TODO Auto-generated constructor stub
		this.plane = plane;
		this.regionMin = regionMin;
		this.regionMax = regionMax;
		this.point = point;
	}

	@Override
	public Intersection intersect(Ray r) {
		// TODO Auto-generated method stub
		float t=-1;
		Vector2f proj_point=null;
		switch(plane) {
		case XY_PLANE: // z = point
			if(Math.abs(r.dir.z) < MathUtils.EPSILON)
				return null;
			t = (point-r.origin.z)/r.dir.z;
			proj_point = new Vector2f(r.origin.x+t*r.dir.x, r.origin.y+t*r.dir.y);
			break;
		case YZ_PLANE: // x = point
			if(Math.abs(r.dir.x) < MathUtils.EPSILON)
				return null;
			t = (point-r.origin.x)/r.dir.x; 
			proj_point = new Vector2f(r.origin.y+t*r.dir.y, r.origin.z+t*r.dir.z);
			break;
		case XZ_PLANE: // y = point
			if(Math.abs(r.dir.z) < MathUtils.EPSILON)
				return null;
			t = (point-r.origin.y)/r.dir.y;
			proj_point = new Vector2f(r.origin.x+t*r.dir.x, r.origin.z+t*r.dir.z);
			break;
		}
		// behind..
		if(t<0)
			return null;
		
		// check if region2 is within region
		if(proj_point.x < regionMin.x || proj_point.y < regionMin.y )
			return null;
		if(proj_point.x > regionMax.x || proj_point.y > regionMax.y )
			return null;
		
        // P = orig + t * dir
        Vector3f P = new Vector3f(t * r.dir.x,
                                  t * r.dir.y,
                                  t * r.dir.z).add(r.origin);
        Vector3f N = NORMALS[plane];
        if(N.dot(r.dir)>0)
        	N = new Vector3f(-N.x, -N.y, -N.z);
		
		return new Intersection(t, P, N);
	}
	
	// XY|XZ|YZ (min2d) (max2d) point
	static public AxisAlignedRectangle create(String cmd) {
		String name = cmd.split(" ")[0];
		cmd = cmd.substring(name.length()+1);
		Matcher m = regex.matcher(cmd);
		if(m.find()) {
			String plane = m.group(1);
			float x0 = Float.parseFloat(m.group(2));
			float y0 = Float.parseFloat(m.group(3));
			float x1 = Float.parseFloat(m.group(4));
			float y1 = Float.parseFloat(m.group(5));
			float p = Float.parseFloat(m.group(6));
			
			int plane_int = 0;
			if(plane.equals("XY"))
				plane_int = XY_PLANE;
			if(plane.equals("YZ"))
				plane_int = YZ_PLANE;
			if(plane.equals("XZ"))
				plane_int = XZ_PLANE;
			
			return new AxisAlignedRectangle(name, plane_int, new Vector2f(x0, y0), new Vector2f(x1, y1), p);
			
		}
		return null;
	}
	
	static public void main(String args[]) {
		AxisAlignedRectangle rectangle = create("GG XY (-1 -1) (1 1) -5");
		System.out.println(rectangle.intersect(new Ray(new Vector3f(0, 0, -10), new Vector3f(0, 0, 1))));
	}

	@Override
	public void applyTransform(Transformation transform) {
		// TODO Auto-generated method stub
		// NOT SUPPORT
	}
}
