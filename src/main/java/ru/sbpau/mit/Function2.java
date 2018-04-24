package ru.sbpau.mit;

public interface Function2<I1, I2, O> {
    O apply(I1 argument1, I2 argument2);

    default <E> Function2<I1, I2, E> compose(Function1<? super O, ? extends E> g) {
        return (I1 argument1, I2 argument2) -> g.apply(this.apply(argument1, argument2));
    }

    default Function2<I1, I2, O> bind1(I1 argument1) {
        return (I1 ignored, I2 argument2) -> apply(argument1, argument2);
    }

    default Function2<I1, I2, O> bind2(I2 argument2) {
        return (I1 argument1, I2 ignored) -> apply(argument1, argument2);
    }

    default Function1<I1, Function1<I2, O>> curry() {
        return (I1 argument1) -> ((I2 argument2) -> apply(argument1, argument2));
    }
}
