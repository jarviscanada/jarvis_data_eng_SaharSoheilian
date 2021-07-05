package ca.jrvs.practice.codingChallenge;

/**
 * https://www.notion.so/jarvisdev/Remove-Element-e0d4c4b17227484eb3b93c736d6cea29
 */
public class RemoveElement {

  /**
   * Big-O: O(n) -> one for-loop
   *
   * @param nums input int array to remove an special value from it
   * @param val  value that should be removed from the input array
   * @return number of remained elements in the array after removing
   */
  public int removeElement(int[] nums, int val) {

    int i = 0;
    for (int j = 0; j < nums.length; j++) {
      if (nums[j] != val) {
        nums[i] = nums[j];
        i++;
      }
    }

    return i;
  }
}