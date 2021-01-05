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


import output.Output;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import driver.*;
import exception.*;
import filesystem.*;

/**
 * This class outputs the list of all names of the files and directories for the given pathname(s).
 * If the "-R" argument is provided then the list of all sub-directories contents for the given
 * pathname(s) will be the output.
 * 
 * @author Christina Ma
 */

public class ListClass extends Command {
  /**
   * This is a ValidationGate object that validates user input
   */
  private ValidationGate valiGate;
  /**
   * This is a FileSystem object that stores all files and directories contents
   */
  private FileSystem filesys;

  /**
   * Constructor creates an instance of an object that executes all functions for the ls command.
   * 
   * @param valiGate This is a ValidationGate object that validates user input
   * @param filesys This is a file system storing all files and directories contents
   */
  public ListClass(ValidationGate valiGate, FileSystem filesys) {
    this.valiGate = valiGate;
    this.filesys = filesys;
  }

  /**
   * This method will append the contents of each given pathname that refers to an existing
   * directory as a list. If the pathname refers to a file, then the pathname is appended. If the
   * "-R" argument is provided then the list of all sub-directories contents for the given
   * pathname(s) will be appended. If a given pathname does not exist, the method halts and the rest
   * of the pathnames will not be executed.
   * 
   * @param userInput This stores all user input that's been parsed
   * @return An Output object stores the message to be printed and exception to be thrown if an
   *         exception occurs
   */
  public Output runCommand(InputProcessor userInput) {
    String[] args = userInput.getArgument();
    String outputMessage = "";
    if (args == null || args.length == 0) { // No path given: appends list at current directory
      outputMessage += appendList(filesys.getCurrentDirectory(), "");
      return new Output(formatMessage(outputMessage), null);
    }
    // Check for -R argument
    boolean hasR = containR(args);
    String[] noRInPaths = removeR(args, hasR);
    if (noRInPaths.length == 0 && hasR) { // No path given and -R: append list of sub-directories
      outputMessage +=
          appendSubdirList(new LinkedList<Directory>(), filesys.getCurrentDirectory(), "");
      return new Output(formatMessage(outputMessage), null);
    }
    for (int i = 0; i < noRInPaths.length; i++) { // Given one or more pathnames
      if (valiGate.isValidPathname(noRInPaths[i])) {
        FileSystemContent pathObj = getPathObject(noRInPaths[i]);
        if (pathObj == null) { // Pathname does not refer to and existing file or directory
          return new Output(formatMessage(outputMessage),
              new InvalidPathException(noRInPaths[i] + " is not an existing directory or file"));
        } else { // Appends pathname's contents
          outputMessage += executeCorrectAppend(pathObj, hasR, noRInPaths[i]);
        }
      } else { // Invalid pathname given
        return new Output(formatMessage(outputMessage),
            new InvalidPathException(noRInPaths[i] + " is not a valid pathname."));
      }
    }
    return new Output(formatMessage(outputMessage), null);
  }

  /**
   * This method will execute the correct helper method that will append the list based on the input
   * arguments.
   * 
   * @param pathObj The File orDirectory object that path refers to
   * @param hasR A boolean that indicates if the user has input the "-R" argument
   * @param path The pathname the user has provided
   * @return
   */
  private String executeCorrectAppend(FileSystemContent pathObj, boolean hasR, String path) {
    String list = "";
    // Given a path of a directory and -R: append list of all sub-directories
    if (pathObj instanceof Directory && hasR) {
      list +=
          appendSubdirList(new LinkedList<Directory>(), (Directory) pathObj, path + "\n") + "\n\n";
      // Given a path of a directory: append list of content names
    } else if (pathObj instanceof Directory) {
      list += appendList((Directory) pathObj, path) + "\n";
      // Given a path of a file: append file path given
    } else if (pathObj instanceof File) {
      list += path + "\n\n";
    }
    return list;
  }

  /**
   * This method reformats outputMessage by removing excess newline characters at the end.
   * 
   * @param outputMessage The message that will be printed onto the console for the user
   * @return The message with excess newline characters at the end removed
   */
  private String formatMessage(String outputMessage) {
    while (outputMessage.endsWith("\n")) {
      outputMessage = outputMessage.substring(0, outputMessage.lastIndexOf("\n"));
    }
    return outputMessage;
  }

  /**
   * This method recursively appends the list of all sub-directories.
   * 
   * @param allSubDir A Queue that saves all the sub-directories that must be appended to the list
   * @param dir The current directory that is being analyzed and appended to the list
   * @param subDirList The list of all sub-directories contents that's been analyzed appended
   * @return Returns a String of the entire list of all sub-directories contents
   */
  private String appendSubdirList(Queue<Directory> allSubDir, Directory dir, String subDirList) {
    subDirList += dir.getPath() + ":\n";
    Iterator<FileSystemContent> allContents = dir.getDirectoryItems();
    // Appends all contents of dir to subDirList and adds all of dir's sub-directories onto
    // allSubDir
    while (allContents.hasNext()) {
      FileSystemContent item = allContents.next();
      if (item instanceof Directory) {
        allSubDir.add((Directory) item);
      }
      subDirList += item.getName() + "\n";
    }
    // Base Case: return if there's no more sub-directories left
    if (allSubDir.isEmpty()) {
      return subDirList;
      // Recursive Case: continue to append sub-directories contents while there's sub-directories
      // left
    } else {
      subDirList += "\n";
      Directory newDir = (Directory) allSubDir.poll();
      subDirList = appendSubdirList(allSubDir, newDir, subDirList);
    }
    return subDirList;
  }

  /**
   * Determines if the user has input the "-R" argument.
   * 
   * @param args An array of all additional arguments the user input
   * @return Returns true if the user has input the "-R" argument, otherwise false
   */
  private boolean containR(String[] args) {
    for (String argItem : args) {
      if (argItem.equals("-R"))
        return true;
    }
    return false;
  }

  /**
   * Returns the array with the "-R" argument removed.
   * 
   * @param args An array of all additional arguments the user input
   * @param hasR A boolean that indicate if "-R" is an argument in args
   * @return Returns args with "-R" argument removed
   */
  private String[] removeR(String[] args, boolean hasR) {
    if (hasR) {
      String[] noR = new String[args.length - 1];
      int i = 0;
      for (String argItem : args) {
        if (!argItem.equals("-R")) {
          noR[i] = argItem;
          i++;
        }
      }
      return noR;
    }
    return args;
  }

  /**
   * This method returns the entire contents of the given directory appended to a string
   * 
   * @param dir The Directory that the list of files and directories will append from
   * @param path The pathname of where the list of files and directories will append from
   * @return A String containing the list of all directories and files for path
   */
  private String appendList(Directory dir, String path) {
    Iterator<FileSystemContent> dirContents = dir.getDirectoryItems();
    String outputMessage = "";
    // Appends pathname if not printing from current directory
    if (!path.isEmpty()) {
      if (path.equals(".") || path.equals("..")) {
        outputMessage += dir.getPath() + ":\n";
      } else {
        outputMessage += path + ":\n";
      }
    }
    // Appends all the contents of dir to outputMessage
    while (dirContents.hasNext()) {
      outputMessage += dirContents.next().getName() + "\n";
    }
    return outputMessage;
  }

  /**
   * This method determines if path refers to an existing directory or file and returns the
   * corresponding Directory or File object, otherwise null is returned.
   * 
   * @param path The pathname that references a directory or file
   * @return Return the Directory or File object that path refers to, otherwise null.
   */
  private FileSystemContent getPathObject(String path) {
    try {
      // Attempts to find the directory in the file system
      Directory dir = filesys.findDirectory(path);
      return dir;
    } catch (InvalidPathException e) { // Do nothing
    }
    try {
      // Retrieves the file name and attempts to finds it in File System
      String[] parsedPath = path.split("/");
      if (parsedPath.length > 0
          && valiGate.isValidDirectoryName(parsedPath[parsedPath.length - 1])) {
        File file = filesys.findFile(path);
        return file;
      }
    } catch (InvalidPathException e) {
      return null;
    }
    return null;
  }
}
