package ca.jrvs.practice.dataStructure.list;

import static org.junit.Assert.*;
import org.junit.Test;

public class ArrayJListsTest{

  @Test
  public void add() {
    ArrayJLists<String> arrayJLists = new ArrayJLists<>();
    arrayJLists.add("Obj 1");
    arrayJLists.add("Obj 2");
    arrayJLists.add("Obj 3");
    arrayJLists.add("Obj 4");
    arrayJLists.add("Obj 5");
    arrayJLists.add("Obj 6");
    arrayJLists.add("Obj 7");
    arrayJLists.add("Obj 8");
    arrayJLists.add("Obj 9");
    arrayJLists.add("Obj 10");
    arrayJLists.add("Obj 11");

    assertEquals(arrayJLists.get(10), "Obj 11");
    assertEquals(arrayJLists.size(), 11);
  }

  @Test
  public void toArray() {
    ArrayJLists<String> arrayJLists = new ArrayJLists<>();
    arrayJLists.add("Obj 1");
    arrayJLists.add("Obj 2");
    assertEquals(arrayJLists.toArray().length, 2);
  }

  @Test
  public void size() {
    ArrayJLists<String> arrayJLists = new ArrayJLists<>();
    arrayJLists.add("Obj 1");
    assertEquals(arrayJLists.size(), 1);
  }

  @Test
  public void isEmpty() {
    ArrayJLists<String> arrayJLists = new ArrayJLists<>();
    assertEquals(arrayJLists.isEmpty(), true);

    arrayJLists.add("Obj");
    assertEquals(arrayJLists.isEmpty(), false);
  }

  @Test
  public void indexOf() {
    ArrayJLists<String> arrayJLists = new ArrayJLists<>();
    arrayJLists.add("Obj 1");

    assertEquals(arrayJLists.indexOf("Obj 1"), 0);
  }

  @Test
  public void contains() {
    ArrayJLists<String> arrayJLists = new ArrayJLists<>();
    arrayJLists.add("Obj 1");
    arrayJLists.add("Obj 2");

    assertEquals(arrayJLists.contains("Obj 1"), true);
  }

  @Test
  public void get() {
    ArrayJLists<String> arrayJLists = new ArrayJLists<>();
    arrayJLists.add("Obj 1");
    arrayJLists.add("Obj 2");

    assertEquals(arrayJLists.get(1), "Obj 2");
  }

  @Test
  public void remove() {
    ArrayJLists<String> arrayJLists = new ArrayJLists<>();
    arrayJLists.add("Obj 1");
    arrayJLists.add("Obj 2");
    arrayJLists.add("Obj 3");

    arrayJLists.remove(1);

    assertEquals(arrayJLists.get(1), "Obj 3");
  }

  @Test
  public void clear() {
    ArrayJLists<String> arrayJLists = new ArrayJLists<>();
    arrayJLists.add("Obj 1");
    arrayJLists.add("Obj 2");
    arrayJLists.add("Obj 3");

    arrayJLists.clear();

    assertEquals(arrayJLists.size(), 0);

  }
}