package pl.cyrkoniowa.centrumdiety.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.cyrkoniowa.centrumdiety.entity.Ingredient;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {

    @NotNull(message = "Pole Nazwa składnika jest wymagane")
    @Size(min = 1, message = "Pole Nazwa składnika jest wymagane")
    private String name;

    @NotNull(message = "Pole Ilość kalorii jest wymagane")
    @Min(value = 0, message = "Ilość kalorii nie może być ujemna")
    @Max(value = 99999, message = "Ilość kalorii nie może być większa niż 99999")
    private Integer caloriesAmount;

    @NotNull(message = "Pole Indeks glikemiczny jest wymagane")
    @DecimalMin(value = "0.0", message = "Indeks glikemiczny nie może być ujemny")
    @Digits(integer = 3, fraction = 2, message = "Indeks glikemiczny może mieć maksymalnie 3 cyfr przed przecinkiem i 2 po przecinku")
    private BigDecimal glycemicIndex;

    @NotNull(message = "Pole Ilość białka jest wymagane")
    @DecimalMin(value = "0.0", message = "Ilość białka nie może być ujemna")
    @Digits(integer = 3, fraction = 2, message = "Ilość białka może mieć maksymalnie 3 cyfr przed przecinkiem i 2 po przecinku")
    private BigDecimal proteinAmount;

    @NotNull(message = "Pole Ilość tłuszczów jest wymagane")
    @DecimalMin(value = "0.0", message = "Ilość tłuszczów nie może być ujemna")
    @Digits(integer = 3, fraction = 2, message = "Ilość tłuszczów może mieć maksymalnie 3 cyfr przed przecinkiem i 2 po przecinku")
    private BigDecimal fatsAmount;

    @NotNull(message = "Pole Ilość węglowodanów jest wymagane")
    @DecimalMin(value = "0.0", message = "Ilość węglowodanów nie może być ujemna")
    @Digits(integer = 3, fraction = 2, message = "Ilość węglowodanów może mieć maksymalnie 3 cyfr przed przecinkiem i 2 po przecinku")
    private BigDecimal carbsAmount;

    public IngredientDto(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.caloriesAmount = ingredient.getCaloriesAmount();
        this.glycemicIndex = ingredient.getGlycemicIndex();
        this.proteinAmount = ingredient.getProteinAmount();
        this.fatsAmount = ingredient.getFatsAmount();
        this.carbsAmount = ingredient.getCarbsAmount();
    }
}
