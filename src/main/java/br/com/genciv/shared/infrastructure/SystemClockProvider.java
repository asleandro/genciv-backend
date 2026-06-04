package br.com.genciv.shared.infrastructure;

import br.com.genciv.shared.application.ClockProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SystemClockProvider implements ClockProvider {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
