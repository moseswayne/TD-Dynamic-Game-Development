package gameengine.map.interfaces;

import java.util.Collection;

public interface IReadableMap <Type> {

	Collection<Collection<Type>> readAll();
	
}
