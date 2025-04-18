package pl.cyrkoniowa.centrumdiety.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.cyrkoniowa.centrumdiety.service.AccountService;

/**
 * Kontroler odpowiedzialny za operacje zarządzania użytkownikami.
 */
@Controller
@RequestMapping("/admin/users")
public class UserManagementController {

    private final AccountService accountService;

    @Autowired
    public UserManagementController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Promuje użytkownika z roli Pacjenta na rolę Dietetyka
     *
     * @param userName nazwa użytkownika osoby do awansowania
     * @param redirectAttributes dla wiadomości flash
     * @return przekierowanie do panelu administratora
     */
    @PostMapping("/{userName}/promote")
    public String promoteUserToDietitian(@PathVariable String userName, RedirectAttributes redirectAttributes) {
        try {
            accountService.promotePatientToDietitian(userName);
            redirectAttributes.addFlashAttribute("successMessage", "Użytkownik " + userName + " został pomyślnie promowany na dietetyka.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Nie udało się promować użytkownika: " + e.getMessage());
        }
        return "redirect:/admin-dashboard";
    }

    /**
     * Degraduje użytkownika z roli Dietetyka do roli Pacjenta
     *
     * @param userName nazwa użytkownika osoby do zdegradowania
     * @param redirectAttributes dla wiadomości flash
     * @return przekierowanie do panelu administratora
     */
    @PostMapping("/{userName}/demote")
    public String demoteUserToPatient(@PathVariable String userName, RedirectAttributes redirectAttributes) {
        try {
            accountService.demoteDietitianToPatient(userName);
            redirectAttributes.addFlashAttribute("successMessage", "Użytkownik " + userName + " został pomyślnie zdegradowany do pacjenta.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Nie udało się zdegradować użytkownika: " + e.getMessage());
        }
        return "redirect:/admin-dashboard";
    }
}
