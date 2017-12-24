package camera;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.*;
import math.*;
import primitive.Sphere;

public class PerspectiveCamera extends Camera {
	public float fov;
	public float aspect;
	public float near;
	
	public Vector3f front;
	public Vector3f up;
	public Vector3f right;
	
	// cached variable
	private Vector3f opticalCenter3d;
	private float pixelWidth;
	//////////////////
	
	static Pattern regex;
	static {
	
		regex = Pattern.compile("([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+) \\(([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+)\\)");
	}
	
	
	public PerspectiveCamera(float fov, float near, Vector2f extent) {
		super(extent);
		// look at +z axis at origin
		
		this.fov = fov;
		this.aspect = extent.x/extent.y;
		this.near = near;
		lookAt(new Vector3f(0, 0, 0), new Vector3f(0, 0, 1), new Vector3f(0, 1, 0));
	}
	
	

	@Override
	public Ray shootRay(int x, int y) {
		// first we calculate optical center in world space
		Vector2f dxAndDy = (new Vector2f(x, y)).sub(opticalCenter);
		// (right * dx * pixelWidth.x + up * -dy * pixelWidth.y) + opticalCenter3d
		Vector3f imagePoint = opticalCenter3d.add(right.mul(dxAndDy.x*pixelWidth).add(up.mul(-dxAndDy.y*pixelWidth)));
		
		Vector3f dir = imagePoint.sub(position);
		return new Ray(imagePoint, dir.normalized());
	}


	@Override
	public void lookAt(Vector3f origin, Vector3f center, Vector3f up) {
		// TODO Auto-generated method stub
		position = origin;
		this.front = center.sub(origin).normalized();
		this.right = front.crossProduct(up.normalized());
		this.up = right.crossProduct(this.front);
		updateCachedVariables();
	}
	
	private void updateCachedVariables() {
		Vector3f nf = front.mul(near);
		opticalCenter3d = position.add(nf);
		
		pixelWidth = 2 * near * (float)Math.tan(fov/2) / extent.y;
	}
	
	static public Camera create(String cmd) {
		// fov near extent
		Matcher m = regex.matcher(cmd);
		if(m.find()) {
			float fov = Float.parseFloat(m.group(1));
			float near = Float.parseFloat(m.group(2));
			float extent_x = Float.parseFloat(m.group(3));
			float extent_y = Float.parseFloat(m.group(4));
			
			return new PerspectiveCamera(fov*(float)Math.PI/180, near, new Vector2f(extent_x, extent_y));
			
		}
		return null;
	}

}
