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

import output.Output;
import driver.InputProcessor;
import exception.InvalidArgumentException;
import filesystem.FileSystem;

/**
 * This class sendss the absolute path of the current working directory to output
 * 
 * @author Luoliang Cai
 */
public class PrintWorkingDirectoryClass extends Command {

  private FileSystem fs;

  /**
   * Constructor that creates instance of pwd command
   * 
   * @param fileSystem The file system which the command will retrieve the working directory from
   */
  public PrintWorkingDirectoryClass(FileSystem fileSystem) {
    this.fs = fileSystem;
  }

  /**
   * Displays the absolute path of the current working directory
   * 
   * @param userInput The parsed userInput
   * @return an Output instance that holds any printable output and error messages
   */
  public Output runCommand(InputProcessor userInput) {
    // check if command is run with correct number of arguments
    if (userInput.getArgument() != null) {
      InvalidArgumentException e =
          new InvalidArgumentException(userInput.getCommand() + " can not take any arguments");
      return new Output(null, e);
    }
    return new Output(this.fs.getCurrentDirectory().getPath(), null);
  }
}
