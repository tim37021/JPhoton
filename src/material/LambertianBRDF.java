package material;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import core.Intersection;
import core.Ray;
import math.*;

public class LambertianBRDF extends BRDF {
	public final Vector3f color;
	
	public LambertianBRDF(Vector3f color) {
		this.color = color;
	}
	
	@Override
	public Vector3f calc(Ray in, Ray out) {
		// TODO Auto-generated method stub
		// brdf() * 1/p(out)
		// (color / pi) * 1/(2pi)
		// 2 * color
		//return color.mul(MathUtils.DIV_1_PI);
		return color.mul(2);
	}

	@Override
	public Ray shootRay(Intersection insec) {
		float u = MathUtils.rand.nextFloat();
		float v = MathUtils.rand.nextFloat();

		Vector3f rvec = new Vector3f(MathUtils.rand.nextFloat() * 2 - 1, MathUtils.rand.nextFloat() * 2 - 1, MathUtils.rand.nextFloat() * 2 - 1);
		Vector3f tangent = rvec.sub(insec.normal.mul(rvec.dot(insec.normal))).normalized();
		Vector3f bitangent = insec.normal.crossProduct(tangent);
	    

		Vector3f objspace = MathUtils.uniformHemisphereSampling(u, v);
		Vector3f dir =  MathUtils.changeBasis(tangent, bitangent, insec.normal, objspace);
		return new Ray(insec.point.add(dir.mul(MathUtils.EPSILON)), dir);
	}
	
	public static void main(String args[]) {
		LambertianBRDF brdf = new LambertianBRDF(new Vector3f(1, 1, 1));
		Intersection insec = new Intersection(0, new Vector3f(0.0f), new Vector3f(0, 1, 0));
		System.out.println(brdf.shootRay(insec));
	}
	
	public static LambertianBRDF create(String cmd) {
		Vector3f v = Vector3f.parse(cmd);
		return new LambertianBRDF(v);
	}
}
