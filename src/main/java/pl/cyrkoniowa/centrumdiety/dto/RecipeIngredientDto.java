package pl.cyrkoniowa.centrumdiety.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.cyrkoniowa.centrumdiety.entity.RecipeIngredient;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDto {
    private String ingredientName;
    private BigDecimal ingredientAmount;
    private String ingredientMeasurementUnit;
    private Integer caloriesAmount;
    private BigDecimal glycemicIndex;
    private BigDecimal proteinAmount;
    private BigDecimal fatsAmount;
    private BigDecimal carbsAmount;

    public RecipeIngredientDto(RecipeIngredient recipeIngredient) {
        this.ingredientName = recipeIngredient.getIngredient().getName();
        this.ingredientAmount = recipeIngredient.getAmount();
        this.ingredientMeasurementUnit = recipeIngredient.getMeasurementUnit();
        this.caloriesAmount = recipeIngredient.getIngredient().getCaloriesAmount();
        this.glycemicIndex = recipeIngredient.getIngredient().getGlycemicIndex();
        this.proteinAmount = recipeIngredient.getIngredient().getProteinAmount();
        this.fatsAmount = recipeIngredient.getIngredient().getFatsAmount();
        this.carbsAmount = recipeIngredient.getIngredient().getCarbsAmount();
    }
}
