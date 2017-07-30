package XML.xmlmanager.interfaces.filemanager;

import java.io.IOException;
import java.util.Collection;

import XML.xmlmanager.exceptions.IllegalFileException;

/**
 * @author Gideon
 *
 * This interface should be used for reading file contents and checking the files that have been added.
 * A concrete example is {@link  DirectoryFileWriter}. In {@link  DirectoryFileWriter} it is expected that
 * you pass in a filename without the file path because the instantiation defines the root directory.
 * This interface could be extended to allow for file reading on any system where files added is meant to be trancked. 
 */

public interface DirectoryFileReader {

	/**
	 * @param fileName the file you are searching for
	 * @return a boolean whether or not that file exists
	 */
	boolean fileExists(String fileName);
	
	/**
	 * 
	 * @param filename the name of the file to extract the text from
	 * @return the String text extracted from the file
	 * @throws IOException this occurs when the filename specified is faulty
	 * @throws IllegalFileException occurs when you try to read a file you don't have access to
	 */
	String getFileContent(String filename) throws IOException, IllegalFileException;
	
	/**
	 * @return a collection of filenames for all of the new files added
	 */
	Collection<String> getAllNewFilenames();
	
	/**
	 * @return a collection of all of the files in the directory
	 */
	Collection<String> getAllFilesInDirectory();
	
}
