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
import java.util.ArrayList;
import java.util.Iterator;
import driver.*;
import exception.*;
import filesystem.*;

/**
 * This class outputs the list of full pathnames of all the sub-directories from all the provided
 * pathname(s) that contain the file or directory name that is being searched for.
 * 
 * @author Christina Ma
 */

public class SearchClass extends Command {
  /**
   * This is a ValidationGate object that validates user input
   */
  private ValidationGate valiGate;
  /**
   * This is a FileSystem object that stores all files and directories contents
   */
  private FileSystem filesys;

  /**
   * Constructor creates an instance of an object that executes all functions for search command
   * 
   * @param valiGate This is a ValidationGate object that validates user input
   * @param filesys This is a file system storing all files and directories contents
   */
  public SearchClass(ValidationGate valiGate, FileSystem filesys) {
    this.valiGate = valiGate;
    this.filesys = filesys;
  }

  /**
   * This method will retrieve the list of full pathnames of all sub-directories from all the given
   * pathname(s) that contain the file or directory name that is being searched for. If a given
   * pathname is invalid or does not exist is provided then the method halts and the rest of the
   * pathnames will not be executed.
   * 
   * @param userInput This stores all user input that's been parsed
   * @return An Output object stores the message to be printed and the exception to be thrown if an
   *         exception has occurred
   */
  public Output runCommand(InputProcessor userInput) {
    String name = getSearchItemName(userInput.getArgument());
    FileSystemContent type = getSearchItemObjType(userInput.getArgument());
    String[] pathList = getPathList(userInput.getArgument(), name, type);
    // Validating if user has provided the required search arguments
    if (name == null || type == null || pathList == null) {
      return new Output(null, new InvalidArgumentException(
          "Missing additional required arguments for search command or incorrect format"));
    }
    // Retrieving the list of sub-directories that have the searched item
    String outputMessage = "";
    try {
      for (String path : pathList) {
        if (valiGate.isValidPathname(path)) {
          Directory dir = filesys.findDirectory(path);
          String list = searchPath(dir, "", name, type);
          if (list.isEmpty()) {
            outputMessage += name + " does not exist within " + path + "\n\n";
          } else {
            outputMessage += path + ":\n" + list + "\n";
          }
        } else {
          return new Output(formatMessage(outputMessage),
              new InvalidPathException(path + " is not a valid pathname."));
        }
      }
      return new Output(formatMessage(outputMessage), null);
    } catch (InvalidPathException e) {
      return new Output(formatMessage(outputMessage), e);
    }
  }

  /**
   * This method recursively appends the pathnames of all the sub-directories that contain the
   * searched item
   * 
   * @param dir The directory that currently being analyzed
   * @param list The list of full pathnames of sub-directories that contain the searched item
   * @param name The name of the searched item
   * @param type The object type of the search item (File or Directory object)
   * @return The current progress of list is returned
   */
  private String searchPath(Directory dir, String list, String name, Object type) {
    Iterator<Directory> allSubDir = dir.getSubdirectories();
    list += appendItems(dir, name, type);
    // Base Case: dir does not have any sub-directories
    if (!allSubDir.hasNext()) {
      return list;
      // Recursive Case: dir has sub-directories and must search through them
    } else {
      while (allSubDir.hasNext()) {
        list = searchPath(allSubDir.next(), list, name, type);
      }
    }
    return list;
  }


  /**
   * A list of the full pathname of the items that matched the searched item's requirements found in
   * dir is returned.
   * 
   * @param dir The Directory where the items are located
   * @param name The name of the searched item
   * @param type The object type of the searched item (File or Directory)
   * @return A String of a list of the full pathname of the items that matched the searched item
   */
  private String appendItems(Directory dir, String name, Object type) {
    String foundSearchItemList = "";
    Iterator<FileSystemContent> contents = dir.getDirectoryItems();
    // Appending content items into a list
    while (contents.hasNext()) {
      FileSystemContent item = contents.next();
      if (item.getClass() == type.getClass() && item.getName().equals(name)) {
        foundSearchItemList += item.getPath() + "\n";
      }
    }
    return foundSearchItemList;
  }


  /**
   * This method removes the additional search arguments and returns an array of only the pathnames
   * provided.
   * 
   * @param args The array that stores all the arguments the user input after the command
   * @param name The searched item's name
   * @param type The searched item's corresponding object (File or Directory object)
   * @return Returns an array of only the pathnames provided, otherwise null if no pathnames were
   *         given or invalid name and type arguments were provided.
   */
  private String[] getPathList(String[] args, String name, FileSystemContent type) {
    if (args == null || name == null || type == null) {
      return null;
    }
    // Turning args into an ArrayList
    ArrayList<String> pathList = new ArrayList<String>();
    for (String argument : args) {
      pathList.add(argument);
    }
    String typeLetter = "f";
    if (type instanceof Directory) {
      typeLetter = "d";
    }
    // Remove type and name from pathList
    pathList.remove("-type");
    pathList.remove(typeLetter);
    pathList.remove("-name");
    pathList.remove("\"" + name + "\"");

    if (pathList.size() <= 0) {
      return null;
    }
    String[] pathListArr = new String[pathList.size()];
    pathListArr = pathList.toArray(pathListArr);
    return pathListArr;
  }

  /**
   * The searched item's name is found and returned.
   * 
   * @param args The array that stores all the arguments the user input after the command
   * @return Returns the searched item's name, otherwise null if arguments are missing or the user
   *         did not follow the format: -name expression
   */
  private String getSearchItemName(String[] args) {
    if (args == null) {
      return null;
    }
    try {
      // Traverse entire array and looking for consecutive arguments: -name expression
      for (int i = 0; i < args.length; i++) {
        if (args[i].equals("-name")) {
          if (valiGate.isValidString(args[i + 1])) {
            String quotesRemoved = args[i + 1].substring(1, args[i + 1].length() - 1);
            return quotesRemoved;
          } else {
            return null;
          }
        }
      }
      return null;
    } catch (IndexOutOfBoundsException e) {
      return null;
    }
  }

  /**
   * The searched item's corresponding object (f for File or d for Directory) is returned based on
   * the provided arguments.
   * 
   * @param args The array that stores all the arguments the user input after the command
   * @return Returns the searched item's object type, otherwise null if arguments are missing or the
   *         user did not follow the format: -type [f|d]
   */
  private FileSystemContent getSearchItemObjType(String[] args) {
    if (args == null) {
      return null;
    }
    try {
      // Traverse entire array and looking for consecutive arguments: -type [f|d]
      for (int i = 0; i < args.length; i++) {
        if (args[i].equals("-type")) {
          if (args[i + 1].equals("f")) {
            return new File(null, null);
          } else if (args[i + 1].equals("d")) {
            return new Directory(null, null);
          } else {
            return null;
          }
        }
      }
      return null;
    } catch (IndexOutOfBoundsException e) {
      return null;
    }
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

}
