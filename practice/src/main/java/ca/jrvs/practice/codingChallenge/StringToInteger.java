package ca.jrvs.practice.codingChallenge;

public class StringToInteger {

  public int strToIntParsing(String s) {
    int intValue;
    s = s.trim();

    if (s.charAt(0) != '-' && s.charAt(0) != '+' && !Character.isDigit(s.charAt(0)))
      return 0;

    int i = 0;
    if (s.charAt(0) == '-' || s.charAt(0) == '+') {
      if (s.length() == 1)
        return 0;
      i = 1;
    }

    while (i < s.length()) {
      if (!Character.isDigit(s.charAt(i)))
        break;
      i++;
    }

    intValue = Integer.parseInt(s.substring(0, i));

    return intValue;
  }

  /**
   *
   * @param s
   * @return
   */
  public int strToIntMod(String s) {
    int intValue = 0, factor = 1;
    boolean negative = false;
    s = s.trim();

    if (s.charAt(0) != '-' && s.charAt(0) != '+' && !Character.isDigit(s.charAt(0)))
      return 0;

    int startIndex = 0;
    if (s.charAt(0) == '-' || s.charAt(0) == '+') {
      if (s.length() == 1)
        return 0;
      if (s.charAt(0) == '-')
        negative = true;
      startIndex = 1;
    }

    int endIndex = startIndex;
    while (endIndex < s.length()) {
      if (!Character.isDigit(s.charAt(endIndex)))
        break;
      endIndex++;
    }

    while (startIndex < endIndex) {
      intValue *= 10;
      intValue += (s.charAt(startIndex) - '0');
      startIndex++;
    }

    if (negative)
      intValue = -intValue;


    return intValue;
  }
}
