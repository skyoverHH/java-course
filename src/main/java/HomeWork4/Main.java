package HomeWork4;

import HomeWork4.model.User;
import HomeWork4.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        UserService userService = context.getBean(UserService.class);

        userService.createUser("First");
        userService.createUser("Second");
        userService.createUser("Number 3");
        System.out.println(userService.getAllUsers());

        userService.updateUser(new User(3L, "Third"));
        System.out.println(userService.getAllUsers());

        userService.deleteUser(2L);
        System.out.println(userService.getAllUsers());

        System.out.println(userService.getUserById(1L));
    }
}