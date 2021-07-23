package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class DuplicateNumberTest {

  private DuplicateNumber duplicateNumber = new DuplicateNumber();

  @Test
  public void findDuplicateSet() {
    int[] numbers1 = {1, 2, 3, 4, 5, 4};
    assertEquals(4, duplicateNumber.findDuplicateSet(numbers1));

    int[] numbers2 = {1, 1};
    assertEquals(1, duplicateNumber.findDuplicateSet(numbers2));
  }

  @Test
  public void findDuplicateSort() {
    int[] numbers1 = {4, 4, 3, 2, 5, 1};
    assertEquals(4, duplicateNumber.findDuplicateSort(numbers1));

    int[] numbers2 = {1, 1};
    assertEquals(1, duplicateNumber.findDuplicateSort(numbers2));
  }
}