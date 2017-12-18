package camera;

import core.Ray;
import math.Vector2f;
import math.Vector3f;

public class PerspectiveCamera extends Camera {
	public float fov;
	public float aspect;
	public float near;
	
	public Vector3f front;
	public Vector3f up;
	public Vector3f right;
	
	// cached variable
	private Vector3f opticalCenter3d;
	private Vector2f pixelWidth;
	//////////////////
	
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
		Vector3f imagePoint = opticalCenter3d.add(right.mul(dxAndDy.x*pixelWidth.x).add(up.mul(-dxAndDy.y*pixelWidth.y)));
		
		Vector3f dir = imagePoint.sub(position);
		return new Ray(imagePoint, dir.normalized());
	}


	@Override
	public void lookAt(Vector3f origin, Vector3f center, Vector3f up) {
		// TODO Auto-generated method stub
		position = origin;
		this.front = center.sub(origin);
		this.right = front.crossProduct(up);
		this.up = right.crossProduct(this.front);
		updateCachedVariables();
	}
	
	private void updateCachedVariables() {
		Vector3f nf = front.mul(near);
		opticalCenter3d = position.add(nf);
		
		float ratio = 2 * near * (float)Math.tan(fov/2) / extent.y;
		pixelWidth = new Vector2f(ratio*aspect, ratio);
	}

}
