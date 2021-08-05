package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * https://www.notion.so/jarvisdev/Find-the-Duplicate-Number-55681ad1a1bb4b61a1a7b0d3b4835cbe
 */
public class DuplicateNumber {

  /**
   * Big-O: O(n) -> for-loop (n: length of array)
   *
   * @param numbers array of int
   * @return duplicate number in array
   */
  public int findDuplicateSet(int[] numbers) {
    Set<Integer> uniqueNumbers = new HashSet<>();
    int preSize = 0;

    for (int num : numbers) {
      uniqueNumbers.add(num);
      if (uniqueNumbers.size() == preSize) {
        return num;
      }
      preSize++;
    }
    return 0;
  }

  /**
   * Big-O: O(nlogn) -> for-loop: O(n) & QuickSort: O(nlogn)
   *
   * @param numbers array of int
   * @return duplicate number in array
   */
  public int findDuplicateSort(int[] numbers) {
    Arrays.sort(numbers);
    for (int i = 1; i < numbers.length; i++ ) {
      if (numbers[i] == numbers[i-1])
        return numbers[i];
    }
    return 0;
  }
}
