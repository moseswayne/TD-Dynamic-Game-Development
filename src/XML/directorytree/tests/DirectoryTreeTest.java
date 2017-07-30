package XML.directorytree.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import XML.directorytree.classes.DirectoryNode;

public class DirectoryTreeTest {

	private DirectoryNode root;
	private String filepath;
	
    public void setUp () {
        filepath = "src/XML/xmlmanager/tests";
    	root = new DirectoryNode(filepath);
    }
	
    //directoryAddition test
	@Test
	public void AddDirectory() {
		setUp();
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/tests"), false);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/"), false);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/"), false);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/tests/foo"), true);
		assertEquals(root.getSubDirectories().size(), 1);		
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/tests/foo"), false);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/tests/foo2"), true);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/tests/foo2"), false);
		assertEquals(root.getSubDirectories().size(), 2);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/tests/foo2/blah1"), true);
		assertEquals(root.getSubDirectories().size(), 2);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/tests/foo2/blah2"), true);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/tests/foo/blah2"), true);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/tests/foo/blah2/testing"), true);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/tests/foo/blah2/test"), true);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/tests/foo/blah2/test2"), true);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/tests/foo/blah/test2"), true);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/tests/foo/blah/test2"), false);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/tests/foo/blah/test2/a/b/c/d/e/f"), true);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/tests/foo/blah/test2/a/b/c"), false);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/tests/"), false);
		System.out.println("test 1");
		root.printTree();
	}
	
    //directoryAddition test
	@Test
	public void AddFile() {
		setUp();
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/tests"), false);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/"), false);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/"), false);
		assertEquals(root.addDirectoryToTree("src/XML/xmlmanager/tests/foo"), true);
		assertEquals(root.getSubDirectories().size(), 1);		
		assertEquals(root.addFileToTree("src/XML/xmlmanager/tests/foo"), false);
		assertEquals(root.addFileToTree("src/XML/xmlmanager/tests/foo/a"), true);
		assertEquals(root.addFileToTree("src/XML/xmlmanager/tests/a"), true);
		assertEquals(root.addFileToTree("src/XML/xmlmanager/tests/"), false);
		assertEquals(root.addFileToTree("src/XML/xmlmanager/tests/a/b"), false);
		System.out.println("\n\n\n\n\ntest 2:");
		root.printTree();
	}

}
