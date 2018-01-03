package math;

public class Matrix4 {
	private final float m[] = new float[16];
	public Matrix4() {
		for(int i=0; i<4; i++)
			m[i*4+i] = 1.0f;
	}
	
	public Vector3f mul(Vector3f v, float w) {
		float x = m[0]*v.x + m[1]*v.y + m[2] * v.z + m[3] * w;
		float y = m[4]*v.x + m[5]*v.y + m[6] * v.z + m[7] * w;
		float z = m[8]*v.x + m[9]*v.y + m[10] * v.z + m[11] * w;
		//float w2 = m[12]*v.x + m[13]*v.y + m[14] * v.z + m[15] * w;
		return new Vector3f(x, y, z);
	}
	
	public Matrix4 mul(Matrix4 mat) {
		Matrix4 ret = new Matrix4();
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				ret.m[i*4+j] = 0;
				for(int k=0; k<4; k++) {
					ret.m[i*4+j] += m[i*4+k] * mat.m[k*4+j];
				}
			}
		}
		return ret;
	}
	
	public static Matrix4 rx(float x) {
		Matrix4 ret = new Matrix4();
		ret.m[5] = (float)Math.cos(x);
		ret.m[6] = (float)-Math.sin(x);
		ret.m[9] = (float)Math.sin(x);
		ret.m[10] = (float)Math.cos(x);
		return ret;
	}
	
	public static Matrix4 ry(float x) {
		Matrix4 ret = new Matrix4();
		ret.m[0] = (float)Math.cos(x);
		ret.m[2] = (float)Math.sin(x);
		ret.m[8] = (float)-Math.sin(x);
		ret.m[10] = (float)Math.cos(x);
		return ret;
	}
	
	public static Matrix4 rz(float x) {
		Matrix4 ret = new Matrix4();
		ret.m[0] = (float)Math.cos(x);
		ret.m[1] = (float)-Math.sin(x);
		ret.m[4] = (float)Math.sin(x);
		ret.m[5] = (float)Math.cos(x);
		return ret;
	}
	
	public static Matrix4 translate(Vector3f t) {
		Matrix4 ret = new Matrix4();
		
		ret.m[3] = t.x;
		ret.m[7] = t.y;
		ret.m[11] = t.z;
		
		return ret;
	}
	
	public static Matrix4 scale(Vector3f s) {
		Matrix4 ret = new Matrix4();
		
		ret.m[0] = s.x;
		ret.m[5] = s.y;
		ret.m[10] = s.z;
		
		return ret;
	}
	
	@Override
	public String toString() {
		String ret = "[";
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				ret = ret + m[i*4+j] + " ";
			}
			ret += ";\n";
		}
		return ret + "]";
	}
}
