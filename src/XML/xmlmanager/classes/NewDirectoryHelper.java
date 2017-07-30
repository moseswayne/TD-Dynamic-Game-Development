package XML.xmlmanager.classes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import XML.xmlmanager.exceptions.IllegalFileException;
import XML.xmlmanager.exceptions.InvalidRootDirectoryException;

/**
 * @author Gideon
 *
 * This class is one flavor of the DirectoryFileMansger
 * This class is to be used when you want to give exclusive access
 * to a directory that doesn't already exist.
 * This class makes the directory upon .
 * {@link ExistingDirectoryHelper} should
 * be used when you want to add to an existing directory
 * 
 * This class provides protection from writing, reading, adding,
 * and deleting from just any file
 */

public class NewDirectoryHelper extends AbstractFileHelper{
	
	private List<String> addedFiles;
	private String rootDir;
	
	/**
	 * @param baseDirectoryPath the directory to add the new directory in
	 * @param newRootDirectoryName the new directories name
	 * @throws InvalidRootDirectoryException when the baseDirectory doesn't exist
	 * @throws IOException when the new directory could not be added
	 */
	public NewDirectoryHelper(String baseDirectoryPath, String newRootDirectoryName) throws InvalidRootDirectoryException, IOException{
		rootDir = baseDirectoryPath + "/" + newRootDirectoryName;
		makeIfPossible(baseDirectoryPath, newRootDirectoryName);
		addedFiles = new ArrayList<>();
	}
	
	/**
	 * Checks for the validity of the instantiation of the class.
	 * Makes sure that the baseDirectory and new directories are valid
	 */
	private void makeIfPossible(String baseDirectoryPath, String newRootDirectoryName) 
			throws InvalidRootDirectoryException, IOException{
		rootDir = baseDirectoryPath + "/" + newRootDirectoryName;
		if(!directoryExists(baseDirectoryPath) 
				|| newRootDirectoryName.contains("/")
				|| directoryExists(rootDir)
				|| baseDirectoryPath.length() < 1){
			throw new InvalidRootDirectoryException(new IllegalStateException("Invalid root"));
		}
		new File(rootDir).mkdirs();
	}
	
	/**
	 * returns whether or not a file with the specified name exists in the is directory
	 */
	@Override
	public boolean fileExists(String filename){
		return fileIsClean(filename) && validFile(rootDir + "/" + filename);
	}

	/**
	 * Adds a String file to the directory, returns whether or not the file was added (false if the file already existed)
	 */
	@Override
	public boolean addStringFileToDirectory(String fileContent, String filename) throws IOException, IllegalFileException{
		return addStringFileToDirectoryHelper(fileContent, filename, rootDir, addedFiles);
	}

	/**
	 * Returns the String file content from the file of interest
	 */
	@Override
	public String getFileContent(String filename) throws IOException, IllegalFileException{
		return getFileContentHelper(rootDir, filename);
	}

	/**
	 * deletes all of the file that have been added by this class
	 */
	@Override
	public void cleanse() throws IOException {
		deleteFilesHelper(addedFiles, rootDir);
		addedFiles.clear();
	}

	/**
	 * returns a collection of all of the new files that have been added and not yet cleared
	 * since the object of this class was made
	 */
	@Override
	public Collection<String> getAllNewFilenames() {
		return Collections.unmodifiableCollection(addedFiles);
	}

	/**
	 * Returns all files currently in the directory this class was instantiated to make
	 */
	@Override
	public Collection<String> getAllFilesInDirectory() {
		return Collections.unmodifiableCollection(getFilesInDir(rootDir));
	}
}
