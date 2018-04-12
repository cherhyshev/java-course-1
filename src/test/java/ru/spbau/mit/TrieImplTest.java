package ru.spbau.mit;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrieImplTest {
    private TrieImpl testTrie = new TrieImpl();

    @Test
    public void dimasExample() {
        testTrie.add("abcdefg");
        assertTrue(testTrie.contains("abcdefg"));
        assertTrue(testTrie.size() == 1);
        testTrie.remove("abcdefg");
        assertTrue(!testTrie.contains("abcdefg"));
        assertTrue(testTrie.size() == 0);
    }


    @Test
    public void typicalCase1() {
        testTrie.add("asdfg");
        assertTrue(testTrie.contains("asdfg"));
        assertTrue(testTrie.size() == 1);
        testTrie.remove("asdfg");
        assertTrue(!testTrie.contains("asdfg"));
        assertTrue(testTrie.size() == 0);
    }

    @Test
    public void typicalCase2() {
        testTrie.add("asdfg");
        assertTrue(testTrie.howManyStartsWithPrefix("asd") == 1);
        testTrie.remove("asdfg");
        assertTrue(testTrie.howManyStartsWithPrefix("asd") == 0);
    }

    @Test
    public void trieSpecificCase() {
        testTrie.add("asd");
        assertTrue(testTrie.size() == 1);
        testTrie.add("asd");
        assertTrue(testTrie.size() == 1);
        testTrie.add("asdfg");
        assertTrue(testTrie.size() == 2);
        testTrie.add("asdfg");
        assertTrue(testTrie.size() == 2);
        assertTrue(testTrie.howManyStartsWithPrefix("asd") == 2);
        assertTrue(testTrie.howManyStartsWithPrefix("asdf") == 1);
        testTrie.remove("asd");
        assertFalse(testTrie.contains("asd"));
        assertTrue(testTrie.size() == 1);
        testTrie.remove("asd");
        assertFalse(testTrie.contains("asd"));
        assertTrue(testTrie.size() == 1);
        assertTrue(testTrie.howManyStartsWithPrefix("asd") == 1);
        testTrie.remove("asdfg");
        assertTrue(testTrie.size() == 0);
        assertTrue(testTrie.howManyStartsWithPrefix("asd") == 0);
        testTrie.remove("asdfg");
        assertTrue(testTrie.size() == 0);
        assertTrue(testTrie.howManyStartsWithPrefix("asd") == 0);
    }

    @Test
    public void nullEmptyCase() {
        testTrie.add(null);
        assertTrue(testTrie.size() == 0);
        testTrie.add("");
        assertTrue(testTrie.size() == 0);
        testTrie.remove(null);
        assertTrue(testTrie.size() == 0);
        testTrie.remove("");
        assertTrue(testTrie.size() == 0);

    }
}