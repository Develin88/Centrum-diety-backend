package pl.cyrkoniowa.centrumdiety.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.cyrkoniowa.centrumdiety.dto.IngredientDto;
import pl.cyrkoniowa.centrumdiety.service.IngredientService;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientsRestController {
    private final IngredientService ingredientService;
    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Konstruktor klasy IngredientsRestController.
     *
     * @param ingredientService serwis obsługujący operacje na składnikach
     */
    @Autowired
    public IngredientsRestController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    /**
     * Pobranie składników z paginacją oraz filtrowaniem.
     *
     * @param textToSearch tekst do filtrowania przepisów
     * @param page numer strony
     * @param size rozmiar strony
     */
    @GetMapping("/findIngredients")
    public Page<IngredientDto> findIngredients(@RequestParam(required = false) String textToSearch,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(required = false) String sortBy,
                                               @RequestParam(required = false) String order) {
        return ingredientService.findIngredientsByText(textToSearch, page, size, sortBy, order);
    }

    @GetMapping("/findIngredientByName")
    public IngredientDto findIngredientByName(@RequestParam String name) {
        return ingredientService.findIngredientByName(name);
    }
}
