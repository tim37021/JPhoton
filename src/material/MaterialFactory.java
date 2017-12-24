package material;

import math.Vector3f;

public class MaterialFactory {
	static private String findPair(String str) {
		int balance = 0;
		int start = -1;
		for(int i=0; i<str.length(); i++) {
			if(str.substring(i, i+1).equals("(")) {
				if(start == -1)
					start = i;
				balance ++;
			}
			if(str.substring(i, i+1).equals(")"))
				balance --;
			
			if(start >= 0 && balance == 0)
				return str.substring(start, i+1);
		}
		return str;
	}
	
	static public Material parseCommand(String cmd) {
		String emission = findPair(cmd);
		Vector3f emissionV = Vector3f.parse(emission);
		cmd = cmd.substring(emission.length()+1);
		String mattype = cmd.split(" ")[0];
		if(mattype.equals("lambert")) {
			return new Material(emissionV, LambertianBRDF.create(cmd.substring(mattype.length()+1)));
		}
		return null;
	}
}
