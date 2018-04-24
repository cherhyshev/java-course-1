package ru.sbpau.mit;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

class Collections {
    private Collections() {
    }

    /**
     * @param f   - функция от 1 аргумента
     * @param a
     * @param <I>
     * @param <O>
     * @return
     */
    static <I, O> Iterable<O> map(final Function1<? super I, ? extends O> f, final Iterable<? extends I> a) {
        ArrayList<O> mappedList = new ArrayList<>();
        for (I elem : a) {
            mappedList.add(f.apply(elem));
        }
        return mappedList;
    }

    static <I> Iterable<I> filter(final Predicate<? super I> p, final Iterable<? extends I> a) {
        ArrayList<I> filteredList = new ArrayList<>();
        for (I elem : a) {
            if (p.apply(elem)) {
                filteredList.add(elem);
            }
        }
        return filteredList;
    }

    static <I> Iterable<I> takeWhile(final Predicate<? super I> p, final Iterable<? extends I> a) {
        ArrayList<I> takenList = new ArrayList<>();
        for (I elem : a) {
            if (!p.apply(elem)) {
                break;
            }
            takenList.add(elem);
        }
        return takenList;
    }

    static <I> Iterable<I> takeUnless(final Predicate<? super I> p, final Iterable<? extends I> a) {
        return takeWhile(p.not(), a);
    }


    static <I1, I2> I1 foldl(final Function2<? super I1, ? super I2, ? extends I1> f,
                             final I1 start, final Iterable<? extends I2> a) {
        I1 acc = start;

        for (I2 elem : a) {
            acc = f.apply(acc, elem);
        }
        return acc;
    }

    static <I1, I2> I2 foldr(final Function2<? super I1, ? super I2, ? extends I2> f,
                             final I2 start, final Iterable<? extends I1> a) {
        if (!a.iterator().hasNext()) {
            return start;
        }
        List<I1> newA = (List<I1>) a;
        return f.apply(head(newA), foldr(f, start, tail(newA)));
    }

    private static <I> I head(final List<I> lst) {
        return lst.get(0);
    }

    private static <I> List<I> tail(final List<I> lst) {
        ArrayList<I> xs = new ArrayList<I>(lst.size() - 1);
        xs.addAll(0, lst.subList(1, lst.size()));
        return unmodifiableList(xs);
    }


}
