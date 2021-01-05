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
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Stack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import exception.InvalidArgumentException;
import exception.InvalidCommandException;
import exception.InvalidNameException;
import exception.InvalidPathException;
import exception.ItemAlreadyExistsException;
import exception.RedirectionNotSupportException;
import filesystem.Directory;
import filesystem.File;
import filesystem.FileSystem;
import filesystem.JFileSystem;
import storage.JShellLoading;
import storage.JShellSaving;

public class JShellSavingLoadingTest {

  private FileSystem fs;
  private Directory dir1;
  private Directory dir2;
  private Directory dir3;
  private File file1;
  private File file2;
  private MockInputStorage mis;
  private Stack<String> dirStack;

  @Before
  /**
   * Initialize the basic data need for saveJShell and loadJShell
   * 
   * @throws InvalidNameException
   * @throws ItemAlreadyExistsException
   * @throws InvalidPathException
   */
  public void setUp()
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    fs = JFileSystem.buildFileSystem();
    this.dirStack = new Stack<String>();
    ArrayList<String> inputHistory = new ArrayList<String>();
    this.mis = new MockInputStorage(inputHistory);
  }

  @After
  public void tearDown() throws Exception {
    dir1 = null;
    dir2 = null;
    dir3 = null;
    file1 = null;
    file2 = null;
    Field field = (fs.getClass()).getDeclaredField("currentSystem");
    field.setAccessible(true);
    field.set(null, null);
  }

  @Test
  /**
   * This test is for testing checkValidation Method in JShellSaving (not taking argument)
   */
  public void testCheckValidationMethod1() {
    // no argument provided
    MockInputProcessor userInput = new MockInputProcessor("saveJShell", null, null, null);
    JShellSaving js = new JShellSaving(this.mis, this.dirStack, this.fs);
    try {
      js.checkValidation(userInput);
    } catch (InvalidArgumentException | RedirectionNotSupportException | InvalidNameException e) {
      // check for the error message
      assertEquals("Invalid argument: saveJShell should take a FileName as an argument",
          e.getMessage());
    }

  }

  @Test
  /**
   * This test is for testing checkValidation method in JShellSaving (takes more than one argument)
   */
  public void testCheckValidationMethod3() {
    // more than one argument
    String[] argument = {"save1", "save2"};
    MockInputProcessor userInput = new MockInputProcessor("saveJShell", argument, null, null);
    JShellSaving js = new JShellSaving(this.mis, this.dirStack, this.fs);
    try {
      js.checkValidation(userInput);
    } catch (InvalidArgumentException | RedirectionNotSupportException | InvalidNameException e) {
      // check for the error message
      assertEquals("Invalid argument: saveJShell can only take one argument", e.getMessage());
    }
  }

  @Test
  /**
   * This test is for testing checkValidation method in JShellSaving (invalid FileName)
   */
  public void testCheckValidationMethod4_1() {
    // Illegal characters located in the middle
    String[] argument = {"s#a@v{e}.t_x>t"};
    MockInputProcessor userInput = new MockInputProcessor("saveJShell", argument, null, null);
    JShellSaving js = new JShellSaving(this.mis, this.dirStack, this.fs);
    try {
      js.checkValidation(userInput);
    } catch (InvalidArgumentException | RedirectionNotSupportException | InvalidNameException e) {
      // check for the error message
      assertEquals("Invalid name: FileName contains invalid characters", e.getMessage());
    }
  }

  @Test
  /**
   * This test is for testing checkValidation method in JShellSaving (invalid FileName)
   */
  public void testCheckValidationMethod4_2() {
    // Illegal characters located at the front
    String[] argument = {"/save"};
    MockInputProcessor userInput = new MockInputProcessor("saveJShell", argument, null, null);
    JShellSaving js = new JShellSaving(this.mis, this.dirStack, this.fs);
    try {
      js.checkValidation(userInput);
    } catch (InvalidArgumentException | RedirectionNotSupportException | InvalidNameException e) {
      // check for the error message
      assertEquals("Invalid name: FileName contains invalid characters", e.getMessage());
    }
  }

  @Test
  /**
   * This test is for testing checkValidation method in JShellSaving (invalid FileName)
   */
  public void testCheckValidationMethod4_3() {
    // Illegal characters located at the ends
    String[] argument = {"save/"};
    MockInputProcessor userInput = new MockInputProcessor("saveJShell", argument, null, null);
    JShellSaving js = new JShellSaving(this.mis, this.dirStack, this.fs);
    try {
      js.checkValidation(userInput);
    } catch (InvalidArgumentException | RedirectionNotSupportException | InvalidNameException e) {
      // check for the error message
      assertEquals("Invalid name: FileName contains invalid characters", e.getMessage());
    }
  }

  @Test
  /**
   * This test is for testing checkValidation method in JShellSaving (invalid FileName)
   */
  public void testCheckValidationMethod4_4() {
    // Entirely made up of illegal characters
    String[] argument = {"!@#$%^&*()/"};
    MockInputProcessor userInput = new MockInputProcessor("saveJShell", argument, null, null);
    JShellSaving js = new JShellSaving(this.mis, this.dirStack, this.fs);
    try {
      js.checkValidation(userInput);
    } catch (InvalidArgumentException | RedirectionNotSupportException | InvalidNameException e) {
      // check for the error message
      assertEquals("Invalid name: FileName contains invalid characters", e.getMessage());
    }
  }

  @Test
  /**
   * This test is for testing checkValidation method in JShellSaving (invalid FileName)
   */
  public void testCheckValidationMethod5() {
    // Illegal characters located at the ends
    String[] argument = {"save/"};
    MockInputProcessor userInput = new MockInputProcessor("saveJShell", argument, null, null);
    JShellSaving js = new JShellSaving(this.mis, this.dirStack, this.fs);
    try {
      js.checkValidation(userInput);
    } catch (InvalidArgumentException | RedirectionNotSupportException | InvalidNameException e) {
      // check for the error message
      assertEquals("Invalid name: FileName contains invalid characters", e.getMessage());
    }
  }


  @Test
  /**
   * This test is for testing CheckValidation method in JShellLoading (no argument)
   */
  public void testCheckValidationMethod6() {
    // valid condition for executing loadJShell
    ArrayList<String> inputHistory = new ArrayList<String>();
    inputHistory.add("loadJShell");
    this.mis = new MockInputStorage(inputHistory);
    JShellLoading jl = new JShellLoading();
    // no argument provided
    MockInputProcessor userCommand = new MockInputProcessor("loadJShell", null, null, null);
    try {
      jl.checkValidation(userCommand, this.mis);
    } catch (InvalidCommandException | InvalidArgumentException | RedirectionNotSupportException
        | InvalidNameException e) {
      // check for the error message
      assertEquals("Invalid argument: saveJShell should take a FileName as an argument",
          e.getMessage());
    }
  }

  @Test
  /**
   * This test is for testing CheckValidation method in JShellLoading (more than one argument)
   */
  public void testCheckValidationMethod7() {
    // loadJShell is the first execute user input
    ArrayList<String> inputHistory = new ArrayList<String>();
    inputHistory.add("loadJShell save1 save2");
    this.mis = new MockInputStorage(inputHistory);
    // more than one argument
    String[] argument = {"save1", "save2"};
    JShellLoading jl = new JShellLoading();
    MockInputProcessor userCommand = new MockInputProcessor("loadJShell", argument, null, null);
    try {
      jl.checkValidation(userCommand, this.mis);
    } catch (InvalidCommandException | InvalidArgumentException | RedirectionNotSupportException
        | InvalidNameException e) {
      // check for the error message
      assertEquals("Invalid argument: saveJShell can only take one argument", e.getMessage());
    }
  }

  @Test
  /**
   * This test is for testing CheckValidation method in JShellLoading (with redirection)
   */
  public void testCheckValidationMethod8() {
    // loadJShell is the first execute user input
    ArrayList<String> inputHistory = new ArrayList<String>();
    inputHistory.add("loadJShell save1 >");
    this.mis = new MockInputStorage(inputHistory);
    // valid argument
    String[] argument = {"save1"};
    JShellLoading jl = new JShellLoading();
    // take a redirection operator
    MockInputProcessor userCommand = new MockInputProcessor("loadJShell", argument, ">", null);
    try {
      jl.checkValidation(userCommand, this.mis);
    } catch (InvalidCommandException | InvalidArgumentException | RedirectionNotSupportException
        | InvalidNameException e) {
      // check for the error message
      assertEquals("Redirection does not support saveJShell command", e.getMessage());
    }
  }

  @Test
  /**
   * This test is for testing CheckValidation method in JShellLoading (invalid FileName)
   */
  public void testCheckValidationMethod9_1() {
    // loadJShell is the first execute user input
    ArrayList<String> inputHistory = new ArrayList<String>();
    inputHistory.add("loadJShell save.txt");
    this.mis = new MockInputStorage(inputHistory);
    // invalid FileName
    String[] argument = {"save.txt"};
    JShellLoading jl = new JShellLoading();
    // take a redirection operator
    MockInputProcessor userCommand = new MockInputProcessor("loadJShell", argument, null, null);
    try {
      jl.checkValidation(userCommand, this.mis);
    } catch (InvalidCommandException | InvalidArgumentException | RedirectionNotSupportException
        | InvalidNameException e) {
      // check for the error message
      assertEquals("Invalid name: FileName contains invalid characters", e.getMessage());
    }
  }

  @Test
  /**
   * This test is for testing saving and loading inputHistory
   * 
   * @throws IOException
   */
  public void testInputHistory() throws IOException {
    // adding mock user input in the inputHistory
    ArrayList<String> inputHistory = new ArrayList<String>();
    inputHistory.add("user command line 1");
    inputHistory.add("user command line 2");
    this.mis = new MockInputStorage(inputHistory);
    // saving JShell file
    JShellSaving js = new JShellSaving(this.mis, this.dirStack, this.fs);
    try {
      js.saveJShell("save");
    } catch (InvalidNameException e) {
      fail("Should not give any exception, except IOException");
    }
    // loading JShell file
    JShellLoading jl = new JShellLoading();
    jl.loadJShell("save");
    MockInputStorage newmis = (MockInputStorage) jl.setInputHistory();
    ArrayList<String> newInputHistory = newmis.getUserInputHistory();
    // check whether the inputHistory and newInputHistory have the same content
    assertEquals(true, inputHistory.equals(newInputHistory));
  }

  @Test
  /**
   * This test is for testing saving and loading directory stack
   * 
   * @throws IOException
   */
  public void testDirStack() throws IOException {
    // adding mock directories into the directory stack
    this.dirStack.add("dir1");
    this.dirStack.add("dir2");
    // saving JShell file
    JShellSaving js = new JShellSaving(this.mis, this.dirStack, this.fs);
    try {
      js.saveJShell("save");
    } catch (InvalidNameException e) {
      fail("Should not give any exception, except IOException");
    }
    // loading JShell file
    JShellLoading jl = new JShellLoading();
    jl.loadJShell("save");
    Stack<String> newDirStack = jl.setDirectoryStack();
    // check whether dirStack and newDirStack have the same content
    assertEquals(true, this.dirStack.equals(newDirStack));
  }

  @Test
  /**
   * This test is for testing saving and loading FileSystem (only the save part)
   */
  public void testFileSystemPart1() throws IOException {
    // set data in the file system
    dir1 = new Directory("dir1", "/dir1");
    dir2 = new Directory("dir2", "/dir1/dir2");
    dir3 = new Directory("dir3", "/dir3");
    file1 = new File("file1", "/file1", "file1_txt");
    file2 = new File("file2", "/file2", "file2_txt");
    try {
      fs.getRoot().addFile(file2);
      fs.getRoot().addDirectory(dir1);
      fs.getRoot().addDirectory(dir3);
      dir1.addDirectory(dir2);
      dir2.addFile(file1);
      // save JShell
      JShellSaving js = new JShellSaving(this.mis, this.dirStack, this.fs);
      js.saveJShell("save");
    } catch (ItemAlreadyExistsException | InvalidNameException e) {
      fail("Should not give any exception, except IOException");
    }
  }

  @Test
  /**
   * This test is for testing saving and loading FileSystem (only the load part)
   */
  public void testFileSystemPart2() throws IOException {
    JShellLoading jl = new JShellLoading();
    try {
      // load JShell
      jl.loadJShell("save");
      FileSystem newFileSystem = jl.setFileSystem();
      assertEquals("/dir1", newFileSystem.findDirectory("/dir1").getPath());
      assertEquals("/dir1/dir2", newFileSystem.findDirectory("/dir1/dir2").getPath());
      assertEquals("/dir3", newFileSystem.findDirectory("/dir3").getPath());
      assertEquals("/file1", newFileSystem.findFile("/file1").getPath());
      assertEquals("/file2", newFileSystem.findFile("/file2").getPath());
      assertEquals("file1_txt", newFileSystem.findFile("/file1").getText());
      assertEquals("file2_txt", newFileSystem.findFile("/file2").getText());
    } catch (InvalidNameException | ItemAlreadyExistsException | InvalidPathException e) {
      fail("Should not give any exception, except IOException");
    }
  }
}
