package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class RotateStringTest {

  @Test
  public void rotateString() {
    RotateString rs = new RotateString();

    assertEquals(rs.rotateString("abcde", "cdeab"), true);
    assertEquals(rs.rotateString("abcde", "abced"), false);
  }
}