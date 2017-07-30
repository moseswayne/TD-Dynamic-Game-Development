package types;
//http://stackoverflow.com/questions/19680418/how-to-use-enum-with-grouping-and-subgrouping-hierarchy-nesting
public enum ActorType {
	ENEMY(Type.ENEMY),
	TOWER(Type.TOWER),
	PROJECTILE(Type.PROJECTILE);
	
	private Type type;
	
	ActorType(Type type) {
		this.type = type;
	}
	
	public boolean isSameType(ActorType actor) {
		return this.type ==actor.type;
	}
	
	public enum Type {
		ENEMY,
		PROJECTILE,
		TOWER;
		
	}
	
	
}
