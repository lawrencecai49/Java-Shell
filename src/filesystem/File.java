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
 * This class represents a file within the file system
 * 
 * @author Luoliang Cai
 */
public class File extends FileSystemContent {
  private String fileText = "";

  /**
   * Creates the default empty file
   * 
   * @param name The name of the file
   * @param path The full path of the file
   * @param text The text that the file contains
   */
  public File(String name, String path) {
    super(name, path);
  }

  /**
   * Creates an instance of a file with text inside it
   * 
   * @param name The name of the file
   * @param path The full path of the file
   * @param text The text that the file contains
   */
  public File(String name, String path, String text) {
    this(name, path);
    this.fileText = text;
  }

  /**
   * Overwrites original text in file with new text
   * 
   * @param newtext The text that will replace the original text in file
   */
  public void overwriteText(String newText) {
    this.fileText = newText;
  }

  /**
   * Append text to the end of a file
   * 
   * @param newText The text to be appended
   */
  public void appendText(String newText) {
    this.fileText = this.fileText + newText;
  }

  /**
   * delete all the text in the file
   */
  public void deleteAll() {
    this.fileText = "";
  }

  /**
   * Retrieve text in file
   */
  public String getText() {
    return this.fileText;
  }

}
