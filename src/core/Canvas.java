package core;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import javax.imageio.ImageIO;

import math.*;

// THIS CLASS MUST BE EXTERNALLY SYNCED

public class Canvas {
	private BufferedImage bi;
	private final int color[];
	private final int width, height;
	public Canvas(int width, int height) {
		this.width = width;
		this.height = height;
		this.bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.color = ((DataBufferInt)this.bi.getRaster().getDataBuffer()).getData();
		System.out.println(this.color.length);
	}
	
	public void setPixel(int x, int y, Vector3f color) {
		color = MathUtils.clamp(color, 0.0f, 1.0f);
		int c = (255<<24) + (((int)(color.x*255))<<16) + (((int)(color.y*255))<<8) + ((int)(color.z*255));
		this.color[y*width+x] = c;
	}
	
	public void setPixel(int x, int y, int color) {
		this.color[y*width+x] = color;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void saveToFile(String filename)
	{
		try {
		    // retrieve image
		    File outputfile = new File(filename);
		    ImageIO.write(bi, "png", outputfile);
		} catch (Exception e) {

		}
	}
	
	static public void main(String args[]) {
		// draw a vertical yellow line
		Canvas canvas = new Canvas(800, 600);
		Vector3f color = new Vector3f(1, 1, 0);
		for(int i=0; i<200; i++)
			canvas.setPixel(50, 50+i, color);
		canvas.saveToFile("GGININ.png");
	}
}
