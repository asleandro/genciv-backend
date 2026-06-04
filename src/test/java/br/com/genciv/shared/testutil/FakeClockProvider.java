package br.com.genciv.shared.testutil;

import br.com.genciv.shared.application.ClockProvider;

import java.time.LocalDateTime;

public class FakeClockProvider implements ClockProvider {

    private final LocalDateTime fixedTime;

    public FakeClockProvider(LocalDateTime fixedTime){
        this.fixedTime = fixedTime;
    }

    @Override
    public LocalDateTime now(){
        return fixedTime;
    }
}
