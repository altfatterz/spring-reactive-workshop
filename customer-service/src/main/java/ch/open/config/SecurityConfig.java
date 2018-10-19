package ch.open.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

@Configuration
class SecurityConfig {

    @Bean
    MapReactiveUserDetailsService userDetailsService() {
        UserDetails john = User.withUsername("user").password("{noop}user").roles("USER").build();
        UserDetails joe = User.withUsername("admin").password("{noop}admin").roles("ADMIN", "USER").build();

        return new MapReactiveUserDetailsService(john, joe);
    }

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .authorizeExchange()
                    .pathMatchers("/customers").hasRole("ADMIN")
                    .pathMatchers("/customers-view").access(this::isAdmin)
                    .anyExchange().authenticated()
                .and()
                    .formLogin()
                .and()
                    .httpBasic();

        return http.build();
    }

    private Mono<AuthorizationDecision> isAdmin(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        return authentication
                .map(Authentication::getAuthorities)
                .map(authorities -> authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .map(AuthorizationDecision::new);
    }
}
