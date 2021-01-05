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
package driver;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * The class stores all user inputs
 * 
 * @author Man Hei Ho
 */
@SuppressWarnings("serial")
public class InputStorage implements Serializable {

  /**
   * This is an ArrayList storing all user inputs
   */
  private ArrayList<String> userInputHistory;

  /**
   * Class constructor
   */
  public InputStorage() {
    this.userInputHistory = new ArrayList<String>();
  }

  /**
   * This method add and store a user input
   * 
   * @param userInput This is a String of user input
   */
  public void addUserInput(String userInput) {
    if (userInput != null && !userInput.trim().isEmpty()) {
      this.userInputHistory.add(userInput);
    }
  }

  /**
   * This methods returns a list of all user input at the time the method is called
   * 
   * @return an ArrayList of all user input
   */
  @SuppressWarnings("unchecked")
  public ArrayList<String> getUserInputHistory() {
    return (ArrayList<String>) this.userInputHistory.clone();
  }
}
