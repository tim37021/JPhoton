package material;

import core.Intersection;
import core.RandomRay;
import core.Ray;
import math.MathUtils;
import math.Vector3f;

public class BlinnPhongBRDF extends BRDF {

	public float N;
	public float alpha;
	
	public BlinnPhongBRDF(float N, float alpha) {
		this.N = N;
		this.alpha = alpha;
	}
	
	@Override
	public Vector3f calc(Ray in, Ray out, Vector3f normal) {
		Vector3f inv_in = in.dir.mul(-1.0f);
		Vector3f H = inv_in.add(out.dir).normalized();
		float normal_dot_H = normal.dot(H);
		// ((n+2) / 2pi)*(N dot H)^alpha
		return new Vector3f((alpha+2)*MathUtils.DIV_1_PI*0.5f*(float)Math.pow(normal_dot_H, alpha));
	}

	@Override
	public RandomRay shootRay(Intersection insec) {
		// cosine weight...
		float u = MathUtils.rand.nextFloat();
		float v = MathUtils.rand.nextFloat();

		Vector3f rvec = new Vector3f(MathUtils.rand.nextFloat() * 2 - 1, MathUtils.rand.nextFloat() * 2 - 1, MathUtils.rand.nextFloat() * 2 - 1);
		Vector3f tangent = rvec.sub(insec.normal.mul(rvec.dot(insec.normal))).normalized();
		Vector3f bitangent = insec.normal.crossProduct(tangent);
	    
		// uniform
		//Vector3f objspace = MathUtils.uniformHemisphereSampling(u, v);
		//float p =  MathUtils.DIV_1_PI*0.5f;
		Vector3f objspace = MathUtils.cosineWeightedHemisphereSampling(u, v);
		float p = objspace.z*MathUtils.DIV_1_PI;
		
		Vector3f dir =  MathUtils.changeBasis(tangent, bitangent, insec.normal, objspace);
		Ray newRay = new Ray(insec.point.add(dir.mul(MathUtils.EPSILON)), dir);
		return new RandomRay(newRay, p);
	}

}
