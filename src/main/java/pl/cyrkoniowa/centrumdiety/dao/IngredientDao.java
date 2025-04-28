package pl.cyrkoniowa.centrumdiety.dao;

import pl.cyrkoniowa.centrumdiety.entity.Ingredient;

import java.util.List;

public interface IngredientDao {
    /**
     * Pobiera listę wszystkich składników.
     *
     * @return lista obiektów Ingredient
     */
    List<Ingredient> findAllIngredients(int pageNumber, int pageSize, String sortBy, String order);

    /**
     * Zlicza ilość składników.
     *
     * @return liczba obiektów Ingredient
     */
    long countAllIngredients();

    /**
     * Pobiera listę składników spełniających filtr wraz ze stronicowaniem.
     *
     * @return lista obiektów Ingredient
     */
    List<Ingredient> findIngredientsByText(String textToSearch, int pageNumber, int pageSize, String sortBy, String order);

    /**
     * Zlicza ilość składników spełniających filtr
     *
     * @return liczba obiektów Ingredient
     */
    long countIngredientsByText(String textToSearch);
}
