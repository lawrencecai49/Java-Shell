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
import org.junit.BeforeClass;
import org.junit.Test;
import driver.ValidationGate;

/**
 * This class is a JUnit Test class for ValidationGate class that tests all its public methods.
 * 
 * @author Christina Ma
 *
 */
public class ValidationGateTest {

  private ValidationGate valiGate;

  @Before
  public void setUp() {
    this.valiGate = new ValidationGate();
  }

  /**
   * Test if isValidToRedirect method return true for commands that support redirection
   */
  @Test
  public void testValidRedirectionCommand() {
    assertEquals(true, valiGate.isValidToRedirect("cat"));
    assertEquals(true, valiGate.isValidToRedirect("ls"));
    assertEquals(true, valiGate.isValidToRedirect("tree"));
    assertEquals(true, valiGate.isValidToRedirect("history"));
    assertEquals(true, valiGate.isValidToRedirect("pwd"));
  }

  /**
   * Test if isValidToRedirect method return false for commands that don't support redirection
   */
  @Test
  public void testInValidRedirectionCommand() {
    // Given a valid command but does not support redirection
    assertEquals(false, valiGate.isValidToRedirect("cd"));
    assertEquals(false, valiGate.isValidToRedirect("mkdir"));
    assertEquals(false, valiGate.isValidToRedirect("pushd"));
    assertEquals(false, valiGate.isValidToRedirect("popd"));
    assertEquals(false, valiGate.isValidToRedirect("exit"));
    // Given random string values
    assertEquals(false, valiGate.isValidToRedirect("jfrjkgrlgnr"));
    assertEquals(false, valiGate.isValidToRedirect("kfok gii"));
  }

  /**
   * Test if isValidCommand method return true for given a valid command
   */
  @Test
  public void testValidCommand() {
    assertEquals(true, valiGate.isValidCommand("cd"));
    assertEquals(true, valiGate.isValidCommand("cp"));
    assertEquals(true, valiGate.isValidCommand("mv"));
    assertEquals(true, valiGate.isValidCommand("history"));
    assertEquals(true, valiGate.isValidCommand("search"));
    assertEquals(true, valiGate.isValidCommand("tree"));
    assertEquals(true, valiGate.isValidCommand("popd"));

  }

  /**
   * Test if isValidCommand method return false for the given invalid command
   */
  @Test
  public void testNotValidCommand() {
    assertEquals(false, valiGate.isValidCommand("")); // Empty string
    assertEquals(false, valiGate.isValidCommand("djgkrw")); // Random string of letters
    assertEquals(false, valiGate.isValidCommand(" ")); // Space
    // A single letter
    assertEquals(false, valiGate.isValidCommand("/"));
    assertEquals(false, valiGate.isValidCommand("a"));
    // Partially misspelled commands
    assertEquals(false, valiGate.isValidCommand("histor"));
    assertEquals(false, valiGate.isValidCommand("pop"));
    assertEquals(false, valiGate.isValidCommand(" pop"));
    assertEquals(false, valiGate.isValidCommand("pop "));
    // Letters in a valid command rearranged
    assertEquals(false, valiGate.isValidCommand("pdw"));
    // An extra added space to a valid command
    assertEquals(false, valiGate.isValidCommand(" cp"));
    assertEquals(false, valiGate.isValidCommand("cp "));
  }

  /**
   * Test if isValidPathname method return true for a pathname that contains valid characters
   */
  @Test
  public void testValidPathname() {
    // Full paths
    assertEquals(true, valiGate.isValidPathname("/dir1/dir2"));
    assertEquals(true, valiGate.isValidPathname("/dir1/dir2_3/"));
    assertEquals(true, valiGate.isValidPathname("/dir1/dir2/dir2/dir2_3/dir1"));

    // Relative paths
    assertEquals(true, valiGate.isValidPathname("dir1/file3__4"));
    assertEquals(true, valiGate.isValidPathname("dir1/dir2_3/"));
    assertEquals(true, valiGate.isValidPathname("dir1/dir2/dir2/dir2_3/dir1/__/"));
  }

  /**
   * Test if isValidPathname method return false for a pathname that contains illegal characters
   */
  @Test
  public void testInValidPathname() {
    // Illegal characters located at the ends
    assertEquals(false, valiGate.isValidPathname("//dir1/dir2"));
    assertEquals(false, valiGate.isValidPathname("/dir1/dir2!"));
    // Illegal characters located in the middle
    assertEquals(false, valiGate.isValidPathname("/dir1/@dir2_2//file__3"));
    assertEquals(false, valiGate.isValidPathname("/d{ir1/dir2_2/file__3"));
    assertEquals(false, valiGate.isValidPathname("/d{ir1/dir2_2/file__>3"));
    // Entirely made up of illegal characters
    assertEquals(false, valiGate.isValidPathname("///@$%&@@@$"));
    assertEquals(false, valiGate.isValidPathname("~"));
    // Illegal characters located at the ends and middle
    assertEquals(false, valiGate.isValidPathname("/!d{ir1/dir2_2/file__3"));
    assertEquals(false, valiGate.isValidPathname("/d{ir1/dir2_2/file__3?"));
    assertEquals(false, valiGate.isValidPathname("/d{ir1/&$%*2/file__3))"));
  }

  /**
   * Test if isValidString method return true for a given string surrounded by double quotations
   * within the string itself
   */
  @Test
  public void testValidString() {
    assertEquals(true, valiGate.isValidString("\" \"")); // Space
    assertEquals(true, valiGate.isValidString("\"\"")); // Empty string
    assertEquals(true, valiGate.isValidString("\"a\"")); // One character string

    // A string of characters
    assertEquals(true, valiGate.isValidString("\"There are words in a sentence\""));
    assertEquals(true, valiGate.isValidString("\"#@$%^&*(\""));
  }

  /**
   * Test if isValidString method return false for a given string not surrounded by double
   * quotations within the string itself
   */
  @Test
  public void testInValidString() {
    assertEquals(false, valiGate.isValidString("\"\"\"")); // One double quotation
    assertEquals(false, valiGate.isValidString("\"\"\"\"\"")); // Multiple quotations

  }

  /**
   * Test if isValidInteger method return true for a given string that can be converted into type
   * int
   */
  @Test
  public void testValidInteger() {
    assertEquals(true, valiGate.isValidInteger("5")); // Single digit
    assertEquals(true, valiGate.isValidInteger("138943")); // Large positive integer
    assertEquals(true, valiGate.isValidInteger("0")); // Zero
    assertEquals(true, valiGate.isValidInteger("-3")); // Negative value
    assertEquals(true, valiGate.isValidInteger("-7890")); // Large negative integer
  }

  /**
   * Test if isValidInteger method return false for a given string that cannot be converted into
   * type int
   */
  @Test
  public void testInValidInteger() {
    // Input a decimal numbers
    assertEquals(false, valiGate.isValidInteger("5.0"));
    assertEquals(false, valiGate.isValidInteger("0.4"));
    // Input a string of non numerical characters
    assertEquals(false, valiGate.isValidInteger("gsrkj"));
    assertEquals(false, valiGate.isValidInteger("a"));

    assertEquals(false, valiGate.isValidInteger("")); // Empty string
    assertEquals(false, valiGate.isValidInteger("0.4")); // Space
  }

  /**
   * Test if isValidDirectoryName method return true for a string that does not contain illegal
   * characters for naming files or directories
   */
  @Test
  public void testValidDirectoryName() {
    assertEquals(true, valiGate.isValidDirectoryName("folder1_1"));
    assertEquals(true, valiGate.isValidDirectoryName("__newfile1_"));
    assertEquals(true, valiGate.isValidDirectoryName("file-1"));
    assertEquals(true, valiGate.isValidDirectoryName("file\1\""));
    assertEquals(true, valiGate.isValidDirectoryName("[f]ile1"));
  }

  /**
   * Test if isValidDirectoryName method return false for a string that contains illegal characters
   * for naming files or directories
   */
  @Test
  public void testInValidDirectoryName() {
    // Illegal characters located at the ends
    assertEquals(false, valiGate.isValidDirectoryName("folder1/"));
    assertEquals(false, valiGate.isValidDirectoryName("!folder1"));
    assertEquals(false, valiGate.isValidDirectoryName("*folder1&"));
    // Illegal characters located in the middle
    assertEquals(false, valiGate.isValidDirectoryName("f@lder1"));
    assertEquals(false, valiGate.isValidDirectoryName("folde(r)1"));
    assertEquals(false, valiGate.isValidDirectoryName("file.txt"));
    // Entirely made up of illegal characters
    assertEquals(false, valiGate.isValidDirectoryName("@"));
    assertEquals(false, valiGate.isValidDirectoryName(")(*&^%$#.."));
    // Illegal characters located at the ends and middle
    assertEquals(false, valiGate.isValidDirectoryName("[f]@|der1(2)"));
    assertEquals(false, valiGate.isValidDirectoryName("!f!e2"));
    assertEquals(false, valiGate.isValidDirectoryName("f!le )"));
    // Used only illegal characters for pathnames
    assertEquals(false, valiGate.isValidDirectoryName("|}|~?"));
  }

  /**
   * Test if isValidFullPath method return true for a string that starts with "/"
   */
  @Test
  public void testValidFullPath() {
    // Full path with valid pathname and directory naming characters
    assertEquals(true, valiGate.isValidFullPath("/folder1_1/folder2/file3"));
    // Full path with illegal characters
    assertEquals(true, valiGate.isValidFullPath("//folder1_1!/folder||2/>/file3"));
    // Root
    assertEquals(true, valiGate.isValidFullPath("/"));
    // One folder
    assertEquals(true, valiGate.isValidFullPath("/folder1_1"));
  }

  /**
   * Test if isValidFullPath method return false for a string that does not start with "/"
   */
  @Test
  public void testInValidFullPath() {
    // Relative path with legal pathname and directory naming characters
    assertEquals(false, valiGate.isValidFullPath("folder1_1/folder_2/file9"));
    // Relative path to one folder
    assertEquals(false, valiGate.isValidFullPath("folder1_1"));
    // Relative path to 2 folders
    assertEquals(false, valiGate.isValidFullPath("f/older1_1"));
  }



}
