package XML.xmlmanager.classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.apache.commons.io.FileUtils;

import XML.xmlmanager.exceptions.IllegalFileException;
import XML.xmlmanager.interfaces.filemanager.FileHelper;
/**
 * 
 * @author Gideon
 * 
 * This class is a concrete implementation of the {link @FileHelper} interface
 * It implements all of the basic functionality you 
 * would want when dealing with files
 */
public class ConcreteFileHelper implements FileHelper{

	public ConcreteFileHelper(){
		
	}
	
	/**
	 * Overwrites the File from the filepath with the String passed 
	 * 
	 * See {@link FileHelper}
	 */
	@Override
	public boolean overwriteStringFile(String filepath, String fileContent) throws IllegalFileException, IOException{
		File file = new File(filepath);
		checkForValidity(file, f -> f.isDirectory(), "filepath passed mapped to a directory");
		boolean test = checkAndConsume(file, f -> f.exists(), f -> f.delete());
		writeFile(file, fileContent);
		return test;
	}

	/**
	 * @param file the file to write the content to
	 * @param fileContent the content to write to the file
	 * @throws IOException when the file couldn't be written to
	 */
	private void writeFile(File file, String fileContent) throws IOException{
		FileWriter fileWriter = new FileWriter(file, false);
		fileWriter.write(fileContent);
		fileWriter.close();
	}

	/**
	 * Deletes the file passed if file is a file
	 * returns whether or not the file was deleted
	 */
	@Override
	public boolean deleteFile(File file) {
		return checkAndConsume(file, f -> f.isFile(), f-> f.delete());
	}

	/**
	 * returns whether or not the directory was deleted
	 * 
	 * See {@link FileHelper}
	 */
	@Override
	public boolean deleteDir(File directory) throws IOException{
		boolean test = directory.isDirectory();
		if(test) deleteDirectory(directory);
		return test;
	}
	
	/**
	 * @param file the file to delete
	 * @throws IOException when the file could not be deleted
	 */
	private void deleteDirectory(File file) throws IOException{
		FileUtils.deleteDirectory(file);
	}

	/**
	 * reads the String content out of a file
	 * 
	 * See {@link FileHelper}
	 */
	@Override
	public String readFile(File file) throws IOException {
		return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
	}
	
	/**
	 * @param f the input to check for error
	 * @param pred the predicate which indicates whether or not an Exception should be thrown
	 * @param error the Error message to be put in the exception
	 * @throws IllegalFileException when the predicate returns true based on the input
	 */
	private <F> void checkForValidity(F f, Predicate<F> pred, String error) throws IllegalFileException{
		if(pred.test(f)){
			throw new IllegalFileException(new IllegalStateException(error));
		}
	}
	
	/**
	 * @param f the input
	 * @param pred the predicate to test whether the input should be consumed
	 * @param consumer the consumer to be applied when the predicate returns true
	 * @return whether or not the consumer was applied
	 */
	private <F> boolean checkAndConsume(F f, Predicate<F> pred, Consumer<F> consumer) {
		boolean test = pred.test(f);
		if(test){
			consumer.accept(f);
		}
		return test;
	}

	/**
	 * Moves the file from the start location to the end location 
	 * 
	 * See {@link FileHelper}
	 */
	@Override
	public void moveFile(String startDirPath, String endDirPath, String filename) throws IllegalFileException, IOException{
		String oldDirPath = startDirPath + "/" + filename;
		String newDirPath = endDirPath + "/" + filename;
		checkForValidity(new File(oldDirPath), f -> !f.exists() || !f.isFile(), "Invalid file to read the file from");
		Files.copy(Paths.get(oldDirPath), Paths.get(newDirPath));
	}
}
