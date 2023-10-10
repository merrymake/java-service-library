package eu.merrymake.service.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MerrymakeTests {

    enum Result { Nothing, A, B, Init ;}

    @Test
    public void first_action() {
        final var spy = new Result[]{Result.Nothing};
        Merrymake.service(new String[]{"a", "{}"}).handle("a", (payload, envelope) -> {
            spy[0] = Result.A;
        }).handle("b", (payload, envelope) -> {
            spy[0] = Result.B;
        }).init(() -> {
            spy[0] = Result.Init;
        });
        Assertions.assertEquals(Result.A, spy[0]);
    }

    @Test
    public void second_action() {
        final var spy = new Result[]{Result.Nothing};
        Merrymake.service(new String[]{"b", "{}"}).handle("a", (payload, envelope) -> {
            spy[0] = Result.A;
        }).handle("b", (payload, envelope) -> {
            spy[0] = Result.B;
        }).init(() -> {
            spy[0] = Result.Init;
        });
        Assertions.assertEquals(Result.B, spy[0]);
    }

    @Test
    public void init() {
        final var spy = new Result[]{Result.Nothing};
        Merrymake.service(new String[]{"", "{}"}).handle("a", (payload, envelope) -> {
            spy[0] = Result.A;
        }).handle("b", (payload, envelope) -> {
            spy[0] = Result.B;
        }).init(() -> {
            spy[0] = Result.Init;
        });
        Assertions.assertEquals(Result.Init, spy[0]);
    }

}
