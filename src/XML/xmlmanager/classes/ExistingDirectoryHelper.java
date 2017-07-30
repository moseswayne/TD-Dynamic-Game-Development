package XML.xmlmanager.classes;

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
 * to a directory that already exists somewhere where as the {@link NewDirectoryHelper} should
 * be used when you want to make a new directory.
 * 
 * This class provides protection from writing, reading, adding,
 * and deleting from just any file
 */
public class ExistingDirectoryHelper extends AbstractFileHelper{
	
	private String directoryPath;
	private List<String> newFiles;
	
	public ExistingDirectoryHelper(String directoryPath) throws InvalidRootDirectoryException{
		if(!directoryExists(directoryPath)){
			throw new InvalidRootDirectoryException(new IllegalStateException("Directory didn't exist"));
		}
		this.directoryPath = directoryPath;
		newFiles = new ArrayList<>();
	}

	/**
	 * removes all of the files that have been added by this class
	 * 
	 * See {@link DirectoryFileManager}
	 */
	@Override
	public void cleanse() throws IOException {
		deleteFilesHelper(newFiles, directoryPath);
		newFiles.clear();
	}

	/**
	 * Adds a String file to the directory this class was instantiated with
	 */
	@Override
	public boolean addStringFileToDirectory(String fileContent, String filename) throws IOException, IllegalFileException{
		return addStringFileToDirectoryHelper(fileContent, filename, directoryPath, newFiles);
	}

	/**
	 * Returns whether or not a file exists in the directory instantiated with
	 */
	@Override
	public boolean fileExists(String filename) {
		return fileIsClean(filename) && validFile(directoryPath + "/" + filename);
	}

	/**
	 * Returns the String content of the filename passed in
	 * 
	 * Looks in the directory instantiated with
	 */
	@Override
	public String getFileContent(String filename) throws IOException, IllegalFileException {
		return getFileContentHelper(directoryPath, filename);
	}

	/**
	 * Returns the names of all files that have been added
	 */
	@Override
	public Collection<String> getAllNewFilenames() {
		return Collections.unmodifiableCollection(newFiles);
	}

	/**
	 * Returns all file sin the directory that the class was instantiated with
	 */
	@Override
	public Collection<String> getAllFilesInDirectory() {
		return Collections.unmodifiableCollection(getFilesInDir(directoryPath));
	}
}
