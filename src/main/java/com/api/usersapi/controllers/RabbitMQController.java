package com.api.usersapi.controllers;

import com.api.usersapi.configuration.UsuarioMensagemConfig;
import com.api.usersapi.dto.UserDto;
import com.api.usersapi.models.UserModel;
import com.api.usersapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQController {

    @Autowired
    private AmqpTemplate rabbitTemplate;
    public RabbitMQController() {

    }

    @PostMapping
    @Operation(summary = "Salvar usuario pelo consumidor RabbitMQ")
    public ResponseEntity<Object> Save(@RequestBody UserDto userDto){

        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
        rabbitTemplate.convertAndSend(UsuarioMensagemConfig.NOME_EXCHANGE, UsuarioMensagemConfig.ROUTING_KEY, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(true);
    }

}