package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class CountPrimesTest {
  private CountPrimes countPrimes = new CountPrimes();

  @Test
  public void countPrimes() {
    assertEquals(0, countPrimes.countPrimes(0));
    assertEquals(0, countPrimes.countPrimes(1));
    assertEquals(4, countPrimes.countPrimes(10));
  }
}