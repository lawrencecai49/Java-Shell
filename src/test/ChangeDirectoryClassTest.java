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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import command.ChangeDirectoryClass;
import exception.InvalidArgumentException;
import exception.InvalidPathException;
import output.Output;

/**
 * JUnit test for ChangeDirectoryClass class
 * 
 * @author Luoliang Cai
 */
public class ChangeDirectoryClassTest {
  private MockFileSystem fs;
  private ChangeDirectoryClass cd;
  private MockInputProcessor input;

  @Before
  public void setUp() {
    fs = new MockFileSystem();
    cd = new ChangeDirectoryClass(fs);
  }

  @After
  public void tearDown() {
    fs = null;
    cd = null;
    input = null;
  }

  /**
   * Test if cd generates correct exception when called with no arguments
   */
  @Test
  public void testRunCommandNoArguments() {
    input = new MockInputProcessor("cd", null, null, null);
    Output actual = cd.runCommand(input);
    assertEquals(actual.getOutputMessage(), null);
    assertTrue(actual.getException() instanceof InvalidArgumentException);
    assertEquals(actual.getException().getMessage(), "cd must take 1 argument");
  }

  /**
   * Test if cd generates correct exception when called with more than two arguments
   */
  @Test
  public void testRunCommandMoreThanTwoArguments() {
    String argument[] = {"path1", "path2"};
    input = new MockInputProcessor("cd", argument, null, null);
    Output actual = cd.runCommand(input);
    assertEquals(actual.getOutputMessage(), null);
    assertTrue(actual.getException() instanceof InvalidArgumentException);
    assertEquals(actual.getException().getMessage(), "cd must take 1 argument");
  }

  /**
   * Test if cd generates correct exception when asked to change to directory that either does not
   * exist or does not refer to a file
   */
  @Test
  public void testRunCommandInvalidPath() {
    String argument[] = {"InvalidPath"};
    input = new MockInputProcessor("cd", argument, null, null);
    Output actual = cd.runCommand(input);
    assertEquals(actual.getOutputMessage(), null);
    assertTrue(actual.getException() instanceof InvalidPathException);
    assertEquals(actual.getException().getMessage(), "Could not change working directory because "
        + "InvalidPath does not refer to an existing directory");
  }

  /**
   * Test if cd successfully changes the current directory when given a valid path
   */
  @Test
  public void testRunCommandCommandValidPath() {
    String argument[] = {"ValidPath"};
    input = new MockInputProcessor("cd", argument, null, null);
    Output actual = cd.runCommand(input);
    assertEquals(actual.getOutputMessage(), null);
    assertEquals(actual.getException(), null);
    assertEquals(fs.getCurrentDirectory().getName(), "ValidPath");
    assertEquals(fs.getCurrentDirectory().getPath(), "ValidPath");
  }
}
