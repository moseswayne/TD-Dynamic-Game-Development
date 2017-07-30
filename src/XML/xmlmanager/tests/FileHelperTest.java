package XML.xmlmanager.tests;

import java.io.IOException;

import XML.xmlmanager.classes.ConcreteFileHelper;
import XML.xmlmanager.exceptions.IllegalFileException;
import XML.xmlmanager.interfaces.filemanager.FileHelper;

public class FileHelperTest {

	public static void main(String[] args){
		System.out.println("testing began");
		FileHelper helper = new ConcreteFileHelper();
		try {
			helper.moveFile("images/projectiles", "images", "dirt.png");
		} catch (IllegalFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
