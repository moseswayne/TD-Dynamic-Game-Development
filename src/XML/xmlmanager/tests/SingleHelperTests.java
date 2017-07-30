package XML.xmlmanager.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import XML.xmlmanager.classes.NewDirectoryHelper;
import XML.xmlmanager.exceptions.IllegalFileException;
import XML.xmlmanager.exceptions.InvalidRootDirectoryException;
import XML.xmlmanager.interfaces.filemanager.DirectoryFileManager;
import gamedata.GameData;

public class SingleHelperTests {
	
	private GameData myData;
	private DirectoryFileManager helper;
	
    public void setUp (boolean test, String rootDir, String newDir) {
       myData = new GameData();
       myData.addType("test1");
       myData.addType("test2");
       try {
    	   helper = new NewDirectoryHelper(rootDir, newDir);
    	   assertEquals(test, true);
       } catch (InvalidRootDirectoryException | IOException e) {
    	   assertEquals(test, false);
       }
    }

    // tests the initialization
	@Test
	public void setupTest() throws IOException {
		setUp(true, "src/XML/xmlmanager/tests", "blah");
		setUp(false, "src/XML/xmlmanager/tests", "blah");
		FileUtils.deleteDirectory(new File("src/XML/xmlmanager/tests/blah"));
		setUp(true, "src/XML/xmlmanager/tests", "blah");
		FileUtils.deleteDirectory(new File("src/XML/xmlmanager/tests/blah"));
	}
	
    // tests adding/writing to files
	@Test
	public void addStringFileTest() throws IOException {
		setUp(true, "src/XML/xmlmanager/tests", "foo");
		fillDirWithFiles();
		assertEquals(addStringFile("", "/"), false);
		assertEquals(addStringFile("", ""), false);
		assertEquals(addStringFile("", "foo/asd"), false);
		FileUtils.deleteDirectory(new File("src/XML/xmlmanager/tests/foo"));
	}
	
    // tests if file exist
	@Test
	public void fileExistsTest() throws IOException {
		setUp(true, "src/XML/xmlmanager/tests", "foo");
		fillDirWithFiles();
		assertEquals(helper.fileExists("testing.txt"), true);
		assertEquals(helper.fileExists("testing2.txt"), true);
		assertEquals(helper.fileExists("abc.txt"), true);
		assertEquals(helper.fileExists("abccc.txt"), false);
		assertEquals(helper.fileExists(""), false);
		assertEquals(helper.fileExists("/"), false);
		FileUtils.deleteDirectory(new File("src/XML/xmlmanager/tests/foo"));
	}
	
    // tests reading files
	@Test
	public void fileContentTest() throws IOException {
		setUp(true, "src/XML/xmlmanager/tests", "foo");
		fillDirWithFiles();
		assertEquals(getFileContent("testing.txt"), "blah");
		assertEquals(getFileContent("testing2.txt"), "123");
		assertEquals(getFileContent("abc.txt"), "");
		assertEquals(getFileContent("abccc.txt"), null);
		assertEquals(getFileContent(""), null);
		assertEquals(getFileContent("/"), null);
		FileUtils.deleteDirectory(new File("src/XML/xmlmanager/tests/foo"));
	}
	
    // tests getting new files back
	@Test
	public void getNewFilesTest() throws IOException {
		setUp(true, "src/XML/xmlmanager/tests", "foo");
		fillDirWithFiles();
		assertEquals(helper.getAllNewFilenames().size(), 3);
		assertEquals(helper.getAllNewFilenames().contains(""), false);
		assertEquals(helper.getAllNewFilenames().contains("testing.txt"), true);
		assertEquals(helper.getAllNewFilenames().contains("testing2.txt"), true);
		assertEquals(helper.getAllNewFilenames().contains("abc.txt"), true);
		FileUtils.deleteDirectory(new File("src/XML/xmlmanager/tests/foo"));
	}
	
	private void fillDirWithFiles(){
		assertEquals(addStringFile("blah", "testing.txt"), true);
		assertEquals(addStringFile("blah again", "testing.txt"), false);
		assertEquals(addStringFile("123", "testing2.txt"), true);
		assertEquals(addStringFile("", "abc.txt"), true);
	}
	
	private boolean addStringFile(String file, String content){
		try {
			 return helper.addStringFileToDirectory(file, content);
		} catch (IOException e) {
			Assert.fail("Filewrite was deemed illegal");
			return false;
		} catch (IllegalFileException ex){
			return false;
		}
	}
	
	private String getFileContent(String filename){
		try {
			return helper.getFileContent(filename);
		} catch (IOException | IllegalFileException e) {
			return null;
		}
	}
}
