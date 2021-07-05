package ca.jrvs.practice.codingChallenge;

/**
 * https://www.notion.so/jarvisdev/Rotate-String-7edeac5b43214bdc9f153000c7ca04c6
 */
public class RotateString {

  /**
   * Big-O: O(n^2) -> contains() uses brute-force and has time complexity of O(nm),
   * in this case O(nn)
   *
   * @param s string which should be rotated and checked
   * @param goal string which s will be checked against
   * @return true if s or rotated s contains goal
   */
  public boolean rotateString(String s, String goal) {

    if (s.length() != goal.length())
      return false;

    return (s + s).contains(goal);
  }
}
