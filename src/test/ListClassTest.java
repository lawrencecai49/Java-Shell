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
import command.ListClass;
import filesystem.Directory;
import filesystem.File;
import output.Output;

/**
 * This is a JUnit test for ListClass class.
 * 
 * @author Christina Ma
 */
public class ListClassTest {
  private ListClass ls;
  private Output output;
  private MockValidationGate valiGate;
  File file1;
  File file2;
  Directory newDir1;
  Directory newDir2;

  /**
   * This method initializes valiGate and sets ups two mock files and two mock directories.
   */
  @Before
  public void setUp() {
    this.valiGate = new MockValidationGate();
    this.file1 = new File("ValidFileNameOne", "FilePathOne");
    this.file2 = new File("ValidFileNameTwo", "FilePathTwo");
    newDir1 = createMockDirectoryWithOneFile();
    newDir2 = createMockDirectoryWithOneSubdir();
  }

  /**
   * This method creates a mock directory that contains one file.
   * 
   * @return Returns a MockDirectory that consist of one file
   */
  private MockDirectory createMockDirectoryWithOneFile() {
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
  private MockDirectory createMockDirectoryWithOneSubdir() {
    // Creates the sub-directory
    MockDirectory newDir1 = createMockDirectoryWithOneFile();
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
   * This method returns a MockFileSystemVerTwo object with the parent directory containing the
   * desired sub-directories and sub files
   * 
   * @param subDirs The desired sub-directories the parent directory contains.
   * @param subFiles The desired sub files the parent directory contains
   * @return Returns a MockFileSystemVerTwo object with the parent directory containing the desired
   *         contents.
   */
  private MockFileSystemVerTwo createParentDir(LinkedList<Directory> subDirs,
      LinkedList<File> subFiles) {
    MockDirectory mockDir = new MockDirectory("ValidDirectoryName", "ValidPath", subDirs, subFiles);
    return new MockFileSystemVerTwo(mockDir, newDir1, newDir2, file1, file2);

  }

  /**
   * This test is when the user does not provide additional arguments and the current working
   * directory has no sub-directories or files.
   */
  @Test
  public void testRunCommandWithNoArgumentAndEmptyDirectory() {
    // Creating a mock directory that will serve as the parent
    MockFileSystemVerTwo mockfsVer2 =
        createParentDir(new LinkedList<Directory>(), new LinkedList<File>());

    this.ls = new ListClass(valiGate, mockfsVer2);
    this.output = ls.runCommand(new MockInputProcessor("ls", null, null, null));
    assertEquals("", output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * This test is when the user does not provide additional arguments and the current working
   * directory has one sub-directory and one file.
   */
  @Test
  public void testRunCommandWithNoArgumentAndFilledDirectory() {
    // Creating mock directories and files
    LinkedList<File> subFiles = createSubFile();
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);

    // Creating a mock directory that will serve as the parent
    MockFileSystemVerTwo mockfsVer2 = createParentDir(subDirs, subFiles);

    this.ls = new ListClass(valiGate, mockfsVer2);
    this.output = ls.runCommand(new MockInputProcessor("ls", null, null, null));
    assertEquals("ValidDirNameOne\n" + "ValidFileNameOne", output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * This test is when the user gives -R as the only other additional argument and the current
   * working directory has no sub-directories or files.
   */
  @Test
  public void testRunCommandWithRAndEmptyDirectory() {
    // Creating a mock directory that will serve as the parent
    MockFileSystemVerTwo mockfsVer2 =
        createParentDir(new LinkedList<Directory>(), new LinkedList<File>());

    this.ls = new ListClass(valiGate, mockfsVer2);
    String[] args = {"-R"};
    this.output = ls.runCommand(new MockInputProcessor("ls", args, null, null));
    assertEquals("ValidPath:", output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * This test is when the user gives -R as the only other additional argument and the current
   * working directory has one sub-directories and one file.
   */
  @Test
  public void testRunCommandWithRAndFilledDirectory() {
    // Creating mock directories and files
    LinkedList<File> subFiles = createSubFile();
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);

    // Creating a mock directory that will serve as the parent
    MockFileSystemVerTwo mockfsVer2 = createParentDir(subDirs, subFiles);

    this.ls = new ListClass(valiGate, mockfsVer2);
    String[] args = {"-R"};
    this.output = ls.runCommand(new MockInputProcessor("ls", args, null, null));
    assertEquals("ValidPath:\n" + "ValidDirNameOne\n" + "ValidFileNameOne\n" + "\n"
        + "DirectoryPathOne:\n" + "ValidFileNameTwo", output.getOutputMessage());
    assertEquals(null, output.getException());
  }


  /**
   * This test is when the user provides one pathname that refers to an existing directory.
   */
  @Test
  public void testRunCommandWithOneExistingDirPath() {
    // Creating sub-directory
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);

    // Creating a mock directory that will serve as the parent
    MockFileSystemVerTwo mockfsVer2 = createParentDir(subDirs, new LinkedList<File>());

    this.ls = new ListClass(valiGate, mockfsVer2);
    String[] args = {"DirectoryPathOne"};
    this.output = ls.runCommand(new MockInputProcessor("ls", args, null, null));
    assertEquals("DirectoryPathOne:\n" + "ValidFileNameTwo", output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * This test is when the user provides two pathnames that refers to a existing directories.
   */
  @Test
  public void testRunCommandWithTwoExistingDirPath() {
    // Creating sub-directory linked list
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);
    subDirs.add(newDir2);

    // Creating a mock directory that will serve as the parent
    MockFileSystemVerTwo mockfsVer2 = createParentDir(subDirs, new LinkedList<File>());

    this.ls = new ListClass(valiGate, mockfsVer2);
    String[] args = {"DirectoryPathOne", "DirectoryPathTwo"};
    this.output = ls.runCommand(new MockInputProcessor("ls", args, null, null));
    assertEquals("DirectoryPathOne:\n" + "ValidFileNameTwo\n" + "\n" + "DirectoryPathTwo:\n"
        + "ValidDirNameOne", output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * This test is when the user provided one existing pathname that refers to an existing directory
   * with the -R argument.
   */
  @Test
  public void testRunCommandWithRAndOneExistingDirPath() {
    // Creating sub-directory
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);

    // Creating a mock directory that will serve as the parent
    MockFileSystemVerTwo mockfsVer2 = createParentDir(subDirs, new LinkedList<File>());

    this.ls = new ListClass(valiGate, mockfsVer2);
    String[] args = {"DirectoryPathOne", "-R"};
    this.output = ls.runCommand(new MockInputProcessor("ls", args, null, null));
    assertEquals("DirectoryPathOne\n" + "DirectoryPathOne:\n" + "ValidFileNameTwo",
        output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * This test is when the user provided two existing pathnames that refers to an existing directory
   * with the -R argument.
   */
  @Test
  public void testRunCommandWithRAndTwoExistingDirPath() {
    // Creating sub-directory linked list
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);
    subDirs.add(newDir2);

    // Creating a mock directory that will serve as the parent
    MockFileSystemVerTwo mockfsVer2 = createParentDir(subDirs, new LinkedList<File>());

    this.ls = new ListClass(valiGate, mockfsVer2);
    String[] args = {"DirectoryPathOne", "DirectoryPathTwo", "-R"};
    this.output = ls.runCommand(new MockInputProcessor("ls", args, null, null));
    assertEquals("DirectoryPathOne\n" + "DirectoryPathOne:\n" + "ValidFileNameTwo\n" + "\n" + "\n"
        + "DirectoryPathTwo\n" + "DirectoryPathTwo:\n" + "ValidDirNameOne\n" + "\n"
        + "DirectoryPathOne:\n" + "ValidFileNameTwo", output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * This test is when the user provided two pathnames such that the first pathname refers to an
   * existing directory, but the second does not refer to an existing
   */
  @Test
  public void testRunCommandWithExistAndNonExistDirPaths() {
    // Creating sub-directory linked list
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);

    // Creating a mock directory that will serve as the parent
    MockFileSystemVerTwo mockfsVer2 = createParentDir(subDirs, new LinkedList<File>());

    this.ls = new ListClass(valiGate, mockfsVer2);
    String[] args = {"DirectoryPathOne", "Nonexisting"};
    this.output = ls.runCommand(new MockInputProcessor("ls", args, null, null));
    assertEquals("DirectoryPathOne:\n" + "ValidFileNameTwo", output.getOutputMessage());
    assertEquals("Nonexisting is not an existing directory or file",
        output.getException().getMessage());
  }

  /**
   * This test is when the user provided two pathnames such that the first pathname refers to an
   * existing directory, but the second does not refer to an existing with the -R argument
   */
  @Test
  public void testRunCommandWithRAndExistAndNonExistDirPaths() {
    // Creating sub-directory linked list
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);

    // Creating a mock directory that will serve as the parent
    MockFileSystemVerTwo mockfsVer2 = createParentDir(subDirs, new LinkedList<File>());

    this.ls = new ListClass(valiGate, mockfsVer2);
    String[] args = {"DirectoryPathOne", "Nonexisting", "-R"};
    this.output = ls.runCommand(new MockInputProcessor("ls", args, null, null));
    assertEquals("DirectoryPathOne\n" + "DirectoryPathOne:\n" + "ValidFileNameTwo",
        output.getOutputMessage());
    assertEquals("Nonexisting is not an existing directory or file",
        output.getException().getMessage());
  }

  /**
   * This test is when the user provides a pathname that does not refer to an existing directory.
   */
  @Test
  public void testRunCommandWithNonExistingDirPath() {
    // Creating parent directory
    MockFileSystemVerTwo mockfsVer2 =
        createParentDir(new LinkedList<Directory>(), new LinkedList<File>());

    this.ls = new ListClass(valiGate, mockfsVer2);
    String[] args = {"Nonexisting"};
    this.output = ls.runCommand(new MockInputProcessor("ls", args, null, null));
    assertEquals("", output.getOutputMessage());
    assertEquals("Nonexisting is not an existing directory or file",
        output.getException().getMessage());
  }

  /**
   * This test is when the user provides a pathname that does not refer to an existing directory
   * with -R.
   */
  @Test
  public void testRunCommandWithRAndNonExistingDirPath() {
    // Creating parent directory
    MockFileSystemVerTwo mockfsVer2 =
        createParentDir(new LinkedList<Directory>(), new LinkedList<File>());

    this.ls = new ListClass(valiGate, mockfsVer2);
    String[] args = {"Nonexisting", "-R"};
    this.output = ls.runCommand(new MockInputProcessor("ls", args, null, null));
    assertEquals("", output.getOutputMessage());
    assertEquals("Nonexisting is not an existing directory or file",
        output.getException().getMessage());
  }

  /**
   * This test is when the user provides a pathname that refers to an existing file.
   */
  @Test
  public void testRunCommandWithExistingFile() {
    // Creating parent directory
    MockFileSystemVerTwo mockfsVer2 = createParentDir(new LinkedList<Directory>(), createSubFile());

    this.ls = new ListClass(valiGate, mockfsVer2);
    String[] args = {"FilePathOne"};
    this.output = ls.runCommand(new MockInputProcessor("ls", args, null, null));
    assertEquals("FilePathOne", output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * This test is when the user provides a pathname that refers to an existing file with -R.
   */
  @Test
  public void testRunCommandWithRAndExistingFile() {
    // Creating parent directory
    MockFileSystemVerTwo mockfsVer2 = createParentDir(new LinkedList<Directory>(), createSubFile());

    this.ls = new ListClass(valiGate, mockfsVer2);
    String[] args = {"FilePathOne", "-R"};
    this.output = ls.runCommand(new MockInputProcessor("ls", args, null, null));
    assertEquals("FilePathOne", output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * This test is when the user provides a pathname that refers to non existing file(s).
   */
  @Test
  public void testRunCommandWithNonExistingFile() {
    // Creating parent directory
    MockFileSystemVerTwo mockfsVer2 = createParentDir(new LinkedList<Directory>(), createSubFile());

    this.ls = new ListClass(valiGate, mockfsVer2);
    String[] args = {"NonexistingValidFilePath"};
    this.output = ls.runCommand(new MockInputProcessor("ls", args, null, null));
    assertEquals("", output.getOutputMessage());
    assertEquals("NonexistingValidFilePath is not an existing directory or file",
        output.getException().getMessage());
  }

  /**
   * This test is when the user provides a pathname that refers to non existing file with -R.
   */
  @Test
  public void testRunCommandWithRAndNonExistingFile() {
    // Creating parent directory
    MockFileSystemVerTwo mockfsVer2 = createParentDir(new LinkedList<Directory>(), createSubFile());

    this.ls = new ListClass(valiGate, mockfsVer2);
    String[] args = {"NonexistingValidFilePath", "-R"};
    this.output = ls.runCommand(new MockInputProcessor("ls", args, null, null));
    assertEquals("", output.getOutputMessage());
    assertEquals("NonexistingValidFilePath is not an existing directory or file",
        output.getException().getMessage());
  }

  /**
   * This test is when the user provides an invalid pathname.
   */
  @Test
  public void testRunCommandWithInvalidPath() {
    // Creating parent directory
    MockFileSystemVerTwo mockfsVer2 = createParentDir(new LinkedList<Directory>(), createSubFile());

    this.ls = new ListClass(valiGate, mockfsVer2);
    String[] args = {"InvalidPath"};
    this.output = ls.runCommand(new MockInputProcessor("ls", args, null, null));
    assertEquals("", output.getOutputMessage());
    assertEquals("InvalidPath is not a valid pathname.", output.getException().getMessage());
  }

  /**
   * This test is when the user provides an invalid pathname with -R argument.
   */
  @Test
  public void testRunCommandWithRAndWithInvalidPath() {
    // Creating parent directory
    MockFileSystemVerTwo mockfsVer2 = createParentDir(new LinkedList<Directory>(), createSubFile());

    this.ls = new ListClass(valiGate, mockfsVer2);
    String[] args = {"InvalidPath", "-R"};
    this.output = ls.runCommand(new MockInputProcessor("ls", args, null, null));
    assertEquals("", output.getOutputMessage());
    assertEquals("InvalidPath is not a valid pathname.", output.getException().getMessage());
  }
}
