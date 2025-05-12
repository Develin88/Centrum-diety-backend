package pl.cyrkoniowa.centrumdiety.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.cyrkoniowa.centrumdiety.dto.IngredientDto;
import pl.cyrkoniowa.centrumdiety.dto.UserRegistrationDto;
import pl.cyrkoniowa.centrumdiety.service.IngredientService;

@Slf4j
@Controller
public class IngredientsController {

    IngredientService ingredientService;

    /**
     * Konstruktor klasy IngredientsController.
     *
     * @param ingredientService serwis do obsługi składników
     */
    @Autowired
    public IngredientsController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

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
     * Wyświetla stronę dodawania składnika
     *
     * @param model model do przekazywania danych do widoku
     * @return widok dodawania składnika
     */
    @GetMapping("/add-ingredient")
    public String showAddIngredientPage(Model model) {
        model.addAttribute("ingredientDto", new IngredientDto());
        return "dietitian/add-ingredient";
    }

    /**
     * Wyświetla stronę edycji składnika
     *
     * @param request żądanie HTTP
     * @return widok edycji składnika
     */
    @GetMapping("/edit-ingredient")
    public String showEditIngredientPage(HttpServletRequest request, Model model) {
        model.addAttribute("ingredientDto", new IngredientDto());
        if(request.getParameter("name")!=null){
            IngredientDto ingredientDto = ingredientService.findIngredientByName(request.getParameter("name"));
            if(ingredientDto!=null){
                model.addAttribute("ingredientDto", ingredientDto);
            }
        }
        return "dietitian/add-ingredient";
    }


    /**
     * Przetwarza formularz Dodawania/edycji składnika.
     *
     * @param ingredientDto składnika z formularza
     * @param bindingResult wynik walidacji formularza
     * @param session       sesja HTTP
     * @param model         model do przekazywania danych do widoku
     * @return nazwa widoku po dodaniu składnika
     */
    @PostMapping("/process-ingredient-form")
    public String processIngredientForm(
            @Valid @ModelAttribute("ingredientDto") IngredientDto ingredientDto,
            BindingResult bindingResult,
            HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            return "dietitian/add-ingredient";
        }
        ingredientService.save(ingredientDto);
        return "dietitian/dashboard";
    }
}
