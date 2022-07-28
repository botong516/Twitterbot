package org.cis120;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.BufferedReader;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for FileLineIterator */
public class FileLineIteratorTest {

    /*
     * Here's a test to help you out, but you still need to write your own.
     */

    @Test
    public void testHasNextAndNext() {

        // Note we don't need to create a new file here in order to test out our
        // FileLineIterator if we do not want to. We can just create a
        // StringReader to make testing easy!
        String words = "0, The end should come here.\n"
                + "1, This comes from data with no duplicate words!";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        FileLineIterator li = new FileLineIterator(br);
        assertTrue(li.hasNext());
        assertEquals("0, The end should come here.", li.next());
        assertTrue(li.hasNext());
        assertEquals("1, This comes from data with no duplicate words!", li.next());
        assertFalse(li.hasNext());
    }

    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */
    @Test
    public void testNoHasNextAndNoNextEmpty() {

        String words = "";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        FileLineIterator li = new FileLineIterator(br);
        assertFalse(li.hasNext());
        assertThrows(NoSuchElementException.class, () -> {
            li.next();
        });
    }

    @Test
    public void testHasNextAndNextSentenceAndSpace() {

        String words = "One.\n  ";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        FileLineIterator li = new FileLineIterator(br);
        assertTrue(li.hasNext());
        assertEquals("One.", li.next());
        assertTrue(li.hasNext());
        assertEquals("  ", li.next());
    }

    @Test
    public void testFileLineIteratorNullReader() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FileLineIterator((BufferedReader) null);
        });
    }

    @Test
    public void testFileLineIteratorNullFilepath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FileLineIterator((String) null);
        });
    }

    @Test
    public void testFileLineIteratorNonexistentFile() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FileLineIterator("Nonexistent file.csv");
        });
    }

    @Test
    public void testFileLineIteratorExistentFile() {
        FileLineIterator li = new FileLineIterator("files/dog_feelings_tweets.csv");
        assertTrue(li.hasNext());
    }
}
