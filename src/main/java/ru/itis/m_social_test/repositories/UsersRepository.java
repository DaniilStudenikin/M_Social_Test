package ru.itis.m_social_test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.m_social_test.models.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
