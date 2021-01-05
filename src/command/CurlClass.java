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

import java.net.*;
import driver.*;
import filesystem.FileSystem;
import output.Output;
import exception.*;
import java.io.*;

/**
 * This class implements the curl command
 * 
 * @author Yuanyuan Li
 *
 */
public class CurlClass extends Command {

  /**
   * This is a FileSystem object
   */
  private FileSystem fileSystem;

  /**
   * Class constructor
   * 
   * @param fileSystem
   * @param valiGate
   */
  public CurlClass(FileSystem fileSystem) {
    this.fileSystem = fileSystem;
  }


  @Override
  /**
   * This command execute the curl command and save the URL content
   * 
   * @param userInput This is an InputProcessor object that contains parse user input
   * @return output This contains the output information
   */
  public Output runCommand(InputProcessor userInput) {
    Output output = new Output(null, null);
    // no argument
    if (userInput.getArgument() == null)
      output.setException(new InvalidArgumentException("curl should takse one URL as argument"));
    // more than one argument
    else if (userInput.getArgument().length != 1)
      output.setException(
          new InvalidArgumentException("curl takes no more than one URL as argument"));
    // add url
    else
      return getURL(userInput.getArgument()[0]);
    return output;
  }

  /**
   * This method will get the file name of the URL
   * 
   * @param url User provide URL
   * @return String file name
   */
  private String getFileName(String url) {
    // invalid char
    String[] inValidPathnameChar = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "{", "}", "~",
        "|", "<", ">", "?", " ", "/", "."};
    // URL file name start at the last index of "/"
    int index = url.lastIndexOf("/");
    String name;
    if (index != -1 && index != url.length() - 1) {
      name = url.substring(index + 1);
      for (int i = 0; i < inValidPathnameChar.length; i++) {
        if (name.contains(inValidPathnameChar[i]))
          name = name.replace(inValidPathnameChar[i], "");
      }
    } else {
      name = "";
    }
    return name;
  }

  /**
   * This method will add the URL file into the fileSystem
   * 
   * @param fileName The name of the URL file
   * @param content The content of the URL file
   * @return output This contains the output information
   */
  private Output addURL(String fileName, String content) {
    Output output = new Output(null, null);
    try {
      this.fileSystem.makeFile(fileName, content);
    } catch (InvalidNameException | ItemAlreadyExistsException | InvalidPathException e) {
      output.setException(e);
    }
    return output;
  }

  /**
   * This method will get the content of the URL
   * 
   * @param url User provide URL
   * @return output This contains the output information
   */
  private Output getURL(String urlPath) {
    Output output = new Output(null, null);
    try {
      URL url = new URL(urlPath);
      String fileName = getFileName(urlPath);
      URLConnection uc = url.openConnection();
      uc.setConnectTimeout(2000);
      uc.connect();
      BufferedReader readInputURL = new BufferedReader(new InputStreamReader(uc.getInputStream()));
      String readURL;
      StringBuilder stringBuilder = new StringBuilder();
      while ((readURL = readInputURL.readLine()) != null) {
        stringBuilder.append(readURL + "\n");
      }
      readInputURL.close();
      output = addURL(fileName, stringBuilder.toString());
    } catch (Exception e) {
      output.setException(new IOException("invalid URL"));
    }
    return output;
  }
}
