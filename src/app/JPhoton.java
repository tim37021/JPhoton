package app;

import camera.*;
import core.*;

public class JPhoton{
	static private Vector3f radiance(Ray r) {
		return new Vector3f(1, 1, 1);
	}
	
    static public void main(String args[]) {
    	Canvas canvas = new Canvas(800, 600);
    	Camera cam = new PerspectiveCamera(45.0f*3.1415f/180.0f, 0.1f, new Vector2f(800, 600));
    	for(int y=0; y<600; y++) {
    		for(int x=0; x<800; x++) {
    			Ray r = cam.shootRay(x, y);
    			canvas.setPixel(x, y, radiance(r));
    		}
    	}
    	canvas.saveToFile("GGVERYIN.png");
    }
}
