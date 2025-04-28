package pl.cyrkoniowa.centrumdiety.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.cyrkoniowa.centrumdiety.dto.AccountDto;
import pl.cyrkoniowa.centrumdiety.dto.RecipeDto;
import pl.cyrkoniowa.centrumdiety.service.AccountService;
import pl.cyrkoniowa.centrumdiety.service.RecipeService;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/recipes")
public class RecipesRestController {
    private final RecipeService recipeService;
    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Konstruktor klasy RecipesRestController.
     *
     * @param recipeService serwis obsługujący operacje na przepisach
     */
    @Autowired
    public RecipesRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Pobranie przepisów z paginacją oraz filtrowaniem.
     *
     * @param textToSearch tekst do filtrowania przepisów
     * @param page numer strony
     * @param size rozmiar strony
     */
    @GetMapping("/findRecipes")
    public Page<RecipeDto> findRecipes(@RequestParam(required = false) String textToSearch,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(required = false) String sortBy,
                                       @RequestParam(required = false) String order) {
        return recipeService.findRecipesByText(textToSearch, page, size, sortBy, order);
    }
}
