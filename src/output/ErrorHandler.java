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

package output;

/**
 * ErrorHandler
 * 
 * This class will handle the exception message
 * 
 * @author Yuanyuan Li
 *
 */
public class ErrorHandler {

  /**
   * Class Constructor
   */
  public ErrorHandler() {

  }

  /**
   * This method will return a string that contains the exception message
   * 
   * @param e Exception
   * @return The message in e
   */
  public String getErrorMessage(Exception e) {
    if (e == null)
      return null;
    return e.getMessage();
  }

  /**
   * Method overload (GetErrorMessage)
   * 
   * @param e String
   * @return The res
   */
  public String getErrorMessage(String e) {
    if (e == null)
      return null;
    return e;
  }

  /**
   * This method will print the exception message
   * 
   * @param e Exception
   */
  public void printErrorMessage(Exception e) {
    if (e == null)
      return;
    System.setErr(System.out);
    System.err.println(e);
  }

  /**
   * Method overload (PrintErrorMessage)
   * 
   * @param e String
   */
  public void printErrorMessage(String e) {
    if (e == null)
      return;
    System.setErr(System.out);
    System.err.println(e);
  }
}
