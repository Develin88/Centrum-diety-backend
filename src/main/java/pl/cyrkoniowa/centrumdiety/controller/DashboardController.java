package pl.cyrkoniowa.centrumdiety.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.cyrkoniowa.centrumdiety.entity.Account;
import org.springframework.security.core.Authentication;

import java.util.logging.Logger;

@Slf4j
@Controller
public class DashboardController {
    private final Logger logger = Logger.getLogger(getClass().getName());

    @GetMapping("/")
    public String handleRootRedirect(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboard";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // Sprawdzamy, czy użytkownik ma rolę "PATIENT"
            boolean isPatient = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_PATIENT"));
            if (isPatient) {
                // Jeśli użytkownik ma rolę PATIENT
                return "redirect:/patient-dashboard";
            }
            // Sprawdzamy, czy użytkownik ma rolę "DIETITIAN"
            boolean isDietitian = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_DIETITIAN"));
            if (isDietitian) {
                // Jeśli użytkownik ma rolę DIETITIAN
                return "redirect:/dietitian-dashboard";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/patient-dashboard")
    public String showPatientDashboard() {
        return "patient/dashboard";
    }

    @GetMapping("/dietitian-dashboard")
    public String showDietitianDashboard() {
        return "patient/dashboard";
    }
}
