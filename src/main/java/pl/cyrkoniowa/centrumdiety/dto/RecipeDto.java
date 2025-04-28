package pl.cyrkoniowa.centrumdiety.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.cyrkoniowa.centrumdiety.entity.Recipe;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
    private String name;
    private int caloriesAmount;
    private String glycemicLoad;
    private String proteinAmount;
    private String fatsAmount;
    private String carbsAmount;
    private int preparationTime;

    public RecipeDto(Recipe recipe) {
        this.name = recipe.getName();
        this.caloriesAmount = recipe.getCaloriesAmount();
        this.glycemicLoad = recipe.getGlycemicLoad();
        this.proteinAmount = recipe.getProteinAmount().toString();
        this.fatsAmount = recipe.getFatsAmount().toString();
        this.carbsAmount = recipe.getCarbsAmount().toString();
        this.preparationTime = recipe.getPreparationTime();
    }
}
