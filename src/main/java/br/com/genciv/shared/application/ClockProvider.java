package br.com.genciv.shared.application;

import java.time.LocalDateTime;

public interface ClockProvider {
    LocalDateTime now();
}
