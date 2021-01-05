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

import driver.InputProcessor;
import exception.InvalidNameException;
import exception.InvalidPathException;
import exception.ItemAlreadyExistsException;
import exception.RedirectionException;

/**
 * This class processes output messages
 * 
 * @author: Man Hei Ho
 */

public class OutputProcessor {

  /**
   * This is a RedirectionProcessor object that redirects output messages
   */
  private RedirectionProcessor redirProcessor;

  /**
   * Class constructor
   * 
   * @param redirProcessor This is a RedirectionProcessor object that redirects output messages
   */
  public OutputProcessor(RedirectionProcessor redirProcessor) {
    this.redirProcessor = redirProcessor;
  }

  /**
   * This method takes an output message and user input, and based on user input, processes and
   * displays the message to shell or redirect the output message. The output message will be ending
   * in a new line character
   * 
   * @param outputMessage This is a String of output message
   * @param userInput This object contains the parsed user input
   * @throws InvalidPathException
   * @throws ItemAlreadyExistsException
   * @throws InvalidNameException
   * @throws RedirectionException
   */
  public void processOutputMessage(String outputMessage, InputProcessor userInput)
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException,
      RedirectionException {

    if (userInput.getRedirectionOperator() != null) {
      // redirect output
      this.redirProcessor.redirectOutputMessage(outputMessage, userInput);

    } else if (userInput.getRedirectionOperator() == null
        && userInput.getRedirectionPathname() == null && outputMessage != null
        && !outputMessage.isEmpty()) {
      // output the output message ending in a new line character to shell
      System.out.println(outputMessage);
    }
  }
}
