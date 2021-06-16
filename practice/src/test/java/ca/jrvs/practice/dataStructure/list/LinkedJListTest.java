package ca.jrvs.practice.dataStructure.list;

import static org.junit.Assert.*;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import org.junit.Before;
import org.junit.Test;

public class LinkedJListTest {

  private LinkedJList<String > jList;

  @Before
  public void setUp() throws Exception {
    jList = new LinkedJList<>();
  }

  @Test
  public void add() {
    jList.add("Lion");

    assertEquals(jList.size, 1);
    assertEquals(jList.get(0), "Lion");
  }

  @Test
  public void toArray() {
    jList.add("Lion");
    jList.add("Fish");
    jList.add("Bird");

    String[] stringArray = {"Lion", "Fish", "Bird"};

    assertArrayEquals(jList.toArray(), stringArray);
    assertEquals(jList.toArray().length, 3);
    assertEquals(jList.toArray()[1], "Fish");
  }

  @Test
  public void size() {
    jList.add("Lion");
    jList.add("Fish");
    jList.add("Bird");
    jList.add("Bear");

    assertEquals(jList.size, 4);
  }

  @Test
  public void isEmpty() {
    assertEquals(jList.isEmpty(), true);

    jList.add("Bee");
    assertEquals(jList.isEmpty(), false);
  }

  @Test
  public void indexOf() {
    jList.add("Lion");
    jList.add("Fish");
    jList.add("Bird");
    jList.add("Bear");

    assertEquals(jList.indexOf("Fish"), 1);
  }

  @Test
  public void contains() {
    jList.add("Lion");
    jList.add("Fish");
    jList.add("Bird");

    assertEquals(jList.contains("Bird"), true);
  }

  @Test
  public void get() {
    jList.add("Lion");
    jList.add("Fish");
    jList.add("Bird");

    assertEquals(jList.get(1), "Fish");
  }

  @Test
  public void remove() {
    jList.add("Lion");
    jList.add("Fish");
    jList.add("Bird");
    jList.add("Bee");
    jList.add("Cat");

    assertEquals(jList.remove(1), "Fish");
    assertEquals(jList.size(), 4);

  }

  @Test
  public void clear() {
    jList.add("Lion");
    jList.add("Fish");
    jList.add("Bird");
    assertEquals(jList.size(), 3);

    jList.clear();
    assertEquals(jList.size(), 0);
  }
}