package ru.sbpau.mit;

import org.junit.Test;

import static org.junit.Assert.*;

public class Function2Test {
    private Function1<Integer, Integer> succ = a -> a + 1;
    private Function2<Integer, Integer, Integer> summ = (a, b) -> a + b;
    private Function2<Integer, Integer, Integer> prod = (a, b) -> a * b;

    @Test
    public void applyTest() {
        assertEquals(4, (int) summ.apply(1, 3));
        assertEquals(6, (int) prod.apply(2, 3));
    }

    @Test
    public void compose() {
        assertEquals(5, (int) summ.compose(succ).apply(1, 3));
        assertEquals(4, (int) prod.compose(succ).apply(1, 3));
    }

    @Test
    public void bind1() {
        assertEquals(2, (int) summ.bind1(1).apply(9999999, 1));
    }

    @Test
    public void bind2() {
        assertEquals(2, (int) summ.bind2(1).apply(1, 9999999));
    }

    @Test
    public void curry() {
        assertEquals(summ.apply(1, 3), summ.curry().apply(1).apply(3));
    }
}