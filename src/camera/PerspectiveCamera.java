package camera;

import core.Ray;
import core.Vector2f;
import core.Vector3f;

public class PerspectiveCamera extends Camera {
	public float fov;
	public float near;
	public float far;
	
	public Vector3f front;
	public Vector3f up;
	
	public PerspectiveCamera(float fov, float near, float far, Vector2f extent) {
		super(extent);	
	}
	
	

	@Override
	public Ray shootRay(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void lookAt(Vector3f origin, Vector3f center, Vector3f up) {
		// TODO Auto-generated method stub
		position = origin;
		front = center.sub(origin);
	}

}
