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

import driver.*;
import exception.*;
import filesystem.*;

/**
 * This class processes and redirects output messages
 * 
 * @author Man Hei Ho
 */
public class RedirectionProcessor {

  /**
   * This is a file system storing all files and directories contents
   */
  private FileSystem fileSystem;


  /**
   * This is a ValidationGate object that validates user input
   */
  private ValidationGate valiGate;

  /**
   * Class constructor
   * 
   * @param fileSystem This is a file system storing all files and directories contents
   */
  public RedirectionProcessor(FileSystem fileSystem, ValidationGate valiGate) {
    this.fileSystem = fileSystem;
    this.valiGate = valiGate;
  }

  /**
   * This method takes an output message and user input, and based on the user input, processes and
   * redirects the output message. It throws an exception if the output message or the redirection
   * operator or redirection pathname is not valid.
   * 
   * @param outputMessage This is a String of output message
   * @param userInput This object contains the parsed user input
   * @throws RedirectionException when output message or the redirection operator or redirection
   *         pathname is not valid
   * @throws InvalidPathException
   * @throws ItemAlreadyExistsException
   * @throws InvalidNameException
   */
  public void redirectOutputMessage(String outputMessage, InputProcessor userInput)
      throws RedirectionException, InvalidNameException, ItemAlreadyExistsException,
      InvalidPathException {

    // return if command does not support redirection
    if (!this.valiGate.isValidToRedirect(userInput.getCommand()))
      return;

    if (userInput.getRedirectionOperator() == null || userInput.getRedirectionPathname() == null)
      throw new RedirectionException("Unable to redirect.");

    if (!this.valiGate.isValidPathname(userInput.getRedirectionPathname()))
      throw new RedirectionException(
          userInput.getRedirectionPathname() + " is not a valid pathname");

    // check redirection operator
    if (userInput.getRedirectionOperator().equals(">")) {
      // overwrite
      this.overwriteToFile(outputMessage, userInput.getRedirectionPathname());
    } else if (userInput.getRedirectionOperator().equals(">>")) {
      // append
      this.appendToFile(outputMessage, userInput.getRedirectionPathname());
    } else {
      throw new RedirectionException(
          userInput.getRedirectionOperator() + " is not a valid redirection operator.");
    }
  }

  /**
   * This method takes an output message and overwrite that message to the file specified by the
   * pathname if file exists; creates a new file otherwise
   * 
   * @param outputMessage This is a String of output message
   * @param pathname This is the pathname representing the redirection location
   * @throws InvalidPathException
   * @throws ItemAlreadyExistsException
   * @throws InvalidNameException
   */
  private void overwriteToFile(String outputMessage, String pathname)
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    try {
      // find file
      File file = this.fileSystem.findFile(pathname);
      if (outputMessage == null) {
        file.overwriteText("");
      } else {
        file.overwriteText(outputMessage);
      }
    } catch (InvalidPathException e) {
      this.createAndWriteToNewFile(outputMessage, pathname);
    }
  }

  /**
   * This method takes an output message and append that message to the file specified by the
   * pathname if file exists; creates a new file otherwise
   * 
   * @param outputMessage This is a String of output message
   * @param pathanme This is the pathname representing the redirection location
   * @throws InvalidPathException
   * @throws ItemAlreadyExistsException
   * @throws InvalidNameException
   */
  private void appendToFile(String outputMessage, String pathname)
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    try {
      // find file
      File file = this.fileSystem.findFile(pathname);
      if (outputMessage != null && !outputMessage.isEmpty()) {
        if (file.getText().isEmpty()) {
          file.overwriteText(outputMessage);
        } else {
          file.overwriteText(file.getText() + "\n" + outputMessage);
        }
      }
    } catch (InvalidPathException e) {
      this.createAndWriteToNewFile(outputMessage, pathname);
    }
  }

  /**
   * This method creates a new file with the given pathname and writes the output message to the new
   * file
   * 
   * @param outputMessage This is a String of output message
   * @param pathanme This is the pathname representing the redirection location
   * @throws InvalidNameException
   * @throws ItemAlreadyExistsException
   * @throws InvalidPathException
   */
  private void createAndWriteToNewFile(String outputMessage, String pathname)
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    if (outputMessage == null) {
      this.fileSystem.makeFile(pathname, "");
    } else {
      this.fileSystem.makeFile(pathname, outputMessage);
    }
  }
}
