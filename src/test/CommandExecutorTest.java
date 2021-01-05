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
import driver.CommandExecutor;
import driver.InputProcessor;
import driver.ValidationGate;
import exception.InvalidCommandException;

/**
 * This is a JUnit test class for CommandExecutor
 * 
 * @author Man Hei Ho
 */
public class CommandExecutorTest {

  private CommandExecutor executor;

  @Before
  public void setup() {
    ValidationGate valiGate = new MockValidationGate();
    this.executor = new CommandExecutor(valiGate, null, null, null, null, null);
  }

  /**
   * Test CommandExecutor runCommand() to run invalid command
   */
  @Test
  public void testRunCommandToRunInvalidCommand() {
    InputProcessor userInput = new MockInputProcessor("InvalidCommand", null, null, null);
    try {
      this.executor.runCommand(userInput);
    } catch (InvalidCommandException e) {
      String expectedError = "InvalidCommand is not a valid command.";
      assertEquals(expectedError, e.getMessage());
      return;
    } catch (Exception e) {
      fail("should catch InvalidCommandException");
      return;
    }
    fail("should catch InvalidCommandException");
  }

  /**
   * Test CommandExecutor runCommand() to run valid command
   */
  @Test
  public void testRunCommandToRunValidCommand() {
    InputProcessor userInput = new MockInputProcessor("ValidCommand", null, null, null);
    try {
      this.executor.runCommand(userInput);
    } catch (Exception e) {
      fail("should not catch any exception.");
      return;
    }
  }
}
