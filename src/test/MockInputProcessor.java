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
package test;

import driver.InputProcessor;

/**
 * This is a mock InputProcessor class for JUnit Testing
 * 
 * @author Man Hei Ho
 */
public class MockInputProcessor extends InputProcessor {

  private String cmd;
  private String[] args;
  private String redirOper;
  private String redirPath;

  public MockInputProcessor(String cmd, String[] args, String redirOper, String redirPath) {
    this.cmd = cmd;
    this.args = args;
    this.redirOper = redirOper;
    this.redirPath = redirPath;
  }

  public String getCommand() {
    return this.cmd;
  }

  public String[] getArgument() {
    return this.args;
  }

  public String getRedirectionOperator() {
    return this.redirOper;
  }

  public String getRedirectionPathname() {
    return this.redirPath;
  }
}
