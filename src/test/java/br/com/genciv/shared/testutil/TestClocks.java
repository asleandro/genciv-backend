package br.com.genciv.shared.testutil;

import java.time.LocalDateTime;

public class TestClocks {

    private TestClocks(){}

    public static LocalDateTime fixed(){
        return LocalDateTime.of(2026, 1, 1, 10, 0);
    }

}
