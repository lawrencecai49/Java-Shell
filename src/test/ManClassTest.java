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
import command.ManClass;
import output.*;

/**
 * This is a JUnit test for ManClass
 * 
 * @author Yuanyuan Li
 */
public class ManClassTest {
  private ManClass man;
  private Output output;
  private final String[] allCommands = {"exit", "mkdir", "cd", "ls", "pwd", "pushd", "popd",
      "history", "cat", "echo", "man", "tree", "curl", "search", "cp", "mv", "rm"};

  @Before
  public void setUp() {
    this.man = new ManClass();
    this.output = new Output(null, null);
  }

  @Test
  /**
   * This test is for the valid command
   */
  public void validCommand() {
    for (int i = 0; i < this.allCommands.length; i++) {
      String[] argument = {this.allCommands[i]};
      MockInputProcessor userInput = new MockInputProcessor("man", argument, null, null);
      this.output = this.man.runCommand(userInput);
      assertEquals(null, this.output.getException());
    }
  }

  @Test
  /**
   * This test is for the invalid command
   */
  public void invalidCommand() {
    String[] argument = {"invalidCommand"};
    MockInputProcessor userInput = new MockInputProcessor("man", argument, null, null);
    this.output = this.man.runCommand(userInput);
    assertEquals("Error: Documentation not found, not a valid command",
        this.output.getException().getMessage());
    assertEquals(null, output.getOutputMessage());
  }

  @Test
  /**
   * This test is for more than one arguments
   */
  public void moreArgument() {
    String[] argument = {"cmd1", "cmd2"};
    MockInputProcessor userInput = new MockInputProcessor("man", argument, null, null);
    this.output = this.man.runCommand(userInput);
    assertEquals("Invalid argument: man command only takes one argument",
        this.output.getException().getMessage());
    assertEquals(null, this.output.getOutputMessage());
  }

  @Test
  /**
   * This test is for no argument
   */
  public void lessArgument() {
    MockInputProcessor userInput = new MockInputProcessor("man", null, null, null);
    this.output = this.man.runCommand(userInput);
    assertEquals("Invalid argument: no argument provide", this.output.getException().getMessage());
    assertEquals(null, this.output.getOutputMessage());
  }
}
