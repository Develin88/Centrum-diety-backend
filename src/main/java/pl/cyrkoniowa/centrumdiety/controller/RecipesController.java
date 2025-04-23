package pl.cyrkoniowa.centrumdiety.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class RecipesController {

    /**
     * Wyświetla listę przepisów
     *
     * @return widok listy przepisów
     */
    @GetMapping("/recipes-list")
    public String showRecipesListPage() {
        return "shared/display-recipes";
    }

    /**
     * Wyświetla dodawania przepisu
     *
     * @return widok dodawania przepisu
     */
    @GetMapping("/add-recipe")
    public String showAddRecipePage() {
        return "dietitian/add-recipe";
    }
}
