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

import static org.junit.Assert.*;
import org.junit.Test;
import driver.InputProcessor;

public class InputProcessorTest {

  /**
   * This class is a JUnit test for InputProcessor class
   * 
   * @author Yuanyuan Li
   */

  @SuppressWarnings("deprecation")
  @Test
  /**
   * This test is for user input with command
   */
  public void onlyCommand() {
    // with no white space
    InputProcessor userCommand = new InputProcessor("command");
    assertEquals("command", userCommand.getCommand());
    assertEquals(null, userCommand.getArgument());
    assertEquals(null, userCommand.getRedirectionOperator());
    assertEquals(null, userCommand.getRedirectionPathname());

    // with whit space
    InputProcessor userCommand2 = new InputProcessor("  command  ");
    assertEquals("command", userCommand2.getCommand());
    assertEquals(null, userCommand2.getArgument());
    assertEquals(null, userCommand2.getRedirectionOperator());
    assertEquals(null, userCommand2.getRedirectionPathname());
  }

  @Test
  /**
   * This test is for user input with command and argument(s)
   */
  public void commandAndArguments() {
    // one argument
    InputProcessor userCommand = new InputProcessor("command dir1");
    assertEquals("command", userCommand.getCommand());
    assertEquals("dir1", userCommand.getArgument()[0]);
    assertEquals(1, userCommand.getArgument().length);
    assertEquals(null, userCommand.getRedirectionOperator());
    assertEquals(null, userCommand.getRedirectionPathname());

    // two arguments
    // case 1: one space
    InputProcessor userCommand2 = new InputProcessor("command dir1 dir2");
    assertEquals("command", userCommand2.getCommand());
    assertEquals("dir1", userCommand2.getArgument()[0]);
    assertEquals("dir2", userCommand2.getArgument()[1]);
    assertEquals(2, userCommand2.getArgument().length);
    assertEquals(null, userCommand2.getRedirectionOperator());
    assertEquals(null, userCommand2.getRedirectionPathname());


    // case 2: more than one spaces
    InputProcessor userCommand3 = new InputProcessor("  command   dir1    dir2     ");
    assertEquals("command", userCommand3.getCommand());
    assertEquals("dir1", userCommand3.getArgument()[0]);
    assertEquals("dir2", userCommand3.getArgument()[1]);
    assertEquals(2, userCommand3.getArgument().length);
    assertEquals(null, userCommand3.getRedirectionOperator());
    assertEquals(null, userCommand3.getRedirectionPathname());


    // more than two arguments
    // case 1: one space
    InputProcessor userCommand4 = new InputProcessor("command dir1 dir2 dir3");
    assertEquals("command", userCommand4.getCommand());
    assertEquals("dir1", userCommand4.getArgument()[0]);
    assertEquals("dir2", userCommand4.getArgument()[1]);
    assertEquals("dir3", userCommand4.getArgument()[2]);
    assertEquals(3, userCommand4.getArgument().length);
    assertEquals(null, userCommand4.getRedirectionOperator());
    assertEquals(null, userCommand4.getRedirectionPathname());

    // case 2: more than one spaces
    InputProcessor userCommand5 = new InputProcessor("    command   dir1  dir2     dir3  ");
    assertEquals("command", userCommand5.getCommand());
    assertEquals("dir1", userCommand5.getArgument()[0]);
    assertEquals("dir2", userCommand5.getArgument()[1]);
    assertEquals("dir3", userCommand5.getArgument()[2]);
    assertEquals(3, userCommand5.getArgument().length);
    assertEquals(null, userCommand5.getRedirectionOperator());
    assertEquals(null, userCommand5.getRedirectionPathname());
  }

  @Test
  /**
   * This test is for user input with command, arguments, and redirection operator
   */
  public void completeCommandLine1() {
    // case 1: attach to argument and ">"
    InputProcessor userCommand = new InputProcessor("command dir1 dir2>");
    assertEquals("command", userCommand.getCommand());
    assertEquals("dir1", userCommand.getArgument()[0]);
    assertEquals("dir2", userCommand.getArgument()[1]);
    assertEquals(2, userCommand.getArgument().length);
    assertEquals(">", userCommand.getRedirectionOperator());
    assertEquals("", userCommand.getRedirectionPathname());

    // case 2: attach to argument and ">>"
    InputProcessor userCommand2 = new InputProcessor("command dir1 dir2>>");
    assertEquals("command", userCommand2.getCommand());
    assertEquals("dir1", userCommand2.getArgument()[0]);
    assertEquals("dir2", userCommand2.getArgument()[1]);
    assertEquals(2, userCommand2.getArgument().length);
    assertEquals(">>", userCommand2.getRedirectionOperator());
    assertEquals("", userCommand2.getRedirectionPathname());

    // case 3: not attach to argument and ">" with one space
    InputProcessor userCommand3 = new InputProcessor("command dir1 dir2 >");
    assertEquals("command", userCommand3.getCommand());
    assertEquals("dir1", userCommand3.getArgument()[0]);
    assertEquals("dir2", userCommand3.getArgument()[1]);
    assertEquals(2, userCommand3.getArgument().length);
    assertEquals(">", userCommand3.getRedirectionOperator());
    assertEquals("", userCommand3.getRedirectionPathname());

    // case 4: not attach to argument and ">>" with one space
    InputProcessor userCommand4 = new InputProcessor("command dir1 dir2 >>");
    assertEquals("command", userCommand4.getCommand());
    assertEquals("dir1", userCommand4.getArgument()[0]);
    assertEquals("dir2", userCommand4.getArgument()[1]);
    assertEquals(2, userCommand4.getArgument().length);
    assertEquals(">>", userCommand4.getRedirectionOperator());
    assertEquals("", userCommand4.getRedirectionPathname());

    // case 5: not attach to argument and ">" with more than one white space
    InputProcessor userCommand5 = new InputProcessor("    command    dir1    dir2  > ");
    assertEquals("command", userCommand5.getCommand());
    assertEquals("dir1", userCommand5.getArgument()[0]);
    assertEquals("dir2", userCommand5.getArgument()[1]);
    assertEquals(2, userCommand5.getArgument().length);
    assertEquals(">", userCommand5.getRedirectionOperator());
    assertEquals("", userCommand5.getRedirectionPathname());

    // case 6: not attach to argument and ">>" with more than one white space
    InputProcessor userCommand6 = new InputProcessor("  command     dir1     dir2  >>  ");
    assertEquals("command", userCommand6.getCommand());
    assertEquals("dir1", userCommand6.getArgument()[0]);
    assertEquals("dir2", userCommand6.getArgument()[1]);
    assertEquals(2, userCommand6.getArgument().length);
    assertEquals(">>", userCommand6.getRedirectionOperator());
    assertEquals("", userCommand6.getRedirectionPathname());

  }

  @Test
  /**
   * This test if for user input with command, argument(s), redirection operator and redirection
   * path
   */
  public void completeCommandLine2() {
    // case 1: attach to argument & path and ">"
    InputProcessor userCommand = new InputProcessor("command dir1 dir2>path");
    assertEquals("command", userCommand.getCommand());
    assertEquals("dir1", userCommand.getArgument()[0]);
    assertEquals("dir2", userCommand.getArgument()[1]);
    assertEquals(2, userCommand.getArgument().length);
    assertEquals(">", userCommand.getRedirectionOperator());
    assertEquals("path", userCommand.getRedirectionPathname());

    // case 2: attach to argument & path and ">>"
    InputProcessor userCommand2 = new InputProcessor("command dir1 dir2>>path");
    assertEquals("command", userCommand2.getCommand());
    assertEquals("dir1", userCommand2.getArgument()[0]);
    assertEquals("dir2", userCommand2.getArgument()[1]);
    assertEquals(2, userCommand2.getArgument().length);
    assertEquals(">>", userCommand2.getRedirectionOperator());
    assertEquals("path", userCommand2.getRedirectionPathname());

    // case 3: not attach to argument & path and ">" with one space
    InputProcessor userCommand3 = new InputProcessor("command dir1 dir2 > path");
    assertEquals("command", userCommand3.getCommand());
    assertEquals("dir1", userCommand3.getArgument()[0]);
    assertEquals("dir2", userCommand3.getArgument()[1]);
    assertEquals(2, userCommand3.getArgument().length);
    assertEquals(">", userCommand3.getRedirectionOperator());
    assertEquals("path", userCommand3.getRedirectionPathname());

    // case 4: not attach to argument & path and ">>" with one space
    InputProcessor userCommand4 = new InputProcessor("command dir1 dir2 >> path");
    assertEquals("command", userCommand4.getCommand());
    assertEquals("dir1", userCommand4.getArgument()[0]);
    assertEquals("dir2", userCommand4.getArgument()[1]);
    assertEquals(2, userCommand4.getArgument().length);
    assertEquals(">>", userCommand4.getRedirectionOperator());
    assertEquals("path", userCommand4.getRedirectionPathname());

    // case 5: not attach to argument & path and ">" with more than one white space
    InputProcessor userCommand5 = new InputProcessor("  command     dir1    dir2    >  path  ");
    assertEquals("command", userCommand5.getCommand());
    assertEquals("dir1", userCommand5.getArgument()[0]);
    assertEquals("dir2", userCommand5.getArgument()[1]);
    assertEquals(2, userCommand5.getArgument().length);
    assertEquals(">", userCommand5.getRedirectionOperator());
    assertEquals("path", userCommand5.getRedirectionPathname());

    // case 6: not attach to argument & path and ">>" with more than one white space
    InputProcessor userCommand6 =
        new InputProcessor("        command     dir1      dir2   >>  path  ");
    assertEquals("command", userCommand6.getCommand());
    assertEquals("dir1", userCommand6.getArgument()[0]);
    assertEquals("dir2", userCommand6.getArgument()[1]);
    assertEquals(2, userCommand6.getArgument().length);
    assertEquals(">>", userCommand6.getRedirectionOperator());
    assertEquals("path", userCommand6.getRedirectionPathname());
  }

  @Test
  /**
   * This test if for user input with command, argument(s), redirection operator and redirection
   * path (with white space)
   */
  public void completeCommandLine3() {
    // case 1: attach to argument & path and ">"
    InputProcessor userCommand = new InputProcessor("command dir1 dir2>path name");
    assertEquals("command", userCommand.getCommand());
    assertEquals("dir1", userCommand.getArgument()[0]);
    assertEquals("dir2", userCommand.getArgument()[1]);
    assertEquals(2, userCommand.getArgument().length);
    assertEquals(">", userCommand.getRedirectionOperator());
    assertEquals("path name", userCommand.getRedirectionPathname());

    // case 2: attach to argument & path and ">>"
    InputProcessor userCommand2 = new InputProcessor("command dir1 dir2>>path name");
    assertEquals("command", userCommand2.getCommand());
    assertEquals("dir1", userCommand2.getArgument()[0]);
    assertEquals("dir2", userCommand2.getArgument()[1]);
    assertEquals(2, userCommand2.getArgument().length);
    assertEquals(">>", userCommand2.getRedirectionOperator());
    assertEquals("path name", userCommand2.getRedirectionPathname());

    // case 3: not attach to argument & path and ">" with one space
    InputProcessor userCommand3 = new InputProcessor("command dir1 dir2 > path name");
    assertEquals("command", userCommand3.getCommand());
    assertEquals("dir1", userCommand3.getArgument()[0]);
    assertEquals("dir2", userCommand3.getArgument()[1]);
    assertEquals(2, userCommand3.getArgument().length);
    assertEquals(">", userCommand3.getRedirectionOperator());
    assertEquals("path name", userCommand3.getRedirectionPathname());

    // case 4: not attach to argument & path and ">>" with one space
    InputProcessor userCommand4 = new InputProcessor("command dir1 dir2 >> path name");
    assertEquals("command", userCommand4.getCommand());
    assertEquals("dir1", userCommand4.getArgument()[0]);
    assertEquals("dir2", userCommand4.getArgument()[1]);
    assertEquals(2, userCommand4.getArgument().length);
    assertEquals(">>", userCommand4.getRedirectionOperator());
    assertEquals("path name", userCommand4.getRedirectionPathname());

    // case 5: not attach to argument & path and ">" with more than one white space
    InputProcessor userCommand5 = new InputProcessor("  command     dir1    dir2    >  path name ");
    assertEquals("command", userCommand5.getCommand());
    assertEquals("dir1", userCommand5.getArgument()[0]);
    assertEquals("dir2", userCommand5.getArgument()[1]);
    assertEquals(2, userCommand5.getArgument().length);
    assertEquals(">", userCommand5.getRedirectionOperator());
    assertEquals("path name", userCommand5.getRedirectionPathname());

    // case 6: not attach to argument & path and ">>" with more than one white space
    InputProcessor userCommand6 =
        new InputProcessor("        command     dir1      dir2   >>  path name ");
    assertEquals("command", userCommand6.getCommand());
    assertEquals("dir1", userCommand6.getArgument()[0]);
    assertEquals("dir2", userCommand6.getArgument()[1]);
    assertEquals(2, userCommand6.getArgument().length);
    assertEquals(">>", userCommand6.getRedirectionOperator());
    assertEquals("path name", userCommand6.getRedirectionPathname());
  }

  @Test
  /**
   * This test is for user input with string
   */
  public void withString() {
    // case 1: with string (no double quote inside string) and no redirection
    InputProcessor userCommand = new InputProcessor("cmd \"string\"");
    assertEquals("cmd", userCommand.getCommand());
    assertEquals("\"string\"", userCommand.getArgument()[0]);
    assertEquals(1, userCommand.getArgument().length);
    assertEquals(null, userCommand.getRedirectionOperator());
    assertEquals(null, userCommand.getRedirectionPathname());

    // case 2: with string (double quotes and redirection operator inside string) and no redirection
    InputProcessor userCommand2 = new InputProcessor("cmd \"str >\"i\"ng\"");
    assertEquals("cmd", userCommand2.getCommand());
    assertEquals("\"str >\"i\"ng\"", userCommand2.getArgument()[0]);
    assertEquals(1, userCommand2.getArgument().length);
    assertEquals(null, userCommand2.getRedirectionOperator());
    assertEquals(null, userCommand2.getRedirectionPathname());

    // case 3: with string (double quotes and redirection operator inside string) and redirection
    // (attached)
    InputProcessor userCommand3 = new InputProcessor("cmd \"str >\"i\"ng\">");
    assertEquals("cmd", userCommand3.getCommand());
    assertEquals("\"str >\"i\"ng\"", userCommand3.getArgument()[0]);
    assertEquals(1, userCommand3.getArgument().length);
    assertEquals(">", userCommand3.getRedirectionOperator());
    assertEquals("", userCommand3.getRedirectionPathname());

    // case 4: with string (double quotes and redirection operator inside string) and redirection
    InputProcessor userCommand4 = new InputProcessor("cmd \"str >\"i\"ng\" >");
    assertEquals("cmd", userCommand4.getCommand());
    assertEquals("\"str >\"i\"ng\"", userCommand4.getArgument()[0]);
    assertEquals(1, userCommand4.getArgument().length);
    assertEquals(">", userCommand4.getRedirectionOperator());
    assertEquals("", userCommand4.getRedirectionPathname());

    // case 5: with string (double quotes and redirection operator inside string), other argument
    // and redirection
    InputProcessor userCommand5 = new InputProcessor("cmd dir1 \"str >\"i\"ng\" dir2 > ");
    assertEquals("cmd", userCommand5.getCommand());
    assertEquals("dir1", userCommand5.getArgument()[0]);
    assertEquals("\"str >\"i\"ng\"", userCommand5.getArgument()[1]);
    assertEquals("dir2", userCommand5.getArgument()[2]);
    assertEquals(3, userCommand5.getArgument().length);
    assertEquals(">", userCommand5.getRedirectionOperator());
    assertEquals("", userCommand5.getRedirectionPathname());

    // case 6: with string (double quotes and redirection operator inside string) , other arguments
    // and redirection (path and
    // operator)
    InputProcessor userCommand6 =
        new InputProcessor(" cmd    dir1  \"str >\"i\"ng\"   dir2  dir3  >  path name");
    assertEquals("cmd", userCommand6.getCommand());
    assertEquals("dir1", userCommand6.getArgument()[0]);
    assertEquals("\"str >\"i\"ng\"", userCommand6.getArgument()[1]);
    assertEquals("dir2", userCommand6.getArgument()[2]);
    assertEquals("dir3", userCommand6.getArgument()[3]);
    assertEquals(4, userCommand6.getArgument().length);
    assertEquals(">", userCommand6.getRedirectionOperator());
    assertEquals("path name", userCommand6.getRedirectionPathname());
  }
}

