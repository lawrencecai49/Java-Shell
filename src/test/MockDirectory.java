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

import java.util.Iterator;
import java.util.LinkedList;
import filesystem.Directory;
import filesystem.File;
import filesystem.FileSystemContent;

/**
 * This is a mock Directory class for JUnit testing
 * 
 * @author Man Hei Ho
 */
public class MockDirectory extends Directory {

  public LinkedList<Directory> mockSubDir;
  public LinkedList<File> mockFiles;

  public MockDirectory(String name, String pathname, LinkedList<Directory> dirs,
      LinkedList<File> files) {
    super(name, pathname);
    this.mockSubDir = dirs;
    this.mockFiles = files;
  }

  public Iterator<FileSystemContent> getDirectoryItems() {
    LinkedList<FileSystemContent> directoryContents = new LinkedList<FileSystemContent>();
    directoryContents.addAll(this.mockSubDir);
    directoryContents.addAll(this.mockFiles);
    return directoryContents.iterator();
  }

  public Iterator<Directory> getSubdirectories() {
    return this.mockSubDir.iterator();
  }

  public Iterator<File> getFiles() {
    return this.mockFiles.iterator();
  }

  public void remove(String path) {
    if (path.contains("FilePath")) {
      this.mockFiles.remove(0);
    } else if (path.contains("DirectoryPath")) {
      this.mockSubDir.remove(0);
    }
  }

  public boolean hasChild(Directory dir) {

    if (dir.getPath().contains("child")) {
      return true;
    } else {
      return false;
    }
  }
}
