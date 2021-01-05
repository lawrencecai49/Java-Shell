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
import driver.*;
import exception.*;
import java.lang.reflect.*;

/**
 * The ManClass implements the man command and displays the documentation of each command in
 * argument
 * 
 * @author Yuanyuan Li
 */
public class ManClass extends Command {

  /**
   * Class Constructor
   *
   */
  public ManClass() {

  }

  /**
   * This method contains the documentation for rm command
   * 
   * @return The documentation for rm
   */
  private String rm() {
    return "rm DIR\r\n" + "\r\n" + "The program will remove the DIR from the file system. "
        + "This also removes all the children of DIR \r\n"
        + "recursively. The program will give back error messages when any error occurs.";
  }

  /**
   * This method contains the documentation for exit command
   * 
   * @return The documentation for exit
   */
  private String exit() {
    return "exit \r\n\nExit the program";
  }

  /**
   * This method contains the documentation for mkdir command
   * 
   * @return The documentation for mkdir
   */
  private String mkdir() {
    return "mkdir DIR [DIR...]\r\n" + "\r\n" + "option: [DIR...]\r\n" + "\r\n"
        + "The program will create directories, each of which may be relative to the current"
        + " directory or maybe a full path. \r\n"
        + "And it will continue to create directories until hit the first invalid DIR. "
        + "If any error occurs, the program will give back error messages.";
  }

  /**
   * This method contains the documentation for cd command
   * 
   * @return The documentation for cd
   */
  private String cd() {
    return "cd DIR\r\n" + "\r\n"
        + "Change the current directory to DIR ( DIR can be both a full path or "
        + "relative to current directory).";
  }

  /**
   * This method contains the documentation for ls
   * 
   * @return The documentation for ls
   */
  private String ls() {
    return "ls [-R][PATH...]\r\n" + "\r\n" + "option: [-R] [PATH...]\r\n" + "\r\n"
        + "If [-R] is present, recursively list all subdirectories.\r\n" + "\r\n"
        + "If no paths are given, the program will display the contents (file or"
        + " directory) of the current directory, with a new line following each "
        + "of the content (file or directory).\r\n"
        + "Otherwise, for each path p, the order listed:\r\n"
        + "If p specifies a file, the program will display p\r\n"
        + "If p specifies a directory, the program will display p, a colon, then"
        + " the contents of that directory, then an extra new line.\r\n"
        + "If any error occur, the program will send error messages.";
  }

  /**
   * This method contains the documentation for pwd command
   * 
   * @return The documentation for pwd
   */
  private String pwd() {
    return "pwd\r\n" + "\r\n"
        + "The program will print the full pathname of the current working directory.";
  }

  /**
   * This method contains the documentation for mv command
   * 
   * @return The documentation for mv
   */
  private String mv() {
    return "mv OLDPATH NEWPATH\r\n" + "\r\n"
        + "The program will move item OLDPATH to NEWPATH.Both OLDPATH and NEWPATH may "
        + "be relative to the current directory or may be full paths. \r\n"
        + "If NEWPATH is a directory, move the item into the directory. "
        + "The program will send error message if any error occurs.";
  }

  /**
   * This method contains the documentation for cp
   * 
   * @return The documentation for cp
   */
  private String cp() {
    return "cp OLDPATH NEWPATH\r\n" + "\r\n"
        + "The program will copy item OLDPATH to NEWPATH.Both OLDPATH and NEWPATH may be"
        + " relative to the current directory or may be full paths. \r\n"
        + "If NEWPATH is adirectory, copy the item into the directory."
        + " The program will send error message if any error occurs.";
  }

  /**
   * This method contains the documentation for cat command
   * 
   * @return The documentation for cat
   */
  private String cat() {
    return "cat FILE...\r\n" + "\r\n"
        + "The program will display the contents of FILE and other files on theconsole in "
        + "the shell. The program will continue to process the arguments of cat until\r\n"
        + "it hits the first invalid argument and stop."
        + " If any error occurs, the program will display error messages.";
  }

  /**
   * This method contains the documentation for curl command
   * 
   * @return The documentation for curl
   */
  private String curl() {
    return "curl URL\r\n" + "\r\n"
        + "The program will retrieve the file at that URL (a website address) and add"
        + " it to the current working directory.";
  }

  /**
   * This method contains the documentation for pushd command
   * 
   * @return The documentation for pushd
   */
  private String pushd() {
    return "pushd DIR\r\n" + "\r\n"
        + "The program will push the current working directory (full pathname) onto "
        + "directory stack and change the current working directory to\r\n"
        + "DIR (DIR can be relative or full path).";
  }

  /**
   * This method contains the documentation for popd command
   * 
   * @return The documentation for popd
   */
  private String popd() {
    return "popd\r\n" + "\r\n"
        + "The program will remove the top entry from the directory stack and change "
        + "the current working directory to it.\r\n"
        + "If the directory stack is empty, the program will give back an error message.";
  }

  /**
   * This method contains the documentation for history command
   * 
   * @return The documentation for history
   */
  private String history() {
    return "history [number]\r\n" + "\r\n" + "option: [number]\r\n" + "\r\n"
        + "The program will print out recent commands, one per line.\r\n"
        + "The output has two columns. The first column is numbered such that the line "
        + "with the highest number is the most recent command. The second column contains "
        + "the actual command.\r\n" + "\r\n"
        + "If the option [number] is specified, the program will print "
        + "the last [number] commands.";
  }

  /**
   * This method contains the documentation for echo command
   * 
   * @return The documentation for echo
   */
  private String echo() {
    return "echo STRING \r\n" + "\r\n" + "The program will output the STRING";
  }

  /**
   * This method contains the documentation for man command
   * 
   * @return The documentation for man
   */
  private String man() {
    return "man CMD1 \r\n" + "\r\n" + "The program will display documentation(s) for CMD."
        + "\r\nIf any error occurs, the program will send error messages.";
  }

  /**
   * This method contains the documentation for saveJShell command
   * 
   * @return The documentation for saveJShell
   */
  private String saveJShell() {
    return "saveJShell FileName\r\n" + "\r\n"
        + "This command will interact with the real file system on the computer."
        + " The program will save the entire state of the program "
        + "(entire mock filesystem including\r\nany state of any of the commands in a file)"
        + " and write to a file, namely FileName."
        + " And the file FileName is stored on the actual file system of the computer.\r\n"
        + "If the file FileName is not an existing file, create and then store.\r\n"
        + "If the file FileName has already existed, overwrite the file completely.\r\n"
        + "The program will send error messages when any error occurs.";
  }

  /**
   * This method contains the documentation for loadJShell command
   * 
   * @return The documentation for loadJShell
   */
  private String loadJShell() {
    return "loadJShell FileName\r\n" + "\r\n"
        + "The program will load the contents of the FileName and reinitialize everything "
        + "that was saved previously into the FileName. "
        + "If users start with new JShell session and\r\nwant to resume activity from where "
        + "they left out previously, they need to type this command before any"
        + " command has been typed in the program. " + "Otherwise, the\r\ncommand will be disabled."
        + " If there is any error occurs, the program will send error messages.";
  }

  /**
   * This method contains the documentation for search command
   * 
   * @return The documentation for search
   */
  private String search() {
    return "search path ... -type [f|d] -name expression\r\n" + "\r\n"
        + "The program will search through path (one or more) and find all files or"
        + " directories have name exactly expression.\r\n"
        + "The path (one or more) can be absolute or relative path."
        + " And -type f is search for files and -type d is search for directories.\r\n"
        + "If there is any error, the program will send error messages.";
  }

  /**
   * This method contains the documentation for tree command
   * 
   * @return The documentation for tree
   */
  private String tree() {
    return "tree\r\n" + "\r\n" + "The program will visualize the mock file system."
        + " If there is any error, the program will send error messages.";
  }


  /**
   * This method is for finding the documentation by the command name
   * 
   * @param commandName Each command name in user's input command argument
   * @return Return the documentation for commandName
   * @throws InvalidArgumentException
   */
  private String displayDoc(String commandName) throws InvalidArgumentException {
    // use reflection to get the specific documentation
    try {
      Method cmdMethod = this.getClass().getDeclaredMethod(commandName);
      cmdMethod.setAccessible(true);
      String output = (String) cmdMethod.invoke(this);
      return output;
    } catch (NoSuchMethodException | SecurityException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException e) {
      // error message
      throw new InvalidArgumentException("Error: Documentation not found, not a valid command");
    }

  }

  /**
   * This method executes the man command and output the documentation of each command in argument
   * 
   * @param userInput This is an InputProcessor object that contains parse user input
   * @return output This contains the output information
   */
  public Output runCommand(InputProcessor userInput) {
    Output output = new Output(null, null);
    String[] argument = userInput.getArgument();
    // check the validation of the user input
    if (argument == null)
      output.setException(new InvalidArgumentException("Invalid argument: no argument provide"));
    // several arguments
    else if (argument != null && argument.length > 1)
      output.setException(
          new InvalidArgumentException("Invalid argument: man command only takes one argument"));
    // call the displayDoc to display the documentation for the command in the argument
    else {
      String outputMes;
      try {
        outputMes = "\n------------------------------\n" + this.displayDoc(argument[0])
            + "\n------------------------------\n";
        output.setOutputMessage(outputMes);
      } catch (InvalidArgumentException e) {
        output.setException(e);
      }
    }
    return output;
  }
}


