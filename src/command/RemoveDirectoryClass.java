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

import driver.*;
import filesystem.*;
import output.*;
import exception.*;

/**
 * This class implements the rm command and remove an existing directory in the file system
 * 
 * @author Man Hei Ho
 */
public class RemoveDirectoryClass extends Command {

  /**
   * This is a FileSystem object that storing all files and directories
   */
  private FileSystem fileSystem;

  /**
   * This is a ValidationGate object that validates user input
   */
  private ValidationGate valiGate;

  /**
   * Class constructor
   * 
   * @param fileSystem This is a FileSystem object that storing all files and directories
   * @param valiGate This is a ValidationGate object that validates user input
   */
  public RemoveDirectoryClass(FileSystem fileSystem, ValidationGate valiGate) {
    this.fileSystem = fileSystem;
    this.valiGate = valiGate;
  }

  /**
   * This method executes the rm command and removes an existing directory
   * 
   * @param userInput This contains the parsed user input
   * @return An Output object representing the output from the command class
   */
  public Output runCommand(InputProcessor userInput) {
    Output output = new Output(null, null);

    if (userInput.getArgument() == null) {
      // no argument
      output.setException(
          new InvalidArgumentException("rm takes one directory pathname as argument."));
    } else if (userInput.getArgument().length != 1) {
      // not one argument
      output.setException(
          new InvalidArgumentException("rm takes one directory pathname as argument."));
    } else if (!this.valiGate.isValidPathname(userInput.getArgument()[0])) {
      // invalid pathname
      output.setException(
          new InvalidPathException(userInput.getArgument()[0] + " is not a valid pathname."));
    } else {
      return this.removeDir(userInput.getArgument()[0]);
    }

    return output;
  }

  /**
   * This method removes the directory at pathname if pathname is referring to an existing directory
   * at the file system
   * 
   * @param pathname The directory to be removed
   * @return An Output object representing the output from the command class
   */
  private Output removeDir(String pathname) {
    Output output = new Output(null, null);

    Directory dirToRemove = null;
    try {
      dirToRemove = this.fileSystem.findDirectory(pathname); // find directory to delete

      if (dirToRemove.getPath().equals("/")) {
        output.setException(new InvalidPathException("Cannot delete root directorry."));
        return output;
      }

      Directory currentDir = this.fileSystem.getCurrentDirectory(); // find current directory
      if (currentDir.getPath().equals(dirToRemove.getPath())) { // cannot delete current directory
        output.setException(new InvalidPathException("Cannot delete current working directory."));
      } else if (dirToRemove.hasChild(currentDir)) { // cannot delete parent of current directory
        output.setException(new InvalidPathException(pathname
            + " cannot be deleted because it is one of the parents of the current directory."));
      } else {
        this.fileSystem.removeDirectory(pathname); // delete directory
      }
    } catch (InvalidPathException e) { // directory not found
      output.setException(e);
    }

    return output;
  }
}
