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
import pl.cyrkoniowa.centrumdiety.user.WebUser;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	private final Logger logger = Logger.getLogger(getClass().getName());

    private AccountService accountService;

	@Autowired
	public RegistrationController(AccountService accountService) {
		this.accountService = accountService;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {
		theModel.addAttribute("webUser", new WebUser());
		return "register/registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(
			@Valid @ModelAttribute("webUser") WebUser theWebUser,
			BindingResult theBindingResult,
			HttpSession session, Model theModel) {

		String userName = theWebUser.getUserName();
		logger.info("Processing registration form for: " + userName);

		// form validation
		 if (theBindingResult.hasErrors()){
			 return "register/registration-form";
		 }

		// check the database if user already exists
        Account existing = accountService.findByUserName(userName);
        if (existing != null){
        	theModel.addAttribute("webUser", new WebUser());
			theModel.addAttribute("registrationError", "User name already exists.");

			logger.warning("User name already exists.");
        	return "register/registration-form";
        }

        // create user account and store in the databse
		accountService.save(theWebUser);

        logger.info("Successfully created user: " + userName);

		// place user in the web http session for later use
		session.setAttribute("user", theWebUser);

        return "register/registration-confirmation";
	}
}
