package ru.sbpau.mit;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CollectionsTest {
    private List<Integer> listInt = Arrays.asList(1, 2, 3, 4, 5);
    private List<String> listStr = Arrays.asList("a", "s", "d", "f", "g", "h");
    private Function2<Integer, Integer, Integer> summ = (a, b) -> a + b;
    private Function2<String, String, String> concat = (a, b) -> a + b;


    @Test
    public void map() {
        assertEquals(Collections.map(i -> i * i, listInt), Arrays.asList(1, 4, 9, 16, 25));
        assertEquals(Collections.map(i -> i + "1", listStr), Arrays.asList("a1", "s1", "d1", "f1", "g1", "h1"));
    }

    @Test
    public void filter() {
        assertEquals(Collections.filter(i -> i % 3 == 2, listInt), Arrays.asList(2, 5));
        assertEquals(Collections.filter(i -> i.startsWith("a"), listStr), Arrays.asList("a"));
    }

    @Test
    public void takeWhile() {
        assertEquals(Collections.takeWhile(i -> i <= 2, listInt), Arrays.asList(1, 2));
    }

    @Test
    public void takeUnless() {
        assertEquals(Collections.takeUnless(i -> i > 3, listInt), Arrays.asList(1, 2, 3));
    }

    @Test
    public void foldl() {
        assertEquals((int) Collections.foldl(summ, 0, listInt), 15);
        assertEquals(Collections.foldl(concat, "", listStr), "asdfgh");

    }

    @Test
    public void foldr() {
        assertEquals((int) Collections.foldr(summ, 0, listInt), 15);
        assertEquals(Collections.foldr(concat, "", listStr), "asdfgh");
    }
}