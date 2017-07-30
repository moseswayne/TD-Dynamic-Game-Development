package util.generator;

import java.awt.image.BufferedImage;


/**
 * Date storage for WebImageCollector
 * 
 * @author maddiebriere
 *
 */
public class ImageInfo {
	private BufferedImage myImage;
	private String myName;
	private String myPath;
	
	
	public ImageInfo(BufferedImage myImage, String myName, String myPath) {
		super();
		this.myImage = myImage;
		this.myName = myName;
		this.myPath = myPath;
	}
	public BufferedImage getMyImage() {
		return myImage;
	}
	public void setMyImage(BufferedImage myImage) {
		this.myImage = myImage;
	}
	public String getMyName() {
		return myName;
	}
	public void setMyName(String myName) {
		this.myName = myName;
	}
	public String getMyPath() {
		return myPath;
	}
	public void setMyPath(String myPath) {
		this.myPath = myPath;
	}
	
	
	
}
