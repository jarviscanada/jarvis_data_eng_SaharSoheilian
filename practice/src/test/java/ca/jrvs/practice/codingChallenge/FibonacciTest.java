package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FibonacciTest {

  private Fibonacci fib = new Fibonacci();

  @Test
  public void fibRecursive() {
    assertEquals(0, fib.fibRecursive(0));
    assertEquals(1, fib.fibRecursive(1));
    assertEquals(1, fib.fibRecursive(2));
    assertEquals(34, fib.fibRecursive(9));

  }

  @Test
  public void fibDP() {
    assertEquals(0, fib.fibDP(0));
    assertEquals(1, fib.fibDP(1));
    assertEquals(1, fib.fibDP(2));
    assertEquals(34, fib.fibDP(9));
  }
}