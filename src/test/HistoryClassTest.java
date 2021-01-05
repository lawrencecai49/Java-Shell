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
import org.junit.Test;
import command.HistoryClass;
import driver.InputProcessor;
import driver.InputStorage;
import driver.ValidationGate;
import output.Output;

/**
 * This is the JUnit test for HistoryClass
 * 
 * @author Man Hei Ho
 */
public class HistoryClassTest {

  /**
   * Test history runCommand() with no argument, and empty history
   */
  @Test
  public void testHistoryRunCommandWithNoArgumentWithEmptyStorage() {
    ArrayList<String> input = new ArrayList<String>();
    InputStorage inputHistory = new MockInputStorage(input);
    ValidationGate valiGate = new MockValidationGate();
    HistoryClass history = new HistoryClass(inputHistory, valiGate);
    InputProcessor userInput = new MockInputProcessor("history", null, null, null);
    Output output = history.runCommand(userInput);

    assertEquals("", output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * Test history runCommand() with no argument, and a non-empty history
   */
  @Test
  public void testHistoryRunCommandWithNoArgumentWithNonEmptyStorage() {
    ArrayList<String> input = new ArrayList<String>();
    input.add("test1");
    input.add("   test2");
    input.add("test3   ");
    input.add("    test4   ");
    input.add("    tes    t5   ");
    InputStorage inputHistory = new MockInputStorage(input);
    ValidationGate valiGate = new MockValidationGate();
    HistoryClass history = new HistoryClass(inputHistory, valiGate);
    InputProcessor userInput = new MockInputProcessor("history", null, null, null);
    Output output = history.runCommand(userInput);

    String expectedOutputString =
        "1. test1\n2.    test2\n3. test3   \n4.     test4   \n5.     tes    t5   ";

    assertEquals(expectedOutputString, output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * Test history runCommand() with one invalid argument
   */
  @Test
  public void testHistoryRunCommandWithOneInvalidArgument() {
    ArrayList<String> input = new ArrayList<String>();
    input.add("test1");
    input.add("   test2");
    input.add("test3   ");
    input.add("    test4   ");
    input.add("    tes    t5   ");
    InputStorage inputHistory = new MockInputStorage(input);
    ValidationGate valiGate = new ValidationGate();
    HistoryClass history = new HistoryClass(inputHistory, valiGate);
    String[] args = {"param"};
    InputProcessor userInput = new MockInputProcessor("history", args, null, null);
    Output output = history.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("Should throw exception.");
    String expectedErrorMessage = "history takes no argument or one number (>=0) as argument";
    assertEquals(expectedErrorMessage, output.getException().getMessage());
  }

  /**
   * Test history runCommand() with one negative integer
   */
  @Test
  public void testHistoryRunCommandWithNegativeInteger() {
    ArrayList<String> input = new ArrayList<String>();
    input.add("test1");
    input.add("   test2");
    input.add("test3   ");
    input.add("    test4   ");
    input.add("    tes    t5   ");
    InputStorage inputHistory = new MockInputStorage(input);
    ValidationGate valiGate = new ValidationGate();
    HistoryClass history = new HistoryClass(inputHistory, valiGate);
    String[] args = {"-1"};
    InputProcessor userInput = new MockInputProcessor("history", args, null, null);
    Output output = history.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("Should throw exception.");
    String expectedErrorMessage = "history takes no argument or one number (>=0) as argument";
    assertEquals(expectedErrorMessage, output.getException().getMessage());
  }

  /**
   * Test history runCommand() with zero as argument
   */
  @Test
  public void testHistoryRunCommandWithIntegerZero() {
    ArrayList<String> input = new ArrayList<String>();
    input.add("test1");
    input.add("   test2");
    input.add("test3   ");
    input.add("    test4   ");
    input.add("    tes    t5   ");
    InputStorage inputHistory = new MockInputStorage(input);
    ValidationGate valiGate = new ValidationGate();
    HistoryClass history = new HistoryClass(inputHistory, valiGate);
    String[] args = {"0"};
    InputProcessor userInput = new MockInputProcessor("history", args, null, null);
    Output output = history.runCommand(userInput);

    assertEquals("", output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * Test history runCommand() with a number that is out of the size of history
   */
  @Test
  public void testHistoryRunCommandWithOneValidOutOfBoundArgument() {
    ArrayList<String> input = new ArrayList<String>();
    input.add("test1");
    input.add("   test2");
    input.add("test3   ");
    input.add("    test4   ");
    input.add("    tes    t5   ");
    InputStorage inputHistory = new MockInputStorage(input);
    ValidationGate valiGate = new MockValidationGate();
    HistoryClass history = new HistoryClass(inputHistory, valiGate);
    String[] args = {"10"};
    InputProcessor userInput = new MockInputProcessor("history", args, null, null);
    Output output = history.runCommand(userInput);

    String expectedOutputString =
        "1. test1\n2.    test2\n3. test3   \n4.     test4   \n5.     tes    t5   ";

    assertEquals(expectedOutputString, output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * Test history runCommand() with a number that is within the size of history
   */
  @Test
  public void testHistoryRunCommandWithOneValidInBoundArgument() {
    ArrayList<String> input = new ArrayList<String>();
    input.add("test1");
    input.add("   test2");
    input.add("test3   ");
    input.add("    test4   ");
    input.add("    tes    t5   ");
    InputStorage inputHistory = new MockInputStorage(input);
    ValidationGate valiGate = new MockValidationGate();
    HistoryClass history = new HistoryClass(inputHistory, valiGate);
    String[] args = {"1"};
    InputProcessor userInput = new MockInputProcessor("history", args, null, null);
    Output output = history.runCommand(userInput);

    String expectedOutputString = "5.     tes    t5   ";

    assertEquals(expectedOutputString, output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * Test history runCommand() with a number that is the size of history
   */
  @Test
  public void testHistoryRunCommandWithArgumentEqualToSizeOfStorage() {
    ArrayList<String> input = new ArrayList<String>();
    input.add("test1");
    input.add("   test2");
    input.add("test3   ");
    input.add("    test4   ");
    input.add("    tes    t5   ");
    InputStorage inputHistory = new MockInputStorage(input);
    ValidationGate valiGate = new MockValidationGate();
    HistoryClass history = new HistoryClass(inputHistory, valiGate);
    String[] args = {"5"};
    InputProcessor userInput = new MockInputProcessor("history", args, null, null);
    Output output = history.runCommand(userInput);

    String expectedOutputString =
        "1. test1\n2.    test2\n3. test3   \n4.     test4   \n5.     tes    t5   ";

    assertEquals(expectedOutputString, output.getOutputMessage());
    assertEquals(null, output.getException());
  }

  /**
   * Test history runCommand() with more than one argument
   */
  @Test
  public void testHistoryRunCommandWithMoreThanOneArgument() {
    ArrayList<String> input = new ArrayList<String>();
    input.add("test1");
    input.add("   test2");
    input.add("test3   ");
    input.add("    test4   ");
    input.add("    tes    t5   ");
    InputStorage inputHistory = new MockInputStorage(input);
    ValidationGate valiGate = new ValidationGate();
    HistoryClass history = new HistoryClass(inputHistory, valiGate);
    String[] args = {"param1", "param2"};
    InputProcessor userInput = new MockInputProcessor("history", args, null, null);
    Output output = history.runCommand(userInput);

    assertEquals(null, output.getOutputMessage());

    if (output.getException() == null)
      fail("Should throw exception.");
    String expectedErrorMessage = "history takes no argument or one number (>=0) as argument";
    assertEquals(expectedErrorMessage, output.getException().getMessage());
  }
}
