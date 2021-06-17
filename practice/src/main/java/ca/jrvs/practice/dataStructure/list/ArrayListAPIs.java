package ca.jrvs.practice.dataStructure.list;

import com.sun.org.apache.xpath.internal.operations.Bool;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListAPIs {
  public static void main(String[] args) {
    // No need to specify type in <>
    // ArrayList is dynamic length, so no size is required
    List<String> animals = new ArrayList<>();

    // add element
    animals.add("Lion");
    animals.add("Tiger");
    animals.add(2, "Cat");

    // size, not length:
    int s = animals.size();

    // get
    String firstElement = animals.get(0);

    // search O(n)
    Boolean hasCat = animals.contains("Cat"); // true

    // get index
    int catIndex = animals.indexOf("Cat"); // 2

    // remove
    boolean isCatRemoved = animals.remove("Cat"); // remove by object
    String removedElement = animals.remove(1); //remove by index

    // sort
    // pass comparator using lambda
    animals.sort(String::compareToIgnoreCase);

    // convert list to array
    System.out.println(Arrays.toString(animals.toArray()));

  }

}
