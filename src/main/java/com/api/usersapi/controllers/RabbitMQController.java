package com.api.usersapi.controllers;

import com.api.usersapi.configuration.UserRabbitConfig;
import com.api.usersapi.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public RabbitMQController(AmqpTemplate amqpTemplate) { this.amqpTemplate = amqpTemplate;  }

    @PostMapping
    @Operation(summary = "Salvar usuario pelo consumidor RabbitMQ")
    public ResponseEntity<Object> Save(@RequestBody UserDto userDto){

        amqpTemplate.convertAndSend(UserRabbitConfig.USER_SAVE_EXCHANGE, UserRabbitConfig.USER_SAVE_ROUTING_KEY, userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(true);
    }
}