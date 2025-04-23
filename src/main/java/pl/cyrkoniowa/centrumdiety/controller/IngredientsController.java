package pl.cyrkoniowa.centrumdiety.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IngredientsController {
    /**
     * Wyświetla listę składników
     *
     * @return widok listy składników
     */
    @GetMapping("/ingredients-list")
    public String showIngredientsListPage() {
        return "shared/display-ingredients";
    }

    /**
     * Wyświetla dodawania składnika
     *
     * @return widok dodawania składnika
     */
    @GetMapping("/add-ingredient")
    public String showAddIngredientPage() {
        return "dietitian/add-ingredient";
    }
}
