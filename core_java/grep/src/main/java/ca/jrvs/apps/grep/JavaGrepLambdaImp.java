package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp {

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
    javaGrepLambdaImp.setRegex(args[0]);
    javaGrepLambdaImp.setRootPath(args[1]);
    javaGrepLambdaImp.setOutFile(args[2]);

    try {
      javaGrepLambdaImp.process();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Implement the method using Lambda and Stream API
   *
   * @param inputFile
   * @return
   * @throws IOException
   */
  @Override
  public List<String> readLines(File inputFile) throws IOException {

    List<String> lines = Files.lines(Paths.get(inputFile.getAbsolutePath()))
                        .collect(Collectors.toList());

    return lines;
  }

  /**
   * Implement the method using Lambda and Stream API
   *
   * @param rootDir
   * @return
   */
  @Override
  public List<File> listFiles(String rootDir) throws IOException {
    List<File> listFiles = Files.walk(Paths.get(rootDir))
                            .filter(Files::isRegularFile)
                            .map(Path::toFile)
                            .collect(Collectors.toList());

    return listFiles;
  }
}
