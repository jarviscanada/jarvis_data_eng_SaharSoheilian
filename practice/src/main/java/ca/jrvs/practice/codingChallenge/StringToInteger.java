package ca.jrvs.practice.codingChallenge;

/**
 * https://www.notion.so/jarvisdev/String-to-Integer-atoi-00722fb6989042bbadb3308522f65739
 */
public class StringToInteger {

  /**
   * Big-O: O(n) where n is the input length -> while-loop
   *
   * @param s
   * @return
   */
  public int strToIntParsing(String s) {
    if (s.isEmpty())
      return 0;

    s = s.trim();
    char firstChar = s.charAt(0);

    if (!Character.isDigit(firstChar)) {
      if (s.length() == 1 || (firstChar != '-' && firstChar != '+'))
        return 0;
    }

    int i = 0;
    if (firstChar == '-' || firstChar == '+')
      i = 1;


    while (i < s.length()) {
      if (!Character.isDigit(s.charAt(i)))
        break;
      i++;
    }

    long result = Long.parseLong(s.substring(0, i));

    if (result > Integer.MAX_VALUE)
      return Integer.MAX_VALUE;
    else if (result < Integer.MIN_VALUE)
      return Integer.MIN_VALUE;
    else
      return (int) result;
  }

  /**
   * Big-O: O(n) where n is the input length -> while-loop
   *
   * @param s
   * @return
   */
  public int strToIntMod(String s) {
    if (s.isEmpty())
      return 0;

    s = s.trim();
    char firstChar = s.charAt(0);

    if (!Character.isDigit(firstChar)) {
      if (s.length() == 1 || (firstChar != '-' && firstChar != '+'))
        return 0;
    }

    long result = 0;
    boolean negative = false;
    int i = 0, j;

    if (firstChar == '-' || firstChar == '+') {
      i = 1;
      if (firstChar == '-')
        negative = true;
    }

    j = i;
    while (j < s.length()) {
      if (!Character.isDigit(s.charAt(j)))
        break;
      j++;
    }

    while (i < j) {
      result *= 10;
      result += (s.charAt(i) - '0');
      i++;
    }

    if (negative)
      result = -result;

    if (result > Integer.MAX_VALUE)
      return Integer.MAX_VALUE;
    else if (result < Integer.MIN_VALUE)
      return Integer.MIN_VALUE;
    else
      return (int) result;
  }
}
