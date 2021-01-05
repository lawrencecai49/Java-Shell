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

package filesystem;

import java.util.Stack;
import driver.ValidationGate;
import exception.InvalidNameException;
import exception.InvalidPathException;
import exception.ItemAlreadyExistsException;

/**
 * This class represents the file system to be used by the Jshell
 * 
 * @author Luoliang Cai
 */
public class JFileSystem implements FileSystem {
  private Directory root;
  private Directory currentDirectory;
  private static JFileSystem currentSystem = null;
  private ValidationGate gate = new ValidationGate();

  /** Default constructor that creates an instance of a file system */
  private JFileSystem() {
    this.root = new Directory("/", "/");
    this.currentDirectory = root;
    this.root.setParent(root);;
  }

  /**
   * Retrieves the file system to be used by the Jshell
   * 
   * @return the file system instance to be used by the Jshell
   */
  public static JFileSystem buildFileSystem() {
    if (currentSystem == null) {
      currentSystem = new JFileSystem();
    }
    return currentSystem;
  }

  /**
   * Retrieves the root of the file system
   * 
   * @return The directory that is the root of the file system
   */
  public Directory getRoot() {
    return root;
  }

  /**
   * Retrieves the current directory in the file system
   * 
   * @return The current directory in the file system
   */
  public Directory getCurrentDirectory() {
    return currentDirectory;
  }

  /**
   * Sets a new directory to be the current directory
   * 
   * @param newCurrentDirectory The new current directory
   */
  public void setCurrentDirectory(Directory newCurrentDirectory) {
    this.currentDirectory = newCurrentDirectory;
  }

  /**
   * Finds the directory at a given path
   * 
   * @param path The path of the directory to be found
   * @return the Directory object located at the given path
   * @throws InvalidPathException if directory does not exists at the given path
   */
  public Directory findDirectory(String path) throws InvalidPathException {
    if (path.isEmpty()) {
      return this.currentDirectory;
    }
    if (!gate.isValidPathname(path)) {
      throw new InvalidPathException(path + " is not a valid pathname");
    }
    Stack<String> parsedPath = parsePath(path);
    Directory foundDirectory;

    if (isFullPath(path)) {
      foundDirectory = findSubdirectory(parsedPath, root);
      if (foundDirectory == null) {
        throw new InvalidPathException(path + " does not refer to an existing directory");
      }
    } else {
      foundDirectory = findSubdirectory(parsedPath, currentDirectory);
      if (foundDirectory == null) {
        throw new InvalidPathException(path + " does not refer to an existing directory");
      }
    }
    return foundDirectory;
  }

  /**
   * Creates a new directory in the file system. Throws exception if the name of the new directory
   * contains illegal characters or an item with the same path already exists or the path already
   * refers to an existing item
   * 
   * @param path The path (absolute or relative) specifying location of the new directory
   * @return The directory that was added to the system if it was created and added successfully
   * @throws InvalidNameException when attempting to create file with invalid name
   * @throws ItemAlreadyExistsException when trying to create file at a path where a file or
   *         directory already exists
   * @throws InvalidPathException when trying to create file at a path that can not be reached
   */
  public Directory makeDirectory(String path)
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    if (path.endsWith("/")) {
      path = path.substring(0, path.lastIndexOf("/"));
    }
    String splitPath[] = splitPath(path);
    if (!gate.isValidDirectoryName(splitPath[0])) {
      throw new InvalidNameException(splitPath[0] + " contains invalid characters");
    }
    Directory parentDirectory = this.findDirectory(splitPath[1]);
    String newDirectoryPath;

    if (parentDirectory == root) {
      newDirectoryPath = parentDirectory.getPath() + splitPath[0];
    } else {
      newDirectoryPath = parentDirectory.getPath() + "/" + splitPath[0];
    }
    // creates a new directory and adds it to the directory
    Directory newDirectory = new Directory(splitPath[0], newDirectoryPath);
    parentDirectory.addDirectory(newDirectory);
    return newDirectory;
  }

  /**
   * Retrieves the file at located at given path
   * 
   * @param path The path (absolute or relative) specifying location of file
   * @return File located at path
   * @throws InvalidPathException if path does not refer to an existing file
   */
  public File findFile(String path) throws InvalidPathException {
    if (path.equals("/") || path.isEmpty()) {
      throw new InvalidPathException("There is no file at " + path);
    }
    String splitPath[] = splitPath(path);
    try {
      Directory parentDirectory = findDirectory(splitPath[1]);
      File returnFile = parentDirectory.getFile(splitPath[0]);
      if (returnFile == null) {
        throw new InvalidPathException("There is no file at " + path);
      }
      return returnFile;
    } catch (InvalidPathException e) {
      throw new InvalidPathException("There is no file at " + path);
    }
  }

  /**
   * This method creates a new file with in the file system. Throws exception if the name of the new
   * file contains illegal characters or an item with the same path already exists
   * 
   * @param path The path (relative or absolute) of the file to be created
   * @param text The text inside the file
   * @return The file that was added to the system if it was created and added successfully
   * @throws InvalidNameException when attempting to create file with invalid name
   * @throws ItemAlreadyExistsException when trying to create file at a path where a file or
   *         directory already exists
   * @throws InvalidPathException when trying to create file at a path that can not be reached
   */
  public File makeFile(String path, String text)
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    String splitPath[] = splitPath(path);
    if (!gate.isValidDirectoryName(splitPath[0])) {
      throw new InvalidNameException(splitPath[0] + " contains invalid characters");
    }
    Directory parentDirectory = findDirectory(splitPath[1]);
    String newFilePath;
    if (parentDirectory == root) {
      newFilePath = parentDirectory.getPath() + splitPath[0];
    } else {
      newFilePath = parentDirectory.getPath() + "/" + splitPath[0];
    }

    File newFile = new File(splitPath[0], newFilePath, text);
    parentDirectory.addFile(newFile);
    return newFile;
  }

  /**
   * Remove a directory from the file system recursively
   * 
   * @param path The path of the directory
   * @throws InvalidPathException if the directory can does not exist
   */
  public void removeDirectory(String path) throws InvalidPathException {
    Directory dir = findDirectory(path);
    dir.destroyAll();
    dir.getParent().remove(dir.getName());
  }

  /**
   * Finds a directory recursively using a path converted into a stack
   * 
   * @param pathStack A parsed path stored in a stack
   * @param directoryName The name of the directory to search for the next subdirectory
   * @return The directory located at the unparsed path stored in pathStack
   */
  private Directory findSubdirectory(Stack<String> pathStack, Directory directoryName) {
    if (pathStack.isEmpty()) {
      return directoryName;
    }
    String subdirectory = pathStack.pop();
    Directory nextDirectoryName = directoryName.getDirectory(subdirectory);

    if (nextDirectoryName == null) {
      return null;
    }
    return findSubdirectory(pathStack, nextDirectoryName);
  }

  /**
   * This method parses a string representing a path into a stack
   * 
   * @param path The string representation of the path
   * @return A Stack containing the elements in the path
   */
  private Stack<String> parsePath(String path) {
    String[] parsedArr = path.split("/");
    Stack<String> parsedStack = new Stack<String>();
    for (int i = parsedArr.length - 1; i >= 0; i--) {
      if (!parsedArr[i].isEmpty()) {
        parsedStack.push(parsedArr[i]);
      }
    }
    return parsedStack;
  }

  /**
   * Splits a path of an item into two parts, The path of the parent of the item and the name of the
   * item
   * 
   * @param path The path to be split
   * @return An array were the first element is the name and the last is the path of the parent
   */
  private String[] splitPath(String path) {
    String parent = path.substring(0, Math.max(path.lastIndexOf("/"), 0));
    if (parent.isEmpty() && path.contains("/")) {
      parent = "/";
    }
    String name = path.substring(Math.max(path.lastIndexOf("/") + 1, 0));
    String splitPath[] = {name, parent};
    return splitPath;
  }

  /**
   * Determines if a path is an absolute path or a relative path
   * 
   * @param path The path
   * @return boolean value corresponding to whether the path is an absolute path
   */
  private boolean isFullPath(String path) {
    if (path.startsWith("/")) {
      return true;
    }
    return false;
  }
}
