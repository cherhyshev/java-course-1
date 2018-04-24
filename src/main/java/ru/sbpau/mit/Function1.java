package ru.sbpau.mit;

public interface Function1<I, O> {
    O apply(I argument);

    default <E> Function1<I, E> compose(Function1<? super O, ? extends E> g) {
        return (I argument) -> g.apply(this.apply(argument));
    }
}
