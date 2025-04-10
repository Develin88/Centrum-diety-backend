package pl.cyrkoniowa.centrumdiety.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import pl.cyrkoniowa.centrumdiety.service.UserService;

import javax.sql.DataSource;

@Configuration
public class CentrumDietySecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserService userService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        jdbcUserDetailsManager.setUsersByUsernameQuery("select id, password, active from users where id=?");
//
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select user_id, role from roles where user_id=?");
//
//        return jdbcUserDetailsManager;
//    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//
//        UserDetails michal = User.builder()
//                .username("michal")
//                .password("{noop}pass")
//                .roles("USER")
//                .build();
//
//        UserDetails ewelina = User.builder()
//                .username("ewelina")
//                .password("{noop}pass")
//                .roles("DIETITIAN")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{noop}pass")
//                .roles("ADMIN")
//                .build();
//
//
//        return new InMemoryUserDetailsManager(michal, ewelina, admin);
//    }

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


