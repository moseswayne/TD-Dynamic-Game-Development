package util;

public class Tuple<X, Y> {
	public X x;
	public Y y;

	public Tuple(X x, Y y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		return this.x + "\t" + this.y;
	}
}