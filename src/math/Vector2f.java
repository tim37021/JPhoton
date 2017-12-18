package math;

public final class Vector2f {
	public final float x, y;
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(Vector2f v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	public Vector2f add(Vector2f v) {
		return new Vector2f(x+v.x, y+v.y);
	}

	public Vector2f sub(Vector2f v) {
		return new Vector2f(x-v.x, y-v.y);
	}
	
	public Vector2f mul(Vector2f v) {
		return new Vector2f(x*v.x, y*v.y);
	}
	
	public Vector2f mul(float scale) {
		return new Vector2f(x*scale, y*scale);
	}
	
	public float length2() {
		return x*x+y*y;
	}
	
	public float dot(Vector2f v) {
		return x*v.x+y*v.y;
	}
	
	public Vector2f normalized() {
		float l = (float)Math.sqrt(length2());
		return new Vector2f(x/l, y/l);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Vector2f) {
			final Vector2f v = (Vector2f)o;
			return (x==v.x) && (y==v.y);
		}
		return false;
		
	}
	
	@Override
	public Object clone() {
		return new Vector2f(this);
	}
}
