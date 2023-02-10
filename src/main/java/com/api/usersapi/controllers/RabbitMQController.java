package com.api.usersapi.controllers;

import com.api.usersapi.configuration.RabbitMQConfig;
import com.api.usersapi.dto.UserDto;
import com.api.usersapi.models.UserModel;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public RabbitMQController(AmqpTemplate amqpTemplate) { this.amqpTemplate = amqpTemplate;  }

    @PostMapping
    @Operation(summary = "Salvar usuario pelo consumidor RabbitMQ")
    public ResponseEntity<Object> Save(@RequestBody UserDto userDto){

        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
        amqpTemplate.convertAndSend(RabbitMQConfig.USER_SAVE_EXCHANGE, RabbitMQConfig.USER_SAVE_ROUTING_KEY, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(true);
    }
}