package org.example.service;

import org.example.dto.UserDto;
import org.example.repository.UserRepository;
import org.example.model.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String username) {
        userRepository.save(new UserEntity(username));
    }

    public void updateUser(UserEntity userEntity) {
        userRepository.findById(userEntity.getId()).ifPresentOrElse(
                userEntityOptional -> userRepository.save(userEntity),
                () -> {
                    throw new NoSuchElementException("Не найдена запись User c id = " + userEntity.getId());
                }
        );
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(
                userEntityOptional -> userRepository.deleteById(id),
                () -> {
                    throw new NoSuchElementException("Не найдена запись User c id = " + id);
                }
        );
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new NoSuchElementException("Не найдена запись User c id = " + id));
    }

    public List<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private UserDto toDto(UserEntity userEntity) {
        return new UserDto(userEntity.getId(), userEntity.getUsername());
    }
}
