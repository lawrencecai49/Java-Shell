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
 * This class holds the output (output message and/or exception) of a command.
 * 
 * @author Man Hei Ho
 */
public class Output {

  /**
   * This is the output message of a command
   */
  private String outputMessage;

  /**
   * This is the exception caught during the execution of the command
   */
  private Exception exception;

  /**
   * Class constructor
   * 
   * @param message This is the output message of a command; null if no output message
   * @param e This is an exception caught during the execution of the command; null if no exception
   */
  public Output(String message, Exception e) {
    this.outputMessage = message;
    this.exception = e;
  }


  /**
   * This method gets the output message of a command
   * 
   * @return a String representing the output message; null if there is no output message
   */
  public String getOutputMessage() {
    return this.outputMessage;
  }


  /**
   * This method returns the exception caught during the execution of the command
   * 
   * @return an Exception object; null if there is no exception caught
   */
  public Exception getException() {
    return this.exception;
  }


  /**
   * This method set the output message
   * 
   * @param outputMessage This is a String representing the output message; set null if there is no
   *        output message
   */
  public void setOutputMessage(String outputMessage) {
    this.outputMessage = outputMessage;
  }


  /**
   * This method set the exception
   * 
   * @param e this is the exception caught during the execution of the command; set null if there is
   *        no exception
   */
  public void setException(Exception e) {
    this.exception = e;
  }

}
