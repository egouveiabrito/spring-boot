package com.api.usersapi.services;

import com.api.usersapi.models.UserModel;
import com.api.usersapi.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel Save(UserModel userModel) {
        return userRepository.save(userModel);
    }
    public Page<UserModel> FindAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<UserModel> FindById(long id) {
        var users = userRepository.findAll();
        return  users.stream().findFirst();
    }
}
