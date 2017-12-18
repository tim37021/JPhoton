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
			return new Vector3f(1.0f);
		else
			return new Vector3f(0.0f);
	}
	
    static public void main(String args[]) {
    	Canvas canvas = new Canvas(800, 600);
    	Camera cam = new PerspectiveCamera(45.0f*3.1415f/180.0f, 1.f, new Vector2f(800, 600));
    	//cam.lookAt(new Vector3f(0), new Vector3f(0, 0, -10), new Vector3f(0, 1, 0));
        //
    	tri = new Triangle(new Vector3f(-2, -2, -5), new Vector3f(2, -2, -5), new Vector3f(0,  2, -5));
    	Ray rr = cam.shootRay(400, 300);
    	System.out.println(tri.intersect(rr));
    	
    	for(int y=0; y<600; y++) {
    		for(int x=0; x<800; x++) {
    			Ray r = cam.shootRay(x, y);
    			canvas.setPixel(x, y, radiance(r));
    		}
    	}
    	canvas.saveToFile("outImg/GGVERYIN.png");
    }
}
