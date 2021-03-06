package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

/**
 * An interface to implement Grep functionality in Java.
 */
public interface JavaGrep {

  /**
   * Top level search workflow.
   *
   * @throws IOException if file read failed
   */
  void process() throws IOException;

  /**
   * Traverse a given directory and return all files.
   *
   * @param rootDir input directory
   * @return files under the rootDir
   */
  List<File> listFiles(String rootDir) throws IOException;

  /**
   * Read a file and return all the lines.
   * ! FileReader, BufferedReader, Character encoding!
   *
   * @param inputFile file to be read
   * @return lines
   * @throws IllegalArgumentException if a given input file is not a file
   */
  Stream<String> readLines(File inputFile) throws IllegalArgumentException, IOException;

  /**
   * check if a line contains the regex pattern passed by user.
   *
   * @param line input string (line)
   * @return true if there is a match
   */
  boolean containsPattern(String line);

  /**
   * Write lines to a file.
   * ! FileOutputStream, OutputStreamWriter, BufferedWriter!
   *
   * @param lines matched lines
   * @throws IOException if write failed
   */
  void writeToFile(List<String> lines) throws IOException;

  String getRootPath();

  void setRootPath(String rootPath);

  String getRegex();

  void setRegex(String regex);

  String getOutFile();

  void setOutFile(String outFile);
}
