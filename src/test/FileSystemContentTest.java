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
import filesystem.Directory;
import filesystem.FileSystemContent;

/**
 * JUnit test for FileSystemContent class
 * 
 * @author Luoliang Cai
 */
public class FileSystemContentTest {
  private FileSystemContent testContent;

  /**
   * Initialize and instance of FileSystemContent
   */
  @Before
  public void setUp() {
    testContent = new FileSystemContent("testContent", "root/testContent");
  }

  /**
   * test if any new file system content made has an empty parent directory along with the correct
   * name and path
   */
  @Test
  public void newContentTest() {
    assertEquals(testContent.getName(), "testContent");
    assertEquals(testContent.getPath(), "root/testContent");
    assertEquals(testContent.getParent(), null);
  }

  /**
   * Test if set parent successfully changes the directories parent
   */
  @Test
  public void setParentTest() {
    Directory parent = new Directory("newDir", "root");
    testContent.setParent(parent);
    assertEquals(testContent.getParent(), parent);
  }

  @After
  public void tearDown() {
    testContent = null;
  }
}
