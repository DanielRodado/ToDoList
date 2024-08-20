package com.mindhub.todolist.repositories;

import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Set<Task> findByUserEntity(UserEntity userEntity);

    boolean existsByIdAndUserEntity(Long id, UserEntity userEntity);

}
