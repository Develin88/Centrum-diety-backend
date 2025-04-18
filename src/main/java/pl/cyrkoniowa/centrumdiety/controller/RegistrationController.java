package pl.cyrkoniowa.centrumdiety.controller;

import java.util.logging.Logger;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cyrkoniowa.centrumdiety.entity.Account;
import pl.cyrkoniowa.centrumdiety.service.AccountService;
import pl.cyrkoniowa.centrumdiety.dto.UserRegistrationDto;

/**
 * Kontroler odpowiedzialny za obsługę procesu rejestracji użytkowników.
 */
@Controller
@RequestMapping("/register")
public class RegistrationController {

	private final Logger logger = Logger.getLogger(getClass().getName());

    private final AccountService accountService;

	/**
	 * Konstruktor kontrolera rejestracji.
	 *
	 * @param accountService serwis obsługujący operacje na kontach użytkowników
	 */
	@Autowired
	public RegistrationController(AccountService accountService) {
		this.accountService = accountService;
	}

	/**
	 * Inicjalizuje binder do przetwarzania danych formularza.
	 * Usuwa białe znaki z pól tekstowych.
	 *
	 * @param dataBinder binder do przetwarzania danych formularza
	 */
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	/**
	 * Wyświetla formularz rejestracji.
	 * @param model model do przekazywania danych do widoku
	 * @return nazwa widoku formularza rejestracji
	 */
	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model model) {
		model.addAttribute("userRegistrationDto", new UserRegistrationDto());
		return "register/registration-form";
	}

	/**
	 * Przetwarza formularz rejestracji.
	 * @param userRegistrationDto dane użytkownika z formularza
	 * @param bindingResult wynik walidacji formularza
	 * @param session sesja HTTP
	 * @param model model do przekazywania danych do widoku
	 * @return nazwa widoku potwierdzenia rejestracji
	 */
	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(
			@Valid @ModelAttribute("userRegistrationDto") UserRegistrationDto userRegistrationDto,
			BindingResult bindingResult,
			HttpSession session, Model model) {

		String userName = userRegistrationDto.getUserName();
		logger.info("Processing registration form for: " + userName);
		//Walidacja formularza
		 if (bindingResult.hasErrors()){
			 return "register/registration-form";
		 }
        Account existing = accountService.findByUserName(userName);
        if (existing != null){
			model.addAttribute("userRegistrationDto", new UserRegistrationDto());
			model.addAttribute("registrationError", "Użytkownik o takim loginie jest już zarejestrowany.");
			logger.warning("Użytkownik o takim loginie jest już zarejestrowany: " + userName);
        	return "register/registration-form";
        }
        // Stworzenie użytkownika i zapis go w bazie danych
		accountService.save(userRegistrationDto);
        logger.info("Użytkownik stworzony poprawnie: " + userName);
		//Ustawienie użytkownika w sesji
		session.setAttribute("user", userRegistrationDto);
        return "register/registration-confirmation";
	}
}
