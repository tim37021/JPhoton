package core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import math.*;

public final class Transformation {
	private Vector3f translate;
	private Vector3f scale;
	private Vector3f rotate;
	
	private Matrix4 cachedMatrix;
	private Matrix4 cachedTMatrix, cachedSMatrix, cachedRMatrix;
	private boolean dirty = false;
	
	private static Pattern regex;
	static {
		regex = Pattern.compile("\\(([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+)\\) \\(([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+)\\) ([+-]?[0-9]*[.]?[0-9]+)");
	}
	
	public Transformation() {
		setTranslate(new Vector3f(0.0f));
		setRotate(new Vector3f(0.0f));
		setScale(new Vector3f(1.0f));
	}
	
	public Matrix4 getMatrix() {
		if(dirty) {
			cachedMatrix = cachedTMatrix.mul(cachedSMatrix).mul(cachedRMatrix);
			dirty = false;
		}
		
		return cachedMatrix;
	}

	public Vector3f getTranslate() {
		return translate;
	}

	public void setTranslate(Vector3f translate) {
		this.translate = translate;
		cachedTMatrix = Matrix4.translate(translate);
		dirty = true;
	}

	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
		cachedSMatrix = Matrix4.scale(scale);
		dirty = true;
	}

	public Vector3f getRotate() {
		return rotate;
	}

	public void setRotate(Vector3f rotate) {
		this.rotate = rotate;
		cachedRMatrix = MathUtils.eularRotation(rotate);
		dirty = true;
	}
	
	@Override
	public String toString() {
		return "t: " + translate + " r: " + rotate + " s: " + scale;
	}
	
	// T R S
	// () (x y z)  
	public static Transformation parse(String cmd) {
		Transformation ret = new Transformation();
		
		Matcher m = regex.matcher(cmd);
		if(m.find()) {
			float tx = Float.parseFloat(m.group(1));
			float ty = Float.parseFloat(m.group(2));
			float tz = Float.parseFloat(m.group(3));
			ret.setTranslate(new Vector3f(tx, ty, tz));
			
			float rx = Float.parseFloat(m.group(4)) * MathUtils.DIV_PI_180;
			float ry = Float.parseFloat(m.group(5)) * MathUtils.DIV_PI_180;
			float rz = Float.parseFloat(m.group(6)) * MathUtils.DIV_PI_180;
			ret.setRotate(new Vector3f(rx, ry, rz));
			
			float s = Float.parseFloat(m.group(7));
			ret.setScale(new Vector3f(s));
		}
		
		return ret;
	}
	
	static public void main(String args[]) {
		Transformation trans = Transformation.parse("(0 2 1) (0 0 0) 2");
		float time = 0;
		while(true) {
			//trans.setRotate(new Vector3f(0, time, 0));
			//trans.setTranslate(new Vector3f(0, (float)Math.sin(time), 0));
			trans.getMatrix();
		}
	} 
	

	
}
