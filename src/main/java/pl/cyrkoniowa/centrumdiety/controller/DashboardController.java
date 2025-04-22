package pl.cyrkoniowa.centrumdiety.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.cyrkoniowa.centrumdiety.entity.Account;
import org.springframework.security.core.Authentication;
import pl.cyrkoniowa.centrumdiety.security.Roles;
import pl.cyrkoniowa.centrumdiety.service.AccountService;

import java.util.List;
import java.util.logging.Logger;

/**
 * Kontroler odpowiedzialny za obsługę widoków paneli użytkowników.
 * Zgodny z najlepszymi praktykami Spring MVC z odpowiednią strukturą URL.
 */
@Slf4j
@Controller
public class DashboardController {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private final AccountService accountService;

    /**
     * Konstruktor kontrolera paneli użytkowników.
     *
     * @param accountService serwis obsługujący operacje na kontach użytkowników
     */
    @Autowired
    public DashboardController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Obsługuje główny adres URL i przekierowuje w zależności od statusu uwierzytelnienia.
     *
     * @param authentication bieżące uwierzytelnienie
     * @return przekierowanie do odpowiedniego panelu lub strony logowania
     */
    @GetMapping("/")
    public String handleRootRedirect(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboard";
        } else {
            return "redirect:/login";
        }
    }

    /**
     * Przekierowuje do odpowiedniego panelu na podstawie roli użytkownika.
     *
     * @param request żądanie HTTP
     * @param response odpowiedź HTTP
     * @return przekierowanie do panelu specyficznego dla roli
     */
    @GetMapping("/dashboard")
    public String showDashboard(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // Sprawdzamy, czy użytkownik ma rolę "PATIENT"
            boolean isPatient = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals(Roles.PATIENT.getRoleNameWithPrefix()));
            if (isPatient) {
                // Jeśli użytkownik ma rolę PATIENT
                return "redirect:/patient-dashboard";
            }
            // Sprawdzamy, czy użytkownik ma rolę "DIETITIAN"
            boolean isDietitian = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals(Roles.DIETITIAN.getRoleNameWithPrefix()));
            if (isDietitian) {
                // Jeśli użytkownik ma rolę "DIETITIAN"
                return "redirect:/dietitian-dashboard";
            }
            // Sprawdzamy, czy użytkownik ma rolę "ADMIN"
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals(Roles.ADMIN.getRoleNameWithPrefix()));
            if (isAdmin) {
                // Jeśli użytkownik ma rolę "ADMIN"
                return "redirect:/admin-dashboard";
            }
        }
        return "redirect:/login";
    }

    /**
     * Wyświetla panel pacjenta.
     *
     * @return widok panelu pacjenta
     */
    @GetMapping("/patient-dashboard")
    public String showPatientDashboard() {
        return "patient/dashboard";
    }

    /**
     * Wyświetla panel dietetyka.
     *
     * @return widok panelu dietetyka
     */
    @GetMapping("/dietitian-dashboard")
    public String showDietitianDashboard() {
        return "dietitian/dashboard";
    }

    /**
     * Wyświetla panel administratora z listami pacjentów i dietetyków.
     *
     * @param model model Spring MVC do przekazywania danych do widoku
     * @return widok panelu administratora
     */
    @GetMapping("/admin-dashboard")
    public String showAdminDashboard(Model model) {
//        // Pobierz listę wszystkich pacjentów
//        List<Account> patients = accountService.findAllPatients();
//
//        // Pobierz listę wszystkich dietetyków
//        List<Account> dietitians = accountService.findAllDietitians();
//
//        // Dodaj listy do modelu
//        model.addAttribute("patients", patients);
//        model.addAttribute("dietitians", dietitians);

        return "admin/dashboard";
    }
}
