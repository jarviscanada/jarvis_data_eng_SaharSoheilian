package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class RemoveElementTest {

  @Test
  public void removeElement() {
    int[] nums = new int[]{4, 3, 5, 8, 1, 2, 3, 3};

    RemoveElement re = new RemoveElement();
    assertEquals(re.removeElement(nums, 3), 5);
  }
}