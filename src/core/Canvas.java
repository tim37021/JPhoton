package core;

// THIS CLASS MUST BE EXTERNALLY SYNCED

public class Canvas {
	private int width, height;
	private int color[];
	public Canvas(int width, int height) {
		this.width = width;
		this.height = height;
		this.color = new int[width*height];
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
}
