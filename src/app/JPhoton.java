package app;

import camera.*;
import core.*;
import math.*;
import primitive.*;

public class JPhoton {
	static private Triangle tri;
	static private Vector3f radiance(Ray r) {
		Intersection itsec = tri.intersect(r);
		if(itsec != null)
			return itsec.normal;
		else
			return new Vector3f(0.0f);
	}
	
    static public void main(String args[]) {
    	Canvas canvas = new Canvas(800, 600);
    	Camera cam = new PerspectiveCamera(3.1415926f/4, 0.1f, new Vector2f(800, 600));
    	cam.lookAt(new Vector3f(0, 0, 0f), new Vector3f(0, 0, 1), new Vector3f(0, 1, 0));
    	
    	tri = new Triangle(new Vector3f(-2, -2, 10), new Vector3f(2, -2, 10), new Vector3f(0,  2, 10));
    	Ray rr = cam.shootRay(0, 0);
    	System.out.println(rr);
    	
    	for(int y=0; y<600; y++) {
    		for(int x=0; x<800; x++) {
    			Ray r = cam.shootRay(x, y);
    			canvas.setPixel(x, y, radiance(r));
    		}
    	}
    	canvas.saveToFile("outImg/GGVERYIN.png");
    }
}
