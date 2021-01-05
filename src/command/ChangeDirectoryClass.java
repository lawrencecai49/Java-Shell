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

import driver.InputProcessor;
import exception.InvalidArgumentException;
import exception.InvalidPathException;
import filesystem.Directory;
import filesystem.FileSystem;
import output.Output;

/**
 * This class changes the current working directory of the file system
 * 
 * @author Luoliang Cai
 */
public class ChangeDirectoryClass extends Command {
  private FileSystem system;

  /**
   * Creates an instance of the Change directory command
   * 
   * @param fileSystem The File system that cd will act upon
   */
  public ChangeDirectoryClass(FileSystem fileSystem) {
    this.system = fileSystem;
  }

  /**
   * Changes the current directory to directory located at path, if the path does not lead to a
   * directory, current directory remains unchanged
   * 
   * @param userInput The parsed command
   * @return an Output instance that holds any printable output and error messages
   */
  public Output runCommand(InputProcessor userInput) {
    // check if command is run with correct number of arguments
    if (userInput.getArgument() == null || userInput.getArgument().length != 1) {
      InvalidArgumentException e =
          new InvalidArgumentException(userInput.getCommand() + " must take 1 argument");
      return new Output(null, e);
    }
    try {
      Directory newCurrentDirectory = this.system.findDirectory(userInput.getArgument()[0]);
      this.system.setCurrentDirectory(newCurrentDirectory);
      return new Output(null, null);
    } catch (InvalidPathException e) {
      InvalidPathException f =
          new InvalidPathException("Could not change working directory because " + e.getMessage());
      return new Output(null, f);
    }
  }
}
