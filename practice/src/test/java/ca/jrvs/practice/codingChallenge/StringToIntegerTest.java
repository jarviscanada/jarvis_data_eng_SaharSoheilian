package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringToIntegerTest {

  private StringToInteger stoi = new StringToInteger();

  @Test
  public void strToIntParsing() {
//    assertEquals(-12, stoi.strToIntParsing("   -12"));
    assertEquals(0, stoi.strToIntMod("-"));
//    assertEquals(Integer.MAX_VALUE, Integer.parseInt(String.valueOf(Integer.MAX_VALUE + 1)));
  }
}