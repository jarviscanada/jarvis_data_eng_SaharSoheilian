package ca.jrvs.practice.dataStructure.list;

import java.util.Arrays;
import java.util.List;

/**
 * practice java array APIs
 */
public class ArrayAPIs {

  public static void main(String[] args) {
    //create an array of integers
    int[] intArray = new int[10];
    intArray[0] = 100;
    intArray[1] = 200;
    intArray[2] = 300;

    //shortcut syntax to create and initialize an array
    int[] inlineArray = {100, 200, 300};

    //2D array
    String[][] names = {
        {"Mr. ", "Mrs. ", "Ms. "},
        {"Smith", "Sahar", "Sarah", "Jones"}
    };

    //copy array
    char[] copyFrom = {'d', 'c', 'a', 'f', 'f', 'e', 'i', 'm', 'w'};
    char[] copyTo = new char[6];

    System.arraycopy(copyFrom, 2, copyTo, 0, 6);

    // no need to convert to String since print(char[]) is allowed
    System.out.println(copyTo);
    System.out.println(new String(copyTo));

    // convert an array to list
    List<String> fruits = Arrays.asList("apple", "mango", "peach");
    System.out.println(fruits);

    fruits = Arrays.asList(new String[]{"strawberry", "blueberry"});
    System.out.println(fruits);

    //copy
    String[] fruitArray = new String[]{"orange", "banana", "grape"};
    fruits = Arrays.asList(fruitArray);
    System.out.println(fruits);

    String[] anotherFruitArray = Arrays.copyOfRange(fruitArray, 0, 1);

    //toString
    /* println, printf just work for char[]
       not other kinds of arrays, int[], String[], ...
       use Arrays.toString() to convert them before print
       seems that myArray.toString() doesn't work!
     */
    System.out.println(anotherFruitArray); // prints [Ljava.lang.String;@1b6d3586
    System.out.printf(anotherFruitArray.toString()); // prints [Ljava.lang.String;@1b6d3586
    System.out.println();
    System.out.println(Arrays.toString(fruitArray));

    //sort
    Arrays.sort(fruitArray);
    System.out.println(Arrays.toString(fruitArray));

    //binary search
    int exact = Arrays.binarySearch(fruitArray,"grape");
    System.out.println(exact);
  }

}
