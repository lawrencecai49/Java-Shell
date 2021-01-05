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

import java.util.Iterator;
import java.util.LinkedList;
import exception.ItemAlreadyExistsException;

/**
 * This class represents a directory inside the file system.
 * 
 * @author Luoliang Cai
 */
public class Directory extends FileSystemContent {
  private LinkedList<Directory> subdirectories;
  private LinkedList<File> files;

  /**
   * Default constructor that creates an instance of a directory
   * 
   * @param name The name of the directory
   * @param pathname The absolute path of the directory
   * @param parent The parent directory
   */
  public Directory(String name, String pathname) {
    super(name, pathname);
    subdirectories = new LinkedList<Directory>();
    files = new LinkedList<File>();
  }

  /**
   * Retrieves the parent directory of the directory
   * 
   * @return returns the parent directory
   */
  public Directory getParent() {
    if (this.parentDirectory == null) {
      return this;
    }
    return this.parentDirectory;
  }

  /**
   * Retrieves a linked list of all subdirectories
   * 
   * @return LinkedList containing all subdirectories
   */
  public LinkedList<Directory> getSubdirectoryList() {
    return this.subdirectories;
  }

  /**
   * Retrieves a linked list of all files
   * 
   * @return LinkedList containing all files
   */
  public LinkedList<File> getFileList() {
    return this.files;
  }

  /**
   * Adds a new directory instance into the directory, throws exception if a file or directory with
   * the same name as the new directory already exists in the directory.
   * 
   * @param newDirectory The directory to be added
   * @throws ItemAlreadyExistsException when trying to add a directory that has a name matching an
   *         item that already exists in the directory
   */
  public void addDirectory(Directory newDirectory) throws ItemAlreadyExistsException {
    if (this.hasItem(newDirectory.getName())) {
      throw new ItemAlreadyExistsException(
          "there is already an item named " + newDirectory.getName() + " in " + this.getName());
    }
    this.subdirectories.add(newDirectory);
    newDirectory.setParent(this);
  }

  /**
   * Adds a new file in the directory, throws an exception if the file is null or if a file or
   * directory with the same name already exists in the directory
   * 
   * @param newFile The file to be added
   * @throws ItemAlreadyExistsException when trying to add a file that has a name matching an item
   *         that already exists in the directory
   */
  public void addFile(File newFile) throws ItemAlreadyExistsException {
    if (this.hasItem(newFile.getName())) {
      throw new ItemAlreadyExistsException(
          "there is already an item named " + newFile.getName() + " in " + this.getName());
    }
    this.files.add(newFile);
    newFile.setParent(this);
  }

  /**
   * Retrieves a list of all the contents in the directory
   * 
   * @return A linked list containing all subdirectories and files in the directory
   */
  public Iterator<FileSystemContent> getDirectoryItems() {
    LinkedList<FileSystemContent> directoryContents = new LinkedList<FileSystemContent>();
    directoryContents.addAll(this.subdirectories);
    directoryContents.addAll(this.files);
    return directoryContents.iterator();
  }

  /**
   * Retrieves a list of all the contents in the directory
   * 
   * @return A linked list containing all subdirectories and files in the directory
   */
  public Iterator<Directory> getSubdirectories() {
    return this.subdirectories.iterator();
  }

  /**
   * Retrieves a list of all the contents in the directory
   * 
   * @return A linked list containing all subdirectories and files in the directory
   */
  public Iterator<File> getFiles() {
    return this.files.iterator();
  }

  /**
   * Retrieve a subdirectory in the directory
   * 
   * @param itemName Name of the directory to be retrieved
   * @return The directory with the specified name
   */
  public Directory getDirectory(String directoryName) {
    if (directoryName.equals("..")) {
      return this.getParent();
    } else if (directoryName.equals(".")) {
      return this;
    } else {
      for (int i = 0; i < this.subdirectories.size(); i++) {
        if (subdirectories.get(i).getName().equals(directoryName)) {
          return subdirectories.get(i);
        }
      }
    }
    return null;
  }

  /**
   * Remove a specific item from the directory
   * 
   * @param name The name of the item
   */
  public void remove(String name) {
    boolean removeItem = false;
    for (Directory d : this.subdirectories) {
      if (d.getName().equals(name)) {
        this.subdirectories.remove(d);
        removeItem = true;
      }
      if (removeItem == true) {
        return;
      }
    }
    for (File f : this.files) {
      if (f.getName().equals(name)) {
        this.files.remove(f);
        removeItem = true;
      }
      if (removeItem == true) {
        return;
      }
    }
    return;
  }

  /**
   * clear all items in the directory recursively
   */
  public void destroyAll() {
    for (Directory d : this.subdirectories) {
      d.destroyAll();
    }
    this.subdirectories.clear();
    this.files.clear();
  }

  /**
   * Retrieves a file in the directory
   * 
   * @param itemName Name of the file to be retrieved
   * @return file The file with the specified name
   */
  public File getFile(String fileName) {
    for (int i = 0; i < this.files.size(); i++) {
      if (files.get(i).getName().equals(fileName)) {
        return files.get(i);
      }
    }
    return null;
  }

  /**
   * Determines if directory is a child (subdirectory, subdirectory of a subdirectory etc) of this
   * directory
   * 
   * @param dir The directory
   * @return whether directory is a child of this directory
   */
  public boolean hasChild(Directory dir) {
    if (dir == null || this.subdirectories.isEmpty()) {
      return false;
    }
    if (this.subdirectories.contains(dir)) {
      return true;
    }
    for (Directory d : this.subdirectories) {
      if (d.hasChild(dir) == true) {
        return true;
      }
    }
    return false;
  }

  /**
   * Determines whether the directory contains an item with a certain name
   * 
   * @param itemName The name of the item
   * @return A boolean value that corresponds to whether the directory has a file with the name
   */
  private boolean hasItem(String itemName) {
    if (this.getDirectory(itemName) == null && this.getFile(itemName) == null) {
      return false;
    }
    return true;
  }
}
