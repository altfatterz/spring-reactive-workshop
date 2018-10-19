package com.example.demo.security;

import org.springframework.web.reactive.function.server.ServerRequest;

public interface SecurityManager {

    boolean hasAccess(ServerRequest request);

}

