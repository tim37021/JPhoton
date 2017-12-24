package algorithm;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import camera.*;
import core.*;
import math.*;
import primitive.*;
import material.*;
public class PathTracing extends Algorithm {
	private ArrayList<Entity> objects;
	private int maxIter;
	private static Pattern regex;
	static {
		regex = Pattern.compile("([0-9]+) \\(([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+)\\) \\(([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+)\\)");
	}
	
	public void render() {
		double result[] = new double[(x1-x0)*(y1-y0)*3];
		int offset = 0;
		for(int i=0; i<maxIter; i++) {
			offset = 0;
			for(int y = y0; y < y1; y++) {
				for(int x = x0; x<x1; x++) {
					Ray r = camera.shootRay(x, y);
					Vector3f color = radiance(r, 5);
					result[offset+0] += color.x / maxIter;
					result[offset+1] += color.y / maxIter;
					result[offset+2] += color.z / maxIter;
					offset += 3;
				}
			}
			System.out.println("iter:"+i);
		}
		// copy..
		offset = 0;
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x<x1; x++) {
				canvas.setPixel(x, y, new Vector3f((float)result[offset], (float)result[offset+1], (float)result[offset+2]));
				offset+=3;
			}
		}
	}
	
	private Vector3f radiance(Ray r, int depth) {
		if(depth == 0)
			return new Vector3f(0.0f);
		// find nearest object
		Intersection minSec = null;
		Entity hitObj = null;
		for(Entity object: objects) {
			Intersection sec = object.intersect(r);
			if(minSec==null || (sec!=null && sec.t < minSec.t)) {
				minSec = sec;
				hitObj = object;
			}
		}
		
		if(minSec != null){
			Material mat = hitObj.getMaterial();
			Ray newRay = mat.brdf.shootRay(minSec);
			float coef = minSec.normal.dot(newRay.dir);
			Vector3f rad = radiance(newRay, depth-1).mul(mat.brdf.calc(r, newRay)).mul(coef);
			return mat.emission.add(rad);
		} else
			return new Vector3f(0.0f);
	}
	
	
	public PathTracing(Canvas canvas, Camera camera, ArrayList<Entity> objects) {
		super(canvas, camera);
		this.objects = objects;
		this.maxIter = 5000;

	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		render();
	}

	
	static public Algorithm Create(Canvas canvas, Camera camera, ArrayList<Entity> objects, String cmd) {
		Matcher m = regex.matcher(cmd);
		if(m.find()) {
			int iterations = Integer.parseInt(m.group(1));
			int x0 = Integer.parseInt(m.group(2));
			int y0 = Integer.parseInt(m.group(3));
			int x1 = Integer.parseInt(m.group(4));
			int y1 = Integer.parseInt(m.group(5));
			
			PathTracing algorithm = new PathTracing(canvas, camera, objects);
			algorithm.setRegion(x0, y0, x1, y1);
			algorithm.maxIter = iterations;
			return algorithm;
			
		}
		return null;
	}  
	
	static private Entity obj;
	static private Vector3f radiance_(Ray r) {
		Intersection itsec = obj.intersect(r);
		if(itsec != null)
			return itsec.point;
		else
			return new Vector3f(0.0f);
	}
	



	
}
