package ca.jrvs.practice.codingChallenge;

/**
 * https://www.notion.so/jarvisdev/Sample-Check-if-a-number-is-even-or-odd-0383d4500bc249f3980a32f66103cdea
 */
public class EvenOdd {

  /**
   * Big-O: O(1) -> an arithmetic operation
   *
   * @param input
   * @return
   */
  public String evenOddMod(int input) {
    return input % 2 == 0 ? "Number is even" : "Number is odd";
  }

  /**
   * Big-O: O(1) -> a bitwise operation
   *
   * @param input
   * @return
   */
  public String evenOddBit(int input) {
    return (input & 1) != 1 ? "Number is even" : "Number is odd";
  }
}
