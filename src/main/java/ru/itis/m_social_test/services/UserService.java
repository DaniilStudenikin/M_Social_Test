package ru.itis.m_social_test.services;

import ru.itis.m_social_test.DTO.UserDto;
import ru.itis.m_social_test.models.User;

import java.util.Optional;

public interface UserService {
    String signUp(User user);

    Optional<User> getInfoAboutUser(Long userId);

    Optional<User> updateUser(UserDto user, Long userId);

    String deleteUser(Long userId);
}
