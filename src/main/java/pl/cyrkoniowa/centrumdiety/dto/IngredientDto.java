package pl.cyrkoniowa.centrumdiety.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.cyrkoniowa.centrumdiety.entity.Ingredient;
import pl.cyrkoniowa.centrumdiety.entity.Recipe;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {

    private String name;
    private int caloriesAmount;
    private BigDecimal glycemicIndex;
    private BigDecimal proteinAmount;
    private BigDecimal fatsAmount;
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
