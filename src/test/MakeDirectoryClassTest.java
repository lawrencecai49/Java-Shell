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
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import command.MakeDirectoryClass;
import driver.InputProcessor;
import exception.InvalidArgumentException;
import exception.InvalidNameException;
import exception.InvalidPathException;
import exception.ItemAlreadyExistsException;
import output.Output;

/**
 * JUnit test for MakeDirectoryClass
 * 
 * @author Luoliang Cai
 */
public class MakeDirectoryClassTest {
  private MockFileSystem fs;
  private MakeDirectoryClass mkdir;
  private InputProcessor input;

  @Before
  public void setUp() throws Exception {
    fs = new MockFileSystem();
    mkdir = new MakeDirectoryClass(fs);
  }

  @After
  public void tearDown() throws Exception {
    fs = null;
    mkdir = null;
    input = null;
  }

  /**
   * Test if correct exception is generated if user uses no arguments
   */
  @Test
  public void testRunCommandNoArguments() {
    input = new MockInputProcessor("mkdir", null, null, null);
    Output actual = mkdir.runCommand(input);
    assertEquals(actual.getOutputMessage(), null);
    assertTrue(actual.getException() instanceof InvalidArgumentException);
    assertEquals(actual.getException().getMessage(), "mkdir must take at least one argument");
  }

  /**
   * Test if correct exception is generated if user inputs an invalid path as one of the arguments
   */
  @Test
  public void testRunCommandInvalidPath() {
    String arguments[] = {"InvalidPath"};
    input = new MockInputProcessor("mkdir", arguments, null, null);
    Output actual = mkdir.runCommand(input);
    assertEquals(actual.getOutputMessage(), null);
    assertTrue(actual.getException() instanceof InvalidPathException);
    assertEquals(actual.getException().getMessage(),
        "Could not create any directory because InvalidPath is an invalid path");
  }

  /**
   * Test if correct exception is generated if user inputs a path with an invalid name as one of the
   * arguments
   */
  @Test
  public void testRunCommandInvalidName() {
    String arguments[] = {"InvalidName"};
    input = new MockInputProcessor("mkdir", arguments, null, null);
    Output actual = mkdir.runCommand(input);
    assertEquals(actual.getOutputMessage(), null);
    assertTrue(actual.getException() instanceof InvalidNameException);
    assertEquals(actual.getException().getMessage(),
        "Could not create any directory because InvalidName has an invalid name");
  }

  /**
   * Test if correct exception is generated if user inputs a path with an invalid name as one of the
   * arguments
   */
  @Test
  public void testRunCommandAlreadyExistingItem() {
    String arguments[] = {"PathOfExistingItem"};
    input = new MockInputProcessor("mkdir", arguments, null, null);
    Output actual = mkdir.runCommand(input);
    assertEquals(actual.getOutputMessage(), null);
    assertTrue(actual.getException() instanceof ItemAlreadyExistsException);
    assertEquals(actual.getException().getMessage(),
        "Could not create any directory because item already exists at PathOfExistingItem");
  }

  /**
   * Test if mkdir successfully creates one directory
   */
  @Test
  public void testRunCommandMakeOneDirectory() {
    String arguments[] = {"ValidPath"};
    input = new MockInputProcessor("mkdir", arguments, null, null);
    Output actual = mkdir.runCommand(input);
    assertEquals(actual.getOutputMessage(), null);
    assertEquals(actual.getException(), null);
  }

  /**
   * Test if mkdir successfully creates multiple directories
   */
  @Test
  public void testRunCommandMakeMultipleDirectories() {
    String arguments[] = {"ValidPath1", "ValidPath2", "ValidPath3"};
    input = new MockInputProcessor("mkdir", arguments, null, null);
    Output actual = mkdir.runCommand(input);
    assertEquals(actual.getOutputMessage(), null);
    assertEquals(actual.getException(), null);
    ArrayList<String> expected = new ArrayList<String>();
    expected.add("ValidPath1");
    expected.add("ValidPath2");
    expected.add("ValidPath3");
    assertEquals(fs.getDirectories(), expected);
  }

  /**
   * Test if mkdir successfully creates two directories where the second directory being created
   * successfully is reliant on the first directory being created successfully
   */
  @Test
  public void testRunCommandMakeMultipleDirectoriesInvalidName() {
    String arguments[] = {"ValidPath1", "ValidPath2", "InvalidName", "ValidPath3"};
    input = new MockInputProcessor("mkdir", arguments, null, null);
    Output actual = mkdir.runCommand(input);
    assertEquals(actual.getOutputMessage(), null);
    assertEquals(actual.getException().getMessage(),
        "Could not create directory at InvalidName because InvalidName has an invalid name but was "
            + "able to create all directories before it");
    assertTrue(actual.getException() instanceof InvalidNameException);
    ArrayList<String> expected = new ArrayList<String>();
    expected.add("ValidPath1");
    expected.add("ValidPath2");
    assertEquals(fs.getDirectories(), expected);
  }

  /**
   * Test if mkdir successfully creates two directories where the second directory being created
   * successfully is reliant on the first directory being created successfully
   */
  @Test
  public void testRunCommandMakeMultipleDirectoriesInvalidPath() {
    String arguments[] = {"ValidPath1", "ValidPath2", "InvalidPath", "ValidPath3"};
    input = new MockInputProcessor("mkdir", arguments, null, null);
    Output actual = mkdir.runCommand(input);
    assertEquals(actual.getOutputMessage(), null);
    assertEquals(actual.getException().getMessage(),
        "Could not create directory at InvalidPath because InvalidPath is an invalid path but was "
            + "able to create all directories before it");
    assertTrue(actual.getException() instanceof InvalidPathException);
    ArrayList<String> expected = new ArrayList<String>();
    expected.add("ValidPath1");
    expected.add("ValidPath2");
    assertEquals(fs.getDirectories(), expected);
  }

  /**
   * Test if mkdir successfully creates two directories where the second directory being created
   * successfully is reliant on the first directory being created successfully
   */
  @Test
  public void testRunCommandMakeMultipleDirectoriesItemAlreadyExists() {
    String arguments[] = {"ValidPath1", "ValidPath2", "PathOfExistingItem", "ValidPath3"};
    input = new MockInputProcessor("mkdir", arguments, null, null);
    Output actual = mkdir.runCommand(input);
    assertEquals(actual.getOutputMessage(), null);
    assertEquals(actual.getException().getMessage(),
        "Could not create directory at PathOfExistingItem because item already exists at "
            + "PathOfExistingItem but was able to create all directories before it");
    assertTrue(actual.getException() instanceof ItemAlreadyExistsException);
    ArrayList<String> expected = new ArrayList<String>();
    expected.add("ValidPath1");
    expected.add("ValidPath2");
    assertEquals(fs.getDirectories(), expected);
  }

}
