package pl.cyrkoniowa.centrumdiety.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.cyrkoniowa.centrumdiety.dao.RecipeDao;
import pl.cyrkoniowa.centrumdiety.dto.RecipeDto;
import pl.cyrkoniowa.centrumdiety.entity.Recipe;
import pl.cyrkoniowa.centrumdiety.service.RecipeService;

import java.util.List;

/**
 * Implementacja serwisu RecipeService odpowiedzialnego za operacje na przepisach.
 */
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeDao recipeDao;

    @Autowired
    public RecipeServiceImpl(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    /**
     * {@inheritDoc}
     * Implementacja metody pobierającej listę przepisów spełniających filtr wraz ze stronicowaniem i sortowaniem.
     *
     * @param textToSearch tekst do wyszukania
     * @param pageNumber numer strony
     * @param pageSize rozmiar strony
     * @param sortBy nazwa kolumny do sortowania
     * @param order kierunek sortowania (asc/desc)
     * @return lista obiektów RecipeDto
     */
    @Override
    public Page<RecipeDto> findRecipesByText(String textToSearch, int pageNumber, int pageSize, String sortBy, String order) {
        List<RecipeDto> recipeDtos;
        long totalRecords;

        if(textToSearch==null || textToSearch.isEmpty()){
            List<Recipe> recipes = recipeDao.findAllRecipes(pageNumber, pageSize, sortBy, order);
            recipeDtos = recipes.stream()
                    .map(RecipeDto::new)
                    .toList();
            totalRecords = recipeDao.countAllRecipes();
        }else{
            List<Recipe> recipes = recipeDao.findRecipesByText(textToSearch, pageNumber, pageSize, sortBy, order);
            recipeDtos = recipes.stream()
                    .map(RecipeDto::new)
                    .toList();
            totalRecords = recipeDao.countRecipesByText(textToSearch);
        }
        return new PageImpl<>(recipeDtos, PageRequest.of(pageNumber, pageSize), totalRecords);
    }
}
