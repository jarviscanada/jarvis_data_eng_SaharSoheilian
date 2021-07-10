package ca.jrvs.practice.codingChallenge;

/**
 * https://www.notion.so/jarvisdev/Fibonacci-Number-Climbing-Stairs-060c76a6a8db4ec4996cdef37081fa4a
 */
public class Fibonacci {

  /**
   * Big-O: O(2^n) -> height of the recursion tree is n, and there are 2^n nodes in this tree (2^n calls)
   *
   * @param n
   * @return F(n) = F(n-1) + F(n-2)
   */
  public int fibRecursive(int n) {
    if (n <= 1)
      return n;

    return fibRecursive(n - 1) + fibRecursive(n - 2);
  }

  /**
   * Big-O: O(n) -> for-loop
   *
   * @param n
   * @return
   */
  public int fibDP(int n) {
    if (n <= 1)
      return n;

    int[] fibValues = new int[n+1];
    fibValues[0] = 0;
    fibValues[1] = 1;

    for (int i = 2; i <= n; i++)
      fibValues[i] = fibValues[i-1] + fibValues[i-2];

    return fibValues[n];
  }
}
