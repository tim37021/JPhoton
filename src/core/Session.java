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
		
		if(cmds[0].equals("saveto")) {
			canvasMap.get(cmds[1]).saveToFile(cmd.substring(7+cmds[1].length()+1));
		}
	}
	
	static public void main(String args[]) {
		Session s = new Session();
		s.enterCommand("spawn sphere GGININ (0 0 25) 10");
		s.enterCommand("spawn sphere GGININDER (0 0 6) 3.5");
		//s.enterCommand("spawn sphere GGININDER (0 0 6) 3.5");
		
		s.enterCommand("newmat emitmat (1 1 1) lambert (1 1 1)");
		s.enterCommand("assignmat emitmat GGININ");
		
		s.enterCommand("spawncam cam1 default 45 0.1 (800 600)");
		s.enterCommand("camlookat cam1 (0 15 8) (0 0 9) (0 0 1)");
		s.enterCommand("render result_1 cam1 pathtracing 500 (0 0) (800 600)");
		s.enterCommand("saveto result_1 outImg/GGININ.png");
	} 
}
