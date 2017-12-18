package app;

import camera.*;
import core.*;
import math.*;
import primitive.*;

public class JPhoton {
	static private Triangle tri;
	static private Vector3f radiance(Ray r) {
		if(tri.intersect(r) != null)
			return new Vector3f(1, 1, 1);
		else
			return new Vector3f(0, 0, 0);
	}
	
    static public void main(String args[]) {
    	Canvas canvas = new Canvas(800, 600);
    	Camera cam = new PerspectiveCamera(45.0f*3.1415f/180.0f, 1.f, new Vector2f(800, 600));
    	tri = new Triangle(new Vector3f(-2, -2, 5), new Vector3f(2, -2, 5), new Vector3f(0,  2, 5));
    
    	for(int y=0; y<600; y++) {
    		for(int x=0; x<800; x++) {
    			Ray r = cam.shootRay(x, y);
    			canvas.setPixel(x, y, radiance(r));
    		}
    	}
    	canvas.saveToFile("outImg/GGVERYIN.png");
    }
}
