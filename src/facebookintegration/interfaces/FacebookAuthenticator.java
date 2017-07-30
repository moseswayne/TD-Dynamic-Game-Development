package facebookintegration.interfaces;

/**
 * 
 * @author Gideon
 *
 * This interface is designed to be used for authenticating the user in 
 * Facebook. It can be queried to see if the user is authenticated all ready
 * and can be called to authenticate which will set the user up.
 */
public interface FacebookAuthenticator {

	/**
	 * @return whether or not the user has been authenticated already
	 */
	boolean isAuthenticated();
	
	/**
	 * Authenticates the user by prompting them to login to their Facebook and grant 
	 * access rights to the application. 
	 */
	void authenticate();
	
}
