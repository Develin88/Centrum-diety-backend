package pl.cyrkoniowa.centrumdiety.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.cyrkoniowa.centrumdiety.entity.Recipe;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
    private String name;
    private int caloriesAmount;
    private String glycemicLoad;
    private BigDecimal proteinAmount;
    private BigDecimal fatsAmount;
    private BigDecimal carbsAmount;
    private int preparationTime;

    public RecipeDto(Recipe recipe) {
        this.name = recipe.getName();
        this.caloriesAmount = recipe.getCaloriesAmount();
        this.glycemicLoad = recipe.getGlycemicLoad();
        this.proteinAmount = recipe.getProteinAmount();
        this.fatsAmount = recipe.getFatsAmount();
        this.carbsAmount = recipe.getCarbsAmount();
        this.preparationTime = recipe.getPreparationTime();
    }
}
