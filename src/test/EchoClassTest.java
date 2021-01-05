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
import command.EchoClass;
import output.*;

/**
 * This is a JUnit test for EchoClass class
 * 
 * @author Yuanyuan Li
 */
public class EchoClassTest {
  private EchoClass echo;
  private Output output;

  @Before
  public void setUp() throws Exception {
    this.echo = new EchoClass();
    this.output = new Output(null, null);
  }

  @Test
  /**
   * This test is for valid command line
   */
  public void validCommandLine() {
    String[] argument = {"\"text text\""};
    MockInputProcessor userInput = new MockInputProcessor("echo", argument, null, null);
    this.output = this.echo.runCommand(userInput);
    assertEquals(null, this.output.getException());
    assertEquals("text text", this.output.getOutputMessage());
  }

  @Test
  /**
   * This test is for invalid string
   */
  public void invalidString() {
    String[] argument = {" \\\"i.n!va)lidS%$t~ring"};
    MockInputProcessor userInput = new MockInputProcessor("echo", argument, null, null);
    this.output = this.echo.runCommand(userInput);
    assertEquals(null, this.output.getOutputMessage());
    assertEquals("Invalid argument: invalid string", this.output.getException().getMessage());
  }

  @Test
  /**
   * This test is for no argument
   */
  public void noArgument() {
    MockInputProcessor userInput = new MockInputProcessor("echo", null, null, null);
    this.output = this.echo.runCommand(userInput);
    assertEquals(null, this.output.getOutputMessage());
    assertEquals("Invalid argument: no string provided", this.output.getException().getMessage());
  }

  @Test
  /**
   * This test is for more than one arguments
   */
  public void moreArgument() {
    String[] argument = {"arg1", "arg2"};
    MockInputProcessor userInput = new MockInputProcessor("echo", argument, null, null);
    this.output = this.echo.runCommand(userInput);
    assertEquals(null, this.output.getOutputMessage());
    assertEquals("Invalid argument: echo should take no more than one string as an argument",
        this.output.getException().getMessage());
  }

}
