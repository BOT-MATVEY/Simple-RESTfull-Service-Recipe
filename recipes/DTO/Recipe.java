package recipes.DTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import recipes.Controller.RecipeController;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private int id;

    @Column(name = "name")
    @JsonView(RecipeController.class)
    @NotBlank
    private String name;

    @Column(name = "description")
    @JsonView(RecipeController.class)
    @NotBlank
    private String description;

    @Column(name = "ingredients")
    @JsonView(RecipeController.class)
    @NotEmpty
    private ArrayList<String> ingredients;

    @Column(name = "directions")
    @JsonView(RecipeController.class)
    @NotEmpty
    private ArrayList<String> directions;

    @Column(name = "date")
    @JsonView(RecipeController.class)
    private LocalDateTime date;

    @Column(name = "category")
    @JsonView(RecipeController.class)
    @NotBlank
    private String category;

    @ManyToOne
    @JoinColumn(name = "user_email")
    @JsonIgnore
    User user;

}

