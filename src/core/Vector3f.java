package core;

public final class Vector3f {
	public final float x, y, z;
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f(Vector3f v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public Vector3f add(Vector3f v) {
		return new Vector3f(x+v.x, y+v.y, z+v.z);
	}

	public Vector3f sub(Vector3f v) {
		return new Vector3f(x-v.x, y-v.y, z-v.z);
	}
	
	public Vector3f mul(Vector3f v) {
		return new Vector3f(x*v.x, y*v.y, z*v.z);
	}
	
	public float length2() {
		return x*x+y*y+z*z;
	}
	
	public float dot(Vector3f v) {
		return x*v.x+y*v.y+z*v.z;
	}
	
	public Vector3f normalized() {
		float l = (float)Math.sqrt(length2());
		return new Vector3f(x/l, y/l, z/l);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Vector3f) {
			final Vector3f v = (Vector3f)o;
			return (x==v.x) && (y==v.y) && (z==v.z);
		}
		return false;
		
	}
	
	@Override
	public Object clone() {
		return new Vector3f(this);
	}
}
