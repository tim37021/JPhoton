package algorithm;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import camera.*;
import core.*;
import primitive.*;

public class PathTracing extends Algorithm {
	private static Pattern regex;
	static {
		regex = Pattern.compile("([0-9]+) \\(([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+)\\) \\(([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+)\\)");
	}
	private int maxIter;
	private Entity objects[];

	
	public PathTracing(Canvas canvas, Camera camera, ArrayList<Entity> objects, int maxIter) {
		super(canvas, camera);
		this.maxIter = maxIter;
		this.objects = new Entity[objects.size()];
		objects.toArray(this.objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// 8 threads
		Thread workers[] = new Thread[8];
		PathTracingWorker pworkers[] = new PathTracingWorker[8];
		for(int i=0; i<workers.length; i++) {
			pworkers[i] = new PathTracingWorker(this, i, workers.length);
			workers[i] = new Thread(pworkers[i]);
			workers[i].start();
		}
		try {
			for(int i=0; i<workers.length; i++) {
				workers[i].join();
			}
		} catch(Exception e) {
			
		}
	}

	static public Algorithm Create(Canvas canvas, Camera camera, ArrayList<Entity> objects, String cmd) {
		Matcher m = regex.matcher(cmd);
		if(m.find()) {
			int iterations = Integer.parseInt(m.group(1));
			int x0 = Integer.parseInt(m.group(2));
			int y0 = Integer.parseInt(m.group(3));
			int x1 = Integer.parseInt(m.group(4));
			int y1 = Integer.parseInt(m.group(5));
			
			PathTracing algorithm = new PathTracing(canvas, camera, objects, iterations);
			algorithm.setRegion(new Region(x0, y0, x1, y1));
			return algorithm;
			
		}
		return null;
	}
	public Entity[] getObjects() {
		return objects;
	}
	
	public int getMaxIter() {
		return maxIter;
	}

	
}
