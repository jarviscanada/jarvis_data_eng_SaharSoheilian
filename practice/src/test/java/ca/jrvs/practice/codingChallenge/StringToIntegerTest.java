package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringToIntegerTest {

  private StringToInteger stoi = new StringToInteger();

  @Test
  public void strToIntParsing() {
    assertEquals(0, stoi.strToIntParsing("word 45643 word"));
    assertEquals(0, stoi.strToIntParsing("+"));
    assertEquals(-12, stoi.strToIntParsing("   -12"));
    assertEquals(123, stoi.strToIntParsing("  +123 word"));
    assertEquals(-2147483648, stoi.strToIntParsing("  -91283472332"));
  }

  @Test
  public void strToIntMod() {
    assertEquals(0, stoi.strToIntMod("word 45643 word"));
    assertEquals(0, stoi.strToIntMod("+"));
    assertEquals(-12, stoi.strToIntMod("   -12"));
    assertEquals(123, stoi.strToIntMod("  +123 word"));
    assertEquals(-2147483648, stoi.strToIntMod("  -91283472332"));
  }
}