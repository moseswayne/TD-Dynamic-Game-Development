package XML.directorytree.classes;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import XML.directorytree.interfaces.BoolSwitch;
import XML.directorytree.interfaces.StringDirectoryTree;
import XML.directorytree.interfaces.Pair;

/**
 * 
 * @author Gideon
 *
 * This class was never integrated into the project
 * 
 * This class build a tree of directories and files in those directories.
 * 
 * It was intended that this structure would help to generate file systems,
 * but more effective alternatives were found See {@link FileHelper}
 * 
 * Nonethless, the algorithms used in this class are pretty effective/complex
 * 
 * This class needs significant refactoring, polishing, an interface, 
 * and to be tested further before I would be comfortable implementing it.
 */
public class DirectoryNode implements StringDirectoryTree{

	private Collection<String> files;
	private Collection<DirectoryNode> subDirectories;
	private String rootDirectory;
	
	public DirectoryNode(String rootDirectory){
		this(rootDirectory, new ArrayList<>(), new ArrayList<>());
	}
	
	public DirectoryNode(String rootDirectory, Collection<String> files, Collection<DirectoryNode> directories){
		this.rootDirectory = rootDirectory;
		this.files = files;
		this.subDirectories = directories;
	}
	
	/**
	 * @param filepath the filepath which is being checked to see if it is internal to the ootDir
	 * @return the boolean indicating whether or not the filepath is a child of the rootDir from instantiation
	 */
	private boolean sharesFilepathWith(String filepath){
		if(filepath == null) return false;
		return filepath.split(rootDirectory).length > 1  
				&& filepath.split(rootDirectory)[1].length() > 1
				&& filepath.split(rootDirectory)[1].startsWith("/")
				|| filepath.equals(rootDirectory);
	}
	
	/**
	 * Returns whether or not the directory was added to the tree
	 * 
	 * Reasons for returning false may be the directory
	 * already existed or if the filepath
	 * was external to the rootDirectory (could not be added to the rootDir)
	 */
	public boolean addDirectoryToTree(String filepath){
		if(isInvalidFilepath(filepath)) return false;
		Pair<Integer, Collection<DirectoryNode>> finder = new ConcretePair<>(-1, subDirectories);
		findProperDirectory(this, filepath, 0, finder);
		finder.getValue().add(new DirectoryNode(filepath));
		return true;
	}
	
	/**
	 * @param filepath the filepath to be checked for validity
	 * @return whether or not the filepath was invalid
	 */
	private boolean isInvalidFilepath(String filepath){
		return (filepath == null 
				|| filepath.length() == 0
				|| directoryExistsAlready(this, filepath).getKey()
				|| !this.sharesFilepathWith(filepath) );
	}
	
	/**
	 * The same as the addDirectoryToTree method except for the file
	 * will be added to an existing directory instead of making a new one
	 * 
	 * Returns whether or not the file could be added based on the filepath
	 */
	public boolean addFileToTree(String filepath){
		if(isInvalidFilepath(filepath) || filepath.indexOf("/") == -1 ) return false;
		if(filepath.split(rootDirectory).length < 2) return false;
		
		Pair<Boolean, DirectoryNode> indicator =  directoryExistsAlready(this, 
				filepath.substring(0, filepath.lastIndexOf("/")));
		if(!indicator.getKey()) return false;
		indicator.getValue().addFile(filepath);
		return true;
	}
	
	/**
	 * @param root The root of the directory tree being searched
	 * @param filepath the filepath to which the file is being added
	 * @param depth the depth of the tree during its search
	 * @param best the Pair of subdirectory paired with its current search depth
	 */
	private void findProperDirectory(DirectoryNode root, String filepath, int depth, 
			Pair<Integer, Collection<DirectoryNode>> best){
		if(root == null || !root.sharesFilepathWith(filepath)) return;
		if(best.getKey() < depth){
			if(newIntermediateDirectoryExists(root, filepath)){
				String intermedDirectory = root.rootDirectory + getIntermediateDirectory(root, filepath);
				if(!directoryExistsAlready(root, intermedDirectory).getKey()){
					DirectoryNode newDir = new DirectoryNode(intermedDirectory);
					root.subDirectories.add(newDir);
					findProperDirectory(newDir, filepath, depth + 1, best);
				}
			}
			best.setKey(depth);
			best.setValue(root.subDirectories);
		}
		for(DirectoryNode dir: root.getSubDirectories()){
			findProperDirectory(dir, filepath, depth + 1, best);
		}
	}
	
	/**
	 * @param filepath Adds a file to the Collection of added files
	 */
	private void addFile(String filepath){
		files.add(filepath);
	}
	
	/**
	 * @param root the root to start seraching for the filepath from
	 * @param filepath the filepath of interest in terms of existence
	 * @return whether or not the filepath exists within that root directory "foo if didn't exist"
	 */
	private Pair<Boolean, DirectoryNode> directoryExistsAlready(DirectoryNode root, String filepath) {
		BoolSwitch bswitch = new ConcreteBoolSwitch(false);
		Pair<BoolSwitch, DirectoryNode> indicator = new ConcretePair<>(bswitch, new DirectoryNode("foo"));
		directoryExistsHelper(root, filepath, indicator);
		return new ConcretePair<>(indicator.getKey().getState(), indicator.getValue());
	}
	
	/**
	 * @param root the root for the searching of the filepath
	 * @param filepath the filepath that is being searched for
	 * @param pair the pair holding the boolean of existence and the RootDir
	 */
	private void directoryExistsHelper(DirectoryNode root, String filepath, Pair<BoolSwitch, DirectoryNode> pair){
		if(root == null) return;
		if(root.rootDirectory.equals(filepath)){
			pair.getKey().setTrue();
			pair.setValue(root);
			return;
		}
		for(DirectoryNode child: root.getSubDirectories()){
			directoryExistsHelper(child,filepath, pair);
		}
	}
	
	/**
	 * @param root the root directory searching for an intermediate directory
	 * @param newDir the newDirectory from which to extract any intermediateDirectories
	 * @return true if the directory is an intermediateDirectory
	 * 
	 * for example true when "root = src" and newDir = "src/intermediate/newDir"
	 * false when "root = src" and newDir = "src/newDir"
	 */
	private boolean newIntermediateDirectoryExists(DirectoryNode root, String newDir){
		if(!root.sharesFilepathWith(newDir) || root.getRootDirectory().equals(newDir)) return false;
		String[] splitDir = newDir.split(root.getRootDirectory());
		return splitDir.length > 1 && splitDir[1].length() > 2 && splitDir[1].indexOf("/", 1) != -1;
	}
	
	/**
	 * @param root the directory to serve as the root
	 * @param newDir the newDirectory filepath
	 * @return the intermediate filepath
	 * 
	 * for example true when "root = src" and newDir = "src/intermediate/newDir"
	 * returns "src/intermediate"
	 */
	private String getIntermediateDirectory(DirectoryNode root, String newDir){
		String[] splitDir = newDir.split(root.getRootDirectory());
		return splitDir[0] + splitDir[1].substring(0, splitDir[1].indexOf("/", 1));
	}
	
	/**
	 * returns the rootDirectory filepath from instantiation
	 */
	public String getRootDirectory(){
		return rootDirectory;
	}
	
	/**
	 * returns the subDirectories of the DirectoryNode
	 */
	public Collection<DirectoryNode> getSubDirectories(){
		return Collections.unmodifiableCollection(subDirectories);
	}
	
	/**
	 * Returns the files added to the node
	 */
	public Collection<String> getFiles(){
		return Collections.unmodifiableCollection(files);
	}
	
	/**
	 * prints the directory tree in a relatively readable way
	 * 
	 * uses the directory helper which recursively goes through all 
	 * directories and files
	 */
	public void printTree(){
		printHelper("root", this);
	}
	
	/**
	 * @param parent the parent of the directory being printed
	 * @param child the DirectoryNode child of the specified parent filepath
	 * 
	 * Prints the directory and all of the children
	 */
	private void printHelper(String parent, DirectoryNode child){
		if(child == null) return;
		System.out.println("DIRECTORY: " + parent + " --> " + child.getRootDirectory());
		for(String file: child.files){
			System.out.println("FILE: " + parent + " --> " + file);
		}
		for(DirectoryNode nextGen: child.subDirectories){
			printHelper(child.getRootDirectory(), nextGen);
		}
	}
	
}
