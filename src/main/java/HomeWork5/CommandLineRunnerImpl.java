package HomeWork5;

import HomeWork5.service.UserService;
import HomeWork5.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) {
        userService.createUser("First");
        userService.createUser("Second");
        userService.createUser("Number 3");
        System.out.println(userService.getAllUsers());

        userService.updateUser(new UserEntity(3L, "Third"));
        System.out.println(userService.getAllUsers());

        userService.deleteUser(2L);
        System.out.println(userService.getAllUsers());

        System.out.println(userService.getUserById(1L));
    }
}
