package com.company.timecompany.repositories;

import com.company.timecompany.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    Integer countById(Integer id);

    @Query("SELECT u FROM User u WHERE u.username =:username")
    User getUserByUsername(@Param("username") String username);

    @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id=?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    User findByUsername(String username);
}
