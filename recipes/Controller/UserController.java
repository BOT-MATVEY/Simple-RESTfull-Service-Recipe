package recipes.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.DTO.User;
import recipes.Service.UserServiceImpl;

@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/api/register")
    public String  UserRegister(@Validated @RequestBody User user) {
        String password = user.getPassword();
        userService.UserRegister(user);
        return "Registered";
    }
}
