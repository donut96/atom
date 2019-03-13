package ru.example;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class MyTest {

    @Test
    public void getWordFromFile() throws Exception {
        assertNotNull(BullsAndCows.getWordFromFile("dictionary.txt"));
    }

    @Test
    public void checkBulls() throws Exception {
        String word = "java";
        String userWord = "lava";

        assertEquals(3, BullsAndCows.checkBullsAndCows(userWord, word)[0]);
    }

    @Test
    public void checkCows() throws Exception {
        String word = "java";
        String userWord = "lava";

        assertEquals(0, BullsAndCows.checkBullsAndCows(userWord, word)[1]);
    }
}