package ru.itis.m_social_test.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.itis.m_social_test.DTO.UserDto;
import ru.itis.m_social_test.models.User;
import ru.itis.m_social_test.repositories.UsersRepository;
import ru.itis.m_social_test.services.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public String signUp(User user) {
        try {
            usersRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return "Пользователь с такими данными уже существует.";
        }
        User userFromDb = usersRepository.findUserByEmail(user.getEmail());
        return "Пользователь с email: " + userFromDb.getEmail() + " и username: " + userFromDb.getUsername() + " зарегистрирован! Ваш User-Id = " + userFromDb.getId();
    }

    @Override
    public Optional<User> getInfoAboutUser(Long userId) {
        return usersRepository.findById(userId);
    }

    @Override
    public Optional<User> updateUser(UserDto user, Long userId) {
        Optional<User> userFromDb = usersRepository.findById(userId);
        if (userFromDb.isPresent()) {
            if (user.getName() != null) {
                userFromDb.get().setName(user.getName());
            }
            if (user.getUsername() != null) {
                userFromDb.get().setUsername(user.getUsername());
            }
            usersRepository.save(userFromDb.get());
            return userFromDb;
        }
        return Optional.empty();
    }


    @Override
    public String deleteUser(Long userId) {
        Optional<User> user = usersRepository.findById(userId);
        if (user.isPresent()) {
            usersRepository.delete(user.get());
            return "Пользователь удален";
        } else return "Пользователя с таким id не существует";
    }
}
