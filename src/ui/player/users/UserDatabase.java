package ui.player.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Stores the list of all {@link ui.player.users.User User}s and their pertinent information.
 * @author Vishnu Gottiparthy
 * @author sarahzhou
 */
public class UserDatabase {
	
	private Collection<User> database;
	private Passwords passwords;
	private User activeUser;
	
	public UserDatabase() {
		database = new ArrayList<User>();
		passwords = new Passwords();
		activeUser = new User();
		addUser(activeUser);
	}
	
	/**
	 * Gets the active (logged in) {@code User} if nobody has logged in, the default {@code User} is returned
	 * @return The active {@code User}
	 */
	public User getActiveUser() {
		return activeUser;
	}
	
	/**
	 * Changes the active {@code User}. For use on login
	 * @param user The {@code User} to set as active
	 */
	public void setActiveUser(User user) {
		activeUser = user;
	}
	
	/**
	 * Gets the {@code Passwords} associated with this database
	 * @return The {@code Passwords} associated with this database
	 */
	public Passwords getPasswords(){
		return passwords;
	}
	
	/**
	 * Gets the entire database as a {@code Collection}
	 * @return
	 */
	public Collection<User> getDatabase(){
		return database;
	}
	
	/**
	 * Adds a {@code User} to the database
	 * @param newUser {@code User} to add
	 */
	public void addUser(User newUser) {
		database.add(newUser);
	}
	
	/**
	 * Removes a user from the database
	 * @param delete {@code User} to remove
	 */
	public void deleteUser(User delete) {
		database.remove(delete);
	}
	
	/**
	 * Gets an {@code Iterator} of {@code User}s in order by experience. For use with 
	 * {@link ui.player.leaderboard.LeaderboardView LeaderboardView}
	 * @return
	 */
	public Iterator<User> getUsersInExpOrder() {
		return database.stream().sorted(new Comparator<User>() {
			@Override
			public int compare(User o1, User o2) {
				return Double.compare(o1.getExperience(), o2.getExperience());
			}
		}).iterator();
	}	
}
