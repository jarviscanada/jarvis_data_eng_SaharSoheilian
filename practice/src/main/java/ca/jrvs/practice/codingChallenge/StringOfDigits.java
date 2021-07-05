package ca.jrvs.practice.codingChallenge;

import java.util.regex.Pattern;

/**
 * https://www.notion.so/jarvisdev/Check-if-a-String-contains-only-digits-2d7732b3c8f747898c47a614dfc4a03f
 */
public class StringOfDigits {

  /**
   * Big-O: O(N) -> N: length of input string.
   * loops through the string and checks every char
   *
   * @param input
   * @return true if the input only contains digits
   */
  public boolean containDigitsASCII(String input) {
    for (int i = 0; i < input.length(); i++){
      if (input.charAt(i) < 48 || input.charAt(i) > 57)
        return false;
    }
    return true;
  }

  /**
   * Big-O: O(N) -> N: length of input string.
   *
   * @param input
   * @return true if the input only contains digits
   */
  public boolean containDigitsJavaAPI(String input) {
    boolean result = true;

    try {
      Integer.valueOf(input);
    } catch (NumberFormatException ex) {
      result = false;
    }

    return result;
  }

  /**
   * Big-O: O(N) -> N: length of input string.
   * matches each char in the String with the regex pattern
   *
   * @param input
   * @return
   */
  public boolean containDigitsRegex(String input) {
    return Pattern.matches("[0-9]+", input);
  }
}
