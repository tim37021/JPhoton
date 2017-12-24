package algorithm;

import camera.Camera;
import core.Canvas;

public abstract class Algorithm {
	protected Camera camera;
	protected Canvas canvas;
	protected int x0, y0, x1, y1;
	public Algorithm(Canvas canvas, Camera camera)
	{
		this.camera = camera;
		this.canvas = canvas;
	}
	
	public void setRegion(int x0, int y0, int x1, int y1) {
		this.x0 = x0;
		this.x1 = x1;
		this.y0 = y0;
		this.y1 = y1;
	}
	
	public abstract void run();
}
