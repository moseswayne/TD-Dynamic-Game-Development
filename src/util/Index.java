package util;

public class Index {
	private Tuple<Integer,Integer> index;
	
	public Index(Integer col, Integer row){
		setIndex(col, row);
	}

	public Integer getX() {
		return index.x;
	}
	
	public Integer getY() {
		return index.y;
	}

	public void setIndex(Integer col, Integer row) {
		this.index = new Tuple<Integer,Integer>(col, row);
	}
	
	
}
