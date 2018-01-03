package material;

import math.*;

public class Material {
	public Vector3f emission;
	public BRDF brdf;
	public BRDF microfacetBRDF;
	private float ks;
	private float kd;

	public final static Material DEFAULTMATERIAL = new Material(new Vector3f(0.0f), new LambertianBRDF(new Vector3f(1.0f)));
	
	static {
		//https://refractiveindex.info/?shelf=3d&book=plastics&page=pmma
		DEFAULTMATERIAL.setMicrofacetModel(new BlinnPhongBRDF(1.4906f, 30.0f));
		DEFAULTMATERIAL.setKsKd(0.1f, 0.9f);
	}
	
	public Material(Vector3f emission, BRDF brdf)
	{
		this.emission = emission;
		this.brdf = brdf;
		this.microfacetBRDF = null;
		this.ks = 0.0f;
		this.kd = 1.0f;
	}
	
	public void setMicrofacetModel(BRDF model) {
		this.microfacetBRDF = model;
	}
	
	public void setKsKd(float ks, float kd) {
		this.kd = MathUtils.clamp(kd, 0, 1);
		ks = MathUtils.clamp(ks, 0, 1);
		this.ks = (float)Math.min(ks, 1.0-this.kd);
	}
	
	public float getKs() {
		return ks;
	}
	
	public float getKd() {
		return kd;
	}
}

