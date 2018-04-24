package ru.sbpau.mit;

import java.lang.Boolean;

public interface Predicate<I> extends Function1<I, Boolean> {
    @Override
    Boolean apply(I argument);


    //    наиболее общий тип тут и везде вообще
    //    Run -> Run with coverage
    default Predicate<I> or(Predicate<? super I> arg) {
        return (I argument) -> (arg.apply(argument) || this.apply(argument));
    }

    default Predicate<I> and(Predicate<? super I> arg) {
        return (I argument) -> (arg.apply(argument) && this.apply(argument));
    }

    default Predicate<I> not() {
        return (I argument) -> !this.apply(argument);
    }

    Predicate<Object> ALWAYS_TRUE = ignored -> true;
    Predicate<Object> ALWAYS_FALSE = ALWAYS_TRUE.not();

}
