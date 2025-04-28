package pl.cyrkoniowa.centrumdiety.service;

import org.springframework.data.domain.Page;
import pl.cyrkoniowa.centrumdiety.dto.RecipeDto;

public interface RecipeService {
    /**
     * Pobiera listę przepisów spełniających filtr wraz ze stronicowaniem i sortowaniem.
     *
     * @param textToSearch tekst do wyszukania
     * @param pageNumber numer strony
     * @param pageSize rozmiar strony
     * @param sortBy nazwa kolumny do sortowania
     * @param order kierunek sortowania (asc/desc)
     * @return lista obiektów RecipeDto
     */
    Page<RecipeDto> findRecipesByText(String textToSearch, int pageNumber, int pageSize, String sortBy, String order);
}
