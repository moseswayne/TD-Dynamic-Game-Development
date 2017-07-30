package facebookintegration.interfaces;

import javafx.scene.image.Image;

/**
 * 
 * @author Gideon
 * This interface is part of the MasterFacebookUser interface.
 * It allows for access to the Profile Picture in the form of an Image. 
 * Can be passed to classes that only need access to the profile pic. 
 */
public interface ProfilePictureAccessor {

	/**
	 * @return the image of the users profile picture
	 */
	Image getProfilePicture();
	
}
