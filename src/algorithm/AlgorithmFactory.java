package algorithm;

import java.util.ArrayList;

import primitive.*;
import camera.*;
import core.*;

public class AlgorithmFactory {
	static public Algorithm parseCommand(Canvas canvas, Camera camera, ArrayList<Entity> objects, String cmd) {
		String algoname = cmd.split(" ")[0];
		if(algoname.equals("pathtracing")) {
			return PathTracing.Create(canvas, camera, objects, cmd.substring(12));
		}
		if(algoname.equals("debugtracing")) {
			return DebugTracing.Create(canvas, camera, objects, cmd.substring(12));
		}
		return null;
	}
}
