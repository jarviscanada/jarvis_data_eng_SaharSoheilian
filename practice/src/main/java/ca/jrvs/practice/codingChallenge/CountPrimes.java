package ca.jrvs.practice.codingChallenge;

import org.apache.commons.math3.primes.Primes;

/**
 * https://www.notion.so/jarvisdev/Count-Primes-378f64e5de5345db86654190cf4b5c28
 */
public class CountPrimes {

  /**
   * Big-O: O(nlogn) -> isPrime() has O(klogn) which is used in a for loop
   *
   * @param n input number
   * @return number of prime numbers before input number
   */
  public int countPrimes(int n) {
    int count = 0;

    if (Primes.isPrime(n))
      return 0;
    else {
      for (int i = 2; i < n; i ++) {
        if (Primes.isPrime(i))
          count++;
      }
    }

    return count;
  }
}
