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
import org.junit.*;
import driver.InputProcessor;
import driver.ValidationGate;
import exception.*;
import filesystem.FileSystem;
import output.RedirectionProcessor;

/**
 * This is a JUnit test class for RedirectionProcessor class
 * 
 * @author Man Hei Ho
 */
public class RedirectionProcessorTest {

  private FileSystem fs;
  private ValidationGate vg;
  private RedirectionProcessor redirProcessor;

  @Before
  public void setup() {
    this.fs = new MockFileSystem();
    this.vg = new MockValidationGate();
    this.redirProcessor = new RedirectionProcessor(this.fs, this.vg);
  }

  /**
   * Test RedirectionProcessor redirectOutputMessage() to output for command that does not support
   * redirection (ie: does not have any output)
   */
  @Test
  public void testRedirectOutputMessageToOutputForInvalidCommmand() {
    InputProcessor input = new MockInputProcessor("invalid command", null, ">", "ValidPathname");
    try {
      this.redirProcessor.redirectOutputMessage("", input);
    } catch (Exception e) {
      fail("should not throw any exception.");
    }
    // should not create any file
    assertEquals(false, ((MockFileSystem) this.fs).files.contains("ValidPathname"));
  }

  /**
   * Test RedirectionProcessor redirectOutputMessage() to output with user missing redirection
   * operator
   */
  @Test
  public void testRedirectOutputMessageWithNoRedirectionOperator() {
    InputProcessor input =
        new MockInputProcessor("ValidCommandSupportRedirection", null, null, "ValidPathname");
    try {
      this.redirProcessor.redirectOutputMessage("", input);
    } catch (RedirectionException e) {
      String expectedError = "Unable to redirect.";
      assertEquals(expectedError, e.getMessage());
      return;
    } catch (Exception e) {
      fail("should catch RedirectionException");
      return;
    }
    fail("should catch RedirectionException");
  }

  /**
   * Test RedirectionProcessor redirectOutputMessage() to output with user missing redirection
   * pathname
   */
  @Test
  public void testRedirectOutputMessageWithNoRedirectionPathname() {
    InputProcessor input =
        new MockInputProcessor("ValidCommandSupportRedirection", null, ">", null);
    try {
      this.redirProcessor.redirectOutputMessage("", input);
    } catch (RedirectionException e) {
      String expectedError = "Unable to redirect.";
      assertEquals(expectedError, e.getMessage());
      return;
    } catch (Exception e) {
      fail("should catch RedirectionException");
      return;
    }
    fail("should catch RedirectionException");
  }

  /**
   * Test RedirectionProcessor redirectOutputMessage() to output with user entering an invalid
   * redirection operator
   */
  @Test
  public void testRedirectOutputMessageWithInvalidRedirectionOperator() {
    InputProcessor input =
        new MockInputProcessor("ValidCommandSupportRedirection", null, "<", "ValidPathname");
    try {
      this.redirProcessor.redirectOutputMessage("", input);
    } catch (RedirectionException e) {
      String expectedError = "< is not a valid redirection operator.";
      assertEquals(expectedError, e.getMessage());
      return;
    } catch (Exception e) {
      fail("should catch RedirectionException");
      return;
    }
    fail("should catch RedirectionException");
  }

  /**
   * Test RedirectionProcessor redirectOutputMessage() to output with user entering an invalid
   * redirection pathname
   */
  @Test
  public void testRedirectOutputMessageWithInvalidRedirectionPathname() {
    InputProcessor input =
        new MockInputProcessor("ValidCommandSupportRedirection", null, ">", "InvalidPathname");
    try {
      this.redirProcessor.redirectOutputMessage("", input);
    } catch (RedirectionException e) {
      String expectedError = "InvalidPathname is not a valid pathname";
      assertEquals(expectedError, e.getMessage());
      return;
    } catch (Exception e) {
      fail("should catch RedirectionException");
      return;
    }
    fail("should catch RedirectionException");
  }

  /**
   * Test RedirectionProcessor redirectOutputMessage() to overwrite to an existing file
   */
  @Test
  public void testRedirectOutputMessageToOverwriteExistingFile() {
    InputProcessor input =
        new MockInputProcessor("ValidCommandSupportRedirection", null, ">", "ExistingFilePath");
    String expectedString = "Overwrite this";
    try {
      this.redirProcessor.redirectOutputMessage(expectedString, input);
      assertEquals(expectedString, ((MockFileSystem) this.fs).lastModifiedFile.getText());
    } catch (Exception e) {
      fail("should catch any exception");
      return;
    }
  }

  /**
   * Test RedirectionProcessor redirectOutputMessage() to overwrite to a non-existing file
   */
  @Test
  public void testRedirectOutputMessageToOverwriteNonExistingFile() {
    InputProcessor input = new MockInputProcessor("ValidCommandSupportRedirection", null, ">",
        "NonexistingValidPathname");
    String expectedString = "Create and write this.";
    try {
      this.redirProcessor.redirectOutputMessage(expectedString, input);
      assertEquals("NonexistingValidPathname",
          ((MockFileSystem) this.fs).lastModifiedFile.getPath());
      assertEquals(expectedString, ((MockFileSystem) this.fs).lastModifiedFile.getText());
    } catch (Exception e) {
      fail("should catch any exception");
      return;
    }
  }

  /**
   * Test RedirectionProcessor redirectOutputMessage() to append an existing file
   */
  @Test
  public void testRedirectOutputMessageToAppendExistingFile() {
    InputProcessor input =
        new MockInputProcessor("ValidCommandSupportRedirection", null, ">>", "ExistingFilePath");
    String expectedString = "Create and write this.";
    try {
      this.redirProcessor.redirectOutputMessage(expectedString, input);
      assertEquals(expectedString, ((MockFileSystem) this.fs).lastModifiedFile.getText());
    } catch (Exception e) {
      fail("should catch any exception");
      return;
    }
  }

  /**
   * Test RedirectionProcessor redirectOutputMessage() to append an non-existing file
   */
  @Test
  public void testRedirectOutputMessageToAppendNonExistingValidFile() {
    InputProcessor input = new MockInputProcessor("ValidCommandSupportRedirection", null, ">>",
        "NonexistingValidPathname");
    String expectedString = "Create and write this.";
    try {
      this.redirProcessor.redirectOutputMessage(expectedString, input);
      assertEquals("NonexistingValidPathname",
          ((MockFileSystem) this.fs).lastModifiedFile.getPath());
      assertEquals(expectedString, ((MockFileSystem) this.fs).lastModifiedFile.getText());
    } catch (Exception e) {
      fail("should catch any exception");
      return;
    }
  }

}
