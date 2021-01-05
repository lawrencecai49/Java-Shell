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

package storage;

import java.io.File;
import java.io.IOException;

/**
 * This class will handler the file in the real computer
 * 
 * @author Yuanyuan Li
 *
 */
public class FileHandler {

  /**
   * This method is for creating a file in the actual file system
   * 
   * @param fileName User input FileName
   * @throws IOException
   */
  public void processFile(String fileName) throws IOException {
    File file = null;
    try {
      file = new File(fileName);
      // if the file has already existed delete and create a new one
      if (file.exists())
        file.delete();
      file.createNewFile();
    }
    // error message
    catch (IOException e) {
      throw new IOException("Error: fail to create file");
    }
  }

  /**
   * This method is for checking whether file exists given file path
   * 
   * @param filePath The pathname of the file
   * @return boolean
   */
  public boolean fileExist(String fileName) {
    File file = null;
    file = new File(fileName);
    return file.exists();
  }
}
