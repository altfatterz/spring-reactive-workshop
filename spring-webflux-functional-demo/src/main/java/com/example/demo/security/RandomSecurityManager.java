package com.example.demo.security;

import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Random;

public class RandomSecurityManager implements SecurityManager {

    private Random rnd = new Random();

    @Override
    public boolean hasAccess(ServerRequest request) {
        return this.rnd.nextBoolean();
    }
}

