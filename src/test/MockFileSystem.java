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
package test;

import java.util.ArrayList;
import exception.InvalidNameException;
import exception.InvalidPathException;
import exception.ItemAlreadyExistsException;
import filesystem.Directory;
import filesystem.File;
import filesystem.FileSystem;

public class MockFileSystem implements FileSystem {
  private Directory root;
  private Directory currentDirectory;
  public ArrayList<String> files;
  public ArrayList<String> directories;
  public File lastModifiedFile;

  /** Default constructor that creates an instance of a file system */
  public MockFileSystem() {
    this.root = new Directory("/", "/");
    this.currentDirectory = new Directory("currDir", "currDir");
    this.root.setParent(root);
    this.files = new ArrayList<String>();
    this.directories = new ArrayList<String>();
    this.lastModifiedFile = null;
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
    return this.currentDirectory;
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
  @Override
  public Directory findDirectory(String path) throws InvalidPathException {
    if (path.isEmpty()) {
      throw new InvalidPathException(path + " does not refer to an existing directory");
    } else if (path.equals("InvalidPath")) {
      throw new InvalidPathException(path + " does not refer to an existing directory");
    } else if (path.equals("FilePath")) {
      throw new InvalidPathException(path + " does not refer to an existing directory");
    } else if (path.equals("NonexistingValidPathname")) {
      throw new InvalidPathException(path + " does not refer to an existing directory");
    } else {
      return new Directory(path, path);
    }
  }

  /**
   * Creates a new directory in the file system. Throws exception if the name of the new directory
   * contains illegal characters or an item with the same path already exists or the path already
   * refers to an existing item
   * 
   * @param path The path (absolute or relative) specifying location of the new directory
   * @throws InvalidNameException when attempting to create file with invalid name
   * @throws ItemAlreadyExistsException when trying to create file at a path where a file or
   *         directory already exists
   * @throws InvalidPathException when trying to create file at a path that can not be reached
   */
  @Override
  public Directory makeDirectory(String path)
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    if (path.equals("PathOfExistingItem")) {
      throw new ItemAlreadyExistsException("item already exists at " + path);
    } else if (path.equals("InvalidPath")) {
      throw new InvalidPathException(path + " is an invalid path");
    } else if (path.equals("InvalidName")) {
      throw new InvalidNameException(path + " has an invalid name");
    } else {
      this.directories.add(path);
      return new Directory(path, path);
    }
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
    } else if (path.equals("InvalidPath")) {
      throw new InvalidPathException("There is no file at " + path);
    } else if (path.equals("DirectoryPath")) {
      throw new InvalidPathException("There is no file at " + path);
    } else if (path.equals("NonexistingValidPathname")) {
      throw new InvalidPathException("There is no file at " + path);
    } else {
      File file = new File(path, path);
      this.lastModifiedFile = file;
      return file;
    }
  }

  /**
   * This method creates a new file with in the file system. Throws exception if the name of the new
   * file contains illegal characters or an item with the same path already exists
   * 
   * @param path The path (relative or absolute) of the file to be created
   * @param text The text inside the file
   * @throws InvalidNameException when attempting to create file with invalid name
   * @throws ItemAlreadyExistsException when trying to create file at a path where a file or
   *         directory already exists
   * @throws InvalidPathException when trying to create file at a path that can not be reached
   */
  @Override
  public File makeFile(String path, String text)
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    if (path.equals("PathOfExistingItem")) {
      throw new ItemAlreadyExistsException("there is already an item at" + path);
    } else if (path.equals("InvalidPath")) {
      throw new InvalidPathException(path + "is an invalid path");
    } else if (path.equals("InvalidName")) {
      throw new InvalidNameException(path + " has an invalid name");
    } else {
      this.files.add(path);
      File file = new File(path, path, text);
      this.lastModifiedFile = file;
      return file;
    }
  }

  /**
   * Remove a directory from the file system recursively
   * 
   * @param path The path of the directory
   * @throws InvalidPathException if the directory can does not exist
   */
  @Override
  public void removeDirectory(String path) throws InvalidPathException {
    Directory toBeRemoved = this.findDirectory(path);
    this.directories.remove(toBeRemoved.getPath());
  }

  /**
   * Sets a new directory to be the current directory
   * 
   * @param newCurrentDirectory The new current directory
   */
  public ArrayList<String> getFiles() {
    return this.files;
  }

  public ArrayList<String> getDirectories() {
    return this.directories;
  }
}
