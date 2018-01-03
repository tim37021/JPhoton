package material;

import core.*;
import math.*;

public abstract class BRDF {
	public abstract Vector3f calc(Ray in, Ray out, Vector3f normal);
	public abstract RandomRay shootRay(Intersection insec);
}

