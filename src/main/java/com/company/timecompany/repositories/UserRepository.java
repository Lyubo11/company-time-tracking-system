package com.company.timecompany.repositories;

import com.company.timecompany.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
