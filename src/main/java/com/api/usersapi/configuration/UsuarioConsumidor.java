package com.api.usersapi.configuration;

import com.api.usersapi.models.UserModel;
import com.api.usersapi.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Component
public class UsuarioConsumidor
{
    final UserService userService;

    public UsuarioConsumidor(UserService userService) {
        this.userService = userService;
    }
    @RabbitListener(bindings = @QueueBinding(value = @Queue(UsuarioMensagemConfig.NOME_FILA),
            exchange = @Exchange(name = UsuarioMensagemConfig.NOME_EXCHANGE),
            key = UsuarioMensagemConfig.ROUTING_KEY))

    public void processarMensagem(final Message message, final UserModel userModel) {
        log.info("Mensagem {}", userModel);
        userModel.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
        userService.Save(userModel);
    }
}
