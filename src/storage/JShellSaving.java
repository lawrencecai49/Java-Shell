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

package storage;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import driver.*;
import exception.*;
import filesystem.*;
import filesystem.File;

/**
 * This class implements saveJShell command and save entire current mock fileSystem including any
 * state of any of the commands in the file fileName
 * 
 * @author Yuanyuan Li
 *
 */
public class JShellSaving {

  private FileSystem fileSystem;
  private InputStorage inputHistory;
  private Stack<String> dirStack;
  private String[] inValidPathnameChar = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "{",
      "}", "~", "|", "<", ">", "?", " ", "/", "."};

  /**
   * Class constructor
   * 
   * @param inputHistory user input history
   * @param dirStack directory stack
   * @param fileSystem mock file system
   */
  public JShellSaving(InputStorage inputHistory, Stack<String> dirStack, FileSystem fileSystem) {
    this.fileSystem = fileSystem;
    this.dirStack = dirStack;
    this.inputHistory = inputHistory;
  }

  /**
   * This method if for checking the validation of saveJShell command input
   * 
   * @param userCommand parsed user input command
   * @return boolean whether the user input is valid
   * @throws InvalidArgumentException
   * @throws RedirectionNotSupportException
   * @throws InvalidNameException
   */
  public boolean checkValidation(InputProcessor userCommand)
      throws InvalidArgumentException, RedirectionNotSupportException, InvalidNameException {
    String[] argument = userCommand.getArgument();
    // takes no argument
    if (argument == null)
      throw new InvalidArgumentException(
          "Invalid argument: saveJShell should take a FileName as an argument");
    // takes too many arguments
    else if (argument.length != 1)
      throw new InvalidArgumentException("Invalid argument: saveJShell can only take one argument");
    // takes redirection operator
    else if (userCommand.getRedirectionOperator() != null)
      throw new RedirectionNotSupportException("Redirection does not support saveJShell command");
    // contains invalid characters
    else {
      for (String character : inValidPathnameChar) {
        if (argument[0].contains(character)) {
          throw new InvalidNameException("Invalid name: FileName contains invalid characters");
        }
      }
    }
    return true;
  }

  /**
   * This method adds the files' pathnames and their content into the queue and return the queue
   * 
   * @param allFiles The linked list that contains all the files
   * @param fileSystemQueue A queue that contains the information about the file system
   * @return queue
   */
  private Queue<String> addItems(Iterator<File> allFiles, Queue<String> fileSystemQueue) {
    while (allFiles.hasNext()) {
      File file = allFiles.next();
      // add file absolute pathname and tag with "FilePath:"
      fileSystemQueue.offer("FilePath:" + file.getPath());
      // add file content and tag with "Content:"
      fileSystemQueue.offer("Content:" + file.getText());
    }
    return fileSystemQueue;
  }

  /**
   * This method will recursively turn a FileSystem object into a queue and assign the corresponding
   * tag.
   * 
   * @param dir Current analyzing directory
   * @param fileSystemQueue A queue that contains the information about the file system
   * @return The current progress of queue is returned
   */
  private Queue<String> queueBuilder(Directory dir, Queue<String> fileSystemQueue) {
    Iterator<Directory> allSubDir = dir.getSubdirectories();
    Iterator<File> allFiles = dir.getFiles();
    // add directory absolute path and tag with "Directory:"
    fileSystemQueue.offer("Directory:" + dir.getPath());
    // adding all of dir's files and their content
    fileSystemQueue = addItems(allFiles, fileSystemQueue);

    // queueBuilder will travel deeper into the fileSystem
    if (!allSubDir.hasNext()) {
      return fileSystemQueue;
    }
    while (allSubDir.hasNext()) {
      fileSystemQueue = queueBuilder(allSubDir.next(), fileSystemQueue);
    }
    return fileSystemQueue;
  }

  /**
   * This method will save the current session of JShell into the file fileName in the actual
   * computer
   * 
   * @param filePath The pathname of the file
   * @throws IOException
   * @throws InvalidNameException
   */
  public void saveJShell(String filePath) throws IOException, InvalidNameException {
    try {
      // create a file
      FileHandler fileHandler = new FileHandler();
      fileHandler.processFile(filePath);
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
      Queue<String> fileSystemQueue = new LinkedList<String>();
      fileSystemQueue = queueBuilder(this.fileSystem.getRoot(), fileSystemQueue);
      // serialize dirStack, inputHistory, fileSystemQueue, current working directory
      oos.writeObject(dirStack);
      oos.writeObject(inputHistory);
      oos.writeObject(fileSystemQueue);
      oos.writeObject(this.fileSystem.getCurrentDirectory().getPath());
      oos.close();
    } catch (IOException e) {
      throw new IOException("Error: Serialization fail");
    }
  }
}
