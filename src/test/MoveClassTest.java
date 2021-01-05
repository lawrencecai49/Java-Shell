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
import command.MoveClass;
import driver.InputProcessor;
import driver.ValidationGate;
import filesystem.Directory;
import filesystem.File;
import filesystem.FileSystem;
import output.Output;

/**
 * This is a JUnit test class for MoveClass. Note that MoveClass extends from the CopyClass, and
 * since the main functionality is already tested in CopyClassTest, MoveClassTest will test if
 * oldPath is properly removed from the file system
 * 
 * @author Man Hei Ho
 */
public class MoveClassTest {

  private FileSystem fs;
  private ValidationGate vg;
  private MoveClass mv;

  @Before
  public void setup() {
    Directory root = new MockDirectory("/", "/", null, null);
    this.fs = new MockFileSystemVerTwo(root, null, null, null, null);
    this.vg = new MockValidationGate();
    this.mv = new MoveClass(this.fs, this.vg);
  }

  /**
   * Test MoveClass runCommand() with user entering no argument
   */
  @Test
  public void testMoveRunCommandWithNoArgument() {
    InputProcessor userInput = new MockInputProcessor("mv", null, null, null);
    Output output = this.mv.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("should have exception.");
    String expectedError = "mv takes two pathnames as argument.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test MoveClass runCommand() with user entering one argument
   */
  @Test
  public void testCMoveRunCommandWithOneArgument() {
    String[] args = {"ValidPath"};
    InputProcessor userInput = new MockInputProcessor("mv", args, null, null);
    Output output = this.mv.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("should have exception.");
    String expectedError = "mv takes two pathnames as argument.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test MoveClass runCommand() with user entering more than one argument
   */
  @Test
  public void testMoveRunCommandWithMoreThanTwoArgument() {
    String[] args = {"ValidPath", "ValidPath", "ValidPath"};
    InputProcessor userInput = new MockInputProcessor("mv", args, null, null);
    Output output = this.mv.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("should have exception.");
    String expectedError = "mv takes two pathnames as argument.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test MoveClass runCommand() with user entering two invalid argument
   */
  @Test
  public void testMoveRunCommandWithTwoInvalidArgument() {
    String[] args = {"InvalidPathOne", "InvalidPathTwo"};
    InputProcessor userInput = new MockInputProcessor("mv", args, null, null);
    Output output = this.mv.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("should have exception.");
    String expectedError = "InvalidPathOne is not a valid pathname.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test MoveClass runCommand() with user entering two pathname with the first pathname a valid
   * pathname and the second pathname an invalid pathname
   */
  @Test
  public void testMoveRunCommandWithOneValidAndOneInvalidArgument() {
    String[] args = {"ValidPathOne", "InvalidPathTwo"};
    InputProcessor userInput = new MockInputProcessor("mv", args, null, null);
    Output output = this.mv.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("should have exception.");
    String expectedError = "InvalidPathTwo is not a valid pathname.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test MoveClass runCommand() with oldDir a non-existing pathname
   */
  @Test
  public void testMoveRunCommandWithNonExistingOldPath() {
    String[] args = {"NonexistingValidPathname", "ValidPathTwo"};
    InputProcessor userInput = new MockInputProcessor("mv", args, null, null);
    Output output = this.mv.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("should have exception.");
    String expectedError =
        "NonexistingValidPathname does not refer to an existing item in the file system.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test MoveClass runCommand() with user entering two valid existing file pathname
   */
  @Test
  public void testMoveRunCommandWithTwoValidFile() {
    String[] args = {"FilePath", "FilePath"};
    InputProcessor userInput = new MockInputProcessor("mv", args, null, null);
    Output output = this.mv.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());
    if (output.getException() == null)
      fail("should have exception.");
    String expectedError = "Cannot copy/move file to the same file.";
    assertEquals(expectedError, output.getException().getMessage());
    assertTrue(((MockFileSystemVerTwo) this.fs).files.contains("FilePath"));
  }

  /**
   * Test MoveClass runCommand() to move a file
   */
  @Test
  public void testMoveRunCommandToMoveAFile() {
    LinkedList<Directory> dirs = new LinkedList<Directory>();
    LinkedList<File> files = new LinkedList<File>();
    File file = new File("FilePathOne", "FilePathOne");
    files.add(file);
    Directory root = new MockDirectory("/", "/", dirs, files);
    file.setParent(root);
    this.fs = new MockFileSystemVerTwo(root, null, null, file, null);
    this.mv = new MoveClass(this.fs, this.vg);

    String[] args = {"FilePathOne", "FilePath"};
    InputProcessor userInput = new MockInputProcessor("mv", args, null, null);
    Output output = this.mv.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());
    assertEquals(null, output.getException());
    assertTrue(!((MockDirectory) root).mockFiles.contains(file));
  }

  /**
   * Test MoveClass runCommand() to move a directory
   */
  @Test
  public void testMoveRunCommandToMoveADirectory() {
    LinkedList<Directory> dirs = new LinkedList<Directory>();
    LinkedList<File> files = new LinkedList<File>();
    Directory dir1 = new MockDirectory("DirectoryPathOne", "DirectoryPathOne", dirs, files);

    LinkedList<Directory> dirs2 = new LinkedList<Directory>();
    LinkedList<File> files2 = new LinkedList<File>();
    Directory dir2 = new MockDirectory("DirectoryPathTwo", "DirectoryPathTwo", dirs2, files2);

    Directory root = new MockDirectory("/", "/", null, null);
    this.fs = new MockFileSystemVerTwo(root, dir1, dir2, null, null);
    this.mv = new MoveClass(this.fs, this.vg);

    String[] args = {"DirectoryPathOne", "DirectoryPathTwo"};
    InputProcessor userInput = new MockInputProcessor("mv", args, null, null);
    Output output = this.mv.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());
    assertEquals(null, output.getException());
    assertTrue(!((MockFileSystemVerTwo) this.fs).directories.contains("DirectoryPathOne"));
  }

  /**
   * Test MoveClass runCommand() to move current moving directory
   */
  @Test
  public void testMoveRunCommandToMoveCurrentWorkingDirectory() {
    Directory currentDir = new MockDirectory("DirectoryPath", "DirectoryPath", null, null);
    this.fs.setCurrentDirectory(currentDir);

    String[] args = {"DirectoryPath", "ValidPathName"};
    InputProcessor userInput = new MockInputProcessor("mv", args, null, null);
    Output output = this.mv.runCommand(userInput);

    if (output.getException() == null)
      fail("should have exception.");
    String expectedError = "Cannot move current working directory.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test MoveClass runCommand() to move a parent of the current working directory
   */
  @Test
  public void testMoveRunCommandToMoveParentOfCurrentWorkingDirectory() {
    Directory currentDir =
        new MockDirectory("childDirectoryPath", "childDirectoryPath", null, null);
    this.fs.setCurrentDirectory(currentDir);

    String[] args = {"parentOfCurrentDirectoryPath", "DirectoryPath"};
    InputProcessor userInput = new MockInputProcessor("mv", args, null, null);
    Output output = this.mv.runCommand(userInput);

    if (output.getException() == null)
      fail("should have exception.");
    String expectedError = "Cannot move any parent of the current working directory.";
    assertEquals(expectedError, output.getException().getMessage());
  }
}
