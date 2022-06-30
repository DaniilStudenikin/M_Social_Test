package ru.itis.m_social_test.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.m_social_test.DTO.UserDto;
import ru.itis.m_social_test.exception.UserNotFoundException;
import ru.itis.m_social_test.models.User;
import ru.itis.m_social_test.repositories.UsersRepository;
import ru.itis.m_social_test.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public String signUp(User user) {
        usersRepository.save(user);
        User userFromDb = usersRepository.findUserByEmail(user.getEmail());
        return "Пользователь с email: " + userFromDb.getEmail() + " и username: " + userFromDb.getUsername() + " зарегистрирован! Ваш User-Id = " + userFromDb.getId();
    }

    @Override
    public User getInfoAboutUser(Long userId) {
        return usersRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void updateUser(UserDto user, Long userId) {
        User userFromDb = usersRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userFromDb.setName(user.getName());
        userFromDb.setUsername(user.getUsername());
        usersRepository.save(userFromDb);
    }


    @Override
    public void deleteUser(Long userId) {
        User user = usersRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        usersRepository.delete(user);

    }
}
