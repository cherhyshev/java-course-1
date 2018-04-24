package ru.sbpau.mit;

import org.junit.Test;

import static org.junit.Assert.*;

public class PredicateTest {
    private Predicate<Integer> leqThenThree = a -> a <= 3;
    private Predicate<Integer> moreThenThree = a -> a > 3;

    @Test
    public void apply() {
        assertEquals(true, leqThenThree.apply(1));
        assertNotEquals(false, leqThenThree.apply(1));
        assertEquals(true, moreThenThree.apply(4));
    }

    @Test
    public void notTest() {
        assertEquals(leqThenThree.not().apply(4), moreThenThree.apply(4));
        assertNotEquals(leqThenThree.not().apply(4), leqThenThree.apply(4));
    }

    @Test
    public void orTest() {
        assertEquals(leqThenThree.or(moreThenThree).apply(4), Predicate.ALWAYS_TRUE.apply(4));
    }

    @Test
    public void andTest() {
        assertEquals(leqThenThree.and(moreThenThree).apply(4), Predicate.ALWAYS_FALSE.apply(4));
    }
}