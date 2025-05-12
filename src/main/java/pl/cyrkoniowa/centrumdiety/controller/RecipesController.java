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
import pl.cyrkoniowa.centrumdiety.dto.RecipeDto;
import pl.cyrkoniowa.centrumdiety.service.RecipeService;

@Slf4j
@Controller
public class RecipesController {

    RecipeService recipeService;

    /**
     * Konstruktor klasy RecipesController.
     *
     * @param recipeService serwis do obsługi przepisów
     */
    @Autowired
    public RecipesController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

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
    public String showAddRecipePage(Model model) {
        model.addAttribute("recipeDto", new RecipeDto());
        return "dietitian/add-recipe";
    }

    /**
     * Wyświetla stronę edycji przepisu
     *
     * @param request żądanie HTTP
     * @return widok edycji przepisu
     */
    @GetMapping("/edit-recipe")
    public String showEditRecipePage(HttpServletRequest request, Model model) {
        model.addAttribute("recipeDto", new RecipeDto());
        if(request.getParameter("name")!=null){
            RecipeDto recipeDto = recipeService.findRecipeByName(request.getParameter("name"));
            if(recipeDto!=null){
                model.addAttribute("recipeDto", recipeDto);
            }
        }
        return "dietitian/add-recipe";
    }

    /**
     * Przetwarza formularz Dodawania/edycji przepisu.
     *
     * @param recipeDto przepis z formularza
     * @param bindingResult wynik walidacji formularza
     * @param session       sesja HTTP
     * @param model         model do przekazywania danych do widoku
     * @return nazwa widoku po dodaniu przepisu
     */
    @PostMapping("/process-recipe-form")
    public String processRecipeForm(
            @Valid @ModelAttribute("recipeDto") RecipeDto recipeDto,
            BindingResult bindingResult,
            HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            return "dietitian/add-recipe";
        }
        recipeService.save(recipeDto);
        return "dietitian/dashboard";
    }
}
