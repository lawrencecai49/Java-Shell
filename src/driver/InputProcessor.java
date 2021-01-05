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
 * InputProcessor Class
 * 
 * Author: Yuanyuan Li
 * 
 * This Class will process the user's input into 4 different parts (command, argument,
 * redirectionOperator, redirectionPathname) command: The command name from user's input command
 * argument: The part of command without command, redirectionOperator, and redirection
 * redirectionOperator: Redirection Operator (">" and ">>") redirectionPathname: Redirection
 * pathname
 * 
 */
import java.util.ArrayList;

public class InputProcessor {

  private String command;
  private String[] argument;
  private String redirectionOperator;
  private String redirectionPathname;

  /**
   * This method will return a string that contains the command of user's input.
   * 
   * @return command name
   */
  public String getCommand() {
    return this.command;
  }

  /**
   * This method will return a array of string that contain all the arguments after command.
   * 
   * @return an array of string containing each part of argument
   */
  public String[] getArgument() {
    return this.argument;
  }

  /**
   * This method will return a string that contains the information of I/O redirection.
   * 
   * @return redirection operator
   */
  public String getRedirectionOperator() {
    return this.redirectionOperator;
  }

  /**
   * This method will return a string that contains the redirection pathname after I/O redirection.
   * 
   * @return redirection pathname
   */
  public String getRedirectionPathname() {
    return this.redirectionPathname;
  }

  /**
   * This method will get the command from user's input.
   * 
   * @param commandLine User's input command
   */
  private void processCommand(String commandLine) {
    commandLine = commandLine.trim();
    String[] arr = commandLine.split("\\s+");
    this.command = arr[0];
  }

  /**
   * This method will get I/O redirection and redirection pathname from user's input.
   * 
   * @param commandLine User's input command
   */
  private void processRedirection(String commandLine) {
    if (commandLine.indexOf(">>") != -1 && commandLine.indexOf(">>") <= commandLine.indexOf(">"))
      this.redirectionOperator = ">>";
    else if (commandLine.indexOf(">") != -1)
      this.redirectionOperator = ">";
    else
      this.redirectionOperator = null;
  }

  /**
   * This method will get redirection pathname from user's input.
   * 
   * @param commandLine User's input command
   */
  private void processPathname(String commandLine) {
    if (this.redirectionOperator != null) {
      if (this.redirectionOperator.equals(">"))
        this.redirectionPathname = commandLine.substring(commandLine.indexOf(">") + 1).trim();
      else
        this.redirectionPathname = commandLine.substring(commandLine.indexOf(">>") + 2).trim();
    } else
      this.redirectionPathname = null;
  }

  /**
   * This method is a helper function for ArgumentProcess. This will concatenate the STRING and the
   * part before and after STRING together.
   * 
   * @param firstSub The part before the STRING
   * @param str This is STRING
   * @param secondSub The part after the STRING
   * @return an array of String with three part concatenate together
   */
  private String[] concatString(String[] firstSub, String str, String[] secondSub) {
    ArrayList<String> strarr = new ArrayList<String>();
    for (int i = 0; i < firstSub.length + 1 + secondSub.length; i++) {
      if (i == firstSub.length)
        strarr.add("\"" + str + "\"");
      else if (i >= firstSub.length && !secondSub[i - 1 - firstSub.length].equals(""))
        strarr.add(secondSub[i - 1 - firstSub.length]);
      else if (i < firstSub.length && !firstSub[i].equals(""))
        strarr.add(firstSub[i]);
    }
    String[] arr = new String[strarr.size()];
    for (int i = 0; i < strarr.size(); i++) {
      arr[i] = strarr.get(i);
    }
    return arr;
  }

  /**
   * This method is a helper function for ArgumentProcess and will return a array of string that
   * contains arguments.
   * 
   * @param SubCommandLine A Command line without command
   * @return an array of string contain each part of the argument
   */
  private String[] processSubCommand(String subCommandLine) {
    // find the first and last index of double quote
    int indicater1 = subCommandLine.indexOf("\""), indicater2 = subCommandLine.lastIndexOf("\"");
    if (indicater1 == indicater2) {
      // not containing STRING, split by white spaces
      String[] arr = subCommandLine.split("\\s+");
      return arr;
    } else {
      // take out the STING and split the part before and after STRING by white spaces
      String subString = subCommandLine.substring(indicater1 + 1, indicater2);
      String[] firstSub = subCommandLine.substring(0, indicater1).split("\\s+");
      String[] secondSub = subCommandLine.substring(indicater2 + 1).trim().split("\\s+");
      // Concatenate
      String[] arr = this.concatString(firstSub, subString, secondSub);
      return arr;
    }
  }

  /**
   * This method will get an array of string containing each part of argument from user's input. If
   * argument is empty, then return null. If argument is not empty, If argument contains STRING,
   * other part without STRING will be split by white spaces and return both StIRNG and the
   * splitting argument in the same order as in command. If argument does not contain STRING,
   * argument will be split by white spaces and return. (The STRING will be surrounding by double
   * quote mark)
   * 
   * @param commandLine User's input command
   * @return an array of string containing each part of the argument
   */
  private void processArgument(String commandLine) {
    // find the sub command line without command, redirection operator and redirection pathname
    String subCommandLine = commandLine.trim().substring(this.command.length()).trim();

    // if there is redirection
    if (subCommandLine.lastIndexOf(">") > subCommandLine.lastIndexOf("\"")) {
      // substring from 0 to index of the first ">" after the index of the last double quote
      int indexOfRedir = subCommandLine.indexOf(">", subCommandLine.lastIndexOf("\""));
      String redirectionArgument = subCommandLine.substring(indexOfRedir).trim();
      subCommandLine = subCommandLine.substring(0, indexOfRedir).trim();
      // process redirection
      this.processRedirection(redirectionArgument);
      this.processPathname(redirectionArgument);
    }

    // no argument after command, return null
    if (subCommandLine.equals("")) {
      this.argument = null;
      return;
    }
    // find whether the sub command line contains String
    if (subCommandLine.contains("\"")) {
      this.argument = this.processSubCommand(subCommandLine);
    } else {
      this.argument = subCommandLine.split("\\s+");
    }
  }

  /**
   * This method will initialize the InputProcessor object with no input parameter.
   */
  public InputProcessor() {

  }

  /**
   * This method will initialize the InputProcessor object with input parameter.
   */
  public InputProcessor(String commandLine) {
    this.processCommand(commandLine);
    this.processArgument(commandLine);
  }
}
