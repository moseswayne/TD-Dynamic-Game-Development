package util.generator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Class for collecting images (and other file types, if
 * necessary) from the internet. 
 * 
 * FOR IMAGES:
 * Contains findImage method that take in a "query" (search topic) and a file
 * type (e.g., "png"), and returns a BufferedImage.
 * 
 * Resources:
 * http://codigogenerativo.com/code/google-custom-search-api/
 * https://developers.google.com/custom-search/json-api/v1/reference/cse/list
 * https://www.google.com/search?client=safari&rls=en&q=convert+from+image+url+to+image+java&ie=UTF-8&oe=UTF-8
 * @author maddiebriere
 *
 */

public class WebImageCollector {
	//API key, generated on Google Custom Search website
	private final static String KEY = "AIzaSyA5cXZKmvGI_SSj0KDfOyVtNTCXNO5o_64";
	//Personal Google web-engine ID
	private final static String CX = "010345643380297177901:4s0abli8aki";
	
	//Delimeters used for search precision
	private final static String IMAGE = "image";
	private final static String PNG = "png";
	private final static String URL_START = "\"link\": \"";
	private final static String URL_END = "\",";
	private final static String API_ADDRESS = "https://www.googleapis.com/customsearch/v1?";
	private final static String IMAGE_FOLDER = "images/";
	
	public static ImageInfo findAndSaveRandomIcon(Random randy, String qry, List<String> hits, List<Integer> hitIters){
		BufferedImage image = findRandomIcon(randy, qry, hits, hitIters);
		BufferedImage transparent = transparent(image, Color.WHITE, Color.LIGHT_GRAY);
		String name = qry + "_random";
		String savePath = name;
		String s = savePng(image, savePath);
		return new ImageInfo(transparent, name + "." +  PNG, s);
	}
	
	//TODO: Debug this
	private static BufferedImage transparent(BufferedImage image, Color c1, Color c2)
	  {
	    // Primitive test, just an example
	    final int r1 = c1.getRed();
	    final int g1 = c1.getGreen();
	    final int b1 = c1.getBlue();
	    final int r2 = c2.getRed();
	    final int g2 = c2.getGreen();
	    final int b2 = c2.getBlue();
	    RGBImageFilter filter = new RGBImageFilter()
	    {
	      public final int filterRGB(int x, int y, int rgb)
	      {
	        int r = (rgb & 0xFF0000) >> 16;
	        int g = (rgb & 0xFF00) >> 8;
	        int b = rgb & 0xFF;
	        if (r >= r1 && r <= r2 &&
	            g >= g1 && g <= g2 &&
	            b >= b1 && b <= b2)
	        {
	          // Set fully transparent but keep color
	          return rgb & 0xFFFFFF;
	        }
	        return rgb;
	      }
	    };

	    ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
	    Image im = Toolkit.getDefaultToolkit().createImage(ip);
	    BufferedImage toRet = convertToBufferedImage(im);
	    return toRet;
	  }
	
	public static BufferedImage convertToBufferedImage(Image image)
	{
	    BufferedImage newImage = new BufferedImage(
	        image.getWidth(null), image.getHeight(null),
	        BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = newImage.createGraphics();
	    g.drawImage(image, 0, 0, null);
	    g.dispose();
	    return newImage;
	}
	
	/**
	 * Saved png to the internet images folder in the workspace
	 * @param image Image to be saved
	 * @param fileName Folder + name of file (e.g., images/button)
	 */
	private static String savePng(BufferedImage image, String fileName) {
		String toRet = "";
        try {
        	toRet = System.getProperty("user.home")+ "/Desktop/" + fileName + "." + PNG;
        	File file = new File(toRet);
            ImageIO.write(image, PNG, file);
            
        } catch (IOException e) {
        	//TODO
            e.printStackTrace();
        }
        return toRet;
    }
	
	/**
	 * For use in random Actor generation.
	 * 
	 * @param Random Object used to choose iteration for image
	 * @param qry The String topic to search
	 * @return BufferedImage found on the internet
	 */
	public static BufferedImage findRandomIcon(Random randy, String qry, List<String> hits, List<Integer> hitIters){
		int index = randy.nextInt(20) + 1;
		return findImage(qry+"+cartoon", PNG, index, hits, hitIters);
	}
	
	/**
	 * For use in random Actor generation.
	 * @param qry String topic to search
	 * @param iter How many layers of search to go through before choosing
	 * the final image
	 * @return BufferedImage found on the internet
	 */
	public static BufferedImage findIcon(String qry, int iter, List<String> hits, List<Integer> hitIters){
		return findImage(qry+"+cartoon", PNG, iter, hits, hitIters);
	}
	
	public static BufferedImage findImage(String qry, String fileType, int iter, List<String> hits, List<Integer> hitIters){
		return findSearchItem(qry, fileType, IMAGE, iter, hits, hitIters, 0);
	}
	
	/**
	 * 
	 * @param qry String search
	 * @param fileType Type of file
	 * @param searchType Type of search
	 * @param iter The number of iterations/ how "deep" to look
	 * @param hit List of already accessed URLs
	 * @param searchIter How many times the method has tried to access an image
	 * @return
	 */
	public static BufferedImage findSearchItem(String qry, String fileType, 
			String searchType, int iter, List<String> hit, List<Integer> hitIters,
			int searchIter){
		hitIters.add(iter);
		if(searchIter>100){
			try {
				return ImageIO.read(new File(IMAGE_FOLDER + "profile_icon.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		BufferedImage toRet = null;
		String search = stringToSearch(qry);
		try{
			HttpURLConnection google = constructSearchUrl(search, fileType, searchType);
			String imagePath = popFilePath(google, iter);
			if(!hit.contains(imagePath)){
				google.disconnect();
				URL toRead = new URL(imagePath);
				toRet = ImageIO.read(toRead);
				hit.add(imagePath);
			}
		} catch(IOException e){
			//TODO
		}
		if(toRet == null && iter-1>0 && !hitIters.contains(iter-1)){
			toRet = findSearchItem(qry, fileType, searchType, --iter, hit, hitIters, ++searchIter);
		} else if (toRet == null && !hitIters.contains(iter+1)){
			toRet = findSearchItem(qry, fileType, searchType, ++iter, hit, hitIters, ++searchIter);
		} else {
			while(hitIters.contains(iter)){
				iter++;
			}
			toRet = findSearchItem(qry, fileType, searchType, iter, hit, hitIters, ++searchIter);
		}
		return toRet;
	}
	
	private static String stringToSearch(String qry){
		return qry.replaceAll(" ", "+");
	}
	
	private static String popFilePath(HttpURLConnection conn, int iter) throws IOException {
		InputStream stream = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader((stream)));
		  
		String output;
		String toRet = "";
		int counter = 0;
		while ((output = br.readLine()) != null) {
		     if(output.contains(URL_START)){                
		         toRet = output.substring(output.indexOf(URL_START)+
		              (URL_START).length(), output.indexOf(URL_END));
		         if(counter++>=iter)
		        	 break;
		     }     
		}
		return toRet;
	}
	
	private static HttpURLConnection constructSearchUrl(String qry, String fileType, String searchType) throws IOException{
		URL url = new URL (API_ADDRESS + 
				"key=" +KEY+ 
				"&cx=" +CX+ 
				"&q=" +qry+
				"&fileType="+fileType+
				"&searchType="+searchType+
				"&alt=json");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    configureConnection(conn);
	    return conn;
	}
	
	private static void configureConnection(HttpURLConnection conn) throws ProtocolException{
		conn.setRequestMethod("GET");
	    conn.setRequestProperty("Accept", "application/json");
	}
	
/*	private static void printExceptionMessage(Exception e){
		JOptionPane.showMessageDialog(null, "Message: " + e.getMessage() 
			+ "\nCause: "+ e.getCause());
		
	}*/
}
