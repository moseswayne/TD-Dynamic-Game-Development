package util.generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Class for collecting images from the internet
 * 
 * Resource:
 * http://codigogenerativo.com/code/google-custom-search-api/
 * https://developers.google.com/custom-search/json-api/v1/reference/cse/list
 * 
 * @author maddiebriere
 *
 */

public class TestImage {
	private String key;
	private String qry;
	private String cx;
	private String fileType;
    private String searchType;
	
	public TestImage(String key, String qry, String cx,
			String fileType, String searchType) {
		this.key = key;
		this.qry = qry;
		this.cx = cx;
		this.fileType = fileType;
		this.searchType = searchType;
	}

	public static void main (String [] args){
		String key="AIzaSyA5cXZKmvGI_SSj0KDfOyVtNTCXNO5o_64";
		String cx = "010345643380297177901:4s0abli8aki";
		TestImage test = new TestImage(key, "puppy", cx, "png", "image");
		try {
			test.search();
		} catch (URISyntaxException | IOException e) {
			System.out.println("WOOPS");
		}
	}
	
	public void search()  throws MalformedURLException, URISyntaxException, IOException {
	  URL url = new URL ("https://www.googleapis.com/customsearch/v1?key=" 
			  +key+ "&cx=" +cx+ "&q=" +qry+"&fileType="+fileType
			  +"&searchType="+searchType+"&alt=json");
	  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	  conn.setRequestMethod("GET");
	  conn.setRequestProperty("Accept", "application/json");
	  
	  BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream() ) ) );
	  
	  String output;
	  System.out.println("Output from Server .... \n");
	  while ((output = br.readLine()) != null) {
	        if(output.contains("\"link\": \"")){                
	            String link=output.substring(output.indexOf("\"link\": \"")+
	            		("\"link\": \"").length(), output.indexOf("\","));
	            System.out.println(link);       //Will print the google search links
	            break;
	        }     
	  }
	  conn.disconnect(); 
	}
		
}