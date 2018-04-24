package ru.sbpau.mit;

import org.junit.Test;

import static org.junit.Assert.*;

public class Function1Test {

    private Function1<Integer, Integer> succ = a -> a + 1;
    private Function1<Integer, Integer> pred = a -> a - 1;
    private Function1<Integer, Integer> prod3 = a -> a * 3;

    @Test
    public void applyTest() {
        assertEquals(2, (int) succ.apply(1));
        assertEquals(2, (int) pred.apply(3));
        assertEquals(3, (int) prod3.apply(1));
    }

    @Test
    public void composeTest() {
        assertEquals(6, (int) succ.compose(prod3).apply(1));
        assertNotEquals((int) succ.compose(prod3).apply(1), (int) prod3.compose(succ).apply(1));
    }
}