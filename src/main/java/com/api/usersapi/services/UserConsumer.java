package com.api.usersapi.services;
import com.api.usersapi.configuration.UserRabbitConfig;
import com.api.usersapi.dto.UserDto;
import com.api.usersapi.models.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;


@Slf4j
@Component
public class UserConsumer
{
    final UserService userService;

    public UserConsumer(UserService userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = UserRabbitConfig.USER_SAVE_QUEUE)
    public void recievedMessage(UserDto userDto)
    {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
        userService.Save(userModel);
    }
}
