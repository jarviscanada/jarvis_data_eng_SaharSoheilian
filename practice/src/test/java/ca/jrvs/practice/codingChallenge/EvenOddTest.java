package ca.jrvs.practice.codingChallenge;

import junit.framework.TestCase;

public class EvenOddTest extends TestCase {
  private EvenOdd evenOdd = new EvenOdd();

  public void testEvenOddMod() {
    assertEquals("Number is odd", evenOdd.evenOddMod(33));
    assertEquals("Number is even", evenOdd.evenOddMod(1000));
  }

  public void testEvenOddBit() {
    assertEquals("Number is odd", evenOdd.evenOddBit(33));
    assertEquals("Number is even", evenOdd.evenOddBit(1000));
  }
}