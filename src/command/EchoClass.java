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

package command;

import output.*;
import driver.*;
import exception.*;

/**
 * This class implement the echo command and output String to shell or files
 * 
 * @author Yuanyuan Li
 */
public class EchoClass extends Command {

  /**
   * Class constructor
   */
  public EchoClass() {

  }

  /**
   * This method executes the echo command and output the STRING to shell or file
   * 
   * @param userInput This is an InputProcessor object that contains the parsed user input
   * @throws InvalidArgumentException
   * @throws RedirectionException
   */
  public Output runCommand(InputProcessor userInput) {
    try {
      ValidationGate valiGate = new ValidationGate();
      String[] argument = userInput.getArgument();
      // check whether takes an argument
      if (argument == null || argument.length == 0) {
        throw new InvalidArgumentException("Invalid argument: no string provided");
      }
      // check if there is correct number of argument
      else if (argument.length != 1) {
        throw new InvalidArgumentException(
            "Invalid argument: echo should take no more than one string as an argument");
      }
      // check whether the argument is a valid string
      else if (!valiGate.isValidString(argument[0])) {
        throw new InvalidArgumentException("Invalid argument: invalid string");
      }
      // output String
      else {
        String outputMes = argument[0].substring(1, argument[0].length() - 1);
        Output output = new Output(outputMes, null);
        return output;
      }
    }
    // catch exception
    catch (InvalidArgumentException e) {
      Output output = new Output(null, e);
      return output;
    }
  }
}
