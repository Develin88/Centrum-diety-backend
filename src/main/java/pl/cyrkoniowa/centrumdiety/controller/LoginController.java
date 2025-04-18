package pl.cyrkoniowa.centrumdiety.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    /**
     * Wyświetla stronę logowania.
     * @param request żądanie HTTP
     * @param model model do przekazywania danych do widoku
     * @return nazwa widoku logowania
     */
    @GetMapping("/login")
    public String showLoginPage(HttpServletRequest request, Model model){
        if(request.getSession().getAttribute("loginError") != null){
            model.addAttribute("loginError", request.getSession().getAttribute("loginError"));
            request.getSession().removeAttribute("loginError");
        }
        if(request.getSession().getAttribute("loginInfo") != null){
            model.addAttribute("loginInfo", request.getSession().getAttribute("loginInfo"));
            request.getSession().removeAttribute("loginInfo");
        }
        return "login";
    }

    /**
     * Wyświetla stronę błędu dostępu.
     * @return nazwa widoku błędu dostępu
     */
    @GetMapping("/accessDenied")
    public String showAccessDenied(){
        return "access-denied";
    }
}
