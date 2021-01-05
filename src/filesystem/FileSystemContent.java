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

/**
 * This class represents an item (Ex. file, directory) inside the file system
 * 
 * @author Luoliang Cai
 */
public class FileSystemContent {
  /** The name of the item */
  protected String itemName;
  /** The absolute path of the item */
  protected String itemPath;
  /** The directory that the item is stored in */
  protected Directory parentDirectory = null;

  /**
   * Basic constructor that creates a new item to be stored in the file system
   * 
   * @param name The name of the item
   * @param path The absolute path of the item
   */
  public FileSystemContent(String name, String path) {
    this.itemPath = path;
    this.itemName = name;
  }

  /**
   * Sets the parent directory
   * 
   * @param parent The new parent directory
   */
  public void setParent(Directory parent) {
    this.parentDirectory = parent;
  }

  /**
   * Retrieves the parent directory of the directory
   * 
   * @return returns the parent directory
   */
  public Directory getParent() {
    return this.parentDirectory;
  }

  /**
   * Retrieves the name of the item in the file system
   * 
   * @return the name of the item
   */
  public String getName() {
    return this.itemName;
  }

  /**
   * Retrieves the full path of the item in the file system
   * 
   * @return the absolute path of the item
   */
  public String getPath() {
    return this.itemPath;
  }

}
