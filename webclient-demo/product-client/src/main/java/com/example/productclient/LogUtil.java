package com.example.productclient;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;

@Slf4j
public class LogUtil {

    public static void logTime(Instant start) {
        log.debug("Elapsed time: " + Duration.between(start, Instant.now()).toMillis() + "ms");
    }

}
