package org.cis120;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Tests for TwitterBot class */
public class TwitterBotTest {

    /*
     * This tests whether your TwitterBot class itself is written correctly
     *
     * This test operates very similarly to our MarkovChain tests in its use of
     * `fixDistribution`, so make sure you know how to test MarkovChain before
     * testing this!
     */
    @Test
    public void simpleTwitterBotTest() {
        List<String> desiredTweet = new ArrayList<>(
                Arrays.asList(
                        "this", "comes", "from", "data", "with", "no", "duplicate", "words", ".",
                        "the", "end", "should", "come", "."
                )
        );
        String words = "0, The end should come here.\n"
                + "1, This comes from data with no duplicate words!";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        TwitterBot t = new TwitterBot(br, 1);
        t.fixDistribution(desiredTweet);

        String expected = "this comes from data with no duplicate words. the end should come.";
        String actual = TweetParser.replacePunctuation(t.generateTweet(12));
        assertEquals(expected, actual);
    }

    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */

    @Test
    public void testGenerateTweetChainHasEnoughValue() {
        String words = "0, CIS 120 rocks.\n"
                + "1, CIS 120 beats CIS 160.";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        TwitterBot t = new TwitterBot(br, 1);

        List<String> desiredTweet = new ArrayList<>(
                Arrays.asList(
                        "cis", "120", "rocks", "."
                )
        );
        t.fixDistribution(desiredTweet);

        String expected = "cis 120 rocks.";
        String actual = TweetParser.replacePunctuation(t.generateTweet(3));
        assertEquals(expected, actual);
    }

    @Test
    public void testGenerateTweetChainHasNoEnoughValue() {
        String words = "0, CIS rocks.\n"
                + "1, CIS 120 beats CIS 160.\n"
                + "2, MATH supports CIS.";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        TwitterBot t = new TwitterBot(br, 1);

        List<String> desiredTweet = new ArrayList<>(
                Arrays.asList(
                        "cis", "120", "beats", "cis", "160", ".",
                        "math", "supports", "cis", ".",
                        "cis", "rocks", "."
                )
        );
        t.fixDistribution(desiredTweet);

        String expected = "cis 120 beats cis 160. math supports cis. cis rocks.";
        String actual = TweetParser.replacePunctuation(t.generateTweet(10));
        assertEquals(expected, actual);
    }

    @Test
    public void testGenerateTweetChainHasNoSentence() {
        StringReader sr = new StringReader("");
        BufferedReader br = new BufferedReader(sr);
        TwitterBot t = new TwitterBot(br, 1);

        String actual = TweetParser.replacePunctuation(t.generateTweet(10));
        assertEquals("", actual);
    }

    @Test
    public void testGenerateTweetChainZeroNumOfWords() {
        String words = "0, CIS rocks.\n"
                + "1, CIS 120 beats CIS 160.\n"
                + "2, MATH supports CIS.";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        TwitterBot t = new TwitterBot(br, 1);

        String actual = TweetParser.replacePunctuation(t.generateTweet(0));
        assertEquals("", actual);
    }

    @Test
    public void testGenerateTweetChainNegativeNumOfWords() {
        String words = "0, CIS rocks.\n"
                + "1, CIS 120 beats CIS 160.\n"
                + "2, MATH supports CIS.";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        TwitterBot t = new TwitterBot(br, 1);

        assertThrows(IllegalArgumentException.class, () -> {
            t.generateTweet(-10);
        });
    }
}
