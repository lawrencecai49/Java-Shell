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

import java.util.*;
import driver.*;
import exception.EmptyStackException;
import exception.InvalidArgumentException;
import exception.InvalidPathException;
import filesystem.*;
import output.Output;

/**
 * This class stores the directory stack and implements the commands pushd and popd. popd will pop
 * the top entry from the directory stack and makes it the current working directory. pushd will
 * push the current working directory onto the directory stack and then the current working
 * directory will be changed to the given pathname.
 * 
 * @author Christina Ma
 */

public class DirectoryStack extends Command {

  /**
   * This is a Stack object that saves the directory stack
   */
  private Stack<String> dirStack;
  /**
   * This is a ValidationGate object that validates user input
   */
  private ValidationGate valiGate;
  /**
   * This is a FileSystem object that stores all files and directories contents
   */
  private FileSystem filesys;

  /**
   * Constructor creates an instance of an object that executes the pushd and popd commands.
   * 
   * @param valiGate This is a ValidationGate object that validates user input
   * @param dirStack The directory stack
   * @param filesys This is a file system storing all files and directories contents
   */
  public DirectoryStack(Stack<String> dirStack, ValidationGate valiGate, FileSystem filesys) {
    this.dirStack = dirStack;
    this.valiGate = valiGate;
    this.filesys = filesys;
  }

  /**
   * This method executes the correct method that is directed by the user based on the given command
   * argument.
   * 
   * @param userInput This stores all user input that has been parsed
   * @return An Output object stores the message to be printed and exception to be thrown
   */
  public Output runCommand(InputProcessor userInput) {
    // Execute the correct method to run command
    String[] path = userInput.getArgument();
    if (userInput.getCommand().equals("pushd")) {
      if (path == null || path.length == 0) {
        return new Output(null, new InvalidArgumentException("pushd takes in one argument"));
      } else if (path.length == 1) {
        if (!valiGate.isValidPathname(path[0])) {
          return new Output(null, new InvalidPathException(path[0] + " is not a valid pathname."));
        }
        return pushd(path[0], dirStack, userInput);
      } else {
        return new Output(null, new InvalidArgumentException("pushd takes in one argument"));
      }
    } else {
      return popd(dirStack, path);
    }
  }

  /**
   * Push the current working directory onto the directory stack and then changes the current
   * working directory to the given pathname (relative or absolute pathname).
   * 
   * @param pathname The pathname of the desired directory (relative or absolute)
   * @param dirStack The directory stack
   * @param userInput This stores all user input that's been parsed
   * @return An Output object stores the message to be printed and the exception to be thrown if an
   *         exception occurs
   */
  private Output pushd(String pathname, Stack<String> dirStack, InputProcessor userInput) {

    try {
      // Getting current working directory
      Directory currentDir = filesys.getCurrentDirectory();
      String currentDirPath = currentDir.getPath();

      // Changes directory
      Directory newDir = filesys.findDirectory(pathname);
      filesys.setCurrentDirectory(newDir);

      // Push currentDirPath onto the directory stack
      dirStack.push(currentDirPath);
    } catch (InvalidPathException e) {
      return new Output(null, e);
    }
    return new Output(null, null);

  }

  /**
   * This method pop the top entry from the directory stack and makes it the current working
   * directory.
   * 
   * @param dirStack The directory stack
   * @return An Output object stores the message to be printed and exception to be thrown
   */
  private Output popd(Stack<String> dirStack, String[] path) {
    try {
      if (path != null) {
        return new Output(null, new InvalidArgumentException("popd take no arguments"));
      }
      // Checks if stack is empty
      if (dirStack.isEmpty()) {
        return new Output(null, new EmptyStackException("Directory Stack is empty"));
      }
      // Changing the current working directory to the top entry in dirStack
      String topPathname = dirStack.pop();
      Directory topDir = filesys.findDirectory(topPathname);
      filesys.setCurrentDirectory(topDir);
    } catch (InvalidPathException e) {
      return new Output(null, e);
    }
    return new Output(null, null);
  }
}
