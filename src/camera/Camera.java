package camera;

import core.Ray;
import core.Vector2f;
import core.Vector3f;

public abstract class Camera {
	protected Vector2f opticalCenter;
	protected Vector2f extent;
	public Vector3f position;
	
	public Camera(Vector2f extent) {
		this.extent = extent;
		this.opticalCenter = extent.mul(0.5f);
	}
	
	public void setOpticalCenter(Vector2f center) {
		this.opticalCenter = center;
	}
	
	public Vector2f getExtent() {
		return extent;
	}
	
	public abstract Ray shootRay(int x, int y);
	public abstract void lookAt(Vector3f origin, Vector3f center, Vector3f up);
}
