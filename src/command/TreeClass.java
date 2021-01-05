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

import output.*;
import java.util.Iterator;
import driver.*;
import exception.*;
import filesystem.*;

/**
 * This class outputs the complete mapping of the entire file system, beginning at the root, in the
 * format of a tree structure.
 * 
 * @author Christina Ma
 */

public class TreeClass extends Command {
  /**
   * This is a FileSystem object that stores all files and directories contents
   */
  private FileSystem filesys;

  /**
   * Constructor creates an instance of an object that executes all functions for tree command.
   * 
   * @param filesys This is a file system storing all files and directories contents
   */
  public TreeClass(FileSystem filesys) {
    this.filesys = filesys;
  }

  /**
   * This method will output the complete mapping of the entire file system, beginning at the root,
   * and format it into a tree structure.
   * 
   * @param userInput This stores all user input that's been parsed
   * @return An Output object stores the message to be printed and the exception to be thrown if an
   *         exception has occurred
   */
  public Output runCommand(InputProcessor userInput) {
    String outputMessage = "";
    int tabCounter = 0;
    // Check if user has given additional arguments
    if (userInput.getArgument() == null) {
      outputMessage = treeBuilder(filesys.getRoot(), outputMessage, tabCounter);
      // Remove excess newline from outputMessage
      while (outputMessage.endsWith("\n")) {
        outputMessage = outputMessage.substring(0, outputMessage.lastIndexOf("\n"));
      }
      return new Output(outputMessage, null);
    }
    return new Output(null, new InvalidArgumentException("tree takes no arguments"));
  }

  /**
   * This method recursively builds the tree structure of the entire file system mapping.
   * 
   * @param dir The directory that is currently being analyzed
   * @param tree The tree structure of the entire file system
   * @param tabCounter The number of indentation needed appending an item to the tree to maintain
   *        the format
   * @return The current progress of tree is returned
   */
  private String treeBuilder(Directory dir, String tree, int tabCounter) {
    Iterator<Directory> allSubDir = dir.getSubdirectories();
    Iterator<File> allFiles = dir.getFiles();
    // Appending dir name and all of dir's files to tree
    tree += " ".repeat(tabCounter) + dir.getName() + "\n";
    tabCounter += 4;
    tree += appendItems(allFiles, tabCounter);
    // treeBuilder will travel deeper into the sub-directories if dir has any
    if (!allSubDir.hasNext()) {
      return tree;
    }
    while (allSubDir.hasNext()) {
      tree = treeBuilder(allSubDir.next(), tree, tabCounter);
    }
    tabCounter -= 4;
    return tree;
  }

  /**
   * This method appends and returns all items in allFiles as a list (a newline is added at the end
   * of every item).
   * 
   * @param allFiles An Iterator that will iterate through all the files from a directory
   * @param tabCounter The number of indentation needed appending an item to the tree to maintain
   *        the format
   * @return A String of the list of all items from allFiles
   */
  private String appendItems(Iterator<File> allFiles, int tabCounter) {
    String contentList = "";
    while (allFiles.hasNext()) {
      contentList += " ".repeat(tabCounter) + allFiles.next().getName() + "\n";
    }
    return contentList;
  }
}
