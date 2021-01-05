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
import command.CopyClass;
import driver.*;
import filesystem.Directory;
import filesystem.File;
import filesystem.FileSystem;
import output.Output;

/**
 * This is a JUnit test class for CopyClass
 * 
 * @author Man Hei Ho
 */
public class CopyClassTest {

  private FileSystem fs;
  private ValidationGate vg;
  private CopyClass cp;

  @Before
  public void setup() {
    Directory root = new MockDirectory("/", "/", null, null);
    this.fs = new MockFileSystemVerTwo(root, null, null, null, null);
    this.vg = new MockValidationGate();
    this.cp = new CopyClass(this.fs, this.vg);
  }

  /**
   * Test CopyClass runCommand() with user entering no argument
   */
  @Test
  public void testCopyRunCommandWithNoArgument() {
    InputProcessor userInput = new MockInputProcessor("cp", null, null, null);
    Output output = this.cp.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("should have exception.");
    String expectedError = "cp takes two pathnames as argument.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test CopyClass runCommand() with user entering one argument
   */
  @Test
  public void testCopyRunCommandWithOneArgument() {
    String[] args = {"ValidPath"};
    InputProcessor userInput = new MockInputProcessor("cp", args, null, null);
    Output output = this.cp.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("should have exception.");
    String expectedError = "cp takes two pathnames as argument.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test CopyClass runCommand() with user entering more than two argument
   */
  @Test
  public void testCopyRunCommandWithMoreThanTwoArgument() {
    String[] args = {"ValidPath", "ValidPath", "ValidPath"};
    InputProcessor userInput = new MockInputProcessor("cp", args, null, null);
    Output output = this.cp.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("should have exception.");
    String expectedError = "cp takes two pathnames as argument.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test CopyClass runCommand() with user entering two invalid pathname
   */
  @Test
  public void testCopyRunCommandWithTwoInvalidArgument() {
    String[] args = {"InvalidPathOne", "InvalidPathTwo"};
    InputProcessor userInput = new MockInputProcessor("cp", args, null, null);
    Output output = this.cp.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("should have exception.");
    String expectedError = "InvalidPathOne is not a valid pathname.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test CopyClass runCommand() with user entering two pathname, the first one is a valid pathname
   * and the second one is an invalid pathname
   */
  @Test
  public void testCopyRunCommandWithOneValidAndOneInvalidArgument() {
    String[] args = {"ValidPathOne", "InvalidPathTwo"};
    InputProcessor userInput = new MockInputProcessor("cp", args, null, null);
    Output output = this.cp.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("should have exception.");
    String expectedError = "InvalidPathTwo is not a valid pathname.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test CopyClass runCommand() with user entering two pathname, the first one is a valid pathname
   * but referring to non-existing pathname in the file system
   */
  @Test
  public void testCopyRunCommandWithNonExistingOldPath() {
    String[] args = {"NonexistingValidPathname", "ValidPathTwo"};
    InputProcessor userInput = new MockInputProcessor("cp", args, null, null);
    Output output = this.cp.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("should have exception.");
    String expectedError =
        "NonexistingValidPathname does not refer to an existing item in the file system";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test CopyClass runCommand() with user entering two existing valid file pathnames
   */
  @Test
  public void testCopyRunCommandWithTwoExistingFilePath() {
    String[] args = {"ValidPathFileOne", "ValidPathFileTwo"};
    InputProcessor userInput = new MockInputProcessor("cp", args, null, null);
    Output output = this.cp.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());
    assertEquals(null, output.getException());
    assertEquals("ValidPathFileTwo", ((MockFileSystemVerTwo) this.fs).lastModifiedFile.getPath());
  }

  /**
   * Test CopyClass runCommand() with user entering two valid pathnames with the first pathname
   * referring to an existing file, and second pathname a non-existing file pathname
   */
  @Test
  public void testCopyRunCommandWithExistingFilePathAndNonexistingFilePath() {
    String[] args = {"ValidPathFileOne", "NonexistingValidPathname"};
    InputProcessor userInput = new MockInputProcessor("cp", args, null, null);
    Output output = this.cp.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());
    assertEquals(null, output.getException());
    assertEquals("NonexistingValidPathname",
        ((MockFileSystemVerTwo) this.fs).lastModifiedFile.getPath());
  }

  /**
   * Test CopyClass runCommand() with user entering two valid pathnames with the first pathname
   * referring to an existing file, and second pathname referring to an existing directory
   */
  @Test
  public void testCopyRunCommandWithExistingFilePathAndExistingDirPath() {
    String[] args = {"FilePath", "DirectoryPath"};
    InputProcessor userInput = new MockInputProcessor("cp", args, null, null);
    Output output = this.cp.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());
    assertEquals(null, output.getException());
    assertEquals("DirectoryPath/FilePath",
        ((MockFileSystemVerTwo) this.fs).lastModifiedFile.getPath());
  }

  /**
   * Test CopyClass runCommand() with user entering two valid pathnames with the first pathname
   * referring to an existing file, and second pathname referring to an non-existing directory
   */
  @Test
  public void testCopyRunCommandWithExistingFilePathAndNonexistingDirPath() {
    String[] args = {"ValidPathFileOne", "NonexistingDirectoryPath/"};
    InputProcessor userInput = new MockInputProcessor("cp", args, null, null);
    Output output = this.cp.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("should have exception");

    String expectedError = "NonexistingDirectoryPath/ is refering to a nonexisting directory.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test CopyClass runCommand() with user entering two valid existing directory pathnames
   */
  @Test
  public void testCopyRunCommandWithTwoExistingDirPath() {
    LinkedList<Directory> dirs = new LinkedList<Directory>();
    LinkedList<File> files = new LinkedList<File>();
    File file1 = new File("FilePath", "FilePath");
    files.add(file1);
    Directory dir1 = new MockDirectory("DirectoryPathOne", "DirectoryPathOne", dirs, files);

    LinkedList<Directory> dirs2 = new LinkedList<Directory>();
    LinkedList<File> files2 = new LinkedList<File>();
    Directory dir2 = new MockDirectory("DirectoryPathTwo", "DirectoryPathTwo", dirs2, files2);

    Directory root = new MockDirectory("/", "/", null, null);
    this.fs = new MockFileSystemVerTwo(root, dir1, dir2, null, null);
    this.cp = new CopyClass(this.fs, this.vg);

    String[] args = {"DirectoryPathOne", "DirectoryPathTwo"};
    InputProcessor userInput = new MockInputProcessor("cp", args, null, null);
    Output output = this.cp.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());
    assertEquals(null, output.getException());
    assertEquals("DirectoryPathTwo/DirectoryPathOne/FilePath",
        ((MockFileSystemVerTwo) this.fs).lastModifiedFile.getPath());
    assertTrue(
        ((MockFileSystemVerTwo) this.fs).directories.contains("DirectoryPathTwo/DirectoryPathOne"));
    assertTrue(((MockFileSystemVerTwo) this.fs).files
        .contains("DirectoryPathTwo/DirectoryPathOne/FilePath"));
  }

  /**
   * Test CopyClass runCommand() with user entering two valid identical existing directory pathnames
   */
  @Test
  public void testCopyRunCommandWithTwoIdenticalExistingDirPath() {
    String[] args = {"DirectoryPath", "DirectoryPath"};
    InputProcessor userInput = new MockInputProcessor("cp", args, null, null);
    Output output = this.cp.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("should have exception");

    String expectedError = "Cannot copy/move directory to the same directory.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test CopyClass runCommand() with user entering two valid existing directory pathnames with the
   * newPath is a child of the oldPath
   */
  @Test
  public void testCopyRunCommandWithTwoExistingDirPathAndNewPathIsAChildOfOldPath() {
    String[] args = {"DirectoryPath", "childDirectoryPath"};
    InputProcessor userInput = new MockInputProcessor("cp", args, null, null);
    Output output = this.cp.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("should have exception");

    String expectedError = "Cannot copy/move parent directory to child directory.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test CopyClass runCommand() with user entering two valid pathnames with the first pathname
   * referring to an existing directory, and second pathname referring to an non-existing directory
   */
  @Test
  public void testCopyRunCommandWithExistingDirPathAndNonexistingDirPath() {
    LinkedList<Directory> dirs = new LinkedList<Directory>();
    LinkedList<File> files = new LinkedList<File>();
    File file1 = new File("FilePath", "FilePath");
    files.add(file1);
    Directory dir1 = new MockDirectory("DirectoryPathOne", "DirectoryPathOne", dirs, files);

    Directory root = new MockDirectory("/", "/", null, null);
    this.fs = new MockFileSystemVerTwo(root, dir1, null, null, null);
    this.cp = new CopyClass(this.fs, this.vg);

    String[] args = {"DirectoryPathOne", "NonexistingDirectoryPath/"};
    InputProcessor userInput = new MockInputProcessor("cp", args, null, null);
    Output output = this.cp.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());
    assertEquals(null, output.getException());
    assertEquals("NonexistingDirectoryPath//FilePath",
        ((MockFileSystemVerTwo) this.fs).lastModifiedFile.getPath());
    assertTrue(((MockFileSystemVerTwo) this.fs).directories.contains("NonexistingDirectoryPath/"));
    assertTrue(
        ((MockFileSystemVerTwo) this.fs).files.contains("NonexistingDirectoryPath//FilePath"));
  }

  /**
   * Test CopyClass runCommand() with user entering two valid pathnames with the first pathname
   * referring to an existing directory, and second pathname referring to an existing file path
   */
  @Test
  public void testCopyRunCommandWithExistingDirPathAndExistingFilePath() {
    String[] args = {"DirectoryPath", "FilePath"};
    InputProcessor userInput = new MockInputProcessor("cp", args, null, null);
    Output output = this.cp.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("should have exception");

    String expectedError = "FilePath is refering to an existing file.";
    assertEquals(expectedError, output.getException().getMessage());
  }

  /**
   * Test CopyClass runCommand() with user entering two valid pathnames with the first pathname
   * referring to an existing directory, and second pathname referring to an non-existing file
   */
  @Test
  public void testCopyRunCommandWithExistingDirPathAndNonexistingFilePath() {
    String[] args = {"DirectoryPath", "NonexistingFilePath"};
    InputProcessor userInput = new MockInputProcessor("cp", args, null, null);
    Output output = this.cp.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("should have exception");

    String expectedError = "NonexistingFilePath is refering to a nonexisting file.";
    assertEquals(expectedError, output.getException().getMessage());
  }
}
