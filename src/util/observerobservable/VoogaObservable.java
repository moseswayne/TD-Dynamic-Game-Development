package util.observerobservable;

import java.util.HashSet;
import java.util.Set;

/**
 * @author sarahzhou
 *
 * @param <T>
 */
public abstract class VoogaObservable<T> {
	private Set<VoogaObserver<T>> myObservers;
	
	public VoogaObservable() {
		myObservers = new HashSet<>();
	}
	
	public void addObserver(VoogaObserver<T> observer) {
		myObservers.add(observer);
		observer.update(notification());
	}
	
	public void removeObserver(VoogaObserver<T> observer) {
		myObservers.remove(observer);
	}
	
	public void notifyObservers() {
		for (VoogaObserver<T> observer : myObservers) {
			observer.update(notification());
		}
	}

	protected abstract T notification();

}
