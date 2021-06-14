package ca.jrvs.practice.codingChallenge;

import junit.framework.TestCase;

public class StringOfDigitsTest extends TestCase {
  private StringOfDigits sd = new StringOfDigits();

  public void testContainDigitsASCII() {
    assertEquals(true, sd.containDigitsASCII("1234"));
    assertEquals(false, sd.containDigitsASCII("123,4"));
    assertEquals(false, sd.containDigitsASCII("1.234"));
  }

  public void testContainDigitsJavaAPI() {
    assertEquals(true, sd.containDigitsJavaAPI("1234"));
    assertEquals(false, sd.containDigitsJavaAPI("123,4"));
    assertEquals(false, sd.containDigitsJavaAPI("1.234"));
  }


  public void testContainDigitsRegex() {
    assertEquals(true, sd.containDigitsRegex("1234"));
    assertEquals(false, sd.containDigitsRegex("123,4"));
    assertEquals(false, sd.containDigitsRegex("1.234"));
  }
}