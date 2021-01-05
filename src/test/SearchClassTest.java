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
import command.SearchClass;
import filesystem.Directory;
import filesystem.File;
import output.Output;

/**
 * This is a JUnit test class for SearchClass class.
 * 
 * @author Christina Ma
 */
public class SearchClassTest {
  private SearchClass search;
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
    Directory newDir1 = createMockDirectoryOne();
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.addLast(newDir1);

    MockDirectory newDir2 =
        new MockDirectory("ValidDirNameTwo", "DirectoryPathTwo", subDirs, new LinkedList<File>());
    return newDir2;
  }

  /**
   * This method creates a linked list consisting of one file.
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
   * @return Returns a MockFileSystemVerTwo object with the parent directory contains the desired
   *         contents.
   */
  private MockFileSystemVerTwo createParentDir(LinkedList<Directory> subDirs,
      LinkedList<File> subFiles) {
    MockDirectory mockDir = new MockDirectory("ValidDirectoryName", "ValidPath", subDirs, subFiles);
    return new MockFileSystemVerTwo(mockDir, newDir1, newDir2, file1, file2);
  }

  /**
   * This test is when no arguments are provided.
   */
  @Test
  public void testRunCommandWithNoArguments() {
    MockFileSystemVerTwo mockfsVerTwo =
        createParentDir(new LinkedList<Directory>(), new LinkedList<File>());
    this.search = new SearchClass(valiGate, mockfsVerTwo);
    this.output = search.runCommand(new MockInputProcessor("search", null, null, null));
    assertEquals(null, output.getOutputMessage());
    assertEquals("Missing additional required arguments for search command or incorrect format",
        output.getException().getMessage());
  }

  /**
   * This test is when user is missing -type and/or [f|d] arguments
   */
  @Test
  public void testRunCommandWithMissingTypeArguments() {
    MockFileSystemVerTwo mockfsVerTwo =
        createParentDir(new LinkedList<Directory>(), new LinkedList<File>());
    this.search = new SearchClass(valiGate, mockfsVerTwo);

    // Case #1: Missing -type argument
    String[] arg1 = {"ValidPath", "d", "-name", "\"ValidDirName\""};
    this.output = search.runCommand(new MockInputProcessor("search", arg1, null, null));
    assertEquals(null, output.getOutputMessage());
    assertEquals("Missing additional required arguments for search command or incorrect format",
        output.getException().getMessage());

    // Case #2: Missing [f|d] argument
    String[] arg2 = {"ValidPath", "-type", "-name", "\"ValidDirName\""};
    this.output = search.runCommand(new MockInputProcessor("search", arg2, null, null));
    assertEquals(null, output.getOutputMessage());
    assertEquals("Missing additional required arguments for search command or incorrect format",
        output.getException().getMessage());

    // Case #3: Missing both arguments
    String[] arg3 = {"ValidPath", "-name", "\"ValidDirName\""};
    this.output = search.runCommand(new MockInputProcessor("search", arg3, null, null));
    assertEquals(null, output.getOutputMessage());
    assertEquals("Missing additional required arguments for search command or incorrect format",
        output.getException().getMessage());
  }

  /**
   * This test is when user is missing -name and/or item's name arguments
   */
  @Test
  public void testRunCommandWithMissingNameArguments() {
    MockFileSystemVerTwo mockfsVerTwo =
        createParentDir(new LinkedList<Directory>(), new LinkedList<File>());
    this.search = new SearchClass(valiGate, mockfsVerTwo);

    // Case #1: Missing -name argument
    String[] arg1 = {"ValidPath", "-type", "d", "ValidDirName\""};
    this.output = search.runCommand(new MockInputProcessor("search", arg1, null, null));
    assertEquals(null, output.getOutputMessage());
    assertEquals("Missing additional required arguments for search command or incorrect format",
        output.getException().getMessage());

    // Case #2: Missing expression (item's name) argument
    String[] arg2 = {"ValidPath", "-type", "f", "-name"};
    this.output = search.runCommand(new MockInputProcessor("search", arg2, null, null));
    assertEquals(null, output.getOutputMessage());
    assertEquals("Missing additional required arguments for search command or incorrect format",
        output.getException().getMessage());

    // Case #3: Missing both arguments
    String[] arg3 = {"ValidPath", "-type", "d"};
    this.output = search.runCommand(new MockInputProcessor("search", arg3, null, null));
    assertEquals(null, output.getOutputMessage());
    assertEquals("Missing additional required arguments for search command or incorrect format",
        output.getException().getMessage());
  }

  /**
   * This test when the user has input the required search arguments in incorrect order.
   */
  @Test
  public void testRunCommandWithSearchArgumentsIncorrectOrder() {
    MockFileSystemVerTwo mockfsVerTwo =
        createParentDir(new LinkedList<Directory>(), new LinkedList<File>());
    this.search = new SearchClass(valiGate, mockfsVerTwo);

    // Case #1: -name and -type side by side
    String[] arg1 = {"ValidPath", "d", "-type", "-name", "\"ValidDirName\""};
    this.output = search.runCommand(new MockInputProcessor("search", arg1, null, null));
    assertEquals(null, output.getOutputMessage());
    assertEquals("Missing additional required arguments for search command or incorrect format",
        output.getException().getMessage());

    // Case #2: -name and -type at the ends
    String[] arg2 = {"ValidPath", "-name", "d", "\"ValidDirName\"", "-type"};
    this.output = search.runCommand(new MockInputProcessor("search", arg2, null, null));
    assertEquals(null, output.getOutputMessage());
    assertEquals("Missing additional required arguments for search command or incorrect format",
        output.getException().getMessage());
  }


  /**
   * This test when the user input the searched item's name with missing double quotations.
   */
  @Test
  public void testRunCommandWithNameMissingQuotations() {
    MockFileSystemVerTwo mockfsVerTwo =
        createParentDir(new LinkedList<Directory>(), new LinkedList<File>());
    this.search = new SearchClass(valiGate, mockfsVerTwo);

    String[] arg = {"ValidPath", "-type", "d", "-name", "\"itemName",};
    this.output = search.runCommand(new MockInputProcessor("search", arg, null, null));
    assertEquals(null, output.getOutputMessage());
    assertEquals("Missing additional required arguments for search command or incorrect format",
        output.getException().getMessage());
  }

  /**
   * This test is when user is missing both search and name arguments
   */
  @Test
  public void testRunCommandWithMissingSearchArguments() {
    MockFileSystemVerTwo mockfsVerTwo =
        createParentDir(new LinkedList<Directory>(), new LinkedList<File>());
    this.search = new SearchClass(valiGate, mockfsVerTwo);

    String[] arg1 = {"ValidPath", "ValidPathTwo"};
    this.output = search.runCommand(new MockInputProcessor("search", arg1, null, null));
    assertEquals(null, output.getOutputMessage());
    assertEquals("Missing additional required arguments for search command or incorrect format",
        output.getException().getMessage());
  }

  /**
   * This test is when user has not provided any pathnames
   */
  @Test
  public void testRunCommandWithMissingPaths() {
    MockFileSystemVerTwo mockfsVerTwo =
        createParentDir(new LinkedList<Directory>(), new LinkedList<File>());
    this.search = new SearchClass(valiGate, mockfsVerTwo);

    String[] arg1 = {"-type", "d", "-name", "\"ValidDirName\""};
    this.output = search.runCommand(new MockInputProcessor("search", arg1, null, null));
    assertEquals(null, output.getOutputMessage());
    assertEquals("Missing additional required arguments for search command or incorrect format",
        output.getException().getMessage());
  }

  /**
   * This test is when user has input one pathname to search for an existing item.
   */
  @Test
  public void testRunCommandWithOnePathToFindExistingItem() {
    // Creating mock sub-directories and sub files
    LinkedList<File> subFiles = createSubFile();
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);

    MockFileSystemVerTwo mockfsVerTwo = createParentDir(subDirs, subFiles);
    this.search = new SearchClass(valiGate, mockfsVerTwo);

    String[] arg = {"DirectoryPathOne", "-type", "f", "-name", "\"ValidFileNameTwo\""};
    this.output = search.runCommand(new MockInputProcessor("search", arg, null, null));
    assertEquals("DirectoryPathOne:\n" + "FilePathTwo", output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * This test is when user has input two pathnames to search for an existing item.
   */
  @Test
  public void testRunCommandWithTwoPathsToFindExistingItem() {
    // Creating mock sub-directories and sub files
    LinkedList<File> subFiles = createSubFile();
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);
    subDirs.add(newDir2);
    subFiles.add(file1);

    MockFileSystemVerTwo mockfsVerTwo = createParentDir(subDirs, subFiles);
    this.search = new SearchClass(valiGate, mockfsVerTwo);

    String[] arg =
        {"DirectoryPathOne", "DirectoryPathTwo", "-type", "f", "-name", "\"ValidFileNameTwo\""};
    this.output = search.runCommand(new MockInputProcessor("search", arg, null, null));
    assertEquals(
        "DirectoryPathOne:\n" + "FilePathTwo\n" + "\n" + "DirectoryPathTwo:\n" + "FilePathTwo",
        output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * This test is when user has input one pathname to search for a non existing item.
   */
  @Test
  public void testRunCommandWithOnePathToFindNonExistingItem() {
    // Creating mock sub-directories and sub files
    LinkedList<File> subFiles = createSubFile();
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);

    MockFileSystemVerTwo mockfsVerTwo = createParentDir(subDirs, subFiles);
    this.search = new SearchClass(valiGate, mockfsVerTwo);

    String[] arg = {"DirectoryPathOne", "-type", "f", "-name", "\"Nonexisting\""};
    this.output = search.runCommand(new MockInputProcessor("search", arg, null, null));
    assertEquals("Nonexisting does not exist within DirectoryPathOne", output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * This test is when user has input two pathnames to search for a non existing item.
   */
  @Test
  public void testRunCommandWithTwoPathsToFindNonExistingItem() {
    // Creating mock sub-directories and sub files
    LinkedList<File> subFiles = createSubFile();
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);
    subDirs.add(newDir2);

    MockFileSystemVerTwo mockfsVerTwo = createParentDir(subDirs, subFiles);
    this.search = new SearchClass(valiGate, mockfsVerTwo);

    String[] arg =
        {"DirectoryPathOne", "DirectoryPathTwo", "-type", "f", "-name", "\"Nonexisting\""};
    this.output = search.runCommand(new MockInputProcessor("search", arg, null, null));
    assertEquals("Nonexisting does not exist within DirectoryPathOne\n" + "\n"
        + "Nonexisting does not exist within DirectoryPathTwo", output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * This test when user inputs a pathname containing invalid characters.
   */
  @Test
  public void testRunCommandWithInvalidPathname() {
    // Creating mock sub-directories and sub files
    LinkedList<File> subFiles = createSubFile();
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);

    MockFileSystemVerTwo mockfsVerTwo = createParentDir(subDirs, subFiles);
    this.search = new SearchClass(valiGate, mockfsVerTwo);

    String[] arg = {"InvalidPath", "-type", "f", "-name", "\"ValidFileNameTwo\""};
    this.output = search.runCommand(new MockInputProcessor("search", arg, null, null));
    assertEquals("", output.getOutputMessage());
    assertEquals("InvalidPath is not a valid pathname.", output.getException().getMessage());
  }

  /**
   * This test when user inputs three pathnames such that the first and third pathname refers to an
   * existing item, while the second pathname is invalid.
   */
  @Test
  public void testRunCommandWithInvalidPathnameInBetween() {
    // Creating mock sub-directories and sub files
    LinkedList<File> subFiles = createSubFile();
    LinkedList<Directory> subDirs = new LinkedList<Directory>();
    subDirs.add(newDir1);

    MockFileSystemVerTwo mockfsVerTwo = createParentDir(subDirs, subFiles);
    this.search = new SearchClass(valiGate, mockfsVerTwo);

    String[] arg = {"DirectoryPathOne", "InvalidPath", "DirectoryPathTwo", "-type", "f", "-name",
        "\"ValidFileNameTwo\""};
    this.output = search.runCommand(new MockInputProcessor("search", arg, null, null));
    assertEquals("DirectoryPathOne:\n" + "FilePathTwo", output.getOutputMessage());
    assertEquals("InvalidPath is not a valid pathname.", output.getException().getMessage());
  }
}
