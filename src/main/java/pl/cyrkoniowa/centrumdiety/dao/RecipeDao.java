package pl.cyrkoniowa.centrumdiety.dao;

import pl.cyrkoniowa.centrumdiety.entity.Ingredient;
import pl.cyrkoniowa.centrumdiety.entity.Recipe;

import java.util.List;

public interface RecipeDao {

    /**
     * Pobiera listę wszystkich Przepisów.
     *
     * @return lista obiektów Recipe
     */
    List<Recipe> findAllRecipes(int pageNumber, int pageSize, String sortBy, String order);

    /**
     * Zlicza ilość przepisów
     *
     * @return liczba obiektów Recipe
     */
    long countAllRecipes();

    /**
     * Pobiera listę przepisów spełniających filtr wraz ze stronicowaniem.
     *
     * @return lista obiektów Recipe
     */
    List<Recipe> findRecipesByText(String textToSearch, int pageNumber, int pageSize, String sortBy, String order);

    /**
     * Zlicza ilość przepisów spełniających filtr
     *
     * @return liczba obiektów Recipe
     */
    long countRecipesByText(String textToSearch);

    /**
     * Znajduje przepis w bazie danych na podstawie nazwy.
     *
     * @param name nazwa przepisu do wyszukania
     */
    Recipe findRecipeByName(String name);

    /**
     * Zapisuje przepis w bazie danych.
     *
     * @param recipe obiekt przepisu do zapisania
     */
    void save(Recipe recipe);

    /**
     * Usuwa przepis z bazy danych.
     *
     * @param recipe obiekt przepisu do zapisania
     */
    void deleteRecipe(Recipe recipe);
}
