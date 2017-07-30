package builders;

import util.IDGenerator;

public class TestID {
	public static void main(String[] args) {
		doTheThing();
		for(int i=0; i<100; i++) {
			System.out.println(IDGenerator.getNewID());
		}
		doTheThing();
	}
	
	public static void doTheThing() {
		System.out.println(IDGenerator.getNewID());
	}
}
