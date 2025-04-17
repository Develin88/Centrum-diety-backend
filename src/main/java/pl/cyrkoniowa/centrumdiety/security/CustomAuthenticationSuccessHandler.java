package pl.cyrkoniowa.centrumdiety.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pl.cyrkoniowa.centrumdiety.entity.Account;
import pl.cyrkoniowa.centrumdiety.service.AccountService;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AccountService accountService;

    public CustomAuthenticationSuccessHandler(AccountService theAccountService) {
        accountService = theAccountService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        System.out.println("In customAuthenticationSuccessHandler");

        String userName = authentication.getName();

        System.out.println("userName=" + userName);

        Account account = accountService.findByUserName(userName);

        //Włożenie danych użytkownika do sesji
        HttpSession session = request.getSession();
        session.setAttribute("account", account);

        //przekierowanie na stronę główną
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }

}
