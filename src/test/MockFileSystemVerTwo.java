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

import java.util.ArrayList;
import exception.InvalidNameException;
import exception.InvalidPathException;
import exception.ItemAlreadyExistsException;
import filesystem.*;

/**
 * This is another version of mock FileSystem class for JUnit testing
 * 
 * @author Man Hei Ho
 */
public class MockFileSystemVerTwo implements FileSystem {

  private Directory root;
  private Directory currentDir;
  public Directory dir1;
  public Directory dir2;
  public File file1;
  public File file2;
  public ArrayList<String> files;
  public ArrayList<String> directories;
  public File lastModifiedFile;

  public MockFileSystemVerTwo(Directory root) {
    this.root = root;
    this.currentDir = root;
  }

  public MockFileSystemVerTwo(Directory root, Directory dir1, Directory dir2, File file1,
      File file2) {
    this.files = new ArrayList<String>();
    this.directories = new ArrayList<String>();
    this.root = root;
    this.currentDir = root;
    this.dir1 = dir1;
    if (dir1 != null)
      this.directories.add(dir1.getPath());
    this.dir2 = dir2;
    if (dir2 != null)
      this.directories.add(dir2.getPath());
    this.file1 = file1;
    if (file1 != null)
      this.files.add(file1.getPath());
    this.file2 = file2;
    if (file2 != null)
      this.files.add(file2.getPath());
    this.lastModifiedFile = null;
  }

  public Directory getRoot() {
    return this.root;
  }


  public Directory getCurrentDirectory() {
    return this.currentDir;
  }


  public void setCurrentDirectory(Directory newCurrentDirectory) {
    this.currentDir = newCurrentDirectory;
  }


  public Directory findDirectory(String path) throws InvalidPathException {
    if (path.isEmpty()) {
      throw new InvalidPathException(path + " does not refer to an existing directory");
    } else if (path.equals("InvalidPath")) {
      throw new InvalidPathException(path + " does not refer to an existing directory");
    } else if (path.equals("FilePath")) {
      throw new InvalidPathException(path + " does not refer to an existing directory");
    } else if (path.equals("DirectoryPathOne")) {
      return this.dir1;
    } else if (path.equals("DirectoryPathTwo")) {
      return this.dir2;
    } else if (path.equals("FilePathOne") || path.equals("FilePathTwo")
        || path.contains("Nonexisting")) {
      throw new InvalidPathException(path + " does not refer to an existing directory");
    } else {
      this.directories.add(path);
      return new MockDirectory(path, path, null, null);
    }
  }

  public Directory makeDirectory(String path)
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    if (path.equals("PathOfExistingItem")) {
      throw new ItemAlreadyExistsException("There is no directory at" + path);
    } else if (path.equals("NotReachablePath")) {
      throw new InvalidPathException("There is no directory at" + path);
    } else if (path.equals("InvalidName")) {
      throw new InvalidNameException("Invalid Name");
    } else {
      this.directories.add(path);
      return new MockDirectory(path, path, null, null);
    }
  }


  public File findFile(String path) throws InvalidPathException {
    if (path.equals("/") || path.isEmpty()) {
      throw new InvalidPathException("There is no file at " + path);
    } else if (path.equals("InvalidPath")) {
      throw new InvalidPathException("There is no file at " + path);
    } else if (path.contains("DirectoryPath")) {
      throw new InvalidPathException("There is no file at " + path);
    } else if (path.contains("Nonexisting")) {
      throw new InvalidPathException("There is no file at " + path);
    } else if (path.equals("FilePathOne")) {
      return this.file1;
    } else if (path.equals("FilePathTwo")) {
      return this.file2;
    } else {
      File file = new File(path, path);
      this.lastModifiedFile = file;
      this.files.add(path);
      return file;
    }
  }

  public File makeFile(String path, String text)
      throws InvalidNameException, ItemAlreadyExistsException, InvalidPathException {
    if (path.equals("PathOfExistingItem")) {
      throw new ItemAlreadyExistsException("There is no file at" + path);
    } else if (path.equals("NotReachablePath")) {
      throw new InvalidPathException("There is no file at" + path);
    } else if (path.equals("InvalidName")) {
      throw new InvalidNameException("Invalid Name");
    } else {
      this.files.add(path);
      File file = new File(path, path, text);
      this.lastModifiedFile = file;
      return file;
    }
  }

  public void removeDirectory(String path) throws InvalidPathException {
    Directory toBeRemoved = this.findDirectory(path);
    this.directories.remove(toBeRemoved.getPath());
  }

}
