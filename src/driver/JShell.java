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

import java.util.Scanner;
import output.*;
import storage.*;
import exception.*;
import filesystem.*;
import java.io.IOException;
import java.util.*;

/**
 * This class runs and initializes data for the shell program. This class also prompts for commands
 * and passes it to CommandExecutor to execute the commands.
 * 
 * @author Yuanyuan Li
 */
public class JShell {


  /**
   * Main function of the JShell program. It initializes and keep track of the data, continuously
   * prompts user to input command line, and exits the program.
   * 
   * @param args
   */
  private ValidationGate valiGate;
  private ErrorHandler errorHandler;
  private FileSystem fileSystem;
  private RedirectionProcessor redirectionProcessor;
  private OutputProcessor outputProcessor;
  private Stack<String> dirStack;
  private InputStorage inputHistory;
  private CommandExecutor executor;

  /**
   * Class constructor and initializes the data needed in the JShell program
   */
  public JShell() {
    this.valiGate = new ValidationGate();
    this.errorHandler = new ErrorHandler();
    this.fileSystem = JFileSystem.buildFileSystem();
    this.redirectionProcessor = new RedirectionProcessor(fileSystem, valiGate);
    this.outputProcessor = new OutputProcessor(redirectionProcessor);
    this.dirStack = new Stack<String>();
    this.inputHistory = new InputStorage();
    this.executor = new CommandExecutor(valiGate, outputProcessor, errorHandler, fileSystem,
        dirStack, inputHistory);
  }

  /**
   * This method is for calling and executing saveJShell command
   * 
   * @param userCommand parsed user input command
   * @throws IOException
   * @throws InvalidArgumentException
   * @throws RedirectionNotSupportException
   * @throws InvalidNameException
   */
  public void saveJshell(InputProcessor userCommand) throws IOException, InvalidArgumentException,
      RedirectionNotSupportException, InvalidNameException {
    JShellSaving js = new JShellSaving(this.inputHistory, this.dirStack, this.fileSystem);
    if (js.checkValidation(userCommand))
      js.saveJShell(userCommand.getArgument()[0]);
  }

  /**
   * This method is for calling and executing loadJShell command
   * 
   * @param userCommand parsed user input command
   * @param commandLine user input command
   * @throws InvalidCommandException
   * @throws InvalidArgumentException
   * @throws RedirectionNotSupportException
   * @throws IOException
   * @throws InvalidNameException
   * @throws ItemAlreadyExistsException
   * @throws InvalidPathException
   */
  public void loadJshell(InputProcessor userCommand, String commandLine)
      throws InvalidCommandException, InvalidArgumentException, RedirectionNotSupportException,
      IOException, InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    JShellLoading jl = new JShellLoading();
    if (jl.checkValidation(userCommand, this.inputHistory)) {
      jl.loadJShell(userCommand.getArgument()[0]);
      // reinitialized JShell
      this.dirStack = jl.setDirectoryStack();
      this.inputHistory = jl.setInputHistory();
      this.inputHistory.addUserInput(commandLine);
      this.fileSystem = jl.setFileSystem();
      this.executor = new CommandExecutor(this.valiGate, this.outputProcessor, this.errorHandler,
          this.fileSystem, this.dirStack, this.inputHistory);
    }
  }

  public static void main(String[] args) {
    System.out.println("JShell\n");
    JShell jshell = new JShell();

    while (true) {
      String commandLine;
      System.out.print("Enter your command: ");
      @SuppressWarnings("resource")
      Scanner in = new Scanner(System.in);
      commandLine = in.nextLine();
      InputProcessor userCommand = new InputProcessor(commandLine);
      // store the command line in inputHistory
      jshell.inputHistory.addUserInput(commandLine);
      // this is for exit the Jshell program
      if (userCommand.getCommand().equals("exit") && userCommand.getArgument() == null
          && userCommand.getRedirectionOperator() == null) {
        break;
      }

      try { // execute the command line, saveJShell, and loadJShell
        if (userCommand.getCommand().equals("saveJShell")) // run saveJShell command
          jshell.saveJshell(userCommand);
        else if (userCommand.getCommand().equals("loadJShell")) // run loadJShell command
          jshell.loadJshell(userCommand, commandLine);
        else // execute command
          jshell.executor.runCommand(userCommand);
      } catch (InvalidCommandException | InvalidArgumentException | RedirectionNotSupportException
          | IOException | InvalidNameException | ItemAlreadyExistsException
          | InvalidPathException e) {
        jshell.errorHandler.printErrorMessage(e); // use default constructor to create a
                                                  // ErrorHandler Object
      }
    }
  }
}


