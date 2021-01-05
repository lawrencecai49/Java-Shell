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
import java.util.Stack;
import org.junit.Before;
import org.junit.Test;
import command.DirectoryStack;
import filesystem.Directory;
import output.Output;

/**
 * This is a JUnit test for DirectoryStack class (popd and pushd commands).
 * 
 * @author Christina Ma
 */
public class DirectoryStackTest {
  private DirectoryStack dStackCmd;
  private Output output;
  private MockInputProcessor mockInput;
  private MockFileSystem mockfs;
  private Stack<String> dirStack;
  private MockValidationGate valiGate;

  /**
   * This method initializes required objects that will be used.
   */
  @Before
  public void setUp() {
    this.mockfs = new MockFileSystem();
    this.dirStack = new Stack<String>();
    this.valiGate = new MockValidationGate();
    this.dStackCmd = new DirectoryStack(dirStack, valiGate, mockfs);
  }

  /**
   * This test is when the user popd from an empty DirectoryStack.
   */
  @Test
  public void testRunCommandPopdFromEmptyStack() {
    this.mockInput = new MockInputProcessor("popd", null, null, null);
    this.output = dStackCmd.runCommand(mockInput);
    assertEquals(null, output.getOutputMessage());
    assertEquals("Directory Stack is empty", output.getException().getMessage());
  }

  /**
   * This test is when the user pushd onto an empty DirectoryStack.
   */
  @Test
  public void testRunCommandPushdOnEmptyStack() {
    String[] argument = {"ValidPath"};
    this.mockInput = new MockInputProcessor("pushd", argument, null, null);
    this.output = dStackCmd.runCommand(mockInput);
    assertEquals(null, output.getOutputMessage());
    assertEquals(null, output.getException());
    assertEquals("ValidPath", mockfs.getCurrentDirectory().getPath());
  }

  /**
   * This is test when user wants to pushd and then immediately popd.
   */
  @Test
  public void testRunCommandPushdThenPopd() {
    // pushd
    String[] argument = {"ValidPath"};
    this.mockInput = new MockInputProcessor("pushd", argument, null, null);
    this.output = dStackCmd.runCommand(mockInput);
    // popd
    this.mockInput = new MockInputProcessor("popd", null, null, null);
    this.output = dStackCmd.runCommand(mockInput);
    assertEquals(null, output.getOutputMessage());
    assertEquals(null, output.getException());
    assertEquals("currDir", mockfs.getCurrentDirectory().getPath());
  }

  /**
   * This test is when the user wants to pushd multiple times consecutively
   */
  @Test
  public void testRunCommandPushdPathsConsecutively() {
    // pushd 1
    String[] arg1 = {"ValidPath1"};
    dStackCmd.runCommand(new MockInputProcessor("pushd", arg1, null, null));
    assertEquals("ValidPath1", mockfs.getCurrentDirectory().getPath());
    // pushd 2
    String[] arg2 = {"ValidPath2"};
    dStackCmd.runCommand(new MockInputProcessor("pushd", arg2, null, null));
    assertEquals("ValidPath2", mockfs.getCurrentDirectory().getPath());
    // pushd 3
    String[] arg3 = {"ValidPath3"};
    this.output = dStackCmd.runCommand(new MockInputProcessor("pushd", arg3, null, null));
    assertEquals(null, output.getOutputMessage());
    assertEquals(null, output.getException());
    assertEquals("ValidPath3", mockfs.getCurrentDirectory().getPath());
  }

  /**
   * This test is when the user wants to popd multiple times consecutively from a filled
   * DirectoryStack
   */
  @Test
  public void testRunCommandPopdConsecutively() {
    testRunCommandPushdPathsConsecutively();
    // Current dirStack: (top of stack) ValidPath2, ValidPath1, currDir (bottom of stack)
    this.mockInput = new MockInputProcessor("popd", null, null, null);
    // popd 1
    this.output = dStackCmd.runCommand(mockInput);
    assertEquals(null, output.getOutputMessage());
    assertEquals(null, output.getException());
    assertEquals("ValidPath2", mockfs.getCurrentDirectory().getPath());
    // popd 2
    this.output = dStackCmd.runCommand(mockInput);
    assertEquals(null, output.getOutputMessage());
    assertEquals(null, output.getException());
    assertEquals("ValidPath1", mockfs.getCurrentDirectory().getPath());
    // popd 3
    this.output = dStackCmd.runCommand(mockInput);
    assertEquals(null, output.getOutputMessage());
    assertEquals(null, output.getException());
    assertEquals("currDir", mockfs.getCurrentDirectory().getPath());
    // popd 4
    this.output = dStackCmd.runCommand(mockInput);
    assertEquals(null, output.getOutputMessage());
    assertEquals("Directory Stack is empty", output.getException().getMessage());
  }

  /**
   * This test is when the user wants to pushd, and then change the current working directory, and
   * then pushd again, and then popd until Directory Stack is empty.
   */
  @Test
  public void testRunCommandChangeDirectoryInBetweenPushdAndPopd() {
    // Command: pushd ValidPath1
    String[] arg1 = {"ValidPath1"};
    dStackCmd.runCommand(new MockInputProcessor("pushd", arg1, null, null));
    assertEquals("ValidPath1", mockfs.getCurrentDirectory().getPath());

    // Change current directory to AnotherValidPath
    mockfs.setCurrentDirectory(new Directory("AnotherValidPathDirName", "AnotherValidPath"));

    // Command: pushd ValidPath2
    String[] arg2 = {"ValidPath2"};
    dStackCmd.runCommand(new MockInputProcessor("pushd", arg2, null, null));
    assertEquals("ValidPath2", mockfs.getCurrentDirectory().getPath());

    // Current dirStack: (top of stack) AnotherValidPath, currDir (bottom of stack)
    this.mockInput = new MockInputProcessor("popd", null, null, null);
    // popd 1
    this.output = dStackCmd.runCommand(mockInput);
    assertEquals(null, output.getOutputMessage());
    assertEquals(null, output.getException());
    assertEquals("AnotherValidPath", mockfs.getCurrentDirectory().getPath());
    // popd 2
    this.output = dStackCmd.runCommand(mockInput);
    assertEquals(null, output.getOutputMessage());
    assertEquals(null, output.getException());
    assertEquals("currDir", mockfs.getCurrentDirectory().getPath());
    // popd 3
    this.output = dStackCmd.runCommand(mockInput);
    assertEquals(null, output.getOutputMessage());
    assertEquals("Directory Stack is empty", output.getException().getMessage());
  }

  /**
   * This test is when the user wants to pushd but has also given more than the additional arguments
   * required.
   */
  @Test
  public void testRunCommandPushdMultipleArguments() {
    String[] arg = {"ValidPath1", "ValidPath2"};
    this.output = dStackCmd.runCommand(new MockInputProcessor("pushd", arg, null, null));
    assertEquals("pushd takes in one argument", output.getException().getMessage());
  }

  /**
   * This test is when the user wants to popd but has given additional arguments.
   */
  @Test
  public void testRunCommandPopdMultipleArguments() {
    // Case #1: Given one argument
    String[] arg = {"ValidPath1"};
    this.output = dStackCmd.runCommand(new MockInputProcessor("popd", arg, null, null));
    assertEquals("popd take no arguments", output.getException().getMessage());

    // Case #2: Given two arguments
    String[] arg2 = {"ValidPath1", "ValidPah2"};
    this.output = dStackCmd.runCommand(new MockInputProcessor("popd", arg2, null, null));
    assertEquals("popd take no arguments", output.getException().getMessage());
  }

  /**
   * This test is when the user wants to pushd but has given an non-existing directory pathname
   */
  @Test
  public void testRunCommandPushdNonexist() {
    String[] arg = {"NonexistingValidPathname"};
    this.output = dStackCmd.runCommand(new MockInputProcessor("pushd", arg, null, null));
    assertEquals("NonexistingValidPathname does not refer to an existing directory",
        output.getException().getMessage());
  }

  /**
   * This test is when the user wants to pushd but has given an invalid pathname
   */
  @Test
  public void testRunCommandPushdInvalidPath() {
    String[] arg = {"InvalidPath"};
    this.output = dStackCmd.runCommand(new MockInputProcessor("pushd", arg, null, null));
    assertEquals("InvalidPath is not a valid pathname.", output.getException().getMessage());
  }
}
