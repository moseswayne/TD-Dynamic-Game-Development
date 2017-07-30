package XML.xmlmanager.interfaces.filemanager;

import java.io.IOException;


/**
 * @author Gideon
 * 
 * This interface extends {@link  DirectoryFileWriter} which gives the capabilities of reading
 * and writing files to specific directory. Additionally, this interfaces gives the user
 * access to cleansing a directory. Cleansing means to delete directory A and
 * all sub directories and files of A where A is the directory pointed to 
 * when concrete class that implements this is instantiated. For example {@link  NewDirectoryHelper}.
 */

public interface DirectoryFileManager extends DirectoryFileWriter{

	/**
	 * This methods removes the directory (ad all sub files/directories) instantiated in the
	 * creation of a concrete class. For example {@link  NewDirectoryHelper}
	 * @throws IOException throws IOException when an error occurs in directory/file deletion
	 */
	void cleanse() throws IOException;
	
}
