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
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import exception.InvalidNameException;
import exception.InvalidPathException;
import exception.ItemAlreadyExistsException;
import filesystem.JFileSystem;
import filesystem.Directory;
import filesystem.File;

/**
 * JUnit test for JFileSystem class (Directory, File and FileSystemContent test already fully
 * tested)
 * 
 * @author Luoliang Cai
 */
public class JFileSystemTest {
  private JFileSystem fs;
  private Directory dir1;
  private Directory dir2;
  private Directory dir3;
  private File file1;
  private File file2;

  /**
   * Initialize a new JFileSystem and set up a new file system represented by the tree below using
   * using pretested methods from Directory class / dir1 dir2 file1 dir3 file2
   * 
   * @throws ItemAlreadyExistsException
   */
  @Before
  public void setUp() throws ItemAlreadyExistsException {
    fs = JFileSystem.buildFileSystem();
    dir1 = new Directory("dir1", "/dir1");
    dir2 = new Directory("dir2", "/dir1/dir2");
    dir3 = new Directory("dir3", "/dir3");
    file1 = new File("file1", "/file1");
    file2 = new File("file2", "/file2");
    fs.getRoot().addFile(file2);
    fs.getRoot().addDirectory(dir1);
    fs.getRoot().addDirectory(dir3);
    dir1.addDirectory(dir2);
    dir2.addFile(file1);
  }

  @After
  public void tearDown() throws Exception {
    dir1 = null;
    dir2 = null;
    dir3 = null;
    file1 = null;
    file2 = null;
    Field field = (fs.getClass()).getDeclaredField("currentSystem");
    field.setAccessible(true);
    field.set(null, null); // setting the ref parameter to null
  }

  /**
   * Test if the root folder is properly constructed
   */
  @Test
  public void testConstructor() {
    assertEquals(fs.getRoot().getName(), "/");
    assertEquals(fs.getRoot().getPath(), "/");
    assertEquals(fs.getCurrentDirectory(), fs.getRoot());
    assertEquals(fs.getRoot().getParent(), fs.getRoot());
  }

  /**
   * Test if indDirectory throws exception if the path does not lead to anything
   */
  @Test(expected = InvalidPathException.class)
  public void testFindDirectoryInvalidPath() throws InvalidPathException {
    fs.findDirectory("/dir1/dir3");
  }

  /**
   * Test if findDirectory throws exception if the path leads to a file
   */
  @Test(expected = InvalidPathException.class)
  public void testFindDirectoryFilePath() throws InvalidPathException {
    fs.findDirectory("/dir1/dir2/file1");
  }

  /**
   * Test if findDirectory can retrieve a directory given an absolute path
   */
  @Test
  public void testFindDirectoryAbsolutePath() throws InvalidPathException {
    Directory actual = fs.findDirectory("/dir1/dir2");
    Directory expected = dir2;
    assertEquals(expected, actual);
  }

  /**
   * Test if findDirectory can retrieve a directory given a relative path
   */
  @Test
  public void testFindDirectoryRelativePath() throws InvalidPathException {
    fs.setCurrentDirectory(dir1);
    Directory actual = fs.findDirectory("dir2");
    Directory expected = dir2;
    assertEquals(expected, actual);
  }

  /**
   * Test if findDirectory can retrieve a directory given a path that ends in a slash
   */
  @Test
  public void testFindDirectoryPathEndingInSlash() throws InvalidPathException {
    Directory actual = fs.findDirectory("/dir1/dir2/../.");
    Directory expected = dir1;
    assertEquals(expected, actual);
  }

  /**
   * Test if findDirectory can retrieve a directory given a path that contains shortcuts .. and .
   */
  @Test
  public void testFindDirectoryPathWithShortCuts() throws InvalidPathException {
    Directory actual = fs.findDirectory("/dir1/dir2/");
    Directory expected = dir2;
    assertEquals(expected, actual);
  }

  /**
   * Test if findFile throws exception if the path does not lead to anything
   */
  @Test(expected = InvalidPathException.class)
  public void testFindFileInvalidPath() throws InvalidPathException {
    fs.findFile("/file3");
  }

  /**
   * Test if findFile throws exception if the path leads to a directory
   */
  @Test(expected = InvalidPathException.class)
  public void testFindFileDirectoryPath() throws InvalidPathException {
    fs.findFile("/dir1/dir2");
  }


  /**
   * Test if findFile return null if given a path of a file but it ends in a slash
   */
  @Test(expected = InvalidPathException.class)
  public void testFindFileEndsInSlash() throws InvalidPathException {
    fs.findFile("/dir1/dir2/file1/");
  }

  /**
   * Test if findFile can retrieve a file given an absolute path
   */
  @Test
  public void testFindFileAbsolutePath() throws InvalidPathException {
    File actual = fs.findFile("/dir1/dir2/file1");
    File expected = file1;
    assertEquals(expected, actual);
  }

  /**
   * Test if findFile can retrieve a file given a relative path
   */
  @Test
  public void testFindFileRelativePath() throws InvalidPathException {
    fs.setCurrentDirectory(dir1);
    File actual = fs.findFile("dir2/file1");
    File expected = file1;
    assertEquals(expected, actual);
  }

  /**
   * Test if findFile can retrieve a file given a path that contains shortcuts .. and .
   */
  @Test
  public void testFindFilePathWithShortCuts() throws InvalidPathException {
    File actual = fs.findFile("/dir3/../dir1/./dir2/file1");
    File expected = file1;
    assertEquals(expected, actual);
  }


  /**
   * Test makeFile successfully creates a file in correct path at root
   */
  @Test
  public void testMakeFileFromRoot() throws Exception {
    fs.makeFile("dir1/dir2/newFile", "text");
    File actual = fs.findFile("/dir1/dir2/newFile");
    assertEquals(actual.getParent(), dir2);
    assertEquals(actual.getText(), "text");
    assertEquals(actual.getName(), "newFile");
  }


  /**
   * Test if makeFile successfully created a file using absolute path while not at root
   */
  @Test
  public void testMakeFileAbsolutePathNotAtRoot() throws Exception {
    fs.setCurrentDirectory(dir2);
    fs.makeFile("/dir3/newFile", "text");
    File actual = fs.findFile("/dir3/newFile");
    assertEquals(actual.getParent(), dir3);
    assertEquals(actual.getText(), "text");
    assertEquals(actual.getName(), "newFile");
  }

  /**
   * Test if makeFile successfully created a file using a valid path containing shortcuts . and ..
   */
  @Test
  public void testMakeFilePathWithShortCuts() throws Exception {
    fs.makeFile("/dir3/../dir1/./newFile", "text");
    File actual = fs.findFile("/dir1/newFile");
    assertEquals(actual.getParent(), dir1);
    assertEquals(actual.getText(), "text");
    assertEquals(actual.getName(), "newFile");
  }

  /**
   * Test if makeFile successfully created a file using relative path while not at root
   */
  @Test
  public void testMakeFileRelativePathNotAtRoot() throws Exception {
    fs.setCurrentDirectory(dir1);
    fs.makeFile("dir2/new", "text");
    File actual = fs.findFile("/dir1/dir2/new");
    assertEquals(actual.getParent(), dir2);
    assertEquals(actual.getText(), "text");
    assertEquals(actual.getName(), "new");
  }

  /**
   * Test if makeFile prevents a file with an invalid name from being created
   */
  @Test(expected = InvalidNameException.class)
  public void testMakeFileInvalidName()
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    fs.makeFile("newFile.txt", "Some shit");
  }

  /**
   * Test if makeFile prevents an file from being created at a path where a file already exists
   */
  @Test(expected = ItemAlreadyExistsException.class)
  public void testMakeFileExisting()
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    fs.makeFile("/file2", "Some text");
  }

  /**
   * Test if makeFile prevents a file with an invalid path from being created
   */
  @Test(expected = InvalidPathException.class)
  public void testMakeFileInvalidPath()
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    fs.makeFile("/file2/newFile", "Some shit");
  }

  /**
   * Test if makeFile throws correct exception when given path that ends in a slash even if the path
   * with the slash remvoed is a valid path
   */
  @Test(expected = InvalidNameException.class)
  public void testMakeFileEndsInSlash()
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    fs.makeFile("/dir3/newFile/", "Some shit");
  }


  /**
   * Test makeDirectory successfully creates a directory in correct path from root given absolute
   * path
   */
  @Test
  public void testMakeDirectoryFromRoot() throws Exception {
    fs.makeDirectory("dir1/newDir");
    Directory actual = fs.findDirectory("/dir1/newDir");
    assertEquals(actual.getParent(), dir1);
    assertEquals(actual.getName(), "newDir");
  }

  /**
   * Test makeDirectory successfully creates a directory in correct path from root given absolute
   * path
   */
  @Test
  public void testMakeDirectoryPathWithShortcuts() throws Exception {
    fs.makeDirectory("/dir1/../dir3/./newDir");
    Directory actual = fs.findDirectory("/dir3/newDir");
    assertEquals(actual.getParent(), dir3);
    assertEquals(actual.getName(), "newDir");
  }

  /**
   * Test if makeDirectory successfully created a directory using absolute path while not at root
   */
  @Test
  public void testMakeDirectoryAbsolutePathNotAtRoot() throws Exception {
    fs.setCurrentDirectory(dir2);
    fs.makeDirectory("/newDir");
    Directory actual = fs.findDirectory("/newDir");
    assertEquals(actual.getParent(), fs.getRoot());
    assertEquals(actual.getName(), "newDir");
  }

  /**
   * Test if makeDirectory successfully created a directory using relative path while not at root
   */
  @Test
  public void testMakeDirectoryRelativePathNotAtRoot() throws Exception {
    fs.setCurrentDirectory(dir1);
    fs.makeDirectory("dir2/new");
    Directory actual = fs.findDirectory("/dir1/dir2/new");
    assertEquals(actual.getParent(), dir2);
    assertEquals(actual.getName(), "new");
  }

  /**
   * Test if makeDirectory successfully creates directory even when path ends in slash
   */
  @Test
  public void testMakeDirectoryPathEndsInSlash() throws Exception {
    fs.setCurrentDirectory(dir1);
    fs.makeDirectory("dir2/new/");
    Directory actual = fs.findDirectory("/dir1/dir2/new");
    assertEquals(actual.getParent(), dir2);
    assertEquals(actual.getName(), "new");
  }

  /**
   * Test if makeDirectory prevents a directory with an invalid name from being created
   */
  @Test(expected = InvalidNameException.class)
  public void testMakeDirectoryInvalidName()
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    fs.makeDirectory("new!Directory");
  }

  /**
   * Test if makeDirectory prevents an directory from being created at a path where a file already
   * exists
   */
  @Test(expected = ItemAlreadyExistsException.class)
  public void testMakeDirectoryeExisting()
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    fs.makeDirectory("/dir1");
  }

  /**
   * Test if makeDirectory prevents a directory with an invalid path from being created
   */
  @Test(expected = InvalidPathException.class)
  public void testMakeDirectoryInvalidPath()
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    fs.makeDirectory("/dir3/dir2/new");
  }

  /**
   * Test if remove directory successfully deletes a directory recursively
   */
  @Test(expected = InvalidPathException.class)
  public void testRemoveDirectory()
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    fs.removeDirectory("/dir1");
    assertTrue(dir2.getFileList().isEmpty());
    assertTrue(dir1.getSubdirectoryList().isEmpty());
    assertEquals(fs.findDirectory("/dir1"), null);
  }

  /**
   * Test if remove directory throws exception when given a path of a file
   */
  @Test(expected = InvalidPathException.class)
  public void testRemoveDirectoryFilePath()
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    fs.removeDirectory("/dir1/dir2/file1");
  }

  /**
   * Test if remove directory throws exception when given a path that does not lead to anything
   */
  @Test(expected = InvalidPathException.class)
  public void testRemoveDirectoryInvalidPath()
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    fs.removeDirectory("/dir1/dir3");
  }

}
