package core;

public abstract class BRDF {
	public abstract Vector3f calc(Ray in, Ray out);
	public abstract Ray shootRay();
}