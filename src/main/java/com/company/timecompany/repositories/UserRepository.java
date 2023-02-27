package com.company.timecompany.repositories;

import com.company.timecompany.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Long countById(Integer id);

    @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id=?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);
}
