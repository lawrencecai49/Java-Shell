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
import org.junit.Before;
import org.junit.Test;
import command.RemoveDirectoryClass;
import driver.InputProcessor;
import driver.ValidationGate;
import filesystem.Directory;
import filesystem.FileSystem;
import output.Output;

/**
 * This is the JUnit test class for RemoveDirectoryClass
 * 
 * @author Man Hei Ho
 */
public class RemoveDirectoryClassTest {

  private FileSystem fs;
  private ValidationGate vg;
  private RemoveDirectoryClass rm;

  @Before
  public void setup() {
    this.fs = new MockFileSystem();
    this.vg = new MockValidationGate();
    this.rm = new RemoveDirectoryClass(this.fs, this.vg);
  }

  /**
   * Test RemoveDirectoryClass runCommand() with user entering no argument
   */
  @Test
  public void testRemoveDirectoryRunCommandWithNoArgument() {
    InputProcessor userInput = new MockInputProcessor("rm", null, null, null);
    Output output = this.rm.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("Should throw exception.");
    String expectedError = "rm takes one directory pathname as argument.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test RemoveDirectoryClass runCommand() with user entering more than one argument
   */
  @Test
  public void testRemoveDirectoryRunCommandWithMoreThanOneArgument() {
    String[] args = {"ValidPathnameOne", "ValidPathname2"};
    InputProcessor userInput = new MockInputProcessor("rm", args, null, null);
    Output output = this.rm.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("Should throw exception.");
    String expectedError = "rm takes one directory pathname as argument.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test RemoveDirectoryClass runCommand() with user entering one invalid argument
   */
  @Test
  public void testRemoveDirectoryRunCommandWithOneInvalidPath() {
    String[] args = {"InvalidPathname"};
    InputProcessor userInput = new MockInputProcessor("rm", args, null, null);
    Output output = this.rm.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("Should throw exception.");
    String expectedError = "InvalidPathname is not a valid pathname.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test RemoveDirectoryClass runCommand() with user entering one valid file path
   */
  @Test
  public void testRemoveDirectoryRunCommandWithOneValidFilePath() {
    String[] args = {"FilePath"};
    InputProcessor userInput = new MockInputProcessor("rm", args, null, null);
    Output output = this.rm.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("Should throw exception.");
    String expectedError = "FilePath does not refer to an existing directory";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test RemoveDirectoryClass runCommand() with one valid non-existing directory path
   */
  @Test
  public void testRemoveDirectoryRunCommandWithOneValidNonexistingDirectoryPath() {
    String[] args = {"NonexistingValidPathname"};
    InputProcessor userInput = new MockInputProcessor("rm", args, null, null);
    Output output = this.rm.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("Should throw exception.");
    String expectedError = "NonexistingValidPathname does not refer to an existing directory";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test RemoveDirectoryClass runCommand() with user entering one valid existing directory path
   */
  @Test
  public void testRemoveDirectoryRunCommandWithOneValidExistingDirectoryPath() {
    String[] args = {"DirectoryPath"};
    InputProcessor userInput = new MockInputProcessor("rm", args, null, null);
    Output output = this.rm.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());
    assertEquals(null, output.getException());
    assertEquals(false, ((MockFileSystem) this.fs).directories.contains("DirectoryPath"));
  }

  /**
   * Test RemoveDirectoryClass runCommand() to remove root
   */
  @Test
  public void testRemoveDirectoryRunCommandToRemoveRoot() {
    String[] args = {"/"};
    InputProcessor userInput = new MockInputProcessor("rm", args, null, null);
    Output output = this.rm.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("Should throw exception.");
    String expectedError = "Cannot delete root directorry.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test RemoveDirectoryClass runCommand() to remove current working directory
   */
  @Test
  public void testRemoveDirectoryRunCommandToRemoveCurrentWorkingDirectory() {
    String path = "CurrentDirValidPath";
    Directory currentDir = new MockDirectory(path, path, null, null);
    this.fs.setCurrentDirectory(currentDir);
    String[] args = {path};
    InputProcessor userInput = new MockInputProcessor("rm", args, null, null);
    Output output = this.rm.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("Should throw exception.");
    String expectedError = "Cannot delete current working directory.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test RemoveDirectoryClass runCommand() to remove parent of current working directory
   */
  @Test
  public void testRemoveDirectoryRunCommandToRemoveParentOfCurrentWorkingDirectory() {
    Directory root = new MockDirectory("/", "/", null, null);
    this.fs = new MockFileSystemVerTwo(root, null, null, null, null);
    this.rm = new RemoveDirectoryClass(this.fs, this.vg);

    Directory currentDir = new MockDirectory("child", "child", null, null);
    this.fs.setCurrentDirectory(currentDir);

    String[] args = {"ValidPath"};
    InputProcessor userInput = new MockInputProcessor("rm", args, null, null);
    Output output = this.rm.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("Should throw exception.");
    String expectedError =
        "ValidPath cannot be deleted because it is one of the parents of the current directory.";
    assertEquals(expectedError, output.getException().getMessage());
  }

}
