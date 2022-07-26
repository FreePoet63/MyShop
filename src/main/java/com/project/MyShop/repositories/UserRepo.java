package com.project.MyShop.repositories;

import com.project.MyShop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByActivationCode(String code);
}
