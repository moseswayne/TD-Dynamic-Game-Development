package XML.xmlmanager.interfaces.filemanager;

/**
 * @author Gideon
 * 
 * This interface is designed to grant access to basic 
 * file functionalities like deleting, overwriting, moving,
 * and more. It assumes no protective power over what can and cannot be done.
 * It will do what it is told with no restriction.
 * 
 * This class should have been refactored to exclusively throw
 * custom made Exceptions. Had I had more time to refactor, this would have been
 * one of my priorities
 */

import java.io.File;

import java.io.IOException;

import XML.xmlmanager.exceptions.IllegalFileException;

public interface FileHelper {
	
	/**
	 * @param filepath the filepath of the file being overwritten
	 * @param fileContent the content to overwrite the file with
	 * @return whether or not the file existed
	 * @throws IllegalFileException when the filepath is a directory not a file
	 * @throws IOException when there is something incorrect in the Java's handling of the files
	 * with the specified filepath
	 */
	boolean overwriteStringFile(String filepath, String fileContent) throws IllegalFileException, IOException;
	
	/**
	 * @param file the file to delete
	 * @return whether or not the file existed
	 */
	boolean deleteFile(File file);
	
	/**
	 * @param directory the directory to delete
	 * @return whether or not the directory existed
	 * @throws IOException when the file cannot be deleted
	 */
	boolean deleteDir(File directory) throws IOException;
	
	/**
	 * @param file the file to read the content from
	 * @return the String extracted from the file
	 * @throws IOException when a String cannot be extracted from the file passed
	 */
	String readFile(File file) throws IOException;
	
	/**
	 * @param startDirPath the start dir of the file
	 * @param endDirPath the end dir of the file
	 * @param filename the failename being moved
	 * @throws IllegalFileException when the file doesn't exist
	 * @throws IOException when the old file cannot be deleted or the new file cannot be made
	 */
	void moveFile(String startDirPath, String endDirPath, String filename) throws IllegalFileException, IOException;	
}
