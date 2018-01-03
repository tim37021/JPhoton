package algorithm;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import camera.*;
import core.*;
import math.*;
import primitive.*;
import material.*;
public class PathTracingWorker implements Runnable {
	private int offsetY, strideY;
	private PathTracing parent;
	
	public PathTracingWorker(PathTracing parent, int offsetY, int strideY) {
		this.parent = parent;
		this.offsetY = offsetY;
		this.strideY = strideY;
	}
	
	public void render() {
		Region reg = parent.getRegion();
		int x0 = reg.x0;
		int y0 = reg.y0;
		int x1 = reg.x1;
		int y1 = reg.y1;
		int maxIter = parent.getMaxIter();
		Camera camera = parent.getCamera();
		Canvas canvas = parent.getCanvas();
		
		double result[] = new double[(x1-x0)*(y1-y0)*3];
		int offset = 0;
		for(int i=0; i<maxIter; i++) {
			offset = 3 * (x1-x0) * offsetY ;
			for(int y = y0+offsetY; y < y1; y+=strideY) {
				for(int x = x0; x<x1; x++) {
					Ray r = camera.shootRay(x, y);
					Vector3f color = radiance(r, 5);
					result[offset+0] += color.x / maxIter;
					result[offset+1] += color.y / maxIter;
					result[offset+2] += color.z / maxIter;
					offset += 3;
				}
				offset += 3 * (x1-x0) * (strideY-1);
			}
			System.out.println("iter:"+i);
		}
		hdr(result);
		// copy..
		offset = 3 * (x1-x0) * offsetY;
		for(int y = y0+offsetY; y < y1; y+=strideY) {
			for(int x = x0; x<x1; x++) {
				canvas.setPixel(x, y, new Vector3f((float)result[offset], (float)result[offset+1], (float)result[offset+2]));
				offset+=3;
			}
			offset += 3 * (x1-x0) * (strideY-1);
		}
	}
	
	private void hdr(double frame[]) {
		int offset = 0;
		Region reg = parent.getRegion();
		int x0 = reg.x0;
		int y0 = reg.y0;
		int x1 = reg.x1;
		int y1 = reg.y1;
		for(int y = y0; y < y1; y++)
		{
			double color_r, color_g, color_b;
			for(int x = x0; x < x1; x++)
			{

		
//				float r = Func.clamp(frame.getPixelR(x, y), 0.0f, 1.0f);
//				float g = Func.clamp(frame.getPixelG(x, y), 0.0f, 1.0f);
//				float b = Func.clamp(frame.getPixelB(x, y), 0.0f, 1.0f);
				
				color_r = frame[offset];
				color_g = frame[offset+1];
				color_b = frame[offset+2];
				double r = color_r;
				double g = color_g;
				double b = color_b;
				
				// Reinhard tone mapping
//				r = r / (1.0f + r);
//				g = g / (1.0f + g);
//				b = b / (1.0f + b);
//				
//				r = (float)Math.pow(r, 1.0 / 2.2);
//				g = (float)Math.pow(g, 1.0 / 2.2);
//				b = (float)Math.pow(b, 1.0 / 2.2);
				
				// Jim Hejl and Richard Burgess-Dawson (GDC)
				// no need of pow(1/2.2)
				color_r = Math.min(Math.max(color_r - 0.004, 0.0), Double.MAX_VALUE);
				color_g = Math.min(Math.max(color_g - 0.004, 0.0), Double.MAX_VALUE);
				color_b = Math.min(Math.max(color_b - 0.004, 0.0), Double.MAX_VALUE);

				double numerator_r = (color_r*6.2+0.5)*color_r;
				double numerator_g = (color_g*6.2+0.5)*color_g;
				double numerator_b = (color_b*6.2+0.5)*color_b;
				
				double denominator_r = (color_r*6.2+1.7) * color_r+0.06;
				double denominator_g = (color_g*6.2+1.7) * color_g+0.06;
				double denominator_b = (color_b*6.2+1.7) * color_b+0.06;
				
				r = numerator_r / denominator_r;
				g = numerator_g / denominator_g;
				b = numerator_b / denominator_b;
				
				if(r != r || g != g || b != b)
				{
					System.out.println("NaN!");
					System.exit(1);
				}
				
				frame[offset] = r;
				frame[offset+1] = g;
				frame[offset+2] = b;
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
		Entity objects[] = parent.getObjects();
		for(Entity object: objects) {
			Intersection sec = object.intersect(r);
			if(minSec==null || (sec!=null && sec.t < minSec.t)) {
				minSec = sec;
				hitObj = object;
			}
		}
		
		if(minSec != null){
			Material mat = hitObj.getMaterial();

			RandomRay newRay = mat.brdf.shootRay(minSec);
			float coef = minSec.normal.dot(newRay.ray.dir);
			if(mat.emission.allGreaterThan(1.0f))
				return mat.emission;
			if(minSec.normal.dot(r.dir) > 0)
				System.out.println("FUCK");
			Vector3f rad = radiance(newRay.ray, depth-1);
			Vector3f diffuseBRDF = mat.brdf.calc(r, newRay.ray, minSec.normal).mul(mat.getKd());
			Vector3f specularBRDF = mat.microfacetBRDF!=null ? mat.microfacetBRDF.calc(r, newRay.ray, minSec.normal).mul(mat.getKs()): new Vector3f(0.0f);
			Vector3f result = rad.mul(diffuseBRDF.add(specularBRDF)).mul(coef).mul(1.0f/newRay.p);
			
			return mat.emission.add(result);
		} else
			return new Vector3f(0.0f);
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		render();
	}

	

	



	
}
