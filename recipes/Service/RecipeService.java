package recipes.Service;


import recipes.DTO.Recipe;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RecipeService {
    public Map<String, Integer> PostRecipe(Recipe recepie);
    public Optional<Recipe> GetRecipe (int id);
    public void DeleteRecipe (int id);

    public Optional<Recipe> UpdateRecipe(int id, Recipe recepie);

    Optional<List<Recipe>> SearchRecipeByCategory (String category);

    Optional<List<Recipe>> SearchRecipeByName (String name);

}
