package com.api.usersapi.controllers;

import com.api.usersapi.dto.UserDto;
import com.api.usersapi.models.UserModel;
import com.api.usersapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/user")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Salvar usuario")
    public ResponseEntity<Object> Save(UserDto userDto){
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.Save(userModel));
    }

    @GetMapping("/all")
    @Operation(summary = "Buscar usuario por paginação")
    public ResponseEntity<Page<UserModel>> findAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(userService.FindAll(pageable));
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por Id")
    public ResponseEntity<Object> FindById(@PathVariable(value = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.FindById(id));
    }
}