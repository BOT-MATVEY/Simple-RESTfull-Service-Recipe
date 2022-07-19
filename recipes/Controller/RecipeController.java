package recipes.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.DTO.Recipe;
import recipes.Service.RecipeServiceImp;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class RecipeController {
    @Autowired
    RecipeServiceImp recipeService;


    @PostMapping ("/api/recipe/new")
    public Map<String, Integer> getPostNewRecipe(@RequestBody Recipe recipeJSON)
    {


        return recipeService.PostRecipe(recipeJSON) ;
    }


    @GetMapping ("/api/recipe/{id}")
    public Optional<Recipe> getRecipe(@PathVariable int id) {
        return recipeService.GetRecipe(id);
    }

    @DeleteMapping("/api/recipe/{id}")
    public void deleteRecipe(@PathVariable int id) {
        recipeService.DeleteRecipe(id);
    }

    @PutMapping("api/recipe/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Optional<Recipe> UpdateRecipe(@PathVariable int id, @RequestBody Recipe recipe) {
        return recipeService.UpdateRecipe(id, recipe);
    }

    @GetMapping("api/recipe/search")
    public Optional<List<Recipe>> SearchRecipe (@RequestParam(value = "category", required = false) String category,
                                       @RequestParam(value = "name", required = false) String name)
            {

        if (!Optional.ofNullable(category).isEmpty() && Optional.ofNullable(name).isEmpty() )
            return recipeService.SearchRecipeByCategory(category);
        else if (!Optional.ofNullable(name).isEmpty() && Optional.ofNullable(category).isEmpty())
            return recipeService.SearchRecipeByName(name);
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("api/recipe/searchname")
    public Optional<List<Recipe>> SearchRecipeByName (@RequestParam(value = "name", required = false) String name)
    {
        return recipeService.SearchRecipeByName(name);
    }

}
