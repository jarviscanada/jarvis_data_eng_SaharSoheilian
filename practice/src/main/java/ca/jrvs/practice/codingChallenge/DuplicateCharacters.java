package ca.jrvs.practice.codingChallenge;

import java.util.*;

/**
 * https://www.notion.so/jarvisdev/Duplicate-Characters-1589ab0c81d5444d84caa9294f58ec75
 */
public class DuplicateCharacters {

  /**
   * Big-O: O(n) -> for-loop with the size of input string(n)
   *
   * @param s input string
   * @return Set of duplicate characters in the input string
   */
  public Set<Character> duplicateChar(String s) {
    Set<Character> charSet = new HashSet<>();
    Set<Character> duplicates = new HashSet<>();

    s = s.toLowerCase();
    s = s.replace(" ", "");

    charSet.add(s.charAt(0));
   int preSize = 1;

    for (int i = 1; i < s.length(); i++) {
      charSet.add(s.charAt(i));
      if (charSet.size() == preSize) {
        duplicates.add(s.charAt(i));
        continue;
      }
      preSize++;
    }

    return duplicates;
  }
}
