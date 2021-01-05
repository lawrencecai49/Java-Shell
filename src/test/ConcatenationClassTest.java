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
import org.junit.Before;
import org.junit.Test;
import command.ConcatenationClass;
import filesystem.Directory;
import filesystem.File;
import output.Output;

/**
 * This is a JUnit test for ConcatenationClass class.
 * 
 * @author Christina Ma
 */
public class ConcatenationClassTest {
  private ConcatenationClass cat;
  private Output output;
  private MockValidationGate valiGate;
  File file1;
  File file2;
  Directory newDir1;
  Directory newDir2;

  /**
   * This method initializes valiGate and creates two mock files and two mock directories.
   */
  @Before
  public void setUp() {
    this.valiGate = new MockValidationGate();
    this.file1 = new File("ValidFileNameOne", "FilePathOne", "Contents in file at FilePathOne");
    this.file2 = new File("ValidFileNameTwo", "FilePathTwo", "Contents in file at FilePathTwo");
    newDir1 = createMockDirectoryOne();
    newDir2 = createMockDirectoryTwo();
  }

  /**
   * This method creates a mock directory that contains one file.
   * 
   * @return Returns a MockDirectory that consist of one file
   */
  private MockDirectory createMockDirectoryOne() {
    // Create the sub files
    LinkedList<File> files = new LinkedList<File>();
    files.add(file2);

    MockDirectory newDir1 = new MockDirectory("ValidDirNameOne", "DirectoryPathOne",
        new LinkedList<Directory>(), files);
    return newDir1;
  }

  /**
   * This method creates a mock directory that contains one sub-directory.
   * 
   * @return Returns a MockDirectory that consist of one sub-directory
   */
  private MockDirectory createMockDirectoryTwo() {
    // Creates the sub-directory
    MockDirectory newDir1 = createMockDirectoryOne();
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.addLast(newDir1);

    MockDirectory newDir2 =
        new MockDirectory("ValidDirNameTwo", "DirectoryPathTwo", subDirs, new LinkedList<File>());
    return newDir2;
  }

  /**
   * This method creates a linked list of one file.
   * 
   * @return Returns a LinkedList consisting of one file
   */
  private LinkedList<File> createSubFile() {
    LinkedList<File> files = new LinkedList<File>();
    files.add(file1);
    return files;
  }

  /**
   * This method returns a MockFileSystemVerTwo object with the root containing the desired
   * sub-directories and sub files
   * 
   * @param subDirs The desired sub-directories the root contains.
   * @param subFiles The desired sub files the root contains
   * @return Returns a MockFileSystemVerTwo object with the root containing the desired contents.
   */
  private MockFileSystemVerTwo createRootDir(LinkedList<Directory> subDirs,
      LinkedList<File> subFiles) {
    MockDirectory mockDir = new MockDirectory("ValidDirectoryName", "ValidPath", subDirs, subFiles);
    return new MockFileSystemVerTwo(mockDir, newDir1, newDir2, file1, file2);
  }

  /**
   * This test when user inputs no additional arguments
   */
  @Test
  public void testRunCommandWithNoArguments() {
    MockFileSystemVerTwo mockfsVerTwo =
        createRootDir(new LinkedList<Directory>(), new LinkedList<File>());

    this.cat = new ConcatenationClass(valiGate, mockfsVerTwo);
    this.output = cat.runCommand(new MockInputProcessor("search", null, null, null));
    assertEquals(null, output.getOutputMessage());
    assertEquals("cat takes in at least one argument", output.getException().getMessage());
  }

  /**
   * This test when user inputs one pathname that refers to an existing file.
   */
  @Test
  public void testRunCommandWithOnePathToExistingFile() {
    // Creating mock sub-directories and sub files
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);

    MockFileSystemVerTwo mockfsVerTwo = createRootDir(subDirs, new LinkedList<File>());

    String[] arg = {"FilePathTwo"};
    this.cat = new ConcatenationClass(valiGate, mockfsVerTwo);
    this.output = cat.runCommand(new MockInputProcessor("search", arg, null, null));
    assertEquals("Contents in file at FilePathTwo", output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * This test when user inputs one pathname that refers to a non existing file.
   */
  @Test
  public void testRunCommandWithOnePathToNonExistingFile() {
    // Creating mock sub-directories and sub files
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);

    MockFileSystemVerTwo mockfsVerTwo = createRootDir(subDirs, new LinkedList<File>());

    String[] arg = {"Nonexisting"};
    this.cat = new ConcatenationClass(valiGate, mockfsVerTwo);
    this.output = cat.runCommand(new MockInputProcessor("search", arg, null, null));
    assertEquals("", output.getOutputMessage());
    assertEquals("There is no file at Nonexisting", output.getException().getMessage());
  }

  /**
   * This test when user inputs two pathnames that refers to existing files.
   */
  @Test
  public void testRunCommandWithTwoPathToExistingFile() {
    // Creating mock sub-directories and sub files
    LinkedList<File> subFiles = createSubFile();
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);

    MockFileSystemVerTwo mockfsVerTwo = createRootDir(subDirs, subFiles);

    String[] arg = {"FilePathTwo", "FilePathOne"};
    this.cat = new ConcatenationClass(valiGate, mockfsVerTwo);
    this.output = cat.runCommand(new MockInputProcessor("search", arg, null, null));
    assertEquals(
        "Contents in file at FilePathTwo\n" + "\n" + "\n" + "Contents in file at FilePathOne",
        output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * This test when user inputs a pathname containing invalid characters.
   */
  @Test
  public void testRunCommandWithInvalidPathname() {
    // Created a MockFileSystemVerTwo object only has the root and no sub=directories or files
    MockFileSystemVerTwo mockfsVerTwo =
        createRootDir(new LinkedList<Directory>(), new LinkedList<File>());

    String[] arg = {"InvalidPath"};
    this.cat = new ConcatenationClass(valiGate, mockfsVerTwo);
    this.output = cat.runCommand(new MockInputProcessor("search", arg, null, null));
    assertEquals("", output.getOutputMessage());
    assertEquals("InvalidPath is an invalid pathname.", output.getException().getMessage());
  }

  /**
   * This test when user inputs three pathnames such that the first and third pathname refers to an
   * existing file, while the second pathname does not.
   */
  @Test
  public void testRunCommandWithNonExistFileInBetween() {
    // Creating mock sub-directories and sub files
    LinkedList<File> subFiles = createSubFile();
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);

    MockFileSystemVerTwo mockfsVerTwo = createRootDir(subDirs, subFiles);

    String[] arg = {"FilePathTwo", "Nonexisting", "FilePathOne"};
    this.cat = new ConcatenationClass(valiGate, mockfsVerTwo);
    this.output = cat.runCommand(new MockInputProcessor("search", arg, null, null));
    assertEquals("Contents in file at FilePathTwo", output.getOutputMessage());
    assertEquals("There is no file at Nonexisting", output.getException().getMessage());
  }

  /**
   * This test when user inputs three pathnames such that the first and third are valid pathnames,
   * while the second pathname is invalid.
   */
  @Test
  public void testRunCommandWithInvalidPathnameInBetween() {
    // Creating mock sub-directories and sub files
    LinkedList<File> subFiles = createSubFile();
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);

    MockFileSystemVerTwo mockfsVerTwo = createRootDir(subDirs, subFiles);

    String[] arg = {"FilePathTwo", "InvalidPath", "FilePathOne"};
    this.cat = new ConcatenationClass(valiGate, mockfsVerTwo);
    this.output = cat.runCommand(new MockInputProcessor("search", arg, null, null));
    assertEquals("Contents in file at FilePathTwo", output.getOutputMessage());
    assertEquals("InvalidPath is an invalid pathname.", output.getException().getMessage());
  }
}
