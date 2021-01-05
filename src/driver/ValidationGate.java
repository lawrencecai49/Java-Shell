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
package driver;

/**
 * This class check if the command, pathname, string, string of a number, and directory or file name
 * are valid and follow the restrictions.
 * 
 * @author Christina Ma
 */
public class ValidationGate {
  private String[] allCommands = {"exit", "mkdir", "cd", "ls", "pwd", "pushd", "popd", "history",
      "cat", "echo", "man", "tree", "curl", "search", "cp", "mv", "rm"};
  private String[] inValidPathnameChar = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "{",
      "}", "~", "|", "<", ">", "?", " ", "//"};
  private String[] moreInValidNamingChar = {"/", ".", " "};
  private String[] validRedirectCommands =
      {"ls", "pwd", "history", "cat", "echo", "man", "tree", "search"};

  /**
   * Class Constructor
   */
  public ValidationGate() {

  }

  /**
   * This method validates if the given command supports redirection.
   * 
   * @param command A string containing the user's command
   * @return Return true if command support redirection, false otherwise.
   */
  public boolean isValidToRedirect(String command) {
    for (String cmd : validRedirectCommands) {
      if (command.equals(cmd)) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method validates if the given command is valid.
   * 
   * @param command A string containing the user's command
   * @return Return true if command is valid, otherwise false
   */
  public boolean isValidCommand(String command) {
    for (int i = 0; i < allCommands.length; i++) {
      if (allCommands[i].equals(command)) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method validates if the given pathname uses valid characters.
   * 
   * @param pathname A string containing the pathname (can be a relative or absolute pathname)
   * @return Return true if pathname is valid, otherwise false
   */
  public boolean isValidPathname(String pathname) {
    for (String character : inValidPathnameChar) {
      if (pathname.contains(character)) {
        return false;
      }
    }
    if (pathname.isEmpty()) {
      return false;
    }
    return true;

  }

  /**
   * This method validates if the given string does not have double questions within the string
   * itself aside from the double quotations that surround the string.
   * 
   * @param str The given string
   * @return Return true if string is a valid string, otherwise false
   */
  public boolean isValidString(String str) {
    // Check if str is surrounded by double quotations
    if (str.endsWith("\"") && str.startsWith("\"") && str.length() > 1) {
      // Check for no double quotations within str
      String subStr = str.substring(str.indexOf("\"") + 1, str.length() - 1);
      if (!subStr.contains("\"") && !subStr.contains(">")) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method validates if the given string can be converted into a valid integer.
   * 
   * @param num The given string
   * @return Return true if num is an integer, otherwise false
   */
  public boolean isValidInteger(String num) {
    if (num == null) {
      return false;
    }
    try {
      Integer.parseInt(num);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  /**
   * This method validates if the given string follow the character restrictions for directory and
   * file naming.
   * 
   * @param dirName The given string
   * @return Return true if dirName is a valid file or directory name, otherwise false
   */
  public boolean isValidDirectoryName(String dirName) {
    // Checks for invalid character restrictions for pathname
    if (!isValidPathname(dirName) || dirName.isEmpty()) {
      return false;
    }
    // Checks for the additional invalid character restrictions for directory/file names
    for (String character : moreInValidNamingChar) {
      if (dirName.contains(character)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Validates if the given pathname is an absolute path but does not validate if the pathname is
   * valid.
   * 
   * @param pathname The given pathname
   * @return Return true if pathname is a absolute path, otherwise false
   */
  public boolean isValidFullPath(String pathname) {
    if (pathname.startsWith("/")) {
      return true;
    }
    return false;
  }

}
