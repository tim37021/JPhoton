package core;

public abstract class Camera {
	public Vector2f opticalCenter;
	public Vector2f extent;
	public Camera(Vector2f extent) {
		this.extent = extent;
		this.opticalCenter = extent.mul(0.5f);
	}
	public abstract Ray shootRay(int x, int y);
}
