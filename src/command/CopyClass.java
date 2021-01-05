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

import java.util.Iterator;
import driver.*;
import exception.*;
import filesystem.*;
import output.*;

/**
 * This class implements the cp command and copy all the contents in the old path to a new path
 * 
 * @author Man Hei Ho
 */
public class CopyClass extends Command {

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
  public CopyClass(FileSystem fileSystem, ValidationGate valiGate) {
    this.fileSystem = fileSystem;
    this.valiGate = valiGate;
  }

  /**
   * This method executes the cp command and copies all contents in the old path to a new path
   * 
   * @param userInput This contains the parsed user input
   * @return An Output object representing the output from the command class
   */
  public Output runCommand(InputProcessor userInput) {
    Output output = new Output(null, null);

    if (userInput.getArgument() == null) {
      // no argument
      output.setException(new InvalidArgumentException("cp takes two pathnames as argument."));
    } else if (userInput.getArgument().length != 2) {
      // not two argument
      output.setException(new InvalidArgumentException("cp takes two pathnames as argument."));
    } else if (!this.valiGate.isValidPathname(userInput.getArgument()[0])) {
      // first path is not valid
      output.setException(
          new InvalidPathException(userInput.getArgument()[0] + " is not a valid pathname."));
    } else if (!this.valiGate.isValidPathname(userInput.getArgument()[1])) {
      // second path is not valid
      output.setException(
          new InvalidPathException(userInput.getArgument()[1] + " is not a valid pathname."));
    } else {
      return this.copy(userInput.getArgument()[0], userInput.getArgument()[1]);
    }

    return output;
  }

  /**
   * This method copies all content in old path to new path. This method uses helper method
   * handleCopyFile if old path refers to an existing file in the file system. This method uses
   * helper method handleCopyDir if old path refers to an existing directory in the file system.
   * 
   * @param oldPath This is the old path to be copied
   * @param newPath This is the new path to copy the old path to
   * @return An Output object representing the output from the command class
   */
  protected Output copy(String oldPath, String newPath) {
    Output output = new Output(null, null);

    try {
      File findFile = this.fileSystem.findFile(oldPath);
      return this.handleCopyFile(findFile, newPath);
    } catch (InvalidPathException e) { // file does not exist; ignore
    }

    try {
      Directory findDir = this.fileSystem.findDirectory(oldPath);
      return this.handleCopyDir(findDir, newPath);
    } catch (InvalidPathException e) { // directory does not exist; ignore
    }

    // oldPath does not refer to any existing item in file system
    output.setException(new InvalidPathException(
        oldPath + " does not refer to an existing item in the file system"));
    return output;
  }

  /**
   * This method copies oldFile to a newPath. If newPath refers to an existing file in file system,
   * the content in newPath will be overwritten to be the content in oldFile. If newPath refers to
   * an existing directory in the file system, oldFile will be copied into the directory at newPath.
   * This method uses handleCopyFileToNonExistingPath if newPath does not refer to an existing item
   * in the file system.
   * 
   * @param oldFile The file at oldPath
   * @param newPath This is the new path to copy the file to
   * @return An Output object representing the output from the command class
   */
  protected Output handleCopyFile(File oldFile, String newPath) {
    Output output = new Output(null, null);

    try {
      File fileAtNewPath = this.fileSystem.findFile(newPath);
      if (fileAtNewPath.getPath().equals(oldFile.getPath())) {
        output.setException(new InvalidPathException("Cannot copy/move file to the same file."));
      } else {
        fileAtNewPath.overwriteText(oldFile.getText());
      }
      return output;
    } catch (InvalidPathException e) { // files does not exist at new path; ignore
    }

    try {
      Directory dirAtNewPath = this.fileSystem.findDirectory(newPath);
      try {
        this.copyContentToDir(oldFile, dirAtNewPath);
      } catch (InvalidNameException | ItemAlreadyExistsException | InvalidPathException e) {
        output.setException(e);
      }
      return output;
    } catch (InvalidPathException e) { // directory does not exist at new path; ignore
    }

    // newFile is a non-existing path
    return this.handleCopyFileToNonExistingPath(oldFile, newPath);
  }

  /**
   * This method copies oldDir to a newPath. If newPath refers to an existing file in file system,
   * an exception will be returned in output. If newPath refers to an existing directory in the file
   * system, oldDir will be copied into the directory at newPath. This method uses
   * handleCopyDirToNonExistingPath if newPath does not refer to an existing item in file system.
   * 
   * @param oldDir The directory at oldDir
   * @param newPath This is the new path to copy the directory to
   * @return An Output object representing the output from the command class
   */
  protected Output handleCopyDir(Directory oldDir, String newPath) {
    Output output = new Output(null, null);
    try {
      this.fileSystem.findFile(newPath);
      output.setException(new InvalidPathException(newPath + " is refering to an existing file."));
      return output;
    } catch (InvalidPathException e) { // file does not exist at new path; ignore
    }

    try {
      Directory dirAtNewPath = this.fileSystem.findDirectory(newPath);
      if (dirAtNewPath.getPath().equals(oldDir.getPath())) {
        output.setException(
            new InvalidPathException("Cannot copy/move directory to the same directory."));
      } else if (oldDir.hasChild(dirAtNewPath)) {
        output.setException(
            new InvalidPathException("Cannot copy/move parent directory to child directory."));
      } else {
        try {
          this.copyContentToDir(oldDir, dirAtNewPath);
        } catch (InvalidNameException | ItemAlreadyExistsException | InvalidPathException e) {
          output.setException(e);
        }
      }
      return output;
    } catch (InvalidPathException e) { // directory does not exist at new path; ignore
    }

    return this.handleCopyDirToNonExistingPath(oldDir, newPath);
  }

  /**
   * This method copies oldFile to newPath and newPath does not refer to an existing item in the
   * file system. If newPath is a file path, a new file will be created at newPath with oldFile's
   * content. This method uses helper method copyContentToDir to copy oldFile to newPath. If newPath
   * is a directory path, an exception will be returned in output.
   * 
   * @param oldDir The directory at oldDir
   * @param newPath This is the new path to copy the directory to
   * @return An Output object representing the output from the command class
   */
  protected Output handleCopyFileToNonExistingPath(File oldFile, String newPath) {
    Output output = new Output(null, null);

    if (newPath.endsWith("/")) { // a non-existing directory path; ignore
      output.setException(
          new InvalidPathException(newPath + " is refering to a nonexisting directory."));
    } else { // make a file at newPath
      try {
        this.fileSystem.makeFile(newPath, oldFile.getText());
      } catch (InvalidNameException | ItemAlreadyExistsException | InvalidPathException e) {
        output.setException(e);
      }
    }

    return output;
  }

  /**
   * This method copies oldDir to newPath and newPath does not refer to an existing item in the file
   * system. If newPath is a file path, an exception will be returned in output. If newPath is a
   * directory path, a new directory will be created at newPath with oldDir's contents. This method
   * uses helper method copyContentToDir to copy contents in oldDir to newPath.
   * 
   * @param oldDir The directory at oldDir
   * @param newPath This is the new path to copy the directory to
   * @return An Output object representing the output from the command class
   */
  protected Output handleCopyDirToNonExistingPath(Directory oldDir, String newPath) {
    Output output = new Output(null, null);

    if (newPath.endsWith("/")) {
      try { // make new directory at newPath
        Directory newDir = this.fileSystem.makeDirectory(newPath);
        if (oldDir.hasChild(newDir)) {
          this.fileSystem.removeDirectory(newDir.getPath());
          output.setException(
              new InvalidPathException("Cannot copy/move parent directory to child directory."));
          return output;
        }
        // copies all children
        Iterator<File> fileIterator = oldDir.getFiles();
        while (fileIterator.hasNext())
          this.copyContentToDir(fileIterator.next(), newDir);
        Iterator<Directory> dirIterator = oldDir.getSubdirectories();
        while (dirIterator.hasNext())
          this.copyContentToDir(dirIterator.next(), newDir);

      } catch (InvalidNameException | ItemAlreadyExistsException | InvalidPathException e) {
        output.setException(e);
      }
      return output;
    }
    // non-existing file path; cannot copy
    output.setException(new InvalidPathException(newPath + " is refering to a nonexisting file."));
    return output;
  }

  /**
   * This method copies file to destDir.
   * 
   * @param file This is the file at oldPath
   * @param destDir this is the directory to copy file into
   * @throws InvalidNameException
   * @throws ItemAlreadyExistsException
   * @throws InvalidPathException
   */
  protected void copyContentToDir(File file, Directory destDir)
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    String newFilePath = destDir.getPath() + "/" + file.getName();
    this.fileSystem.makeFile(newFilePath, file.getText());
  }

  /**
   * This method copies srcDir to destDir.
   * 
   * @param srcDir This is the directory at oldPath
   * @param destDir This is the directory to move srcDir into
   * @throws InvalidNameException
   * @throws ItemAlreadyExistsException
   * @throws InvalidPathException
   */
  protected void copyContentToDir(Directory srcDir, Directory destDir)
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {

    // make new directory
    String newDirPath = destDir.getPath() + "/" + srcDir.getName();
    Directory newDir = this.fileSystem.makeDirectory(newDirPath);

    // copies all children
    Iterator<File> fileIterator = srcDir.getFiles();
    while (fileIterator.hasNext()) {
      this.copyContentToDir(fileIterator.next(), newDir);
    }

    Iterator<Directory> dirIterator = srcDir.getSubdirectories();
    while (dirIterator.hasNext()) {
      this.copyContentToDir(dirIterator.next(), newDir);
    }
  }

  /**
   * This method get a file system object
   * 
   * @return a FileSystem object that stores all files and directories
   */
  protected FileSystem getFileSystem() {
    return this.fileSystem;
  }

  /**
   * This method get a validation gate object
   * 
   * @return a ValidationGate object that validates user input
   */
  protected ValidationGate getValiGate() {
    return this.valiGate;
  }
}
