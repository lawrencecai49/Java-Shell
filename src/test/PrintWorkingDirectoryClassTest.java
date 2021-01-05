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
import command.PrintWorkingDirectoryClass;
import exception.InvalidArgumentException;
import output.Output;

/**
 * JUnit test for PrintWorkingDirectoryClass
 * 
 * @author Luoliang Cai
 */
public class PrintWorkingDirectoryClassTest {
  private MockFileSystem fs;
  private PrintWorkingDirectoryClass pwd;
  private MockInputProcessor input;

  /**
   * Initialize the mock file system and initialize the command to act upon the mock file system
   */
  @Before
  public void setUp() throws Exception {
    fs = new MockFileSystem();
    pwd = new PrintWorkingDirectoryClass(fs);
  }

  @After
  public void tearDown() throws Exception {
    fs = null;
    pwd = null;
    input = null;
  }

  /**
   * Test if correct exception is thrown when using any arguments
   */
  @Test
  public void testRunCommandWithArgument() {
    String argument[] = {"argument"};
    input = new MockInputProcessor("pwd", argument, null, null);
    Output actual = pwd.runCommand(input);
    assertEquals(actual.getOutputMessage(), null);
    assertTrue(actual.getException() instanceof InvalidArgumentException);
    assertEquals(actual.getException().getMessage(), "pwd can not take any arguments");
  }

  /**
   * Test if command executes properly when given no arguments
   */
  @Test
  public void testRunCommandNoArguments() throws Exception {
    input = new MockInputProcessor("pwd", null, null, null);
    Output returned = pwd.runCommand(input);
    assertEquals(returned.getException(), null);
    assertEquals(returned.getOutputMessage(), "currDir");
  }

}
