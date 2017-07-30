package ui.player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;

import ui.player.users.Passwords;

public class XStreamFileChooser {

	String filename;
	
	public XStreamFileChooser() {
		this("");
	}
	
	public XStreamFileChooser(String filename) {
		this.filename = filename;
	}
	
	/**
	 * This creates a new file / overwrites file containing the XStream serialization
	 * @param xml is the String from using the XStream serializer
	 */
	public void writeFile(String xml){
		try {
			File file = new File(filename);
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(xml);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("XML Error");
		}
	}
	
	/**
	 * Reads in the previous saved file
	 * @param mySerializer
	 * @returns the class loaded from the serialized file
	 * 
	 * @TO
	 */
	public Passwords readInPasswords(XStream mySerializer) {
		Passwords passwords = new Passwords();
        try {
            Scanner scanner = new Scanner(new File(filename));
			String content = scanner.useDelimiter("\\Z").next();
			scanner.close();
            passwords = (Passwords) mySerializer.fromXML(content);
        }
        catch (Exception ex) {
        	passwords = new Passwords();
        }
		return passwords;
	}
	
	public String readInClass() {
        try {
        	Scanner scanner = new Scanner(new File(filename));
			String content = scanner.useDelimiter("\\Z").next();
			scanner.close();
			return content;
        }
        catch (Exception ex) {
        	return null;
        }
	}
}
