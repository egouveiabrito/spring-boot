package com.api.usersapi.configuration;
import com.api.usersapi.models.UserModel;
import com.api.usersapi.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class UsuarioConsumidor
{
    final UserService userService;

    public UsuarioConsumidor(UserService userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = RabbitMQConfig.USER_SAVE_QUEUE)
    public void recievedMessage(UserModel userModel)
    {
        userService.Save(userModel);
    }
}
