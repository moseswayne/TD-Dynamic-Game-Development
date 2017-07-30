package XML.xmlmanager.classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import XML.xmlmanager.exceptions.IllegalFileException;
import XML.xmlmanager.interfaces.filemanager.DirectoryFileManager;
/**
 * 
 * @author Gideon
 *
 * This abstract class is responsible for implementing all of the high level
 * file necessities such that the classes that extend it can
 * use the protected methods for their own concrete implementations
 * 
 * See {@link ExistingDirectoryHelper} and {@link NewDirectoryHelper} for example
 */
public abstract class AbstractFileHelper implements DirectoryFileManager{

	public AbstractFileHelper(){
		
	}
	
	private boolean addFileToDirectory(String fileContent, String filepath) throws IOException{
		File file = new File(filepath);
		if(file.exists()) return false;
		writeFile(file, fileContent);
		return true;
	}
	
	/**
	 * @param file the file to write the content to
	 * @param fileContent the content to be written
	 * @throws IOException when the file cannot be written to
	 */
	private void writeFile(File file, String fileContent) throws IOException{
		FileWriter fileWriter = new FileWriter(file, false);
		fileWriter.write(fileContent);
		fileWriter.close();
	}
	
	/**
	 * @param filepath the filepath to the directory
	 * @return whether or not the file exists
	 */
	protected boolean directoryExists(String filepath) {
		return applyAndTest(filepath, fp -> new File(fp), file -> file.exists() && file.isDirectory());
	}
	
	/**
	 * @param filepath the file being examined
	 * @return whether or not the file is an existing file
	 */
	protected boolean validFile(String filepath){
		return applyAndTest(filepath, fp -> new File(fp), file -> file.exists() && !file.isDirectory());
	}

	/**
	 * @param input the input to be tested
	 * @param funct the function to map the input
	 * @param pred the predicate to test the input after the function has been applied
	 * @return whether or not the predicate returned true or false
	 */
	private <I, O> boolean applyAndTest(I input, Function<I, O> funct, Predicate<O> pred){
		return pred.test(funct.apply(input));
	}
	
	/**
	 * @param filepath the filepath to get the content from
	 * @return the String extracted from the file
	 * @throws IOException when the file could not be read
	 */
	private String getFileContentHelper(String filepath) throws IOException{
		return new String(Files.readAllBytes(Paths.get(filepath)));
	}
	
	/**
	 * @param filepaths a list of filepaths to delete
	 */
	private void deleteFiles(List<String> filepaths){
		for(String filepath: filepaths){
			deleteFile(filepath);
		}
	}
	
	/**
	 * @param filename the filename to check for validity
	 * @return whether or not the filename is a clean filename (contains no "/"'s
	 * and is at least a length of 2)
	 */
	protected boolean fileIsClean(String filename){
		return !(filename == null || filename.length() < 1 || filename.contains("/"));
	}
	
	/**
	 * @param filepath the filepath for the file to be deleted
	 */
	private void deleteFile(String filepath){
		File file = new File(filepath);
		file.delete();
	}
	
	/**
	 * @param dirPath the directory filepath
	 * @param filename the filename which should be in the directory specified by the first parameter
	 * @return the String content from the file
	 * @throws IOException when the file could not be read
	 * @throws IllegalFileException when the filename contained a "/"
	 */
	protected String getFileContentHelper(String dirPath, String filename) throws IOException, IllegalFileException{
		if(filename.contains("/")) throw new IllegalFileException(new IllegalStateException("tried reading a file with a / in it"));
		return getFileContentHelper((dirPath + "/" + filename));
	}
	
	/**
	 * @param dirPath the directory to get the files from
	 * @return a collection of the absolute filepaths for all files in the dirPath directory
	 */
	protected Collection<String> getFilesInDir(String dirPath){
		return map(filter(allFilesInDir(dirPath), f -> f.isFile()), f -> f.getAbsolutePath());
	}
	
	/**
	 * @param dirPath the directories to get the files from
	 * @return a collection of the absolute filepaths for all directories in the dirPath directory
	 */
	protected Collection<String> getDirsInDir(String dirPath){
		return map(filter(allFilesInDir(dirPath), f -> f.isDirectory()), f -> f.getAbsolutePath());
	}
	
	/**
	 * @param dirPath the directory path
	 * @return all of the String filepaths which are in the directory specfied by the first parameter
	 */
	private Collection<File> allFilesInDir(String dirPath){
		File folder = new File(dirPath);
		return Arrays.asList(folder.listFiles());
	}
	
	/**
	 * @param col a collection of inputs
	 * @param function a function to map inputs to outputs
	 * @return a Collection of outputs after the function has been aplied to the inputs
	 */
	private <I, O> Collection<O> map(Collection<I> col, Function<I, O> function){
		return col.stream().map(i -> function.apply(i)).collect(Collectors.toList());
	}
	
	/**
	 * @param col a collection of inputs
	 * @param pred a predicate to filter the inputs
	 * @return a collection after filtering the inputs based on the predicate
	 */
	private <I> Collection<I> filter(Collection<I> col, Predicate<I> pred){
		return col.stream().filter(i -> pred.test(i)).collect(Collectors.toList());
	}
	
	/**
	 * @param fileContent the content to be wrtiten into the file
	 * @param filename the name of the file to write it to
	 * @param dirPath the path to the directory to add the file to
	 * @param files a list of files to add the file to
	 * @return whether or not the file was written to (returns false if file already existed)
	 * @throws IOException if the file couldnt be written to
	 * @throws IllegalFileException if the filename was invalid ("contained '/'")
	 */
	protected boolean addStringFileToDirectoryHelper(String fileContent, 
			String filename, String dirPath, Collection<String> files) throws IOException, IllegalFileException{
		if(generateFile(fileContent, filename, dirPath)){
			files.add(filename);
			return true;
		}
		return false;
	}
	
	/**
	 * @param filenames a list of filenames to be deleted
	 * @param rootDir the root directory where the files are stored
	 */
	protected void deleteFilesHelper(Collection<String> filenames, String rootDir){
		deleteFiles(filenames.stream()
				.map(s -> rootDir + "/" + s)
				.collect(Collectors.toList()));		
	}
	
	/**
	 * @param filename the filename to check for cleanliness
	 * @throws IllegalFileException throws an exception when the filename isn't clean (it 
	 * contains a '/' in it)
	 */
	private void checkCleanliness(String filename) throws IllegalFileException{
		if(!fileIsClean(filename)) throw new IllegalFileException(new IllegalStateException("Invalid filename syntax"));
	}
	
	/**
	 * @param fileContent the content to be added to the file
	 * @param filename the name of the file
	 * @param dirPath the path to the directory in which the file will be added
	 * @return whether or not the file was added
	 * @throws IOException when the file cannot be written to
	 * @throws IllegalFileException when the filename contains a '/'
	 */
	private boolean generateFile(String fileContent, String filename, String dirPath) throws IOException, IllegalFileException{
		checkCleanliness(filename);
		String totalPath = dirPath + "/" + filename;
		return addFileToDirectory(fileContent, totalPath);
	}
}
