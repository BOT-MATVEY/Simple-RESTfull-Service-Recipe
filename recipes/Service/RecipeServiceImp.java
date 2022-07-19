package recipes.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import recipes.DTO.Recipe;
import recipes.DTO.User;
import recipes.Repository.RecipeRepository;
import recipes.Repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecipeServiceImp implements RecipeService {
    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public Map<String, Integer> PostRecipe(Recipe inputRecipe) {
        Recipe newRecipe = new Recipe();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails details = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByEmail(details.getUsername());

        newRecipe.setName(inputRecipe.getName());
        newRecipe.setDescription(inputRecipe.getDescription());
        newRecipe.setIngredients(inputRecipe.getIngredients());
        newRecipe.setDirections(inputRecipe.getDirections());
        newRecipe.setDate(LocalDateTime.now());
        newRecipe.setCategory(inputRecipe.getCategory());
        newRecipe.setUser(user);

        int id = recipeRepository
                .save(newRecipe).getId();

        return Map.of("id", id);
    }
    @Transactional
    @Modifying
    @Override
    public Optional<Recipe> UpdateRecipe (int id, Recipe recipe) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails details = (UserDetails) auth.getPrincipal();

        if (!recipeRepository
                .existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

        Optional<Recipe> oldRecepie = recipeRepository
                .findById(id);

        if (details.getUsername() != oldRecepie.get().getUser().getEmail()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        else {
            Recipe oldRecipe2 = oldRecepie.get();
            oldRecipe2.setName(recipe.getName());
            oldRecipe2.setDescription(recipe.getDescription());
            oldRecipe2.setIngredients(recipe.getIngredients());
            oldRecipe2.setDirections(recipe.getDirections());
            oldRecipe2.setDate(LocalDateTime.now());
            oldRecipe2.setCategory(recipe.getCategory());

            //if (oldRecipe2.getDate().toString().length() < 7) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            recipeRepository.save(recipe);

            return Optional.of(recipe);
        }
    }

    @Override
    public Optional<Recipe> GetRecipe(int id) {
        if (recipeRepository.existsById(id)) {

            Optional <Recipe> GotRecipe = recipeRepository.findById(id);
            return GotRecipe;
        }
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public void DeleteRecipe(int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails details = (UserDetails) auth.getPrincipal();

        if (recipeRepository.existsById(id)) {
            }
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Recipe recipe = recipeRepository.findById(id).get();

        if (details.getUsername() != recipe.getUser().getEmail()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        else {
            recipeRepository
                    .deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public Optional<List<Recipe>> SearchRecipeByName(String name) {
        if (name.length() >= 1) {
            List<Recipe> ListRecipe = recipeRepository
                    .findByNameContainingIgnoreCaseOrderByDateDesc(name);
            if (ListRecipe.isEmpty()) return Optional.of(ListRecipe);
            else return Optional.of(ListRecipe);
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @Override
    public Optional<List<Recipe>> SearchRecipeByCategory(String category) {
        if (category.length() >= 1 ) {
            List<Recipe> ListRecipe = recipeRepository
                    .findByCategoryIgnoreCaseOrderByDateDesc(category);
            if (ListRecipe.isEmpty()) return Optional.of(ListRecipe);
            else return Optional.of(ListRecipe);
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

    }
}
