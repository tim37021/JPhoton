package core;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import algorithm.*;
import camera.*;
import material.*;
import primitive.*;
import math.*;


public class Session {
	private HashMap<String, Canvas> canvasMap;
	private HashMap<String, Camera> cameraMap;
	private HashMap<String, Material> materialMap;
	private ArrayList<Entity> objects;
	static private Pattern regexPattern;
	static {
		regexPattern = Pattern.compile("\\(([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+)\\) "
				+ "\\(([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+)\\) "
				+ "\\(([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+) ([+-]?[0-9]*[.]?[0-9]+)\\)");
	}
	public Session() {
		canvasMap = new HashMap<String, Canvas>();
		cameraMap = new HashMap<String, Camera>();
		materialMap = new HashMap<String, Material>();
		objects = new ArrayList<Entity>();
	}
	
	public void enterCommand(String cmd) {
		String cmds[] = cmd.split(" ");
		if(cmds[0].equals("spawn")) {
			objects.add(EntityFactory.parseCommand(cmd.substring(6)));
		}
		
		// spawncam name type
		if(cmds[0].equals("spawncam")) {
			String name = cmds[1];
			Camera cam = CameraFactory.parseCommand(cmd.substring(9+name.length()+1));
			cameraMap.put(name, cam);
		}
		
		if(cmds[0].equals("newmat")) {
			String name = cmds[1];
			Material mat = MaterialFactory.parseCommand(cmd.substring(7+name.length()+1));
			materialMap.put(name, mat);
		}
		
		// modify material
		// setmatkskd name ks kd
		if(cmds[0].equals("setmatkskd")) {
			String matName = cmds[1];
			Material mat = materialMap.get(matName);
			mat.setMicrofacetModel(new BlinnPhongBRDF(1.4906f, 30.0f));
			mat.setKsKd(Float.parseFloat(cmds[2]), Float.parseFloat(cmds[3]));
		}
		
		if(cmds[0].equals("assignmat")) {
			String matName = cmds[1];
			String objName = cmds[2];
			Entity result = null;
			for(Entity object: objects) {
				if(object.getName().equals(objName))
					result = object;
			}
			if(result!=null)
				result.setMaterial(materialMap.get(matName));
		}
		
		// spawncam name type
		if(cmds[0].equals("camlookat")) {
			Camera cam = cameraMap.get(cmds[1]);
			if(cam != null) {
				Matcher m = regexPattern.matcher(cmd.substring(10+cmds[1].length()+1));
				if(m.find()) {
					float oX = Float.parseFloat(m.group(1));
					float oY = Float.parseFloat(m.group(2));
					float oZ = Float.parseFloat(m.group(3));
				
					float cX = Float.parseFloat(m.group(4));
					float cY = Float.parseFloat(m.group(5));
					float cZ = Float.parseFloat(m.group(6));
					
					float uX = Float.parseFloat(m.group(7));
					float uY = Float.parseFloat(m.group(8));
					float uZ = Float.parseFloat(m.group(9));
					cam.lookAt(new Vector3f(oX, oY, oZ), new Vector3f(cX, cY, cZ), new Vector3f(uX, uY, uZ));
					
				}
			}
		}
		
		
		// render canvas_name camera_name algorithm 
		if(cmds[0].equals("render")) {
			Camera cam = cameraMap.get(cmds[2]);
			Canvas canvas = new Canvas((int)cam.getExtent().x, (int)cam.getExtent().y);
			canvasMap.put(cmds[1], canvas);
			
			Algorithm algo = AlgorithmFactory.parseCommand(canvas, cam, objects, cmd.substring(7+cmds[1].length()+cmds[2].length()+2));
			algo.run();
		}
		
		if(cmds[0].equals("applytrans")) {
			String objName = cmds[1];
			Entity result = null;
			for(Entity object: objects) {
				if(object.getName().equals(objName))
					result = object;
			}
			Transformation transform = Transformation.parse(cmd.substring(objName.length()+1));
			System.out.println(transform.getMatrix());
			if(result!=null)
				result.applyTransform(transform);
		}
		
		if(cmds[0].equals("saveto")) {
			canvasMap.get(cmds[1]).saveToFile(cmd.substring(7+cmds[1].length()+1));
		}
	}
	
	static public void main(String args[]) {
		Session s = new Session();
		
		s.enterCommand("spawn aarectangle BOTTOM XZ (-3 -3) (3 3) -3");
		s.enterCommand("spawn aarectangle TOP XZ (-3 -3) (3 3) 3");
		s.enterCommand("spawn aarectangle TOP_LIGHT XZ (-1 -1) (1 1) 2.9");
		s.enterCommand("spawn aarectangle LEFT YZ (-3 -3) (3 3) -3");
		s.enterCommand("spawn aarectangle RIGHT YZ (-3 -3) (3 3) 3");
		s.enterCommand("spawn aarectangle INNER XY (-3 -3) (3 3) -3");
		
		s.enterCommand("spawn mesh CUBE res/cubeobj.obj");
		s.enterCommand("spawn mesh CUBE2 res/cubeobj.obj");
		//s.enterCommand("spawn mesh CUBE3 res/cubeobj.obj");
		
		s.enterCommand("spawn sphere GGININ (0 0.5 0) 1");
		
		s.enterCommand("applytrans CUBE (-1.1 -2 0) (0 -10 0) 1");
		s.enterCommand("applytrans CUBE2 (1.1 -2 0) (0 5 0) 1");
		//s.enterCommand("applytrans CUBE3 (0 0 0) (0 15 0) 1");
		//s.enterCommand("spawn sphere s2 (0 -2 0) 1");
		s.enterCommand("newmat spheremat (0 0 0) lambert (0.8 0.8 0.8)");
		s.enterCommand("setmatkskd spheremat 0.9 0.1");
		s.enterCommand("newmat emitmat (10 10 10) lambert (0.8 0.8 0.8)");
		s.enterCommand("newmat greenwall (0 0 0) lambert (0 0.8 0)");
		s.enterCommand("newmat redwall (0 0 0) lambert (0.8 0 0)");
		s.enterCommand("newmat bluewall (0 0 0) lambert (0 0 0.8)");
		s.enterCommand("assignmat spheremat GGININ");
		s.enterCommand("assignmat emitmat TOP_LIGHT");
		s.enterCommand("assignmat greenwall LEFT");
		s.enterCommand("assignmat redwall RIGHT");
		s.enterCommand("assignmat bluewall TOP");
		
		
		s.enterCommand("spawncam cam1 default 45 0.1 (800 600)");
		s.enterCommand("camlookat cam1 (0 2 10) (0 0 0) (0 1 0)");
		s.enterCommand("render result_1 cam1 pathtracing 100 (0 0) (800 600)");
		//s.enterCommand("render result_1 cam1 debugtracing (0 0) (800 600)");
		s.enterCommand("saveto result_1 outImg/out.png");
	} 
}
