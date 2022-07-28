package org.cis120;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

/** Tests for TweetParser */
public class TweetParserTest {

    // A helper function to create a singleton list from a word
    private static List<String> singleton(String word) {
        List<String> l = new LinkedList<String>();
        l.add(word);
        return l;
    }

    // A helper function for creating lists of strings
    private static List<String> listOfArray(String[] words) {
        List<String> l = new LinkedList<String>();
        for (String s : words) {
            l.add(s);
        }
        return l;
    }

    // Cleaning and filtering tests -------------------------------------------
    @Test
    public void removeURLsTest() {
        assertEquals("abc . def.", TweetParser.removeURLs("abc http://www.cis.upenn.edu. def."));
        assertEquals("abc", TweetParser.removeURLs("abc"));
        assertEquals("abc ", TweetParser.removeURLs("abc http://www.cis.upenn.edu"));
        assertEquals("abc .", TweetParser.removeURLs("abc http://www.cis.upenn.edu."));
        assertEquals(" abc ", TweetParser.removeURLs("http:// abc http:ala34?#?"));
        assertEquals(" abc  def", TweetParser.removeURLs("http:// abc http:ala34?#? def"));
        assertEquals(" abc  def", TweetParser.removeURLs("https:// abc https``\":ala34?#? def"));
        assertEquals("abchttp", TweetParser.removeURLs("abchttp"));
    }

    @Test
    public void testCleanWord() {
        assertEquals("abc", TweetParser.cleanWord("abc"));
        assertEquals("abc", TweetParser.cleanWord("ABC"));
        assertNull(TweetParser.cleanWord("@abc"));
        assertEquals("ab'c", TweetParser.cleanWord("ab'c"));
    }

    @Test
    public void testExtractColumnGetsCorrectColumn() {
        assertEquals(
                " This is a tweet.",
                TweetParser.extractColumn(
                        "wrongColumn, wrong column, wrong column!, This is a tweet.", 3
                )
        );
    }

    @Test
    public void parseAndCleanSentenceNonEmptyFiltered() {
        List<String> sentence = TweetParser.parseAndCleanSentence("abc #@#F");
        List<String> expected = new LinkedList<String>();
        expected.add("abc");
        assertEquals(expected, sentence);
    }

    @Test
    public void testParseAndCleanTweetRemovesURLS1() {
        List<List<String>> sentences = TweetParser
                .parseAndCleanTweet("abc http://www.cis.upenn.edu");
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(singleton("abc"));
        assertEquals(expected, sentences);
    }

    @Test
    public void testCsvDataToTrainingDataSimpleCSV() {
        StringReader sr = new StringReader(
                "0, The end should come here.\n" +
                        "1, This comes from data with no duplicate words!"
        );
        BufferedReader br = new BufferedReader(sr);
        List<List<String>> tweets = TweetParser.csvDataToTrainingData(br, 1);
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(listOfArray("the end should come here".split(" ")));
        expected.add(listOfArray("this comes from data with no duplicate words".split(" ")));
        assertEquals(expected, tweets);
    }

    @Test
    public void testCsvDataToTweetsSimpleCSV() {
        StringReader sr = new StringReader(
                "0, The end should come here.\n" +
                        "1, This comes from data with no duplicate words!"
        );
        BufferedReader br = new BufferedReader(sr);
        List<String> tweets = TweetParser.csvDataToTweets(br, 1);
        List<String> expected = new LinkedList<String>();
        expected.add(" The end should come here.");
        expected.add(" This comes from data with no duplicate words!");
        assertEquals(expected, tweets);
    }

    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */

    @Test
    public void testExtractColumnGetsNullColumn() {
        assertEquals(
                null,
                TweetParser.extractColumn(
                        null, 3
                )
        );
    }

    @Test
    public void testExtractColumnGetsNoAppropriateColumn() {
        assertNull(
                TweetParser.extractColumn(
                        "wrongColumn, wrong column, wrong column!.", 3
                )
        );
    }

    @Test
    public void testExtractColumnEmptyColumn() {
        assertEquals(
                "",
                TweetParser.extractColumn(
                        "wrongColumn, wrong column!,, This is a tweet.", 2
                )
        );
    }

    @Test
    public void testExtractColumnColumnOfSpaces() {
        assertEquals(
                "  ",
                TweetParser.extractColumn(
                        "wrongColumn, wrong column!,  , This is a tweet.", 2
                )
        );
    }

    @Test
    public void testCsvDataToTweetsCSVLineOutOfBound() {
        StringReader sr = new StringReader(
                "0, The end should come here.\n" +
                        "1.\n" +
                        "2, This comes from data with no duplicate words!"
        );
        BufferedReader br = new BufferedReader(sr);
        List<String> tweets = TweetParser.csvDataToTweets(br, 1);
        List<String> expected = new LinkedList<String>();
        expected.add(" The end should come here.");
        expected.add(" This comes from data with no duplicate words!");
        assertEquals(expected, tweets);
    }

    @Test
    public void parseAndCleanSentenceToLowerCase() {
        List<String> sentence = TweetParser.parseAndCleanSentence("AbC dEf");
        List<String> expected = new LinkedList<String>();
        expected.add("abc");
        expected.add("def");
        assertEquals(expected, sentence);
    }

    @Test
    public void parseAndCleanSentenceAllFiltered() {
        List<String> sentence = TweetParser.parseAndCleanSentence("!1 @2 #3");
        List<String> expected = new LinkedList<String>();
        assertEquals(expected, sentence);
    }

    @Test
    public void parseAndCleanSentenceEmptyString() {
        List<String> sentence = TweetParser.parseAndCleanSentence("");
        assertTrue(sentence.isEmpty());
    }

    @Test
    public void parseAndCleanSentenceSpaceString() {
        List<String> sentence = TweetParser.parseAndCleanSentence("  ");
        assertTrue(sentence.isEmpty());
    }

    @Test
    public void parseAndCleanSentenceCleanSpaces() {
        List<String> sentence = TweetParser.parseAndCleanSentence(" abc ");
        List<String> expected = new LinkedList<String>();
        expected.add("abc");
        assertEquals(expected, sentence);
    }

    @Test
    public void testParseAndCleanTweetRemovesBadWords() {
        List<List<String>> sentences = TweetParser
                .parseAndCleanTweet("abc! def @#.");
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(singleton("abc"));
        expected.add(singleton("def"));
        assertEquals(expected, sentences);
    }

    @Test
    public void testParseAndCleanTweetRemovesEntireSentence() {
        List<List<String>> sentences = TweetParser
                .parseAndCleanTweet("abc@# def$.");
        List<List<String>> expected = new LinkedList<List<String>>();
        assertEquals(expected, sentences);
        assertTrue(sentences.isEmpty());
    }

    @Test
    public void testCsvDataToTrainingDataCSVRemoveBadWords() {
        StringReader sr = new StringReader(
                "0, The end should come here.\n" +
                        "1, This comes from data with no duplicate words!\n" +
                        "2, Bad word 1@2#3 is removed!"
        );
        BufferedReader br = new BufferedReader(sr);
        List<List<String>> tweets = TweetParser.csvDataToTrainingData(br, 1);
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(listOfArray("the end should come here".split(" ")));
        expected.add(listOfArray("this comes from data with no duplicate words".split(" ")));
        expected.add(listOfArray("bad word is removed".split(" ")));
        assertEquals(expected, tweets);
    }

    @Test
    public void testCsvDataToTrainingDataCSVRemoveURL() {
        StringReader sr = new StringReader(
                "0, The end should come here.\n" +
                        "1, This comes from data with no duplicate words!\n" +
                        "2, URL http://www.cis.upenn.edu is removed!"
        );
        BufferedReader br = new BufferedReader(sr);
        List<List<String>> tweets = TweetParser.csvDataToTrainingData(br, 1);
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(listOfArray("the end should come here".split(" ")));
        expected.add(listOfArray("this comes from data with no duplicate words".split(" ")));
        expected.add(listOfArray("url is removed".split(" ")));
        assertEquals(expected, tweets);
    }
}
