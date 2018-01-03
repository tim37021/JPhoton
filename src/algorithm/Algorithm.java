package algorithm;

import camera.Camera;
import core.*;


public abstract class Algorithm {
	protected Camera camera;
	protected Canvas canvas;
	protected Region region;
	public Algorithm(Canvas canvas, Camera camera)
	{
		this.camera = camera;
		this.canvas = canvas;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public void setRegion(Region region) {
		this.region = region;
	}
	
	public Region getRegion() {
		return region;
	}
	
	
	public abstract void run();
}
