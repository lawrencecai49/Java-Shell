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
import org.junit.Before;
import org.junit.Test;
import driver.InputStorage;

/**
 * JUnit test for InputStorage
 * 
 * @author Man Hei Ho
 */
public class InputStorageTest {

  private InputStorage inputHistory;

  @Before
  public void setup() {
    this.inputHistory = new InputStorage();
  }

  /**
   * Test InputStorage getUserInputHistory() with empty storage
   */
  @Test
  public void testGetUserInputHistoryOnEmptyStorage() {
    ArrayList<String> test = new ArrayList<String>();
    assertEquals(test, this.inputHistory.getUserInputHistory());
  }

  /**
   * Test InputStorage addUserInput() and getUserInputHistory() with one entry in storage
   */
  @Test
  public void testAddUserInputAndGetUserInputHistoryGivenOneInput() {
    ArrayList<String> test = new ArrayList<String>();
    test.add("random user input");

    this.inputHistory.addUserInput("random user input");

    assertEquals(test, this.inputHistory.getUserInputHistory());
  }

  /**
   * Test InputStorage addUserInput() and getUserInputHistory() with more than one entry in storage
   */
  @Test
  public void testAddUserInputAndGetUserInputHistoryGivenMultipleInput() {
    ArrayList<String> test = new ArrayList<String>();
    test.add("random user inputq duhf  ");
    test.add("   random user input2");
    test.add("   random user      input3");
    test.add(" random user    input4   ");

    this.inputHistory.addUserInput("random user inputq duhf  ");
    this.inputHistory.addUserInput("   random user input2");
    this.inputHistory.addUserInput("   random user      input3");
    this.inputHistory.addUserInput(" random user    input4   ");

    assertEquals(test, this.inputHistory.getUserInputHistory());
  }

}
