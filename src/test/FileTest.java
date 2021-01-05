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
import filesystem.File;

/**
 * JUnit test for File class
 * 
 * @author Luoliang Cai
 */
public class FileTest {
  private File testFile;

  @Before
  public void setUp() {
    testFile = new File("testFile", "/testfile", "some text");
  }

  /**
   * Test if append method successfully appends text to end of a file
   */
  @Test
  public void testAppendText() {
    testFile.appendText(" more text");
    assertEquals(testFile.getText(), "some text more text");
  }

  /**
   * Test if overwrite method successfully overwrites text in file with new text
   */
  @Test
  public void testOverwriteText() {
    testFile.overwriteText("new text");
    assertEquals(testFile.getText(), "new text");
  }

  @After
  public void tearDown() {
    testFile = null;
  }

}
