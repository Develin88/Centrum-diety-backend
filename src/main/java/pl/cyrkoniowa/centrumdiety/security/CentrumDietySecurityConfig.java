package pl.cyrkoniowa.centrumdiety.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pl.cyrkoniowa.centrumdiety.service.AccountService;

/**
 * Klasa konfiguracyjna zabezpieczeń aplikacji Centrum Diety.
 * Definiuje ustawienia uwierzytelniania, autoryzacji i inne aspekty bezpieczeństwa.
 */
@Configuration
public class CentrumDietySecurityConfig {

    /**
     * Tworzy i konfiguruje enkoder haseł BCrypt.
     *
     * @return skonfigurowany obiekt BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Tworzy i konfiguruje dostawcę uwierzytelniania DAO.
     *
     * @param accountService serwis obsługujący operacje na kontach użytkowników
     * @return skonfigurowany obiekt DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(AccountService accountService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(accountService);
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

    /**
     * Konfiguruje łańcuch filtrów bezpieczeństwa dla aplikacji.
     * Definiuje reguły dostępu do zasobów, konfigurację formularza logowania,
     * obsługę wylogowania i inne aspekty bezpieczeństwa.
     *
     * @param http obiekt konfiguracji bezpieczeństwa HTTP
     * @param customAuthenticationSuccessHandler niestandardowy handler udanego uwierzytelnienia
     * @return skonfigurowany łańcuch filtrów bezpieczeństwa
     * @throws Exception w przypadku błędu konfiguracji
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/register/**").permitAll()
                        .requestMatchers("/dashboard","/api/recipes/findRecipes","/api/recipes/findIngredients").hasAnyRole(Roles.DIETITIAN.getRoleName(),Roles.PATIENT.getRoleName(),Roles.ADMIN.getRoleName())
                        .requestMatchers("/dietitian-dashboard","/add-recipe","/add-ingredient").hasRole(Roles.DIETITIAN.getRoleName())
                        .requestMatchers("/patient-dashboard").hasRole(Roles.PATIENT.getRoleName())
                        .requestMatchers("/admin-dashboard","/api/accounts/**","/admin/**").hasRole(Roles.ADMIN.getRoleName())
                        .anyRequest()
                        .authenticated()
        ).formLogin(form ->
                form
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticateUser")
                        .defaultSuccessUrl("/dashboard", true)
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler((request, response, exception) -> {
                            // Zapisujemy błąd w sesji
                            request.getSession().setAttribute("loginError", "Niepoprawny login lub hasło.");
                            response.sendRedirect("/login");
                        })
                        .permitAll()
        ).logout(logout -> logout
                .logoutUrl("/logout") // Domyślny URL, który służy do wylogowania
                .logoutSuccessHandler((request, response, authentication) -> {
                    request.getSession().setAttribute("loginInfo", "Zostałeś wylogowany.");
                    response.sendRedirect("/login");
                }).permitAll()
        ).exceptionHandling(configurer -> configurer.accessDeniedPage("/accessDenied"));
        return http.build();
    }
}
