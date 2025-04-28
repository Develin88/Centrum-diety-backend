package pl.cyrkoniowa.centrumdiety.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.cyrkoniowa.centrumdiety.dao.IngredientDao;
import pl.cyrkoniowa.centrumdiety.dto.IngredientDto;
import pl.cyrkoniowa.centrumdiety.entity.Ingredient;
import pl.cyrkoniowa.centrumdiety.service.IngredientService;

import java.util.List;

/**
 * Implementacja serwisu IngredientService odpowiedzialnego za operacje na składnikach.
 */
@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientDao ingredientDao;

    @Autowired
    public IngredientServiceImpl(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
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
     * @return lista obiektów IngredientDto
     */
    @Override
    public Page<IngredientDto> findIngredientsByText(String textToSearch, int pageNumber, int pageSize, String sortBy, String order) {
        List<IngredientDto> ingredientDtos;
        long totalRecords;

        if(textToSearch==null || textToSearch.isEmpty()){
            List<Ingredient> recipes = ingredientDao.findAllIngredients(pageNumber, pageSize, sortBy, order);
            ingredientDtos = recipes.stream()
                    .map(IngredientDto::new)
                    .toList();
            totalRecords = ingredientDao.countAllIngredients();
        }else{
            List<Ingredient> recipes = ingredientDao.findIngredientsByText(textToSearch, pageNumber, pageSize, sortBy, order);
            ingredientDtos = recipes.stream()
                    .map(IngredientDto::new)
                    .toList();
            totalRecords = ingredientDao.countIngredientsByText(textToSearch);
        }
        return new PageImpl<>(ingredientDtos, PageRequest.of(pageNumber, pageSize), totalRecords);
    }
}
