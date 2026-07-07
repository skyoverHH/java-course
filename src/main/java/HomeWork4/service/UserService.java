package HomeWork4.service;

import HomeWork4.model.User;
import HomeWork4.dao.UserDao;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(String username) {
        userDao.create(username);
    }

    public void updateUser(User user) {
        userDao.findById(user.id()).ifPresentOrElse(
                userOptional -> userDao.update(user.id(), user.username()),
                () -> {
                    throw new NoSuchElementException("Не найдена запись User c id = " + user.id());
                }
        );
    }

    public void deleteUser(Long id) {
        userDao.findById(id).ifPresentOrElse(
                userOptional -> userDao.deleteById(id),
                () -> {
                    throw new NoSuchElementException("Не найдена запись User c id = " + id);
                }
        );
    }

    public User getUserById(Long id) {
        return userDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Не найдена запись User c id = " + id));
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}