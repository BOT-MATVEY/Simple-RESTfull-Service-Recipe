package recipes.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.DTO.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {


    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc (String category);

    List<Recipe> findByNameContainingIgnoreCaseOrderByDateDesc (String name);

}
