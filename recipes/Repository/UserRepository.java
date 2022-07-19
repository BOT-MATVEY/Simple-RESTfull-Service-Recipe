package recipes.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.DTO.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    public User findByEmail(String email);
}
