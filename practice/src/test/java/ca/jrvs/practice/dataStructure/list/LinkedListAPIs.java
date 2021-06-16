package ca.jrvs.practice.dataStructure.list;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LinkedListAPIs {

  public static void main(String[] args) {
    List<String> list = new LinkedList<>();

    list.add("Lion");
    list.add("Snake");
    list.add("Dog");
    System.out.println(list.get(1));

    int size = list.size();

    String firstItem = list.get(0);

    boolean hasCat = list.contains(firstItem);

    int index = list.indexOf("Snake");

    boolean isRemoved = list.remove("Dog");

    String removedItem = list.remove(index);

    list.sort(String::compareToIgnoreCase);

    System.out.println(Arrays.toString(list.toArray()) + " initial size: " + size);

  }

}
