package primitive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.Intersection;
import core.Ray;
import math.MathUtils;
import math.Vector3f;

public class Mesh extends Entity {
    Vector3f triangles[];
    Integer indices[];
    
	public Mesh(String name, Vector3f triangles[], Integer indices[]) {
		super(name);
		this.triangles = triangles;
		this.indices = indices;
	}
	
	private Mesh(String name) {
        super(name);
    }
	
	// Obejct_name file_name
	static public Mesh createMesh(String cmd) {
		String name = cmd.split(" ")[0];
		String fileName =cmd.substring(name.length()+1);
	    ArrayList<Vector3f> triangles = new ArrayList<Vector3f>();
	    ArrayList<Integer> indices = new ArrayList<Integer>();

        BufferedReader reader = null;
        int i = 0;
        try {
            File file = new File(fileName);
            reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");
                String indicator = words[0];
                if(indicator.equals("#")){
                    
                }else if(indicator.equals("v")){
                    triangles.add(new Vector3f(Float.parseFloat(words[1]),
                                               Float.parseFloat(words[2]),
                                               Float.parseFloat(words[3])));
                    
                }else if(indicator.equals("vn")){
                    
                }else if(indicator.equals("f")){
                    indices.add(new Integer((words[1].split("//")[0])) - 1);
                    indices.add(new Integer((words[2].split("//")[0])) - 1);
                    indices.add(new Integer((words[3].split("//")[0])) - 1);

                }
                System.out.println(line);
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Vector3f gg[] = new Vector3f[triangles.size()];
        triangles.toArray(gg);
        Integer ggg[] = new Integer[indices.size()];
        indices.toArray(ggg);
        return new Mesh(name, gg, ggg);
	
	}
	


	@Override
	public Intersection intersect(Ray r) {
	    Intersection inter=null;
	    for(int i = 0 ; i < indices.length ; i+=3){
	        Intersection inter2 = MathUtils.triangleIntersection(triangles[indices[i]], triangles[indices[i+1]], triangles[indices[i+2]], r);
	        if(inter==null || (inter2!=null && inter.t > inter2.t)) {
	            inter = inter2;
	        }
	    }
	    return inter;
		
	}
	
}
