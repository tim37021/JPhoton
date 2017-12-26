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
    ArrayList<Vector3f> triangles;
    ArrayList<Integer> indices;
    
	public Mesh(String name, ArrayList<Vector3f> triangles, ArrayList<Integer> indices) {
		super(name);
		this.triangles = triangles;
		this.indices = indices;
	}
	
	private Mesh(String name) {
        super(name);
    }
	
	// Obejct_name file_name
	static public Mesh createMesh(String fileName) {
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
                    indices.add(new Integer((words[1].split("//")[0])));
                    indices.add(new Integer((words[2].split("//")[0])));
                    indices.add(new Integer((words[3].split("//")[0])));

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
        return new Mesh("TODO ",triangles, indices);
	
	}
	


	@Override
	public Intersection intersect(Ray r) {
	    Intersection inter=null;
	    for(int i = 0 ; i < indices.size() ; i+=3){
	        Intersection inter2 = MathUtils.triangleIntersection(triangles.get(i), triangles.get(i+1), triangles.get(i+2), r);
	        if(inter==null || (inter2!=null && inter.t > inter2.t)) {
	            inter = inter2;
	        }
	    }
	    return inter;
		
	}
	
}
