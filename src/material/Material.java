package material;

import math.*;

public class Material {
	public Vector3f emission;
	public BRDF brdf;

	public final static Material DEFAULTMATERIAL = new Material(new Vector3f(0.0f), new LambertianBRDF(new Vector3f(1.0f)));
	
	public Material(Vector3f emission, BRDF brdf)
	{
		this.emission = emission;
		this.brdf = brdf;
	}
}

