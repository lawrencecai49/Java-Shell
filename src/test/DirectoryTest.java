// **********************************************************
// Assignment2:
// Student1: Yuanyuan Li
// UTORID user_name: liyuan33
// UT Student #: 1005736811
// Author: Yuanyuan Li
//
// Student2: Christina Ma
// UTORID user_name: machri16
// UT Student #: 1006163615
// Author: Christina Ma
//
// Student3:Man Hei Ho
// UTORID user_name: homan10
// UT Student #: 1006030162
// Author: Man Hei Ho
//
// Student4: Luoliang Cai
// UTORID user_name: cailuoli
// UT Student #: 1006156703
// Author:Luoliang Cai
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package test;

import static org.junit.Assert.*;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import exception.ItemAlreadyExistsException;
import filesystem.Directory;
import filesystem.File;

/**
 * JUnit test for Directory Class
 * 
 * @author Luoliang Cai
 */
public class DirectoryTest {
  private Directory testDir;

  /**
   * Set up a directory named testDir with name path
   */
  @Before
  public void setUp() throws ItemAlreadyExistsException {
    testDir = new Directory("testDir", "path");
  }


  @After
  public void tearDown() {
    testDir = null;
  }

  /**
   * Test if addDirectory can successfully add a directory and set the parent of the added directory
   * to itself
   */
  @Test
  public void testAddOneDirectory() throws ItemAlreadyExistsException {
    Directory newDir = new Directory("newDir", "path");
    testDir.addDirectory(newDir);
    LinkedList<Directory> expected = new LinkedList<Directory>();
    expected.add(newDir);
    assertEquals(newDir.getParent(), testDir);
    assertTrue(testDir.getFileList().isEmpty());
    assertEquals(testDir.getSubdirectoryList(), expected);
  }

  /**
   * Test if addDirectory works when used multiple times
   */
  @Test
  public void testAddMultipleDirectories() throws ItemAlreadyExistsException {
    Directory newDir1 = new Directory("newDir1", "path");
    Directory newDir2 = new Directory("newDir2", "path");
    testDir.addDirectory(newDir1);
    testDir.addDirectory(newDir2);
    LinkedList<Directory> expected = new LinkedList<Directory>();
    expected.add(newDir1);
    expected.add(newDir2);
    assertEquals(newDir1.getParent(), testDir);
    assertEquals(newDir2.getParent(), testDir);
    assertTrue(testDir.getFileList().isEmpty());
    assertEquals(testDir.getSubdirectoryList(), expected);
  }

  /**
   * Test if addFile can successfully add a file and set the parent of the added file to itself
   * 
   * @throws ItemAlreadyExistsException
   */
  @Test
  public void testAddFile() throws ItemAlreadyExistsException {
    File newFile = new File("newFile1", "path");
    testDir.addFile(newFile);
    LinkedList<File> expected = new LinkedList<File>();
    expected.add(newFile);
    assertEquals(newFile.getParent(), testDir);
    assertTrue(testDir.getSubdirectoryList().isEmpty());
    assertEquals(testDir.getFileList(), expected);
  }

  /**
   * Test if addFile works when used multiple times
   * 
   * @throws ItemAlreadyExistsException
   */
  @Test
  public void testAddMultipleFiles() throws ItemAlreadyExistsException {
    File newFile1 = new File("newFile1", "path");
    File newFile2 = new File("newFile2", "path");
    testDir.addFile(newFile1);
    testDir.addFile(newFile2);
    LinkedList<File> expected = new LinkedList<File>();
    expected.add(newFile1);
    expected.add(newFile2);
    assertEquals(newFile1.getParent(), testDir);
    assertEquals(newFile2.getParent(), testDir);
    assertTrue(testDir.getSubdirectoryList().isEmpty());
    assertEquals(testDir.getFileList(), expected);
  }

  /**
   * Test if appropriate exception is thrown when adding a directory with a name that already
   * belongs to another subdirectory in the directory
   * 
   * @throws ItemAlreadyExistsException
   */
  @Test(expected = ItemAlreadyExistsException.class)
  public void testAddDirectoryDirectoryWithSameNameAlreadyInDirectory()
      throws ItemAlreadyExistsException {
    Directory newDir1 = new Directory("dir1", "testDir/dir11");
    testDir.addDirectory(newDir1);
    Directory newDir2 = new Directory("dir1", "testDir/dir11");
    testDir.addDirectory(newDir2);
  }

  /**
   * Test if appropriate exception is thrown when adding a file with a name that already belongs to
   * another subdirectory in the directory
   * 
   * @throws ItemAlreadyExistsException
   */
  @Test(expected = ItemAlreadyExistsException.class)
  public void testAddDirectoryFileWithSameNameAlreadyInDirectory()
      throws ItemAlreadyExistsException {
    Directory newDirectory = new Directory("new", "/testDir/new");
    File newFile = new File("new", "/testDir/new");
    testDir.addFile(newFile);
    testDir.addDirectory(newDirectory);
  }

  /**
   * Test if appropriate exception is thrown when adding a file with a name that already belongs to
   * another file in the directory
   * 
   * @throws ItemAlreadyExistsException
   */
  @Test(expected = ItemAlreadyExistsException.class)
  public void testAddFileFileWithSameNameAlreadyInDirectory() throws ItemAlreadyExistsException {
    File newFile1 = new File("newFile1", "/testDir/newFile");
    File newFile2 = new File("newFile1", "/testDir/newFile");
    testDir.addFile(newFile1);
    testDir.addFile(newFile2);
  }

  /**
   * Test if appropriate exception is thrown when adding a file with a name that already belongs to
   * a subdirectory in the directory
   * 
   * @throws ItemAlreadyExistsException
   */
  @Test(expected = ItemAlreadyExistsException.class)
  public void testAddFileDirectoryWithSameNameALreadyInDirectory()
      throws ItemAlreadyExistsException {
    Directory newDir = new Directory("new", "/testDir/new");
    File newFile = new File("new", "/testDir/new2");
    testDir.addDirectory(newDir);
    testDir.addFile(newFile);
  }

  /**
   * Test getFile
   */
  @Test
  public void testGetFile() throws ItemAlreadyExistsException {
    Directory dir1 = new Directory("dir1", "/testDir/new");
    File file1 = new File("file1", "/testDir/new2");
    testDir.addFile(file1);
    testDir.addDirectory(dir1);
    File actual = testDir.getFile("file1");
    assertEquals(actual, file1);
  }

  /**
   * Test if getFile returns null when using a name that does not belong to a file or directory
   */
  @Test
  public void testGetFileInvalidName() throws ItemAlreadyExistsException {
    Directory dir1 = new Directory("dir1", "/testDir/new");
    File file1 = new File("file1", "/testDir/new2");
    testDir.addFile(file1);
    testDir.addDirectory(dir1);
    File actual = testDir.getFile("file2");
    assertEquals(actual, null);
  }

  /**
   * Test if getFile returns null when using a name that belongs to a directory instead of a file
   */
  @Test
  public void testGetFileNameOfDirectory() throws ItemAlreadyExistsException {
    Directory dir1 = new Directory("dir1", "/testDir/new");
    File file1 = new File("file1", "/testDir/new2");
    testDir.addFile(file1);
    testDir.addDirectory(dir1);
    File actual = testDir.getFile("dir1");
    assertEquals(actual, null);
  }

  /**
   * Test getDirectory
   */
  @Test
  public void testGetDirectory() throws ItemAlreadyExistsException {
    Directory dir1 = new Directory("dir1", "/testDir/new");
    File file1 = new File("file1", "/testDir/new2");
    testDir.addFile(file1);
    testDir.addDirectory(dir1);
    Directory actual = testDir.getDirectory("dir1");
    assertEquals(actual, dir1);
  }

  /**
   * Test if getDirectory returns null is returned when using a name that does not belong to any of
   * the files or subdirectory
   */
  @Test
  public void testGetDirectoryNonExistantItem() throws ItemAlreadyExistsException {
    Directory dir1 = new Directory("dir1", "/testDir/new");
    File file1 = new File("file1", "/testDir/new2");
    testDir.addFile(file1);
    testDir.addDirectory(dir1);
    Directory actual = testDir.getDirectory("file2");
    assertEquals(actual, null);
  }

  /**
   * Test if getDirectory returns null is returned when using a name that belongs to a file instead
   * of a directory
   */
  @Test
  public void testGetDirectoryNameOfFile() throws ItemAlreadyExistsException {
    Directory dir1 = new Directory("dir1", "/testDir/new");
    File file1 = new File("file1", "/testDir/new2");
    testDir.addFile(file1);
    testDir.addDirectory(dir1);
    Directory actual = testDir.getDirectory("file1");
    assertEquals(actual, null);
  }

  /**
   * Test if getDirectory returns the the directory if given . as an argument
   */
  @Test
  public void testGetDirectoryOneDot() throws ItemAlreadyExistsException {
    Directory dir1 = new Directory("dir1", "/testDir/new");
    File file1 = new File("file1", "/testDir/new2");
    testDir.addFile(file1);
    testDir.addDirectory(dir1);
    Directory actual = testDir.getDirectory(".");
    Directory expected = testDir;
    assertEquals(actual, expected);
  }

  /**
   * Test if getDirectory returns the parent directory if given .. as an argument
   */
  @Test
  public void testGetDirectoryTwoDots() throws ItemAlreadyExistsException {
    Directory dir1 = new Directory("dir1", "/testDir/new");
    File file1 = new File("file1", "/testDir/new2");
    testDir.addFile(file1);
    testDir.addDirectory(dir1);
    Directory actual = testDir.getDirectory("..");
    Directory expected = testDir.getParent();
    assertEquals(actual, expected);
  }

  /**
   * Test if remove can be successfully remove directory
   */
  @Test
  public void testRemoveDirectory() throws ItemAlreadyExistsException {
    Directory dir1 = new Directory("dir1", "/testDir/new");
    File file1 = new File("file1", "/testDir/new2");
    testDir.addFile(file1);
    testDir.addDirectory(dir1);
    testDir.remove("dir1");
    LinkedList<File> expected = new LinkedList<File>();
    expected.add(file1);
    assertTrue(testDir.getSubdirectoryList().isEmpty());
    assertEquals(expected, testDir.getFileList());
  }

  /**
   * Test if remove can be successfully remove file
   */
  @Test
  public void testRemoveFile() throws ItemAlreadyExistsException {
    Directory dir1 = new Directory("dir1", "/testDir/new");
    File file1 = new File("file1", "/testDir/new2");
    testDir.addFile(file1);
    testDir.addDirectory(dir1);
    testDir.remove("file1");
    LinkedList<Directory> expected = new LinkedList<Directory>();
    expected.add(dir1);
    assertTrue(testDir.getFileList().isEmpty());
    assertEquals(expected, testDir.getSubdirectoryList());
  }

  /**
   * Test if method does nothing if there is not a file or subdirectory with the name in the
   * directory
   */
  @Test
  public void testRemoveNoItemWithName() throws ItemAlreadyExistsException {
    Directory dir1 = new Directory("dir1", "/testDir/new");
    File file1 = new File("file1", "/testDir/new2");
    testDir.addFile(file1);
    testDir.addDirectory(dir1);
    testDir.remove("file2");
    LinkedList<Directory> expectedDirList = new LinkedList<Directory>();
    LinkedList<File> expectedFileList = new LinkedList<File>();
    expectedDirList.add(dir1);
    expectedFileList.add(file1);
    assertEquals(expectedFileList, testDir.getFileList());
    assertEquals(expectedDirList, testDir.getSubdirectoryList());
  }

  /**
   * Test if destroy all successfully destroys all directories and subdirectories recursively
   */
  @Test
  public void testDestroyAll() throws ItemAlreadyExistsException {
    Directory dir1 = new Directory("dir1", "/testDir/new");
    File file1 = new File("file1", "/testDir/new2");
    Directory dir2 = new Directory("dir2", "/testDir/new");
    File file2 = new File("file2", "/testDir/new2");
    testDir.addFile(file1);
    testDir.addDirectory(dir1);
    dir1.addDirectory(dir2);
    dir2.addFile(file2);
    testDir.destroyAll();
    assertTrue(dir1.getSubdirectoryList().isEmpty());
    assertTrue(dir1.getFileList().isEmpty());
    assertTrue(dir2.getSubdirectoryList().isEmpty());
    assertTrue(dir2.getFileList().isEmpty());
    assertTrue(testDir.getSubdirectoryList().isEmpty());
    assertTrue(testDir.getFileList().isEmpty());
  }

  /**
   * Test if hasChild returns true if the directory is a child
   */
  @Test
  public void testHasChild() throws ItemAlreadyExistsException {
    Directory dir1 = new Directory("dir1", "/testDir/new");
    File file1 = new File("file1", "/testDir/new2");
    Directory dir2 = new Directory("dir2", "/testDir/new");
    testDir.addFile(file1);
    testDir.addDirectory(dir1);
    dir1.addDirectory(dir2);
    assertTrue(testDir.hasChild(dir2));
  }

  /**
   * Test if hasChild returns false if the directory is not a child
   */
  @Test
  public void testHasChildNotChild() throws ItemAlreadyExistsException {
    Directory dir1 = new Directory("dir1", "/testDir/new");
    File file1 = new File("file1", "/testDir/new2");
    Directory dir2 = new Directory("dir2", "/testDir/new");
    testDir.addFile(file1);
    testDir.addDirectory(dir1);
    dir1.addDirectory(dir2);
    assertTrue(!dir1.hasChild(testDir));
  }

  /**
   * Test if hasChild returns false if the argument used is the directory itself
   */
  @Test
  public void testHasChildInputItself() throws ItemAlreadyExistsException {
    assertTrue(!testDir.hasChild(testDir));
  }
}
