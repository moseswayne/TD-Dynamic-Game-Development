package facebookintegration.interfaces;


/**
 * 
 * @author Gideon
 *
 * This interface provides the user access to posting content directly onto their wall.
 * They can either post with a link to the game, or they can post independently of
 * the game. 
 *
 */
public interface FacebookPoster {
	
	/**
	 * @param toPost the message you want to post on your wall
	 * @return the link to your post after posting it
	 */
	String postWithoutVoogaLink(String toPost);
	
	/**
	 * @param toPost the post which will append the vooga git link on the end of it
	 * @return the link to your post after posting it
	 */
	String postWithVoogaLink(String toPost);

}
