package ui.handlers;

import java.util.Iterator;

import ui.player.users.User;

/**
 * Allows UI elements to display themselves and query the {@link ui.player.users.UserDatabase UserDatabase}. 
 * @see ui.player.login.LoginMain LoginMain
 * @author Vishnu Gottiparthy
 *
 */
public interface LoginHandler {
	
	/**
	 * Gets the {@code User} who is currently logged in
	 * @return The {@code User} who is currently logged in
	 */
	public User getActiveUser();
	
	/**
	 * Displays a {@link ui.player.users.ProfileCard ProfileCard} containing information for {@code user}
	 * @param user User to display a {@code ProfileCard} for
	 */
	public void showProfile();
	
	/**
	 * Sets the profile picture displayed to the picture of {@code user}
	 * @param user {@code User to show the profile picture of}
	 */
	public void setCornerProfileCard(User user);
	
	/**
	 * Checks whether the username/password combination is valid, and sets the active user. Gives an
	 * error dialog box when the combination is invalid.
	 * @param username Inputted username
	 * @param password Inputted password
	 * @return {@code true} if login was successful, {@code false} if unsuccessful
	 */
	public Boolean login(String username, String password);
	
	/**
	 * Finds a {@code User} with the specified username
	 * @param username Username to associate with a user
	 * @return Desired {@code User} if it exists, or {@code null} if it doesn't.
	 */
	public User findUser(String username);
	
	/**
	 * If login is successful, sets the active user in the database.
	 * @param user {@code User to set as active}
	 */
	public void setActiveUser(User user);
	
	/**
	 * Gets an {@code iterator} of all {@code User}s ordered by experience points
	 * @return {@code Iterator} of all {@code User}s ordered by experience points
	 */
	public Iterator<User> getUsersInExpOrder();
	
	/**
	 * Displays the {@link ui.player.login.Login Login}. For use with back buttons.
	 */
	public void returnToMain();
	
	/**
	 * Displays the {@link ui.player.login.Signup Signup}.
	 */
	public void gotoSignupPage();

	/**
	 * Displays the {@link ui.authoring.AuthoringView AuthoringView}
	 */
	public void gotoAuth();
	
	/**
	 * Displays the {@link ui.player.GameSelector GameSelector}
	 */
	public void gotoGameSelector();
	
	/**
	 * Displays the {@link ui.player.ratings.RatingView RatingView}
	 */
	public void gotoReviews();
	
	/**
	 * Displays the {@link ui.player.leaderboard.LeaderboardView}
	 */
	public void gotoLeaderboard();
}
