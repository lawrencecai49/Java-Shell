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

import java.lang.reflect.*;
import java.util.*;
import output.*;
import command.*;
import exception.*;
import filesystem.*;

/**
 * This class receives user input and executes the corresponding command
 * 
 * @author Man Hei Ho
 */
public class CommandExecutor {

  /**
   * This is a ValidationGate object that validates user input
   */
  private ValidationGate valiGate;


  /**
   * This is an OutputProcessor object that output error message
   */
  private OutputProcessor outputProcessor;


  /**
   * This is an ErrorHandler object that handle error
   */
  private ErrorHandler errorHandler;


  /**
   * This is a HashMap mapping from command to command class name
   */
  private HashMap<String, String> commandHashMap;

  /**
   * This is a HashMap mapping from class name to an instance of that class
   */
  private HashMap<String, Object> fieldHashMap;


  /**
   * Class constructor
   * 
   * @param valiGate This is a ValidationGate object that validates user input
   * @param outputProcessor This is an OutputProcessor object that output error message
   * @param errorHandler This is an ErrorHandler object that handle error
   * @param fileSystem This is a file system containing all files and directories contents
   * @param dirStack This is a stack of directories names
   * @param inputHistory This contains all user input history
   */
  public CommandExecutor(ValidationGate valiGate, OutputProcessor outputProcessor,
      ErrorHandler errorHandler, FileSystem fileSystem, Stack<String> dirStack,
      InputStorage inputHistory) {

    this.valiGate = valiGate;
    this.outputProcessor = outputProcessor;
    this.errorHandler = errorHandler;

    // initializing and populating commandHashMap
    this.commandHashMap = new HashMap<String, String>();
    this.commandHashMap.put("mkdir", "command.MakeDirectoryClass");
    this.commandHashMap.put("cd", "command.ChangeDirectoryClass");
    this.commandHashMap.put("ls", "command.ListClass");
    this.commandHashMap.put("pwd", "command.PrintWorkingDirectoryClass");
    this.commandHashMap.put("pushd", "command.DirectoryStack");
    this.commandHashMap.put("popd", "command.DirectoryStack");
    this.commandHashMap.put("history", "command.HistoryClass");
    this.commandHashMap.put("cat", "command.ConcatenationClass");
    this.commandHashMap.put("echo", "command.EchoClass");
    this.commandHashMap.put("man", "command.ManClass");
    this.commandHashMap.put("cp", "command.CopyClass");
    this.commandHashMap.put("mv", "command.MoveClass");
    this.commandHashMap.put("rm", "command.RemoveDirectoryClass");
    this.commandHashMap.put("search", "command.SearchClass");
    this.commandHashMap.put("tree", "command.TreeClass");
    this.commandHashMap.put("curl", "command.CurlClass");
    // initializing and populating fieldHashMap
    this.fieldHashMap = new HashMap<String, Object>();
    this.fieldHashMap.put("driver.ValidationGate", valiGate);
    this.fieldHashMap.put("filesystem.FileSystem", fileSystem);
    this.fieldHashMap.put("java.util.Stack", dirStack);
    this.fieldHashMap.put("driver.InputStorage", inputHistory);
  }

  /**
   * This method executes the command based on user input. It throws exception if the command and/or
   * the argument that the user input is invalid.
   * 
   * @param userInput This contains parsed user input
   * @throws InvalidCommandException when the command is not valid
   * @throws InvalidArgumentException when argument is not valid
   */
  public void runCommand(InputProcessor userInput)
      throws InvalidCommandException, InvalidArgumentException {

    if (!this.valiGate.isValidCommand(userInput.getCommand()))
      throw new InvalidCommandException(userInput.getCommand() + " is not a valid command.");
    if (userInput.getCommand().equals("exit"))
      throw new InvalidArgumentException("exit does not take any argument.");

    try {
      // get the command class
      Class<?> cmdClass = Class.forName(this.commandHashMap.get(userInput.getCommand()));
      // get the Class objects of its declared field
      Class<?>[] param = this.getFieldClass(cmdClass);
      // get the instances of its fields
      Object[] paramObj = this.getFieldObj(param);
      // get the constructor of the command class and make an instance
      Command cmdObj = (Command) cmdClass.getDeclaredConstructor(param).newInstance(paramObj);
      // execute the command
      Output output = cmdObj.runCommand(userInput);
      this.outputProcessor.processOutputMessage(output.getOutputMessage(), userInput);
      this.errorHandler.printErrorMessage(output.getException());

    } catch (InvalidNameException | ItemAlreadyExistsException | InvalidPathException
        | RedirectionException e) {
      this.errorHandler.printErrorMessage(e);
    } catch (Exception e) { // ignore
    }
  }

  /**
   * This method takes a Class object and returns an array of its declared fields, including the
   * declared fields of its most direct parent class
   * 
   * @param cmdClass This is a class object
   * @return A Class array that contains its declared fields
   */
  private Class<?>[] getFieldClass(Class<?> cmdClass) {
    if (cmdClass == null)
      return null;

    // find all declared fields, including the declared fields of its most direct parent
    Field[] cmdClassField = cmdClass.getDeclaredFields();
    Field[] cmdParentClassField = null;
    if (cmdClass.getSuperclass() != null)
      cmdParentClassField = cmdClass.getSuperclass().getDeclaredFields();

    // populate the field class array
    ArrayList<Class<?>> fieldClass = new ArrayList<Class<?>>();
    if (cmdParentClassField != null) {
      for (Field field : cmdParentClassField) {
        fieldClass.add(field.getType());
      }
    }
    if (cmdClassField != null) {
      for (Field field : cmdClassField) {
        fieldClass.add(field.getType());
      }
    }

    if (fieldClass.isEmpty())
      return null;
    return fieldClass.toArray(new Class<?>[fieldClass.size()]);
  }

  /**
   * This method takes an array of Class objects and returns an array of the corresponding instances
   * of the Class objects
   * 
   * @param cmdFieldClass This is an array of Class objects
   * @return A Object array containing the corresponding instances of the Class objects
   */
  private Object[] getFieldObj(Class<?>[] cmdFieldClass) {

    if (cmdFieldClass == null)
      return null;

    // populate the field object array
    Object[] paramObj = new Object[cmdFieldClass.length];
    for (int i = 0; i < cmdFieldClass.length; i++) {
      paramObj[i] = this.fieldHashMap.get(cmdFieldClass[i].getName());
    }

    if (paramObj.length == 0)
      return null;
    return paramObj;
  }
}
