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

    public RecipeIngredientDto(RecipeIngredient recipeIngredient) {
        this.ingredientName = recipeIngredient.getIngredient().getName();
        this.ingredientAmount = recipeIngredient.getAmount();
        this.ingredientMeasurementUnit = recipeIngredient.getMeasurementUnit();
    }
}
