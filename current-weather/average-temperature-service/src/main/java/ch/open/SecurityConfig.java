package ch.open;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    MapReactiveUserDetailsService userDetailsService() {
        UserDetails john = User.withUsername("user").password("{noop}user").roles("USER").build();
        UserDetails joe = User.withUsername("admin").password("{noop}admin").roles("ADMIN", "USER").build();

        return new MapReactiveUserDetailsService(john, joe);
    }

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeExchange()
                    .pathMatchers(HttpMethod.GET, "/measurements/**").hasRole("USER")
                    .pathMatchers(HttpMethod.POST, "/measurements/**").hasRole("ADMIN")
                    .anyExchange().authenticated()
                .and()
                    .httpBasic();

        return http.build();
    }

}
