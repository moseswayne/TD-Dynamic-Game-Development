package XML.directorytree.interfaces;

import java.util.Collection;

import XML.directorytree.classes.DirectoryNode;

/**
 * @author Gideon
 *
 * This interface is Designed to help build a directory tree 
 * using String filepath to add everything to the tree.
 * All returns and inputs are in the for of Strings, not files.
 * This interface could be exploited to create a file tree by making
 * files out of the String paths returned. For a concrete implementation
 * see {@link DirectoryNode}. For testing, see {@link DirectoryTreeTest}.
 * 
 *
 */

public interface StringDirectoryTree {
	
	/**
	 * @param filepath the filepath to be added to the directory instantiated with
	 * @return whether or not the directory was added
	 */
	boolean addDirectoryToTree(String filepath);
	
	/**
	 * @param filepath the file of the file to be added
	 * @return whether or not the file was added
	 */
	boolean addFileToTree(String filepath);
	
	/**
	 * @return the root directory instantiated with see {@link DirectoryNode}.
	 */
	String getRootDirectory();
	
	/**
	 * @return the sub-directories created on top of the base directory see {@link DirectoryNode}.
	 */
	Collection<DirectoryNode> getSubDirectories();
	
	/**
	 * @return the files added since the tree was made
	 */
	Collection<String> getFiles();
	
	/**
	 * A visual aid of what the tree looks like with arrows of parent dir's 
	 * pointing to children parent --> children
	 */
	void printTree();

}
