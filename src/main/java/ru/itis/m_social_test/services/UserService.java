package ru.itis.m_social_test.services;

import ru.itis.m_social_test.DTO.UserDto;
import ru.itis.m_social_test.models.User;


public interface UserService {
    String signUp(User user);

    User getInfoAboutUser(Long userId);

    void updateUser(UserDto user, Long userId);

    void deleteUser(Long userId);
}
