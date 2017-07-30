package facebookintegration.interfaces;

/**
 * 
 * @author Gideon
 * This interface provides access to all of the functionality
 * for Facebook users. For more information on the functionality provided by this
 * interface see {@link FacebookPoster} or {@link FacebookAuthenticator} or 
 * {@link ProfilePictureAccessor}. Authenticator is a concrete implementation of this interface. 
 */

public interface MasterFacebookUser extends FacebookAuthenticator, FacebookPoster, ProfilePictureAccessor{

}
