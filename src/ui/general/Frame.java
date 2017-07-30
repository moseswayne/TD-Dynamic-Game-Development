package ui.general;

public class Frame {
	private double x;
	private double y;
	private double height;
	private double width;
	private Frame bounds;
	
	public Frame(double x, double y, double width, double height){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		bounds = new Frame(width, height);
	}
	public Frame(double width, double height){
		this.height = height;
		this.width = width;
		this.x = 0;
		this.y = 0;
		bounds = this;
	}
	
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public double getWidth(){
		return width;
	}
	public double getHeight(){
		return height;
	}
	public double getMaxX(){
		return x + width;
	}
	public double getMaxY(){
		return y + height;
	}
	
	/**
	 * purpose is to provide a frame object that defines the local layout properties 
	 * @return same frame but with x and y set to 0
	 */
	public Frame toLocalBounds(){
		return bounds;
	}
	
	public String toString(){
		return "x: " + x + "\ty: " + y + "\tw: " + width + "\th" + height;
	}
}
