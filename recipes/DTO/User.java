package recipes.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Pattern(regexp = "\\S+@\\S+\\.\\S+")
    @Column(name = "email")
    String email;

    @Pattern(regexp = "\\S{8,}")
    @Column(name = "encoded_password")
    String password;

    @Column(name = "roles")
    String role;

}
