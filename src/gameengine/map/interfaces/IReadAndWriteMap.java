package gameengine.map.interfaces;

import java.util.Collection;

public interface IReadAndWriteMap <Identifier, Type>{
	
	boolean add(Identifier identity, Type adding);
	
	Collection<Collection<Type>> readAll();
}
