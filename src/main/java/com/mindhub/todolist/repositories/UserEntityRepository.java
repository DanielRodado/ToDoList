package com.mindhub.todolist.repositories;

import com.mindhub.todolist.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByIdAndIsAdminTrue(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.isAdmin = true WHERE u.id = :userId")
    void convertUserToAdminById(@Param("userId") Long userId);

}
