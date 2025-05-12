package pl.cyrkoniowa.centrumdiety.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.cyrkoniowa.centrumdiety.dao.IngredientDao;
import pl.cyrkoniowa.centrumdiety.dao.RecipeDao;
import pl.cyrkoniowa.centrumdiety.dao.impl.IngredientDaoImpl;
import pl.cyrkoniowa.centrumdiety.dto.IngredientDto;
import pl.cyrkoniowa.centrumdiety.dto.RecipeDto;
import pl.cyrkoniowa.centrumdiety.dto.RecipeIngredientDto;
import pl.cyrkoniowa.centrumdiety.entity.Ingredient;
import pl.cyrkoniowa.centrumdiety.entity.Recipe;
import pl.cyrkoniowa.centrumdiety.entity.RecipeIngredient;
import pl.cyrkoniowa.centrumdiety.service.RecipeService;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementacja serwisu RecipeService odpowiedzialnego za operacje na przepisach.
 */
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeDao recipeDao;
    private final IngredientDao ingredientDao;

    @Autowired
    public RecipeServiceImpl(RecipeDao recipeDao, IngredientDao ingredientDao) {
        this.recipeDao = recipeDao;
        this.ingredientDao = ingredientDao;
    }

    /**
     * {@inheritDoc}
     * Implementacja metody pobierającej listę przepisów spełniających filtr wraz ze stronicowaniem i sortowaniem.
     *
     * @param textToSearch tekst do wyszukania
     * @param pageNumber   numer strony
     * @param pageSize     rozmiar strony
     * @param sortBy       nazwa kolumny do sortowania
     * @param order        kierunek sortowania (asc/desc)
     * @return lista obiektów RecipeDto
     */
    @Override
    public Page<RecipeDto> findRecipesByText(String textToSearch, int pageNumber, int pageSize, String sortBy, String order) {
        List<RecipeDto> recipeDtos;
        long totalRecords;

        if (textToSearch == null || textToSearch.isEmpty()) {
            List<Recipe> recipes = recipeDao.findAllRecipes(pageNumber, pageSize, sortBy, order);
            recipeDtos = recipes.stream()
                    .map(RecipeDto::new)
                    .toList();
            totalRecords = recipeDao.countAllRecipes();
        } else {
            List<Recipe> recipes = recipeDao.findRecipesByText(textToSearch, pageNumber, pageSize, sortBy, order);
            recipeDtos = recipes.stream()
                    .map(RecipeDto::new)
                    .toList();
            totalRecords = recipeDao.countRecipesByText(textToSearch);
        }
        return new PageImpl<>(recipeDtos, PageRequest.of(pageNumber, pageSize), totalRecords);
    }

    /**
     * {@inheritDoc}
     * Implementacja metody znajdującej przepis na podstawie nazwy.
     * @param name nazwa przepisu do wyszukania
     */
    @Override
    public RecipeDto findRecipeByName(String name) {
        Recipe recipe = recipeDao.findRecipeByName(name);
        if (recipe == null) {
            return null;
        }
        return new RecipeDto(recipe);
    }

    /**
     * {@inheritDoc}
     * Implementacja metody zapisującej przepis na podstawie danych z formularza dodawania przepisu.
     *
     * @param recipeDto obiekt zawierający dane przepisu
     */
    @Override
    public void save(RecipeDto recipeDto) {
        Recipe recipe = recipeDao.findRecipeByName(recipeDto.getName());
        if (recipe == null) {
            recipe = new Recipe();
            recipe.setName(recipeDto.getName());
        }
        recipe.setCaloriesAmount(recipeDto.getCaloriesAmount());
        recipe.setCarbsAmount(recipeDto.getCarbsAmount());
        recipe.setProteinAmount(recipeDto.getProteinAmount());
        recipe.setGlycemicLoad(recipeDto.getGlycemicLoad());
        recipe.setFatsAmount(recipeDto.getFatsAmount());
        recipe.setPreparationDescription(recipeDto.getPreparationDescription());
        recipe.setPreparationTime(recipeDto.getPreparationTime());
        if (recipeDto.getIngredientsList() != null && !recipeDto.getIngredientsList().isEmpty()) {
            List<RecipeIngredient> recipeIngredients = new ArrayList<>();
            for (RecipeIngredientDto ingredientDto : recipeDto.getIngredientsList()) {
                RecipeIngredient recipeIngredient = new RecipeIngredient();
                Ingredient ingredient = ingredientDao.findIngredientByName(ingredientDto.getIngredientName());
                if (ingredient != null) {
                    recipeIngredient.setRecipe(recipe);
                    recipeIngredient.setIngredient(ingredient);
                    recipeIngredient.setAmount(ingredientDto.getIngredientAmount());
                    recipeIngredient.setMeasurementUnit(ingredientDto.getIngredientMeasurementUnit());
                    recipeIngredients.add(recipeIngredient);
                }
            }
            recipe.getIngredientsList().clear();
            recipe.getIngredientsList().addAll(recipeIngredients);
        }
        recipeDao.save(recipe);
    }

    @Override
    public void deleteRecipe(String name) {
        Recipe recipe = recipeDao.findRecipeByName(name);
        if(recipe != null) {
            recipeDao.deleteRecipe(recipe);
        }
    }
}
