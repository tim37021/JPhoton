package math;

import java.util.Random;

public class MathUtils {
	public static final float DIV_1_PI = 1.0f/(float)Math.PI;
	public static final float MUL_2_PI = 2.0f*(float)Math.PI;
	public static final float EPSILON = 1e-4f;
	public static Random rand;
	
	static {
		rand = new Random();
	};
	public static Vector3f clamp(Vector3f v, float min, float max) {
		float x = Math.min(Math.max(v.x, min), max);
		float y = Math.min(Math.max(v.y, min), max);
		float z = Math.min(Math.max(v.z, min), max);
		return new Vector3f(x, y, z);
	}
	public static Vector3f uniformHemisphereSampling(float u, float v) {
		float phi = 2*MUL_2_PI*v;
		float T = (float)Math.sqrt(1-(1-u)*(1-u));
	    return new Vector3f((float)(Math.cos(phi)*T), (float)(Math.sin(phi)*T), 1-u);
	}
	
	// TBN * vec
	public static Vector3f changeBasis(Vector3f T, Vector3f B, Vector3f N, Vector3f vec) {
		float x = T.x * vec.x + B.x * vec.y + N.x * vec.z;
		float y = T.y * vec.x + B.y * vec.y + N.y * vec.z;
		float z = T.z * vec.x + B.z * vec.y + N.z * vec.z;
		return new Vector3f(x, y, z);
	}
}
