package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

  private String regex;
  private String rootPath;
  private String outFile;

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    //check if has directory format
    this.rootPath = rootPath;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    //check if has file format
    this.outFile = outFile;
  }

  @Override
  public void process() throws IOException {

    List<String> lines;
    List<String> matchedLines = new ArrayList<>();
    String rootDir = getRootPath();

    for (File file : listFiles(rootDir)) {
      lines = readLines(file);

      for (String line : lines) {
        if (containsPattern(line)) {
          matchedLines.add(line);
        }
      }
    }

    writeToFile(matchedLines);

  }

  @Override
  public List<File> listFiles(String rootDir) throws IOException {
    List<File> listFiles = new ArrayList<>();
    File root = new File(rootDir);

    if (root.listFiles() != null) {
      listFilesRecursively(root, listFiles);
    }
    return listFiles;
  }

  private void listFilesRecursively(File root, List<File> listFiles) {
    File[] files = root.listFiles();

    for (File file : files) {
      if (file.isDirectory()) {
        listFilesRecursively(file, listFiles);
      } else {
        listFiles.add(file);
      }
    }
  }


  @Override
  public List<String> readLines(File inputFile) throws IOException {

    String line;
    List<String> lines = new ArrayList<>();

    BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
    while ((line = bufferedReader.readLine()) != null) {
      lines.add(line);
    }
    return lines;
  }

  @Override
  public boolean containsPattern(String line) {
    return line.matches(getRegex());
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    FileOutputStream fileOutputStream = new FileOutputStream(getOutFile());
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
    BufferedWriter writer = new BufferedWriter(outputStreamWriter);

    for (String line : lines) {
      writer.append(line);
      writer.newLine();
    }

    writer.flush();
  }

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }
    //Use default logger config
    BasicConfigurator.configure();

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (Exception ex) {
      javaGrepImp.logger.error(ex.getMessage(), ex);
    }
  }
}
