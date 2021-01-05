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

import java.util.ArrayList;
import driver.InputStorage;

/**
 * This is a mock InputStorage class for JUnit testing
 * 
 * @author Man Hei Ho
 */
@SuppressWarnings("serial")
public class MockInputStorage extends InputStorage {

  private ArrayList<String> mockStorage;

  public MockInputStorage(ArrayList<String> inputHistory) {
    this.mockStorage = inputHistory;
  }

  public ArrayList<String> getUserInputHistory() {
    return this.mockStorage;
  }
}
