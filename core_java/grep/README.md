# Introduction
This is a project that implements Linux `grep` usage in java. Java Grep application searches 
for a user-specified pattern in all the files of a given directory and outputs the 
matched lines into a file. The project includes:

- JavaGrep Interface: an interface that defines Java Grep app functionality.
- JavaGrepImp Class: a class implements JavaGrep interface.
- JavaGrepLambdaImp: a class inherited from JavaGrepImp. Overrides `listFile()` method 
using Lambda and Stream APIs to improve the efficiency.

Technologies deployed in this project:
- Core Java
- Stream APIs
- Lambda
- slf4j logging API
- Maven
- Maven shade plugin
- Intellij IDE
- Docker
- Git

# Quick Start
Java Grep application takes 3 input arguments:

1. regex: a special text string for describing a search pattern
2. rootPath: root directory path
3. outFile: output file name

To run the application using maven follow the instruction:

```
cd ${project_dir}
mvn clean package
java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp ${regex} ${rootPath} ${outFile}
```

# Implementation
Below is the list of methods and their functionalities defined in JavaGrep Interface:
- `void process()` Top-level search workflow
- `List<File> listFiles(String rootDir)` Traverse a given directory and return all files
- `Stream<String> readLines(File inputFile)` Read a file and return all the lines
- `boolean containsPattern(String line)` Check if a line contains the regex pattern passed 
  by the user
- `void writeToFile(List<String> lines)` Write lines to a file 
- `Getters` and `Setters` methods for instance variables (regex, rootPath, and outFile)

## Pseudocode
`process()` method implements the high level workflow of the application and calls the 
helper methods to find the regex pattern in a given directory recursively:
```
matchedLines = []
for file in listFiles(rootDir)
    for line in readLines(file)
        if containsPattern(line)
            matchedLines.add(line)
writeToFile(matchedLines)            
```

## Performance Issue
To prevent `OutOfMemoryError` error in the case of processing a large file (in `readLines()` 
method), Lambda and Stream APIs are used instead of arrays and for-loops. This enables the 
application to process big files without allocating large JVM heap size and even process data
bigger than the host physical memory size.

# Test
There are two ways for testing Java Grep app; using maven to run the app or create a container
from the application docker image. Below is the instruction for testing the application to 
find the lines that contain both *Romeo* and *Juliet*. The results can be compared to the linux
`grep` command for validation.

1. download sample file:
```
wget -O  ${project_dir}/data/txt/shakespeare.txt https://gist.githubusercontent.com/
jarviscanada/c5408fc8f8ad0aa9606f76c8d4fde269/raw/c04d4f5d5adea39fff35ba3b9ec889d667292a0e/
shakespeare.txt
```

2. run the application using maven:

```
cd ${project_dir}
mvn clean package
java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp .*Romeo.*Juliet.* ./data 
./out/grep.out
```

2. run the docker image (download the image from docker hub):
```
docker pull shrsoheilian/grep
docker run --rm \
-v `pwd`/data:/data -v `pwd`/log:/log \
shrsoheilian/grep .*Romeo.*Juliet.* /data /log/grep.out
```

3. compare the result with linux `grep` command:

`egrep -r .*Romeo.*Juliet.* ./data`

# Deployment
The JavaGrep app is dockerized for easier distribution. The application image is tagged as
**shrsoheilian/grep** and is available in hub.docker.com for download.
Below are the steps of dockerizing Java Grep application:

1. create a docker hub account
2. login to docker hub 
3. create a dockerfile in the project root directory having the following steps:
    ```
    FROM openjdk:8-alpine
    COPY target/grep*.jar /usr/local/app/grep/lib/grep.jar
    ENTRYPOINT ["java","-jar","/usr/local/app/grep/lib/grep.jar"]
    ```
4. package the project using maven

    `mvn clean package`

   
5. build a new docker image locally

    `docker build -t shrsoheilian/grep .`
   

6. push image into dockerhub
    
    `docker push shrsoheilian/grep`

# Improvement
- Implement `listFile()` method in a way that returns a `Stream<File>` instead of an array of 
  files `List<File>`, and replace a for-loop in `process()` method by Lambda API to improve the
  efficiency.
