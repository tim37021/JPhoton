package primitive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.*;
import math.*;

public class Mesh extends Entity {
    private Vector3f vertices[];
    private Integer indices[];
    private BoundingVolume bb;
    private BoundingVolume old_bb;
    
	public Mesh(String name, Vector3f vertices[], Integer indices[], BoundingVolume bb) {
		super(name);
		this.vertices = vertices;
		this.indices = indices;
		this.bb = this.old_bb = bb;
	}
	
	private Mesh(String name) {
        super(name);
    }
	
	// Obejct_name file_name
	static public Mesh create(String cmd) {
		String name = cmd.split(" ")[0];
		String fileName =cmd.substring(name.length()+1);
	    ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
	    ArrayList<Integer> indices = new ArrayList<Integer>();
	    
		float lbx,lby,lbz;
		float rtx,rty,rtz;
		lbx = lby = lbz = Float.MAX_VALUE;
		rtx = rty = rtz = Float.MIN_VALUE;
		
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
                	Vector3f newVertex = new Vector3f(Float.parseFloat(words[1]),
                                               Float.parseFloat(words[2]),
                                               Float.parseFloat(words[3]));
                	lbx = Math.min(newVertex.x, lbx);
                	rtx = Math.max(newVertex.x, rtx);
                	lby = Math.min(newVertex.y, lby);
                	rty = Math.max(newVertex.y, rty);
                	lbz = Math.min(newVertex.z, lbz);
                	rtz = Math.max(newVertex.z, rtz);
                	vertices.add(newVertex);
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
        Vector3f verticesArr[] = new Vector3f[vertices.size()];
        vertices.toArray(verticesArr);
        Integer indicesArr[] = new Integer[indices.size()];
        indices.toArray(indicesArr);
        return new Mesh(name, verticesArr, indicesArr, new AxisAlignedBoundingBox(new Vector3f(lbx, lby, lbz), new Vector3f(rtx, rty, rtz)));
	}

	@Override
	public Intersection intersect(Ray r) {
		if(bb!=null && !bb.testIntersect(r))
			return null;
	    Intersection inter=null;
	    for(int i = 0 ; i < indices.length ; i+=3){
	        Intersection inter2 = MathUtils.triangleIntersection(vertices[indices[i]], vertices[indices[i+1]], vertices[indices[i+2]], r);
	        if(inter==null || (inter2!=null && inter.t > inter2.t)) {
	            inter = inter2;
	        }
	    }
	    return inter;
		
	}

	@Override
	public void applyTransform(Transformation transform) {
		// TODO Auto-generated method stub
		Matrix4 mat = transform.getMatrix();
		for(int i=0; i<vertices.length; i++) {
			vertices[i] = mat.mul(vertices[i], 1.0f);
		}
		bb = (AxisAlignedBoundingBox) old_bb.clone();
		bb.applyTransform(transform);
	}
	
}
