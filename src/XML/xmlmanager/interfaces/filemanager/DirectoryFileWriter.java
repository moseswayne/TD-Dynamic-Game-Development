package XML.xmlmanager.interfaces.filemanager;

import java.io.IOException;

import XML.xmlmanager.exceptions.IllegalFileException;

/**
 * @author Gideon
 *
 * This interface extends {@link  DirectoryFileReader} with the additional functionality of writing to files.
 * using the addStringFileToDirectory method, you can create a new file with specific content and name
 * and store that file in the directory of interest. A concrete implementation can be found in  
 * {@link  NewDirectoryHelper} where the file is always added to the directory of instantiation.
 * This interface could be extended to further implementation involving filewriting. 
 */
public interface DirectoryFileWriter extends DirectoryFileReader{
	
	/**
	 * Writes a file to a directory only if valid and the file doesn't exist
	 * @param fileContent the text to save in the file
	 * @param filename the name of the file you would like to save the content in 
	 * @return true when the file was added, false if the file already existed
	 * @throws IOException thrown when the filepath passed is invalid
	 * @throws IllegalFileException thrown if your file is a valid from an OS file making standpoint, 
	 * but you don't have access to writing it based on class implementation
	 */
	boolean addStringFileToDirectory(String fileContent, String filename) throws IOException, IllegalFileException;
	
}
