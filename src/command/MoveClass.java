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
import exception.*;
import filesystem.*;
import output.*;

/**
 * This class implements the mv command and move all the contents in the oldPath to a newPath. All
 * the contents at oldPath will be removed from the file system
 * 
 * @author Man Hei Ho
 */
public class MoveClass extends CopyClass {

  /**
   * Class constructor
   * 
   * @param fileSystem This is a FileSystem object that storing all files and directories
   * @param valiGate This is a ValidationGate object that validates user input
   */
  public MoveClass(FileSystem fileSystem, ValidationGate valiGate) {
    super(fileSystem, valiGate);
  }

  /**
   * This method executes the mv command and move all contents in the old path to a new path
   * 
   * @param userInput This contains the parsed user input
   * @return An Output object representing the output from the command class
   */
  public Output runCommand(InputProcessor userInput) {
    Output output = new Output(null, null);

    if (userInput.getArgument() == null) {
      // no argument
      output.setException(new InvalidArgumentException("mv takes two pathnames as argument."));
    } else if (userInput.getArgument().length != 2) {
      // not two argument
      output.setException(new InvalidArgumentException("mv takes two pathnames as argument."));
    } else if (!super.getValiGate().isValidPathname(userInput.getArgument()[0])) {
      // first path not valid
      output.setException(
          new InvalidPathException(userInput.getArgument()[0] + " is not a valid pathname."));
    } else if (!super.getValiGate().isValidPathname(userInput.getArgument()[1])) {
      // second path not valid
      output.setException(
          new InvalidPathException(userInput.getArgument()[1] + " is not a valid pathname."));
    } else {
      return this.move(userInput.getArgument()[0], userInput.getArgument()[1]);
    }

    return output;
  }

  /**
   * This method moves all content in old path to new path. This method uses helper method
   * handleMoveFile if old path refers to an existing file in the file system. This method uses
   * helper method handleMoveDir if old path refers to an existing directory in the file system.
   * 
   * @param oldPath This is the old path to be moved
   * @param newPath This is the new path to move the old path to
   * @return An Output object representing the output from the command class
   */
  private Output move(String oldPath, String newPath) {
    Output output = new Output(null, null);

    File findFile = null;
    try {
      findFile = super.getFileSystem().findFile(oldPath);
      return this.handleMoveFile(findFile, newPath);
    } catch (InvalidPathException e) {
    } // file does not exists; ignore

    Directory findDir = null;
    try {
      findDir = super.getFileSystem().findDirectory(oldPath);
      return this.handleMoveDir(findDir, newPath);
    } catch (InvalidPathException e) {
    } // directory does not exist; ignore

    // cannot find file nor directory on oldPath
    output.setException(new InvalidPathException(
        oldPath + " does not refer to an existing item in the file system."));

    return output;
  }

  /**
   * This method moves oldFile to a newPath. If newPath refers to an existing file in file system,
   * the content in newPath will be overwritten to be the content in oldFile. If newPath refers to
   * an existing directory in the file system, oldFile will be moved into the directory at newPath.
   * oldFile will be removed from the file system if oldFile is moved to newPath successfully.
   * 
   * @param oldFile The file at oldPath
   * @param newPath This is the new path to copy the file to
   * @return An Output object representing the output from the command class
   */
  protected Output handleMoveFile(File oldFile, String newPath) {
    Output output = new Output(null, null);

    // copy first
    output = super.handleCopyFile(oldFile, newPath);

    if (output.getException() == null) { // successfully copied
      oldFile.getParent().remove(oldFile.getName()); // remove oldPath if successful
    }

    return output;
  }

  /**
   * This method moves oldDir to a newPath. If newPath refers to an existing file in file system, an
   * exception will be returned in output. If newPath refers to an existing directory in the file
   * system, oldDir will be moved into the directory at newPath. oldDir will be removed from the
   * file system if oldDir is moved to newPath successfully.
   * 
   * @param oldDir The directory at oldDir
   * @param newPath This is the new path to copy the directory to
   * @return An Output object representing the output from the command class
   */
  protected Output handleMoveDir(Directory oldDir, String newPath) {
    Output output = new Output(null, null);

    Directory currentDir = super.getFileSystem().getCurrentDirectory(); // get current directory
    if (currentDir.getPath().equals(oldDir.getPath())) { // if oldPath is current directory
      output.setException(new InvalidPathException("Cannot move current working directory."));
      return output;
    } else if (oldDir.hasChild(currentDir)) { // if oldPath is a parent of current directory
      output.setException(
          new InvalidPathException("Cannot move any parent of the current working directory."));
      return output;
    }

    // copy first
    output = super.handleCopyDir(oldDir, newPath);
    if (output.getException() == null) { // successfully copied
      try {
        super.getFileSystem().removeDirectory(oldDir.getPath()); // remove oldPath if successful
      } catch (InvalidPathException e) {
        output.setException(e);
      }
    }

    return output;
  }
}
