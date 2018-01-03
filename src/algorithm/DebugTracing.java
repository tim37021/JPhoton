package algorithm;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import camera.*;
import core.*;
import math.*;
import primitive.*;

public class DebugTracing extends Algorithm {
	private Entity objects[];
	
	private static Pattern regex;
	static {
		regex = Pattern.compile("\\(([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+)\\) \\(([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+)\\)");
	}
	
	public DebugTracing(Canvas canvas, Camera camera, ArrayList<Entity> objects) {
		super(canvas, camera);
		this.objects = new Entity[objects.size()];
		objects.toArray(this.objects);
		// TODO Auto-generated constructor stub
	}

	private Vector3f radiance(Ray r, int depth) {
		if(depth == 0)
			return new Vector3f(0.0f);
		// find nearest object
		Intersection minSec = null;
		//Entity hitObj = null;
		for(Entity object: objects) {
			Intersection sec = object.intersect(r);
			if(minSec==null || (sec!=null && sec.t < minSec.t)) {
				minSec = sec;
				//hitObj = object;
			}
		}
		
		if(minSec != null){
			return minSec.normal;
		} else
			return new Vector3f(0.0f);
	}
	
	
	
	private void render() {
		Region reg = getRegion();
		int x0 = reg.x0;
		int y0 = reg.y0;
		int x1 = reg.x1;
		int y1 = reg.y1;
		Camera camera = getCamera();
		Canvas canvas = getCanvas();
		
		double result[] = new double[(x1-x0)*(y1-y0)*3];
		int offset = 0;
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x<x1; x++) {
				Ray r = camera.shootRay(x, y);
				Vector3f color = radiance(r, 5);
				result[offset+0] += color.x;
				result[offset+1] += color.y;
				result[offset+2] += color.z;
				offset += 3;
			}
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		render();
	}
	
	static public Algorithm Create(Canvas canvas, Camera camera, ArrayList<Entity> objects, String cmd) {
		Matcher m = regex.matcher(cmd);
		if(m.find()) {
			int x0 = Integer.parseInt(m.group(1));
			int y0 = Integer.parseInt(m.group(2));
			int x1 = Integer.parseInt(m.group(3));
			int y1 = Integer.parseInt(m.group(4));
			
			DebugTracing algorithm = new DebugTracing(canvas, camera, objects);
			algorithm.setRegion(new Region(x0, y0, x1, y1));
			return algorithm;
			
		}
		return null;
	}

}
