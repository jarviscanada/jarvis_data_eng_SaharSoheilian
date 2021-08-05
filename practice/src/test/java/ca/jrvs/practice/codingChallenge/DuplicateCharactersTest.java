package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class DuplicateCharactersTest {

    private DuplicateCharacters dupChars = new DuplicateCharacters();

    @Test
    public void duplicateChar() {
        Set<Character> expected = new HashSet<>();
        expected.add('a');
        expected.add('c');

        assertEquals(expected, dupChars.duplicateChar("A black cat"));
    }
}