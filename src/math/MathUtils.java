package math;

public class MathUtils {
	public static Vector3f clamp(Vector3f v, float min, float max) {
		float x = Math.min(Math.max(v.x, min), max);
		float y = Math.min(Math.max(v.y, min), max);
		float z = Math.min(Math.max(v.z, min), max);
		return new Vector3f(x, y, z);
	}
}
