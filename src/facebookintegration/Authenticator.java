package facebookintegration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.restfb.types.FacebookType;

import facebookintegration.interfaces.MasterFacebookUser;
import javafx.scene.image.Image;
/**
 * 
 * @author Gideon
 * This class is a concrete implementation of the {@link MasterFacebookUser} interface.
 * 
 * This class uses Selenium webdrivers and RestFBApi to grant access to Facebook posting,
 * profile pics, and more
 */
public class Authenticator implements MasterFacebookUser{
	private static final String DOMAIN = "http://www.cs.duke.edu/courses/compsci308/spring17/classwork/";
	private static final String APP_ID = "164672860722038";
	private static final String AUTH_URL = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id="+APP_ID+
			"&redirect_uri="+DOMAIN+"&scope=email,user_about_me,user_friends,user_birthday," + 
			"user_posts,user_photos,public_profile,manage_pages,publish_actions";
	
	private static final String VOOGA_LINK = "https://coursework.cs.duke.edu/CompSci308_2017Spring/voogasalad_ilovesingletons";
	
	private static final String DRIVER_STR = "webdriver.chrome.driver";
	private static final String DRIVER_LOC = "lib/chromedriver";
	
	private WebDriver driver;
	private FacebookClient client;
	private boolean authenticated;
	
	
	public Authenticator(){
		System.setProperty(DRIVER_STR, DRIVER_LOC);
		driver = new ChromeDriver();
	}
	
	/**
	 * @return the image after extracting it from the rest API using
	 * web requests
	 */
	private Image getProfilePicFromClient(){
		JsonObject picture = 
			      client.fetchObject("me/picture", 
				      JsonObject.class, Parameter.with("redirect","false"));
			
			String url = picture.get("data").asObject().get("url").asString();
			return extractImageViewFromURL(url);
	}
	
	/**
	 * @param url the url to extract the image from
	 * @return the image that was extracted from that url
	 */
	private Image extractImageViewFromURL(String url){
		return new Image(url);
	}

	/**
	 * return whether or not the facebook user has been authenticated
	 */
	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	/**
	 * NEEDS TO BE REFACTORED; NO SOLUTION HAS BEEN FOUND FOR DETERMINGING
	 * WHEN THE WEB DRIVER IS CLOSED SO THERE IS NO WAY TO DETERMINE WHEN 
	 * TO EXIT THE AUTHENTICATION LOOP. NEEDS TO BE FIXED BEFORE INTEGRATION
	 * 
	 * Authenticates the user using the res FB API
	 */
	@Override
	public void authenticate() {
		String accessToken;
		driver.get(AUTH_URL);
		
		while(true){ //NEEDS TO BE FIXED
			if(!driver.getCurrentUrl().contains("facebook.com")){
				String url = driver.getCurrentUrl();
				accessToken = url.replaceAll(".*#access_token=(.+)&.*", "$1");
				driver.quit();
				break;
			}
		}
		client = new DefaultFacebookClient(accessToken, Version.LATEST);
		authenticated = true;
	}

	/**
	 * posts something to timline without appending the vooga link
	 */
	@Override
	public String postWithoutVoogaLink(String toPost) {
		FacebookType response = client.publish("me/feed", FacebookType.class, Parameter.with("message", toPost));
		return "fb.com/" + response.getId();
	}

	/**
	 * posts something to timeline after appending the vooga link
	 */
	@Override
	public String postWithVoogaLink(String toPost) {
		return postWithoutVoogaLink(toPost + " Download I<3Singletons today at " + VOOGA_LINK);
	}

	/**
	 * returns the profile picture of the facebook user
	 */
	@Override
	public Image getProfilePicture() {
		return getProfilePicFromClient();
	}
}
