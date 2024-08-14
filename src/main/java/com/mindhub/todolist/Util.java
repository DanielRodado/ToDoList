package com.mindhub.todolist;

import com.mindhub.todolist.enums.TaskStatus;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.repositories.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Util {
    
    @Bean
    public CommandLineRunner initData(UserEntityRepository userEntityRepository, TaskRepository taskRepository) {
        return args -> {

            UserEntity userEntity = new UserEntity("melba51", "melbamorel@gmail.com", "12345");
            userEntityRepository.save(userEntity);

            Task task = new Task("Organizar el armario","Clasificar y doblar la ropa.", TaskStatus.PENDING);
            userEntity.addTask(task);
            taskRepository.save(task);
            
        };
    }
    
}
