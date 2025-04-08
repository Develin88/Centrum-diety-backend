package pl.cyrkoniowa.centrumdiety.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String showHome() {
        return "home";
    }

    @GetMapping("/podstrona-dietetyk")
    public String podStronaDietetyk() {
        return "podstrona-dietetyk";
    }

    @GetMapping("/podstrona-admin")
    public String podStronaAdmin() {
        return "podstrona-admin";
    }
}
