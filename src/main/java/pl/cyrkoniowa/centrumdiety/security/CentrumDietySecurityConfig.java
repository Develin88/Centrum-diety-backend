package pl.cyrkoniowa.centrumdiety.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class CentrumDietySecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails michal = User.builder()
                .username("michal")
                .password("{noop}pass")
                .roles("USER")
                .build();

        UserDetails ewelina = User.builder()
                .username("ewelina")
                .password("{noop}pass")
                .roles("DIETITIAN")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}pass")
                .roles("ADMIN")
                .build();


        return new InMemoryUserDetailsManager(michal, ewelina, admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/css/**", "/js/**").permitAll()
                        .requestMatchers("/podstrona-dietetyk").hasRole("DIETITIAN")
                        .requestMatchers("/podstrona-admin").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
        ).formLogin(form ->
                form
                        .loginPage("/showLoginPage")
                        .loginProcessingUrl("/authenticateUser")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/showLoginPage?error")
                        .permitAll()
        ).logout(logout -> logout.permitAll()
        ).exceptionHandling(configurer -> configurer.accessDeniedPage("/accessDenied"));
        return http.build();
    }
}


